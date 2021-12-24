package ru.vsu.cs.bogdanova.game_fool.objects;

import java.util.HashMap;
import java.util.Map;

public class Card implements Comparable<Card> {
    private static final Map<Integer, String> cardSuit = new HashMap<>() {{
        put(1, "Бубна");
        put(2, "Пика");
        put(3, "Крести");
        put(4, "Черва");
    }};
    private static final Map<Integer, String> cardValue = new HashMap<>() {{
        put(11, "Валет");
        put(12, "Дама");
        put(13, "Король");
        put(14, "Туз");
    }};

    private final Integer suit, value; //масть и достоинство

    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getSuitName() {
        return cardSuit.get(this.getSuit());
    }

    public String getStringValue() {
        if (this.getValue() < 11) {
            return String.valueOf(this.getValue());
        } else {
            return cardValue.get(this.getValue());
        }
    }

    @Override
    public int compareTo(Card o) {
        int result = this.getSuit() - o.getSuit();
        if (result == 0) {
            result = this.getValue() - o.getValue();
        }
        return result;
    }

    @Override
    public String toString() {
        return getSuitName() +
                " " + getStringValue();
    }
}
