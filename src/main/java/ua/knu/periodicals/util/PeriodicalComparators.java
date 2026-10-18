package ua.knu.periodicals.util;

import ua.knu.periodicals.model.Periodical;
import java.util.Comparator;
//сортування
public final class PeriodicalComparators {
    private PeriodicalComparators() {}

    public static Comparator<Periodical> byVolume() {
        return Comparator.comparingInt(p -> p.getChars().getVolume());
    }

    public static Comparator<Periodical> byTitle() {
        return Comparator.comparing(Periodical::getTitle);
    }
}