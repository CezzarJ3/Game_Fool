package ru.vsu.cs.bogdanova.game_fool.objects;

public enum Command {
    CARD("CHOOSE_CARD"),
    RESP("RESPONSE"),
    END("END_MOVE"),
    TAKE("TAKE"),
    SHOWP("PLAYER_DECK"),
    SHOWZ("ZONE_DECK"),
    TRUMP("GAME_TRUMP"),
    FINISH("FINISH");

    public static final String SEPARATOR = ":";

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
