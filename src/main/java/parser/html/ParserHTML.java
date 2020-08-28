package parser.html;

import parser.html.model.Model;
import parser.html.model.strategies.HHStrategy;
import parser.html.model.strategies.MoikrugStrategy;
import parser.html.model.strategies.Provider;
import parser.html.view.HtmlView;

/**
 * @author Ivan Kurilov on 27.08.2020
 */
public class ParserHTML {
    public static void main(String[] args) {
        HtmlView htmlView = new HtmlView();

        Provider providerHH = new Provider(new HHStrategy());
        Provider providerMoikrug = new Provider(new MoikrugStrategy());


        Model model = new Model(htmlView, providerHH, providerMoikrug);
        Controller controller = new Controller(model);

        htmlView.setController(controller);

        htmlView.userCitySelectEmulationMethod();
    }
}
