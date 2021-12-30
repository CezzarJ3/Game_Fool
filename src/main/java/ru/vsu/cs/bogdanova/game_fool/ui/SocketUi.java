package ru.vsu.cs.bogdanova.game_fool.ui;

import ru.vsu.cs.bogdanova.game_fool.objects.Card;
import ru.vsu.cs.bogdanova.game_fool.objects.Command;
import ru.vsu.cs.bogdanova.game_fool.objects.Deck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketUi implements GameUi {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public SocketUi(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot connect to client", ex);
        }
    }

    @Override
    public int chooseCard(String name) {
        String answer = "";
        try {
            String command = Command.CARD.getCommand() + Command.SEPARATOR + "игрок " + name;
            System.out.println("To client: " + command);
            out.println(command);
            while ((answer = in.readLine()) == null) {
            }
            System.out.println("From client: " + answer);
            String[] answerStrings = answer.split(Command.SEPARATOR);
            if (Command.RESP.getCommand().equals(answerStrings[0])) {
                System.out.println("Player's card: " + answerStrings[0]);
            } else {
                throw new IllegalArgumentException("Client response is not recognized: " + answer);
            }
            return Integer.parseInt(answerStrings[1]);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot communicate with a client", e);
        }
    }

    @Override
    public String endRound(String name) {
        String answer = "";
        try {
            String command = Command.END.getCommand() + Command.SEPARATOR + "игрок " + name;
            System.out.println("To client: " + command);
            out.println(command);
            while ((answer = in.readLine()) == null) {
            }
            System.out.println("From client: " + answer);
            String[] answerStrings = answer.split(Command.SEPARATOR);
            if (Command.RESP.getCommand().equals(answerStrings[0])) {
                System.out.println("Player's choice: " + answerStrings[0]);
            } else {
                throw new IllegalArgumentException("Client response is not recognized: " + answer);
            }
            return answerStrings[1];
        } catch (IOException e) {
            throw new IllegalStateException("Cannot communicate with a client", e);
        }
    }

    @Override
    public String takeCards(String name) {
        String answer = "";
        try {
            String command = Command.TAKE.getCommand() + Command.SEPARATOR + "игрок " + name;
            System.out.println("To client: " + command);
            out.println(command);
            while ((answer = in.readLine()) == null) {
            }
            System.out.println("From client: " + answer);
            String[] answerStrings = answer.split(Command.SEPARATOR);
            if (Command.RESP.getCommand().equals(answerStrings[0])) {
                System.out.println("Player's choice: " + answerStrings[0]);
            } else {
                throw new IllegalArgumentException("Client response is not recognized: " + answer);
            }
            return answerStrings[1];
        } catch (IOException e) {
            throw new IllegalStateException("Cannot communicate with a client", e);
        }
    }

    @Override
    public void showTrump(Card card) throws IOException {
        System.out.println("Козырь игры: " + card);
    }

    @Override
    public void showPlayerDeck(String playerName, Deck deck) {
        String answer = deck.toString();
        String command = Command.SHOWP.getCommand() + Command.SEPARATOR + "игрок " + playerName;
        System.out.println("To client: " + command);
        out.println(command);
        System.out.println("From client: " + answer);
        String[] answerStrings = answer.split(Command.SEPARATOR);
        if (Command.RESP.getCommand().equals(answerStrings[0])) {
            System.out.println("Player's deck: " + answerStrings[0]);
        } else {
            throw new IllegalArgumentException("Client response is not recognized: " + answer);
        }
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
