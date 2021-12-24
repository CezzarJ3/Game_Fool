package ru.vsu.cs.bogdanova.game_fool.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Deck implements Iterable<Card> {
    private final List<Card> deck;

    public Deck(List<Card> deck) {
        this.deck = deck;
    }

    public Deck() {
        this.deck = initialize();
    }

    //создание колоды по умолчанию
    private List<Card> initialize() {
        List<Card> cards = new ArrayList<>();
        int suits = 4;
        int min_value = 6;
        int max_value = 14;

        for (int i = 1; i <= suits; i++) {
            for (int j = min_value; j <= max_value; j++) {
                cards.add(new Card(i, j));
            }
        }

        Collections.shuffle(cards);
        return cards;
    }

    public void addAll(List<Card> cards) {
        deck.addAll(cards);
    }

    public void add(Card card) {
        deck.add(card);
    }

    //удалить карту из колоды с возвратом удаляемой карты
    public Card remove(int i) {
        return deck.remove(i);
    }

    public void sort() {
        deck.sort(Card::compareTo);
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public int size() {
        return deck.size();
    }

    public Card get(int i) {
        return deck.get(i);
    }

    public void clear() {
        deck.clear();
    }

    public List<Card> giveCards(int n) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            cards.add(deck.remove(0));
        }
        return cards;
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuilder deckString = new StringBuilder();
        for (Card c : this.deck) {
            deckString.append("[").append(i).append("]").append(c.getSuitName()).append(" ").append(c.getStringValue()).append("; ");
            i++;
        }
        return String.valueOf(deckString);
    }

    @Override
    public Iterator<Card> iterator() {
        return deck.listIterator();
    }

}
