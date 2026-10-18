package ua.knu.periodicals.parser;

import ua.knu.periodicals.model.Periodical;
import java.io.File;
import java.util.List;

public interface PeriodicalParser {
    List<Periodical> parse(File xmlFile) throws Exception;
}