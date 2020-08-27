package parser.html.view;



import parser.html.Controller;
import parser.html.vo.Vacancy;

import java.util.List;

/**
 * @author Ivan Kurilov on 26.08.2020
 */
public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
