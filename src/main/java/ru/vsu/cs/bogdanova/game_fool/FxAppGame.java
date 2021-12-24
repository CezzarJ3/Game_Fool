package ru.vsu.cs.bogdanova.game_fool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxAppGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FxAppGame.class.getResource("start-window.fxml"));
        Parent startWindow = fxmlLoader.load();

        Scene scene = new Scene(startWindow, 320, 200);

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game-window.fxml"));

        StartWindowHandler startWindowHandler = fxmlLoader.getController();
        startWindowHandler.setNextSceneLoader(gameLoader);
        startWindowHandler.setStage(stage);

        stage.setTitle("Start game!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}