package ua.knu.periodicals.model;

import java.util.Objects;

public class Chars {
    private boolean colored;
    private int volume;
    private boolean glossy;
    private String subscriptionIndex; // null для буклетів

    public Chars() {}

    public Chars(boolean colored, int volume, boolean glossy, String subscriptionIndex) {
        this.colored = colored;
        this.volume = volume;
        this.glossy = glossy;
        this.subscriptionIndex = subscriptionIndex;
    }

    public boolean isColored() { return colored; }
    public void setColored(boolean colored) { this.colored = colored; }

    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }

    public boolean isGlossy() { return glossy; }
    public void setGlossy(boolean glossy) { this.glossy = glossy; }

    public String getSubscriptionIndex() { return subscriptionIndex; }
    public void setSubscriptionIndex(String subscriptionIndex) { this.subscriptionIndex = subscriptionIndex; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chars chars = (Chars) o;
        return colored == chars.colored && volume == chars.volume &&
                glossy == chars.glossy && Objects.equals(subscriptionIndex, chars.subscriptionIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colored, volume, glossy, subscriptionIndex);
    }

    @Override
    public String toString() {
        return String.format("Chars{colored=%s, volume=%d, glossy=%s, index='%s'}",
                colored, volume, glossy, subscriptionIndex != null ? subscriptionIndex : "N/A");
    }
}