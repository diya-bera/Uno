import java.util.Random;
import java.util.Scanner;

public class Uno {
    /**
     * nPlayers: Number of players in the game
     * player: Current Player
     * playerHand[][]: List of Players and their hands
     * currentCard: Keeps track of the card on top of the discard pile
     * CARDS_IN_DECK: Total cards in the deck
     * START_HAND: Initial hand size
     * direction: Direction of the play
     * winner: Stores the winner of game
     */
    public static int nPlayers;
    public static int currentPlayer;
    public static UnoCard playerHand[][];
    public static UnoCard currentCard;
    public static final int CARDS_IN_DECK = 112;
    public static final int START_HAND = 7;
    public static boolean direction = true; // true for regular direction, false for reverse direction
    public static int winner;

    /**
     * @param nPlayers
     */
    public Uno(int nPlayers) {
        this.nPlayers = nPlayers;
        this.playerHand = new UnoCard[ nPlayers ][ CARDS_IN_DECK ];
        this.currentPlayer = 0;
    }

    /**
     *
     */
    public static void distributeCards() {
        for (int i = 0; i < nPlayers; i++) {
            for (int j = 0; j < START_HAND; j++) {
                playerHand[ i ][ j ] = new UnoCard();
            }
        }
    }

    /**
     * @param p
     * @param idx
     */
    public static void removeCardFromHand(int p, int idx) {
        int j;
        for (j = idx; j < cardsLeft(p) - 1; j++) {
            playerHand[ p ][ j ] = playerHand[ p ][ j + 1 ];
        }
        playerHand[ p ][ j ] = null;
    }

    /**
     * @param p
     */
    public static void printHandOfPlayer(int p) {
        System.out.println("\nPlayer " + (p + 1) + "'s Hand:");
        for (int i = 0; i < cardsLeft(p); i++) {
            if (playerHand[ p ][ i ].card == CARD.WILD_DRAW4 || playerHand[ p ][ i ].card == CARD.WILD)
                System.out.println(i + 1 + ": " + playerHand[ p ][ i ].card);
            else System.out.println(i + 1 + ": " + playerHand[ p ][ i ].color + " " + playerHand[ p ][ i ].card);
        }
        System.out.printf("0: Draw a card | Skip turn\n");
    }

    /**
     * @param p
     * @return
     */
    public static int cardsLeft(int p) {
        int count = 0;
        while (playerHand[ p ][ count ] != null) count++;
        return count;
    }

    public static void nextPlayer() {
        if (direction == true) {
            if (currentPlayer == nPlayers - 1) currentPlayer = 0;
            else currentPlayer++;
        } else {
            if (currentPlayer == 0) currentPlayer = nPlayers - 1;
            else currentPlayer--;
        }
    }

    /**
     *
     */
    public static void handleWild() {

        System.out.println("Color changes to?");
        System.out.println("1. Red");
        System.out.println("2. Green");
        System.out.println("3. Yellow");
        System.out.println("4. Blue");
        boolean check = true;
        Scanner scanner = new Scanner(System.in);
        do {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    currentCard.color = COLOR.RED;
                    check = false;
                    break;
                case 2:
                    currentCard.color = COLOR.GREEN;
                    check = false;
                    break;
                case 3:
                    currentCard.color = COLOR.YELLOW;
                    check = false;
                    break;
                case 4:
                    currentCard.color = COLOR.BLUE;
                    check = false;
                    break;

            }
        } while (check);

    }

    /**
     *
     */
    public static void handleWildDraw4() {
        handleWild();
        nextPlayer();
        int i = 0;
        int length = cardsLeft(currentPlayer);
        while (i < 4) {
            playerHand[ currentPlayer ][ length + i ] = new UnoCard();
            i++;
        }
        System.out.println("Player " + (currentPlayer + 1) + " has to draw 4 cards!");
        System.out.println("Played " + currentCard.color + " WILD_DRAW4.");
        nextPlayer();
    }

    /**
     *
     */
    public static void handleSkip() {
        nextPlayer();
        System.out.println("Player " + (currentPlayer + 1) + "'s turn has been skipped!");
        nextPlayer();
    }

    /**
     *
     */
    public static void handleReverse() {
        if (direction == true) direction = false;
        else direction = true;
        System.out.println("The game has been reversed!");
    }

    /**
     *
     */
    public static void handleDraw2() {
        nextPlayer();
        int i = 0;
        int length = cardsLeft(currentPlayer);
        while (i < 2) {
            playerHand[ currentPlayer ][ length + i ] = new UnoCard();
            i++;
        }
        System.out.println("Player " + (currentPlayer + 1) + " has to draw 2 cards!");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("How many players are there?");
        nPlayers = scanner.nextInt();
        Uno game = new Uno(nPlayers);
        game.distributeCards();
        System.out.println("Shuffling and Adding Cards to Hands...  ");
        System.out.println("First Card:");
        do {
            game.currentCard = new UnoCard();
            if(game.currentCard.card==CARD.WILD||game.currentCard.card==CARD.WILD_DRAW4||game.currentCard.card==CARD.SKIP||game.currentCard.card==CARD.DRAW_2||game.currentCard.card==CARD.REVERSE){}
            else break;
        }while(true);
        System.out.printf("%s %s\n", game.currentCard.color, game.currentCard.card);
        boolean skip = false;
        boolean winner = false;

        do {
            game.printHandOfPlayer(currentPlayer);
            System.out.println("Which choice?");
            int card = scanner.nextInt();
            UnoCard nextCard;
            if (card == 0) {
                if (!skip) {
                    game.playerHand[ currentPlayer ][ cardsLeft(currentPlayer) ] = new UnoCard();
                    skip = true;
                } else if (skip) {
                    game.nextPlayer();
                    System.out.println("Current Card: ");
                    System.out.println(currentCard.color + " " + currentCard.card);
                    skip = false;
                }
            } else {
                nextCard = playerHand[ currentPlayer ][ card - 1 ];
                if (game.currentCard.isPlayable(nextCard)) {
                    skip = false;
                    if (nextCard.card == CARD.WILD_DRAW4)
                        System.out.println("Played " + nextCard.card + " Card");
                    else if (nextCard.card == CARD.WILD) System.out.println("Played " + nextCard.card );
                    else
                        System.out.println("Played " + nextCard.color + " " + nextCard.card);
                    if (nextCard.isWild()) {
                        game.removeCardFromHand(game.currentPlayer, card - 1);
                        game.currentCard.card = CARD.WILD;
                        game.handleWild();
                    } else if (nextCard.isDraw2()) {
                        game.removeCardFromHand(game.currentPlayer, card - 1);
                        game.currentCard = nextCard;
                        game.handleDraw2();
                    } else if (nextCard.isReverse()) {
                        game.removeCardFromHand(game.currentPlayer, card - 1);
                        game.currentCard = nextCard;
                        game.handleReverse();
                    } else if (nextCard.isSkip()) {
                        game.removeCardFromHand(game.currentPlayer, card - 1);
                        game.currentCard = nextCard;
                        game.handleSkip();
                    } else if (nextCard.isWildDraw4()) {
                        game.removeCardFromHand(game.currentPlayer, card - 1);
                        game.currentCard.card = CARD.WILD_DRAW4;
                        game.handleWildDraw4();
                    } else {
                        game.removeCardFromHand(game.currentPlayer, card - 1);
                        game.currentCard = nextCard;
                    }


                    if (game.cardsLeft(currentPlayer) == 0) {
                        System.out.println("Player " + (currentPlayer + 1) + " wins!");
                        winner = true;
                        break;
                    } else if(nextCard.card!=CARD.WILD_DRAW4&&nextCard.card!=CARD.SKIP){
                        game.nextPlayer();
                    }
                } else {
                    System.out.println("Invalid Choice!");
                    System.out.println("Current Card: ");
                    System.out.println(currentCard.color + " " + currentCard.card);
                }
            }


        } while (!winner);
    }
}
