package ru.vsu.cs.bogdanova.game_fool.objects;

import java.util.List;

public class Player {
    private Deck deck;
    private final String name;
    private PlayerState state;

    public Player(String name) {
        this.name = name;
        this.state = PlayerState.NORMAL;
    }

    public void setPlayerDeck(List<Card> deck) {
        this.deck = new Deck(deck);
        arrangeCards();
    }

    public void takeCards(List<Card> cards) {
        deck.addAll(cards);
        arrangeCards();
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public Card giveCard(int i) {
        return deck.remove(i);
    }

    public Card getCard(int i) {
        return deck.get(i);
    }

    private void arrangeCards() {
        deck.sort();
    }

    public int getCardsCount() {
        return deck.size();
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        return "Player "
                + name
                + ". Карты: " + deck;
    }
}
