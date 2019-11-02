package mk.ukim.finki.av1;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

enum TYPE {
    HEARTS,
    DIAMONDS,
    SPADES,
    CLUBS
}

class PlayingCard {
    private TYPE type;
    private int rank;

    public PlayingCard(TYPE type, int rank) {
        this.type = type;
        this.rank = rank;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "PlayingCard{" +
                "type=" + type +
                ", rank=" + rank +
                '}';
    }
}

class Deck {

    private PlayingCard[] playingCards;
    private boolean[] picked;

    Deck() {
        playingCards = new PlayingCard[52];
        picked = new boolean[52];

        for (int i = 0; i < 52; i++) {
            picked[i] = false;
        }

        for (int i = 0; i < TYPE.values().length; i++) {

            for (int j = 0; j < 13; j++) {
                playingCards[j + (13 * i)] = new PlayingCard(TYPE.values()[i], j + 1);
            }
        }
    }

    public PlayingCard dealCard() {
        Random random = new Random();
        int cardNumber = random.nextInt(52);

        if (IntStream.range(0,52).allMatch(i -> picked[i]==true)) {
            return null;
        }

        if (picked[cardNumber]==true) {
            System.out.println("Card with index :" + cardNumber + "is already picked. Try again");
            return dealCard();
        } else {
            picked[cardNumber] = true;
            System.out.println("Card with index :" + cardNumber + " has been picked");
            return playingCards[cardNumber];
        }

    }
}



public class DecksTest {

    public static void main(String[] args) {
        Deck deck = new Deck();
        PlayingCard card;
        while ((card = deck.dealCard())!=null) {
            System.out.println("Card: " + card.toString() +" is picked");
        }
    }
}
