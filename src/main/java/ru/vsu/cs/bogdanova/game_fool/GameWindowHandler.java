package ru.vsu.cs.bogdanova.game_fool;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ru.vsu.cs.bogdanova.game_fool.objects.Card;
import ru.vsu.cs.bogdanova.game_fool.objects.Deck;
import ru.vsu.cs.bogdanova.game_fool.objects.Player;
import ru.vsu.cs.bogdanova.game_fool.ui.GameUi;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameWindowHandler implements GameUi {
    @FXML
    public TextField chooseCardField;
    @FXML
    public HBox trumpZone;
    @FXML
    private Label firstPlayerName;
    @FXML
    private Label secondPlayerName;
    @FXML
    private HBox firstPlayerCardsBox;
    @FXML
    private HBox secondPlayerCardsBox;
    @FXML
    private HBox topCardsZone;
    @FXML
    private HBox bottomCardsZone;

    private Game game;

    public void startGame(String firstName, String secondName) throws IOException {
        firstPlayerName.setText(firstName);
        secondPlayerName.setText(secondName);
        game = new Game(firstName, secondName, this);
//        game.start();
//        test();
    }

    @Override
    public int chooseCard(String name) {
        updateWindow();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Your bet?");
        dialog.setContentText(name + ", выберите карту: ");

        Optional<String> result = dialog.showAndWait();

        Integer i = result.map(Integer::parseInt).orElseThrow();

        return i;
    }

    @Override
    public String endRound(String name) {
        updateWindow();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Your bet?");
        dialog.setContentText(name + ", сказать всё?: ");

        Optional<String> result = dialog.showAndWait();

        String i = result.toString();

        return i;
    }

    @Override
    public String takeCards(String name) {
        updateWindow();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Your bet?");
        dialog.setContentText(name + ", взять карты?: ");

        Optional<String> result = dialog.showAndWait();

        String i = result.toString();

        return i;
    }

    @Override
    public void showTrump(Card card) throws IOException {
        File filename = new File(String.valueOf(getClass().getResource("images/" + card.getSuit() + " " + card.getValue() + ".bmp")));
        Image image = new Image(filename.getPath());
        ImageView imageView = new ImageView(image);
        trumpZone.getChildren().add(imageView);
    }

    @Override
    public void showPlayerDeck(String playerName, Deck deck) {
//        File filename;
//        Image image;
//        List<ImageView> imageViews = new ArrayList<>();
//        for (Card c : deck) {
//            filename = new File(String.valueOf(getClass().getResource("images/" + c.getSuit() + " " + c.getValue() + ".bmp")));
//            image = new Image(filename.getPath());
//            imageViews.add(new ImageView(image));
//        }
//
////        firstPlayerCardsBox.getChildren().clear();
////        secondPlayerCardsBox.getChildren().clear();
//        if (playerName.equals(firstPlayerName.getText())) {
//            firstPlayerCardsBox.getChildren().setAll(imageViews);
//        } else if (playerName.equals(secondPlayerName.getText())) {
//            secondPlayerCardsBox.getChildren().setAll(imageViews);
//        }
        updateWindow();
    }

    @Override
    public void showCardsOnPlayingZone(String zoneName, Deck deck) {
//        File filename;
//        Image image;
//        List<ImageView> imageViews = new ArrayList<>();
//        for (Card c : deck) {
//            filename = new File(String.valueOf(getClass().getResource("images/" + c.getSuit() + " " + c.getValue() + ".bmp")));
//            image = new Image(filename.getPath());
//            imageViews.add(new ImageView(image));
//        }
//
////        topCardsZone.getChildren().clear();
////        bottomCardsZone.getChildren().clear();
//        if (zoneName.equals("атаки")) {
//            topCardsZone.getChildren().setAll(imageViews);
//        } else if (zoneName.equals("защиты")) {
//            bottomCardsZone.getChildren().setAll(imageViews);
//        }
        updateWindow();
    }

    @Override
    public void showWinner(String playerName) {

    }

    private void test() {
        List<ImageView> imageViews = new ArrayList<>();
//        File filename = new File(String.valueOf(getClass().getResource("images/1 7.bmp")));
//        Image image = new Image(filename.getPath());
//        ImageView imageView = new ImageView(image);
//        firstPlayerCardsBox.getChildren().add(imageView);

//        File dir = new File(Objects.requireNonNull(getClass().getResource("images")).getPath());
//        File dir = new File("E:/Программы на Java/ООП/Game_Fool/src/main/resources/ru/vsu/cs/bogdanova/game_fool/images");
//        if (dir.isDirectory()) {
//            System.out.println("gfc " + dir.getPath());
//        }
////        System.out.println(String.valueOf(getClass().getResource("images/")));
//
//        File[] files = dir.listFiles();
//        System.out.println(files);
//        if (files != null) {
//            for (File f : files) {
//                String path = "1 10.bmp";
//                imageViews.add(new ImageView(f.getPath()));
//                System.out.println(f.getPath());
//            }
//        }

        int j = 1;
        for (int i = 6; i < 10; i++) {
            ImageView imageView1 = new ImageView(new Image(String.valueOf(new File(String.valueOf(getClass().getResource("images/" + j + " " + i + ".bmp"))))));
            imageViews.add(imageView1);
        }

        firstPlayerCardsBox.getChildren().addAll(imageViews);
    }

    @FXML
    public void startGameButtonHandler(MouseEvent mouseEvent) throws IOException {
        game.start();
        updateWindow();
    }

    private void updateWindow() {
        Player[] players = game.getPlayers();
        for (Player p : players) {
            File filename;
            Image image;
            List<ImageView> imageViews = new ArrayList<>();
            if (p.getDeck() != null) {
                for (Card c : p.getDeck()) {
                    filename = new File(String.valueOf(getClass().getResource("images/" + c.getSuit() + " " + c.getValue() + ".bmp")));
                    image = new Image(filename.getPath());
                    imageViews.add(new ImageView(image));
                }

                if (p.getName().equals(firstPlayerName.getText())) {
                    firstPlayerCardsBox.getChildren().setAll(imageViews);
                } else if (p.getName().equals(secondPlayerName.getText())) {
                    secondPlayerCardsBox.getChildren().setAll(imageViews);
                }
            }
        }

        Deck attackZone = game.getAttackZone();
        Deck defenseZone = game.getDefenseZone();

        File filename;
        Image image;
        List<ImageView> imageViews = new ArrayList<>();
        for (Card c : attackZone) {
            filename = new File(String.valueOf(getClass().getResource("images/" + c.getSuit() + " " + c.getValue() + ".bmp")));
            image = new Image(filename.getPath());
            imageViews.add(new ImageView(image));
        }

        if (players[0].getName().equals(firstPlayerName.getText())) {
            topCardsZone.getChildren().setAll(imageViews);
        } else if (players[0].getName().equals(secondPlayerName.getText())) {
            bottomCardsZone.getChildren().setAll(imageViews);
        }

        imageViews.clear();
        for (Card c : defenseZone) {
            filename = new File(String.valueOf(getClass().getResource("images/" + c.getSuit() + " " + c.getValue() + ".bmp")));
            image = new Image(filename.getPath());
            imageViews.add(new ImageView(image));
        }

        if (players[1].getName().equals(firstPlayerName.getText())) {
            topCardsZone.getChildren().setAll(imageViews);
        } else if (players[1].getName().equals(secondPlayerName.getText())) {
            bottomCardsZone.getChildren().setAll(imageViews);
        }

    }

}
