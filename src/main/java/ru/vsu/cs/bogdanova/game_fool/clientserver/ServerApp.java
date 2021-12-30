package ru.vsu.cs.bogdanova.game_fool.clientserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static final int DEFAULT_PORT = 9999;

    private final ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
            ServerApp server = new ServerApp(port);
            server.start();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot start the server", e);
        }
    }

    public ServerApp(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("Game server started on port: "+serverSocket.getLocalPort());
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from: "+clientSocket.getInetAddress());
            GameSession gameSession = new GameSession(clientSocket);
            Thread t = new Thread(gameSession);
            t.start();
        }
    }

}
