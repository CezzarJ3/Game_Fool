package ru.vsu.cs.bogdanova.game_fool.clientserver;

import ru.vsu.cs.bogdanova.game_fool.objects.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientAppGame {
    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) throws IOException {
        ClientAppGame client = new ClientAppGame("localhost", 9999);
        client.start();
    }

    public ClientAppGame(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        String command;
        while (!socket.isClosed() && (command = in.readLine()) != null) {
            String[] commands = command.split(Command.SEPARATOR);
            System.out.println("From server: " + command);
            if (Command.CARD.getCommand().equals(commands[0])) {
                System.out.print("Выберите карту: ");
                int cardNumber = new Scanner(System.in).nextInt();
                String resp = Command.RESP.getCommand() + Command.SEPARATOR + cardNumber;
                System.out.println("To server: " + resp);
                out.println(resp);
            }
            if (Command.END.getCommand().equals(commands[0])) {
                System.out.println("Сказать Всё?\tВсё\tНет");
                String endMove = new Scanner(System.in).nextLine();
                String resp = Command.RESP.getCommand() + Command.SEPARATOR + endMove;
                System.out.println("To server: " + resp);
                out.println(resp);
            }
            if (Command.TAKE.getCommand().equals(commands[0])) {
                System.out.println("Взять карты?\tДа\tНет");
                String takeCards = new Scanner(System.in).nextLine();
                String resp = Command.RESP.getCommand() + Command.SEPARATOR + takeCards;
                System.out.println("To server: " + resp);
                out.println(resp);
            }
            if (Command.SHOWP.getCommand().equals(commands[0])) {
                System.out.println("Карты игрока");
                String resp = Command.RESP.getCommand() + Command.SEPARATOR;
                System.out.println("To server: " + resp);
                out.println(resp);
            }
            if (Command.SHOWZ.getCommand().equals(commands[0])) {
                System.out.println("Карты зоны");
                String resp = Command.RESP.getCommand() + Command.SEPARATOR;
                System.out.println("To server: " + resp);
                out.println(resp);
            }
            if (Command.FINISH.getCommand().equals(commands[0])) {
                socket.close();
            }
        }
    }
}
