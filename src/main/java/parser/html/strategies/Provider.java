package parser.html.strategies;

import parser.html.vo.Vacancy;

import java.util.List;

/**
 * @author Ivan Kurilov on 27.08.2020
 */
public class Provider {
    private Strategy strategy;

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Vacancy> getJavaVacancies(String searchString){

        return strategy.getVacancies(searchString);
    }
}
