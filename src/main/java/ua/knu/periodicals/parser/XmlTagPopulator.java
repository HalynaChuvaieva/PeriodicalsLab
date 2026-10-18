package ua.knu.periodicals.parser;

import ua.knu.periodicals.model.Periodical;
import ua.knu.periodicals.model.PeriodicalType;
//спільний обробник тегів
public final class XmlTagPopulator {
    private XmlTagPopulator() {}

    public static void populateField(Periodical periodical, String tagName, String content) {
        if (periodical == null || content == null || content.isBlank()) return;
        String value = content.trim();

        switch (tagName) {
            case "Title" -> periodical.setTitle(value);
            case "Type" -> periodical.setType(PeriodicalType.valueOf(value));
            case "Monthly" -> periodical.setMonthly(Boolean.parseBoolean(value));
            case "Colored" -> periodical.getChars().setColored(Boolean.parseBoolean(value));
            case "Volume" -> periodical.getChars().setVolume(Integer.parseInt(value));
            case "Glossy" -> periodical.getChars().setGlossy(Boolean.parseBoolean(value));
            case "SubscriptionIndex" -> periodical.getChars().setSubscriptionIndex(value);
            default -> { /* Ігноруємо структурні теги Paper, Periodical, Chars */ }
        }
    }
}