package ru.vsu.cs.bogdanova.game_fool.clientserver;

import ru.vsu.cs.bogdanova.game_fool.Game;
import ru.vsu.cs.bogdanova.game_fool.ui.SocketUi;

import java.io.IOException;
import java.net.Socket;

public class GameSession implements Runnable {
    private final Game game;

    public GameSession(Socket socket) {
        this.game = new Game("Avon Hertz", "Cliffford", new SocketUi(socket));
    }

    @Override
    public void run() {
        try {
            game.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
