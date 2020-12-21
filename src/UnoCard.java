import java.util.Random;

public class UnoCard {

    public COLOR color;
    public CARD card;

    public UnoCard() {
        Random rand = new Random();
        int colorCard = rand.nextInt(4);
        int cardCard = rand.nextInt(15);
        switch (cardCard) {

            case 0:
                this.card = CARD.ZERO;
                break;
            case 1:
                this.card = CARD.ONE;
                break;
            case 2:
                this.card = CARD.TWO;
                break;
            case 3:
                this.card = CARD.THREE;
                break;
            case 4:
                this.card = CARD.FOUR;
                break;
            case 5:
                card = CARD.FIVE;
                break;
            case 6:
                this.card = CARD.SIX;
                break;
            case 7:
                this.card = CARD.SEVEN;
                break;
            case 8:
                this.card = CARD.EIGHT;
                break;
            case 9:
                this.card = CARD.NINE;
                break;
            case 10:
                this.card = CARD.DRAW_2;
                break;
            case 11:
                this.card = CARD.WILD;
                break;
            case 12:
                this.card = CARD.WILD_DRAW4;
                break;
            case 13:
                this.card = CARD.SKIP;
                break;
            case 14:
                this.card = CARD.REVERSE;
                break;
        }
        switch (colorCard) {
            case 0:
                this.color = COLOR.RED;
                break;
            case 1:
                this.color = COLOR.GREEN;
                break;
            case 2:
                this.color = COLOR.YELLOW;
                break;
            case 3:
                this.color = COLOR.BLUE;
                break;
        }
    }

    public UnoCard(CARD card, COLOR color) {
        this.card = card;
        this.color = color;
    }

    public boolean isWild() {
        if (card == CARD.WILD) return true;
        return false;
    }

    public boolean isWildDraw4() {
        if (card == CARD.WILD_DRAW4) return true;
        return false;
    }

    public boolean isDraw2() {
        if (card == CARD.DRAW_2) return true;
        return false;
    }

    public boolean isReverse() {
        if (card == CARD.REVERSE) return true;
        return false;
    }

    public boolean isSkip() {
        if (card == CARD.SKIP) return true;
        return false;
    }

    public boolean isPlayable(UnoCard nextCard) {
        if (nextCard.card == CARD.WILD || nextCard.card == CARD.WILD_DRAW4 || nextCard.card == card || nextCard.color == color)
            return true;
        return false;
    }

}
