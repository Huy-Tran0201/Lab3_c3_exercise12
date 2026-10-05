package e.solutions3.exercise12;

import java.util.ArrayList;
import java.util.List;

import e.chapter3.Card;
import e.chapter3.Deck;
import e.chapter3.Rank;
import e.chapter3.Suit;

public class Exercise12 {

	/* Driver program: one example hand per category, then a random deal. */
	public static void main(String[] args) {
		Hand highCard = hand(c(Rank.ACE, Suit.CLUBS), c(Rank.NINE, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.FIVE, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
		Hand onePair = hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.FIVE, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
		Hand twoPair = hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
		Hand threeKind = hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.KING, Suit.SPADES),
				c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
		Hand aceLowStraight = hand(c(Rank.ACE, Suit.CLUBS), c(Rank.TWO, Suit.HEARTS), c(Rank.THREE, Suit.SPADES),
				c(Rank.FOUR, Suit.DIAMONDS), c(Rank.FIVE, Suit.CLUBS));
		Hand aceHighStraight = hand(c(Rank.TEN, Suit.CLUBS), c(Rank.JACK, Suit.HEARTS), c(Rank.QUEEN, Suit.SPADES),
				c(Rank.KING, Suit.DIAMONDS), c(Rank.ACE, Suit.CLUBS));
		Hand flush = hand(c(Rank.TWO, Suit.HEARTS), c(Rank.FIVE, Suit.HEARTS), c(Rank.NINE, Suit.HEARTS),
				c(Rank.JACK, Suit.HEARTS), c(Rank.KING, Suit.HEARTS));
		Hand fullHouse = hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.KING, Suit.SPADES),
				c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.SEVEN, Suit.CLUBS));
		Hand fourKind = hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.KING, Suit.SPADES),
				c(Rank.KING, Suit.DIAMONDS), c(Rank.SEVEN, Suit.CLUBS));
		Hand straightFlush = hand(c(Rank.FIVE, Suit.SPADES), c(Rank.SIX, Suit.SPADES), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.EIGHT, Suit.SPADES), c(Rank.NINE, Suit.SPADES));

		assert highCard.pokerHand() == PokerHand.HIGH_CARD;
		assert onePair.pokerHand() == PokerHand.ONE_PAIR;
		assert twoPair.pokerHand() == PokerHand.TWO_PAIR;
		assert threeKind.pokerHand() == PokerHand.THREE_OF_A_KIND;
		assert aceLowStraight.pokerHand() == PokerHand.STRAIGHT;
		assert aceHighStraight.pokerHand() == PokerHand.STRAIGHT;
		assert flush.pokerHand() == PokerHand.FLUSH;
		assert fullHouse.pokerHand() == PokerHand.FULL_HOUSE;
		assert fourKind.pokerHand() == PokerHand.FOUR_OF_A_KIND;
		assert straightFlush.pokerHand() == PokerHand.STRAIGHT_FLUSH;

		// Different categories: the stronger category wins.
		assert fullHouse.compareTo(flush) > 0;
		assert flush.compareTo(fullHouse) < 0;
		// Same category: ties are broken by card values (ace-high beats ace-low).
		assert aceHighStraight.compareTo(aceLowStraight) > 0;
		assert threeKind.compareTo(hand(c(Rank.QUEEN, Suit.CLUBS), c(Rank.QUEEN, Suit.HEARTS),
				c(Rank.QUEEN, Suit.SPADES), c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS))) > 0;
		// Suits never break ties.
		assert highCard.compareTo(hand(c(Rank.ACE, Suit.HEARTS), c(Rank.NINE, Suit.CLUBS), c(Rank.SEVEN, Suit.DIAMONDS),
				c(Rank.FIVE, Suit.SPADES), c(Rank.THREE, Suit.HEARTS))) == 0;

		Hand[] examples = { highCard, onePair, twoPair, threeKind, aceLowStraight, aceHighStraight, flush, fullHouse,
				fourKind, straightFlush };
		for (Hand example : examples) {
			System.out.println(example.pokerHand() + " <- " + describe(example));
		}

		Deck deck = new Deck();
		deck.shuffle();
		Hand hand1 = new Hand(Hand.POKER_HAND_SIZE);
		Hand hand2 = new Hand(Hand.POKER_HAND_SIZE);
		for (int i = 0; i < Hand.POKER_HAND_SIZE; i++) {
			hand1.add(deck.draw());
			hand2.add(deck.draw());
		}
		System.out.println("Hand 1: " + hand1.pokerHand() + " <- " + describe(hand1));
		System.out.println("Hand 2: " + hand2.pokerHand() + " <- " + describe(hand2));
		System.out.println("hand1.compareTo(hand2) = " + hand1.compareTo(hand2));
	}

	private static Card c(Rank pRank, Suit pSuit) {
		return new Card(pRank, pSuit);
	}

	private static Hand hand(Card... pCards) {
		Hand hand = new Hand(pCards.length);
		for (Card card : pCards) {
			hand.add(card);
		}
		return hand;
	}

	private static String describe(Hand pHand) {
		List<String> cards = new ArrayList<>();
		for (Card card : pHand) {
			cards.add(card.toString());
		}
		return String.join(", ", cards);
	}
}