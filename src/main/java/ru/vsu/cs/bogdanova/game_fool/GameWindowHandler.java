package ru.vsu.cs.bogdanova.game_fool;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ru.vsu.cs.bogdanova.game_fool.Game;
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
    public Label cardsInDeckLeft;
    @FXML
    public Button buttonStart;
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

    private double[] mouseCoordinates = new double[2];
    private Game game;

    public void startGame(String firstName, String secondName) throws IOException {
        firstPlayerName.setText(firstName);
        secondPlayerName.setText(secondName);
        game = new Game(firstName, secondName, this);
//        test();
    }

    @Override
    public int chooseCard(String name) {

//        if (name.equals(firstPlayerName.getText())) {
//            firstPlayerCardsBox.setStyle("-fx-border-color: #EF6262;");
//            firstPlayerCardsBox.setOnMouseEntered(mouseEvent -> firstPlayerCardsBox.setStyle("-fx-box-shadow: 0 0 10px rgba(0,0,0,0.5);"));
//        } else {
//            secondPlayerCardsBox.setStyle("-fx-border-color: #EF6262;");
//            secondPlayerCardsBox.setOnMouseEntered(mouseEvent -> secondPlayerCardsBox.setStyle("-fx-box-shadow: 0 0 10px rgba(0,0,0,0.5);"));
//        }

        updateWindow();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Номер карты");
        dialog.setContentText(name + ", выберите карту: ");

        Optional<String> result = dialog.showAndWait();

        Integer i = result.map(Integer::parseInt).orElseThrow();

        return i;
    }

    @Override
    public String endRound(String name) {
        updateWindow();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Закончить ход");
        dialog.setContentText(name + ", сказать всё?: ");

        Optional<String> result = dialog.showAndWait();

        String i = result.toString();

        return i;
    }

    @Override
    public String takeCards(String name) {
        updateWindow();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Взять карты");
        dialog.setContentText(name + ", взять карты?: ");

        Optional<String> result = dialog.showAndWait();

        String i = result.toString();

        return i;
    }

    @Override
    public void showTrump(Card card) throws IOException {
        trumpZone.getChildren().clear();
        File filename = new File(String.valueOf(getClass().getResource("images/" + card.getSuit() + " " + card.getValue() + ".bmp")));
        Image image = new Image(filename.getPath());
        ImageView imageView = new ImageView(image);
        trumpZone.getChildren().add(imageView);
    }

    @Override
    public void showPlayerDeck(String playerName, Deck deck) {
        updateWindow();
    }

    @Override
    public void showCardsOnPlayingZone(String zoneName, Deck deck) {
        updateWindow();
    }

    @Override
    public void showWinner(String playerName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Победитель!");
        alert.setContentText("Победителем стал игрок " + playerName);
        alert.showAndWait();
    }

    @FXML
    public void startGameButtonHandler(MouseEvent mouseEvent) throws IOException {
        buttonStart.disabledProperty();
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
        List<ImageView> imageViewsAttack = new ArrayList<>();
        List<ImageView> imageViewsDefense = new ArrayList<>();
        for (Card c : attackZone) {
            filename = new File(String.valueOf(getClass().getResource("images/" + c.getSuit() + " " + c.getValue() + ".bmp")));
            image = new Image(filename.getPath());
            imageViewsAttack.add(new ImageView(image));
        }

        for (Card c : defenseZone) {
            filename = new File(String.valueOf(getClass().getResource("images/" + c.getSuit() + " " + c.getValue() + ".bmp")));
            image = new Image(filename.getPath());
            imageViewsDefense.add(new ImageView(image));
        }

        if (players[0].getName().equals(firstPlayerName.getText())) {
            topCardsZone.getChildren().setAll(imageViewsAttack);
            bottomCardsZone.getChildren().setAll(imageViewsDefense);
        } else if (players[0].getName().equals(secondPlayerName.getText())) {
            bottomCardsZone.getChildren().setAll(imageViewsAttack);
            topCardsZone.getChildren().setAll(imageViewsDefense);
        }

        cardsInDeckLeft.setText("Карт в колоде: " + game.getCardsInDeckLeft());
//        if (players[1].getName().equals(firstPlayerName.getText())) {
//            topCardsZone.getChildren().setAll(imageViews);
//        } else if (players[1].getName().equals(secondPlayerName.getText())) {
//            bottomCardsZone.getChildren().setAll(imageViews);
//        }

    }

    private void resetWindow() {
        firstPlayerCardsBox.getChildren().clear();
        secondPlayerCardsBox.getChildren().clear();
        topCardsZone.getChildren().clear();
        bottomCardsZone.getChildren().clear();
        trumpZone.getChildren().clear();
    }

    @FXML
    public void mouseClickedHandler(MouseEvent mouseEvent) {
        mouseCoordinates[0] = mouseEvent.getX();
        mouseCoordinates[1] = mouseEvent.getY();
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

        System.out.println("dsjsdjd " + firstPlayerCardsBox.getLayoutX());
        System.out.println("children " + firstPlayerCardsBox.getChildren().get(0).getBoundsInParent());


        for (Node imageView : firstPlayerCardsBox.getChildren()) {
            System.out.println("hbox1" + imageView.getLayoutX());
        }
    }

}
