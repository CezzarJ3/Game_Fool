package ru.vsu.cs.bogdanova.game_fool;

import ru.vsu.cs.bogdanova.game_fool.objects.Card;
import ru.vsu.cs.bogdanova.game_fool.objects.Deck;
import ru.vsu.cs.bogdanova.game_fool.ui.GameUi;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleAppGame implements GameUi {
    public static void main(String[] args) throws IOException {
        Game game = new Game("Avon Hertz", "Cliffford", new ConsoleAppGame());
        game.start();
    }

    @Override
    public int chooseCard(String name) {
        Scanner read = new Scanner(System.in);
        System.out.print(name + ", выберите карту: ");
        return read.nextInt();
    }

    @Override
    public String endRound(String name) {
        Scanner read = new Scanner(System.in);
        System.out.println(name + ", сказать \"Всё\"?\n\tВсё\tНет");
        return read.nextLine();
    }

    @Override
    public String takeCards(String name) {
        Scanner read = new Scanner(System.in);
        System.out.println(name + ", забрать карты?\n\tДа\tНет");
        return read.nextLine();
    }

    @Override
    public void showTrump(Card card) {
        System.out.println("Козырь игры: " + card);
    }

    @Override
    public void showPlayerDeck(String playerName, Deck deck) {
        System.out.println("Карты " + playerName + ": " + deck);
    }

    @Override
    public void showCardsOnPlayingZone(String zoneName, Deck deck) {
        System.out.println("Карты " + zoneName + ": " + deck);
    }

    @Override
    public void showWinner(String playerName) {
        System.out.println("Победителем стал игрок " + playerName + "!");
    }
}
