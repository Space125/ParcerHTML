package parser.html.view;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import parser.html.Controller;
import parser.html.vo.Vacancy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Ivan Kurilov on 26.08.2020
 */
public class HtmlView implements View {
    private Controller controller;

    private final String filePath = "./src/main/java/" +
            this.getClass().getPackage().getName().replaceAll("\\.","/") + "/vacancies.html";


    @Override
    public void update(List<Vacancy> vacancies) {
        updateFile(getUpdatedFileContent(vacancies));
        System.out.println(filePath);
        System.out.println(vacancies.size());
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies){
        Document document = null;

        try {
            document = getDocument();

            if(document != null){
                Element templateOriginal = document.getElementsByClass("template").first();
                Element templateCopy = templateOriginal.clone();
                templateCopy.removeAttr("style");
                templateCopy.removeClass("template");
                document.select("tr[class=vacancy]").remove().not("tr[class=vacancy template]");

                for (Vacancy vacancy : vacancies) {
                    Element localTemplate = templateCopy.clone();
                    localTemplate.getElementsByClass("city").first().text(vacancy.getCity());
                    localTemplate.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                    localTemplate.getElementsByClass("salary").first().text(vacancy.getSalary());
                    Element tagA = localTemplate.getElementsByTag("a").first();
                    tagA.text(vacancy.getTitle());
                    tagA.attr("href", vacancy.getUrl());

                    templateOriginal.before(localTemplate.outerHtml());

                }
            }

        }catch (IOException e){
            e.printStackTrace();
            return "Some exception occurred";
        }


        return document.html();
    }

    private void updateFile(String fileContent){

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))){
            bufferedWriter.write(fileContent);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected Document getDocument() throws IOException{
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}
