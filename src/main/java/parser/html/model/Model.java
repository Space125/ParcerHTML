package parser.html.model;



import parser.html.model.strategies.Provider;
import parser.html.view.View;
import parser.html.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Kurilov on 26.08.2020
 */
public class Model {
    private View view;
    private Provider[] providers;

    public Model(View view, Provider... providers) {
        if(view != null && providers != null && providers.length > 0) {
            this.view = view;
            this.providers = providers;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void selectCity(String city){
        List<Vacancy> vacancies = new ArrayList<>();

        for (Provider provider: providers) {
            vacancies.addAll(provider.getJavaVacancies(city));
        }

        view.update(vacancies);
    }

}
