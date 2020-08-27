package parser.html;

import parser.html.model.Model;
import parser.html.strategies.HHStrategy;
import parser.html.strategies.Provider;
import parser.html.view.HtmlView;

/**
 * @author Ivan Kurilov on 27.08.2020
 */
public class ParserHTML {
    public static void main(String[] args) {
        Provider provider = new Provider(new HHStrategy());


        HtmlView htmlView = new HtmlView();
        Model model = new Model(htmlView, provider);
        Controller controller = new Controller(model);

        htmlView.setController(controller);

        htmlView.userCitySelectEmulationMethod();
    }
}
