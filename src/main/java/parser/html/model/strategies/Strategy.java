package parser.html.model.strategies;

import parser.html.vo.Vacancy;

import java.util.List;

/**
 * @author Ivan Kurilov on 27.08.2020
 */
public interface Strategy {
    List<Vacancy> getVacancies(String searchString);
}
