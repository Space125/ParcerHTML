package parser.html;


import parser.html.model.Model;

/**
 * @author Ivan Kurilov on 25.08.2020
 */
public class Controller {

    private Model model;

    public Controller(Model model) {
        if(model != null) {
            this.model = model;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void onCitySelect(String cityName){
        model.selectCity(cityName);
    }
}
