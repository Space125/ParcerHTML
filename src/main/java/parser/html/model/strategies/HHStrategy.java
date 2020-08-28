package parser.html.model.strategies;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.html.vo.Vacancy;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Kurilov on 25.08.2020
 */
public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> listVacancies = new ArrayList<>();

        Document document;
        int pageNumber = 0;
        try {
            while (true) {
                document = getDocument(searchString, pageNumber);

                if (document == null) {
                    break;
                }

                Elements vacancies = document.select("[data-qa=vacancy-serp__vacancy]");

                if (vacancies.size() == 0) {
                    break;
                }

                for (Element element : vacancies) {
                    if (element != null) {
                        Vacancy vacancy = new Vacancy();

                        Element titleElement = element.select("[data-qa=vacancy-serp__vacancy-title]").first();
                        String title = titleElement.text();

                        Element salaryElement = element.select("[data-qa=vacancy-serp__vacancy-compensation]").first();
                        String salary = "";
                        if (salaryElement != null) {
                            salary = salaryElement.text();
                        }

                        vacancy.setTitle(title);
                        vacancy.setCompanyName(element.select("[data-qa=vacancy-serp__vacancy-employer]").first().text());
                        vacancy.setSalary(salary);
                        vacancy.setSiteName("http://hh.ua");
                        vacancy.setUrl(titleElement.attr("href"));
                        vacancy.setCity(element.select("[data-qa=vacancy-serp__vacancy-address]").first().text());

                        listVacancies.add(vacancy);
                    }
                }
                pageNumber++;
            }
        } catch (IOException ignore) {

        }

        return listVacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, URLEncoder.encode(searchString, "UTF-8"), page);
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36")
                .referrer("http://www.google.com")
                .get();
    }
}
