package ru.vsu.cs.bogdanova.game_fool.ui;

import ru.vsu.cs.bogdanova.game_fool.objects.Card;
import ru.vsu.cs.bogdanova.game_fool.objects.Deck;

import java.io.IOException;

public interface GameUi {
    int chooseCard(String name);

    String endRound(String name);

    String takeCards(String name);

    void showTrump(Card card) throws IOException;

    void showPlayerDeck(String playerName, Deck deck);

    void showCardsOnPlayingZone(String zoneName, Deck deck);

    void showWinner(String playerName);
}
