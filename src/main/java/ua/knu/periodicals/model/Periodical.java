package ua.knu.periodicals.model;

import java.util.Objects;

public class Periodical {
    private String id;
    private String title;
    private PeriodicalType type;
    private boolean monthly;
    private Chars chars;

    public Periodical() {
        this.chars = new Chars();
    }

    public Periodical(String id, String title, PeriodicalType type, boolean monthly, Chars chars) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.monthly = monthly;
        this.chars = chars;
        validateBusinessRules();
    }

    public void validateBusinessRules() {
        if (type == null || chars == null) return;
        if (type == PeriodicalType.NEWSPAPER && chars.isGlossy()) {
            throw new IllegalArgumentException("Газета не може бути глянцевою!");
        }
        if (type == PeriodicalType.BOOKLET && chars.getSubscriptionIndex() != null) {
            throw new IllegalArgumentException("Буклет не може мати підписного індексу!");
        }
        if ((type == PeriodicalType.NEWSPAPER || type == PeriodicalType.MAGAZINE)
                && (chars.getSubscriptionIndex() == null || chars.getSubscriptionIndex().isBlank())) {
            throw new IllegalArgumentException("Газети та журнали повинні мати підписний індекс!");
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public PeriodicalType getType() { return type; }
    public void setType(PeriodicalType type) { this.type = type; }

    public boolean isMonthly() { return monthly; }
    public void setMonthly(boolean monthly) { this.monthly = monthly; }

    public Chars getChars() { return chars; }
    public void setChars(Chars chars) { this.chars = chars; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodical that = (Periodical) o;
        return monthly == that.monthly && Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) && type == that.type &&
                Objects.equals(chars, that.chars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, monthly, chars);
    }

    @Override
    public String toString() {
        return String.format("Periodical{id='%s', title='%s', type=%s, monthly=%s, %s}",
                id, title, type, monthly, chars);
    }
}