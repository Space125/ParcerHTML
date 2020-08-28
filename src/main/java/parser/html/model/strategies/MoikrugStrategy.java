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
 * @author Ivan Kurilov on 27.08.2020
 */
public class MoikrugStrategy implements Strategy{

    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
    //private static final String URL_FORMAT = "http://javarush.ru/testdata/big28data2.html";

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

                Elements vacancies = document.getElementsByAttributeValue("class", "vacancy-card");


                if (vacancies.size() == 0) {
                    break;
                }

                for (Element element : vacancies) {
                    if (element != null){
                        Vacancy vacancy = new Vacancy();

                        vacancy.setTitle(element.getElementsByAttributeValue("class", "vacancy-card__title").text());
                        vacancy.setSiteName("https://moikrug.ru");
                        vacancy.setUrl("https://moikrug.ru" + element.getElementsByAttributeValue("class", "vacancy-card__icon-link").attr("href"));
                        String salary = element.getElementsByAttributeValue("class", "vacancy-card__salary").text();
                        vacancy.setSalary(salary.length() > 0 ? salary : "");

                        Elements companyNameAndCity = element.getElementsByAttributeValue("class", "vacancy-card__meta")
                                .select("a[class=link-comp link-comp--appearance-dark]")
                                .attr("href", "/companies/");

                        String companyName = element.getElementsByAttributeValue("class", "vacancy-card__meta")
                                .select("a[class=link-comp link-comp--appearance-dark]")
                                .attr("href", "/companies/").first().text();
                        vacancy.setCompanyName(companyName.length() > 0 ? companyName : "");
                        String city = "";
                        if (companyNameAndCity.size() > 1) {
                            city = companyNameAndCity.get(1).text();
                        }

                        vacancy.setCity(city.length() > 0 ? city : "");

                        listVacancies.add(vacancy);
                    }
                }

                pageNumber++;
            }

            } catch(IOException ignore){

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
