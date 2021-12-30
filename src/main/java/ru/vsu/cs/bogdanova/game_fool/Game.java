package ru.vsu.cs.bogdanova.game_fool;

import ru.vsu.cs.bogdanova.game_fool.objects.Card;
import ru.vsu.cs.bogdanova.game_fool.objects.Deck;
import ru.vsu.cs.bogdanova.game_fool.objects.Player;
import ru.vsu.cs.bogdanova.game_fool.objects.PlayerState;
import ru.vsu.cs.bogdanova.game_fool.ui.GameUi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private final GameUi ui;

    private int beatOffCards = 6; //количество отбитых карт
    private Deck deck; //колода карт, используемая во время одного цикла игры
    private Card trump; //козырь
    private int trumpSuit;

    private Player winner;

    private final Player[] players = new Player[2]; //массив с игроками

    private final Deck attackZone = new Deck(new ArrayList<>());
    private final Deck defenseZone = new Deck(new ArrayList<>());
    private final Deck beatZone = new Deck(new ArrayList<>()); //тут хранятся карты, сброшенные для атаки и для защиты, а так же отложенные в отбой

    public Game(String playerName1, String playerName2, GameUi ui) {
        this.deck = new Deck();
        this.ui = ui;

        players[0] = new Player(playerName1);
        players[1] = new Player(playerName2);
    }

    //тут предлагаем запустить игру
    public void start() throws IOException {
        if (deck.isEmpty()) {
            this.deck = new Deck();
        }
        handOutCards(); //в начале игры раздаём игрокам карты
        selectTrump(); //избрать Трампа

        ui.showPlayerDeck(players[0].getName(), players[0].getDeck());
        ui.showPlayerDeck(players[1].getName(), players[1].getDeck());
        ui.showTrump(trump);

        int currentPlayer = setPrimaryPlayersOrder();

        while (!isFinish()) {
            int nextPlayer = (currentPlayer + 1) % 2; //обороняющийся
            players[0].setState(PlayerState.NORMAL);
            players[1].setState(PlayerState.NORMAL);

            while (players[currentPlayer].getState() == PlayerState.NORMAL && players[nextPlayer].getState() == PlayerState.NORMAL) {
                attackingPlayer(players[currentPlayer]);
                if (players[currentPlayer].getState() == PlayerState.NORMAL) {
                    defensingPlayer(players[nextPlayer]);
                }
            }

            givePlayerCards(players[currentPlayer]);
            givePlayerCards(players[nextPlayer]);
            currentPlayer = nextPlayer(nextPlayer);
            clearPlayingZone();
        }

        if (players[0].getCardsCount() == 0) {
            winner = players[0];
        } else {
            winner = players[1];
        }
        ui.showWinner(winner.getName());
    }

    private int nextPlayer(int nextPlayer) {
        if (players[nextPlayer].getState() == PlayerState.TAKING) {
            return (nextPlayer + 1) % 2;
        } else {
            return nextPlayer;
        }
    }

    //добавляем игроку карт, если их стало меньше 6
    private void givePlayerCards(Player player) {
        int c = 6;
        int cardsAmount = player.getCardsCount();
        if (deck.isEmpty()) {
            if (trump != null) {
                player.addCard(trump);
                trump = null;
            }
            return;
        }
        if (cardsAmount < c) {
            if (deck.size() < c - cardsAmount) {
                player.takeCards(deck.giveCards(deck.size()));
            } else {
                player.takeCards(deck.giveCards(c - cardsAmount));
            }
        }
    }

    //раздаём карты игрокам в начале игры
    private void handOutCards() {
        int cardsNumber = 6;
        for (Player player : players) {
            player.setPlayerDeck(deck.giveCards(cardsNumber));
            //  ui.showPlayerDeck(player.getName(), player.getDeck());
        }
    }

    //выбираем козырь для текущей игры игры
    private void selectTrump() {
        trump = deck.remove(ThreadLocalRandom.current().nextInt(deck.size()));
        trumpSuit = trump.getSuit();
    }

    //устанавливаем начальный порядок игроков, у кого козырь больше, тот атакует, другой защищается
    private int setPrimaryPlayersOrder() {
        if (findMaxPlayersTrump(players[0]) > findMaxPlayersTrump(players[1])) {
            return 0;
        } else {
            return 1;
        }
    }

    //определяем наибольший козырь у игрока, если козыря нет, возвращается 0
    private int findMaxPlayersTrump(Player player) {
        int value = 0;
        Card card;
        for (int i = 0; i < player.getCardsCount(); i++) {
            card = player.getCard(i);
            if (card.getSuit() == trump.getSuit() && card.getValue() > trump.getValue()) {
                value = card.getValue();
            }
        }
        return value;
    }

    private void clearPlayingZone() {
        beatZone.addAll(attackZone.giveCards(attackZone.size()));
        beatZone.addAll(defenseZone.giveCards(defenseZone.size()));
        attackZone.clear();
        defenseZone.clear();
    }

    private void attackingPlayer(Player player) {
        int i = -1;
        Card attackCard;

        ui.showPlayerDeck(player.getName(), player.getDeck());

        if (isFirstCardOnCon()) { //если это будет первая карта на кону, тогда просто кладём её в зону атаки
            while (i < 0 || i >= player.getCardsCount()) {
                i = ui.chooseCard(player.getName());
            }
            attackZone.add(player.giveCard(i)); //добавляем карту в зону атаки
            printCardsOnCon();
        } else { // если не первая тогда даём шанс закончить ход
            String s = ui.endRound(player.getName());

            if (s.contains("Всё")) {
                player.setState(PlayerState.END);
            } else {
                while (i < 0 || i >= player.getCardsCount()) {
                    i = ui.chooseCard(player.getName());
                }
                attackCard = player.getCard(i);

                if (isCardOnCon(attackCard)) { //проверяем, есть ли такая карта на кону
                    attackZone.add(attackCard);
                    player.giveCard(i);
                } else {
                    attackingPlayer(player);
                }
                printCardsOnCon();
            }
        }
    }

    private void defensingPlayer(Player player) {
        int i = -1;
        Card defenseCard;
        Card attackCard = attackZone.get(attackZone.size() - 1); //получаем последнюю положенную на кон карту

        ui.showPlayerDeck(player.getName(), player.getDeck());

        String s = ui.takeCards(player.getName());
        if (s.contains("Да")) {
            player.setState(PlayerState.TAKING);
            player.takeCards(attackZone.giveCards(attackZone.size()));
            player.takeCards(defenseZone.giveCards(defenseZone.size()));

        } else {
            while (i < 0 || i >= player.getCardsCount()) {
                i = ui.chooseCard(player.getName());
            }
            defenseCard = player.getCard(i);

            if (canCardBeat(defenseCard, attackCard)) {
                defenseZone.add(defenseCard);
                setBeatOffCards(beatOffCards - 1); //уменьшаем количество отбоев на 1, остаётся на 1 меньше
                player.giveCard(i);
            } else {
                defensingPlayer(player);
            }
            printCardsOnCon();
        }
    }

    //проверяем, не кончилась ли игра
    private boolean isFinish() {
        return (deck.isEmpty() && (players[0].getCardsCount() == 0 || players[1].getCardsCount() == 0));
    }

    private void setBeatOffCards(int i) {
        this.beatOffCards = i;
    }

    private boolean canCardBeat(Card defCard, Card attCard) {
        return (defCard.getSuit() == attCard.getSuit() && defCard.getValue() > attCard.getValue())
                || (defCard.getSuit() == trumpSuit && attCard.getSuit() != trumpSuit);
    }

    //если на кону ещё нет карт
    private boolean isFirstCardOnCon() {
        return attackZone.isEmpty() && defenseZone.isEmpty();
    }

    //есть ли карта с таким же достоинством
    private boolean isCardOnCon(Card card) {
        boolean sameValue = false;
        for (Card c : attackZone) {
            if (card.getValue() == c.getValue()) {
                sameValue = true;
            }
        }

        for (Card c : defenseZone) {
            if (card.getValue() == c.getValue()) {
                sameValue = true;
            }
        }

        return sameValue;
    }

    private void printCardsOnCon() {
        ui.showCardsOnPlayingZone("атаки", attackZone);
        ui.showCardsOnPlayingZone("защиты", defenseZone);
    }

    public Player[] getPlayers() {
        return players;
    }

    public Deck getAttackZone() {
        return attackZone;
    }

    public Deck getDefenseZone() {
        return defenseZone;
    }

    public int getCardsInDeckLeft() {
        return deck.size();
    }
}

