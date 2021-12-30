package ru.vsu.cs.bogdanova.game_fool;

import ru.vsu.cs.bogdanova.game_fool.ui.ConsoleUi;

import java.io.IOException;

public class ConsoleAppGame {
    public static void main(String[] args) throws IOException {
        Game game = new Game("Avon Hertz", "Cliffford", new ConsoleUi());
        game.start();
    }

}
