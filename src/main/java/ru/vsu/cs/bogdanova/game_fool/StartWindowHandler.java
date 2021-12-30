package ru.vsu.cs.bogdanova.game_fool;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartWindowHandler {
    @FXML
    private TextField secondPlayerName;
    @FXML
    private TextField firstPlayerName;

    private Stage stage;

    private FXMLLoader nextSceneLoader;

    @FXML
    private void handleStartGameButton(MouseEvent mouseEvent) throws IOException {
//        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game-window.fxml"));
//        Parent gameRoot = gameLoader.load();
//        stage.close();
//
//        GameWindowHandler gameWindowHandler = gameLoader.getController();
//        gameWindowHandler.startGame(firstPlayerName.getText(), secondPlayerName.getText());
//
//        Scene scene = new Scene(gameRoot, 600, 300);
//        Stage gameStage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("Fool game");
//        stage.show();

        Parent gameRoot = nextSceneLoader.load();
        GameWindowHandler gameWindowHandler = nextSceneLoader.getController();

        gameWindowHandler.startGame(firstPlayerName.getText(), secondPlayerName.getText());
        Scene scene = new Scene(gameRoot, 708, 524);
        stage.setScene(scene);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNextSceneLoader(FXMLLoader nextSceneLoader) {
        this.nextSceneLoader = nextSceneLoader;
    }
}