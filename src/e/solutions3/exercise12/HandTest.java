package e.solutions3.exercise12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import e.chapter3.Card;
import e.chapter3.Rank;
import e.chapter3.Suit;

public class HandTest {

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

	/* One example hand per category, from the weakest to the strongest. */
	private static Hand highCard() {
		return hand(c(Rank.ACE, Suit.CLUBS), c(Rank.NINE, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.FIVE, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
	}

	private static Hand onePair() {
		return hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.FIVE, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
	}

	private static Hand twoPair() {
		return hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
	}

	private static Hand threeOfAKind() {
		return hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.KING, Suit.SPADES),
				c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
	}

	private static Hand straight() {
		return hand(c(Rank.FIVE, Suit.CLUBS), c(Rank.SIX, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.EIGHT, Suit.DIAMONDS), c(Rank.NINE, Suit.CLUBS));
	}

	private static Hand flush() {
		return hand(c(Rank.TWO, Suit.HEARTS), c(Rank.FIVE, Suit.HEARTS), c(Rank.NINE, Suit.HEARTS),
				c(Rank.JACK, Suit.HEARTS), c(Rank.KING, Suit.HEARTS));
	}

	private static Hand fullHouse() {
		return hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.KING, Suit.SPADES),
				c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.SEVEN, Suit.CLUBS));
	}

	private static Hand fourOfAKind() {
		return hand(c(Rank.KING, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.KING, Suit.SPADES),
				c(Rank.KING, Suit.DIAMONDS), c(Rank.SEVEN, Suit.CLUBS));
	}

	private static Hand straightFlush() {
		return hand(c(Rank.FIVE, Suit.SPADES), c(Rank.SIX, Suit.SPADES), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.EIGHT, Suit.SPADES), c(Rank.NINE, Suit.SPADES));
	}

	private static Hand aceLowStraight() {
		return hand(c(Rank.ACE, Suit.CLUBS), c(Rank.TWO, Suit.HEARTS), c(Rank.THREE, Suit.SPADES),
				c(Rank.FOUR, Suit.DIAMONDS), c(Rank.FIVE, Suit.CLUBS));
	}

	private static Hand aceHighStraight() {
		return hand(c(Rank.TEN, Suit.CLUBS), c(Rank.JACK, Suit.HEARTS), c(Rank.QUEEN, Suit.SPADES),
				c(Rank.KING, Suit.DIAMONDS), c(Rank.ACE, Suit.CLUBS));
	}

	// ---- Categories

	@Test
	void testHighCard() {
		assertEquals(PokerHand.HIGH_CARD, highCard().pokerHand());
	}

	@Test
	void testOnePair() {
		assertEquals(PokerHand.ONE_PAIR, onePair().pokerHand());
	}

	@Test
	void testTwoPair() {
		assertEquals(PokerHand.TWO_PAIR, twoPair().pokerHand());
	}

	@Test
	void testThreeOfAKind() {
		assertEquals(PokerHand.THREE_OF_A_KIND, threeOfAKind().pokerHand());
	}

	@Test
	void testStraight() {
		assertEquals(PokerHand.STRAIGHT, straight().pokerHand());
	}

	@Test
	void testAceLowStraight() {
		assertEquals(PokerHand.STRAIGHT, aceLowStraight().pokerHand());
	}

	@Test
	void testAceHighStraight() {
		assertEquals(PokerHand.STRAIGHT, aceHighStraight().pokerHand());
	}

	@Test
	void testNoWrapAroundStraight() {
		// Q K A 2 3 is not a straight
		Hand hand = hand(c(Rank.QUEEN, Suit.CLUBS), c(Rank.KING, Suit.HEARTS), c(Rank.ACE, Suit.SPADES),
				c(Rank.TWO, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
		assertEquals(PokerHand.HIGH_CARD, hand.pokerHand());
	}

	@Test
	void testFlush() {
		assertEquals(PokerHand.FLUSH, flush().pokerHand());
	}

	@Test
	void testFullHouse() {
		assertEquals(PokerHand.FULL_HOUSE, fullHouse().pokerHand());
	}

	@Test
	void testFourOfAKind() {
		assertEquals(PokerHand.FOUR_OF_A_KIND, fourOfAKind().pokerHand());
	}

	@Test
	void testStraightFlush() {
		assertEquals(PokerHand.STRAIGHT_FLUSH, straightFlush().pokerHand());
	}

	@Test
	void testRoyalFlushIsAStraightFlush() {
		Hand hand = hand(c(Rank.TEN, Suit.HEARTS), c(Rank.JACK, Suit.HEARTS), c(Rank.QUEEN, Suit.HEARTS),
				c(Rank.KING, Suit.HEARTS), c(Rank.ACE, Suit.HEARTS));
		assertEquals(PokerHand.STRAIGHT_FLUSH, hand.pokerHand());
	}

	@Test
	void testAceLowStraightFlush() {
		Hand hand = hand(c(Rank.ACE, Suit.CLUBS), c(Rank.TWO, Suit.CLUBS), c(Rank.THREE, Suit.CLUBS),
				c(Rank.FOUR, Suit.CLUBS), c(Rank.FIVE, Suit.CLUBS));
		assertEquals(PokerHand.STRAIGHT_FLUSH, hand.pokerHand());
	}

	// ---- Comparison across categories

	@Test
	void testEachCategoryBeatsThePreviousOne() {
		Hand[] ascending = { highCard(), onePair(), twoPair(), threeOfAKind(), straight(), flush(), fullHouse(),
				fourOfAKind(), straightFlush() };
		for (int i = 1; i < ascending.length; i++) {
			assertTrue(ascending[i].compareTo(ascending[i - 1]) > 0);
			assertTrue(ascending[i - 1].compareTo(ascending[i]) < 0);
		}
	}

	@Test
	void testLowCardsOfStrongerCategoryBeatHighCardsOfWeakerCategory() {
		Hand lowPair = hand(c(Rank.TWO, Suit.CLUBS), c(Rank.TWO, Suit.HEARTS), c(Rank.THREE, Suit.SPADES),
				c(Rank.FOUR, Suit.DIAMONDS), c(Rank.SIX, Suit.CLUBS));
		assertTrue(lowPair.compareTo(highCard()) > 0);
	}

	// ---- Tie-breaking within a category

	@Test
	void testHighCardTieBreak() {
		Hand better = hand(c(Rank.ACE, Suit.CLUBS), c(Rank.NINE, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.FIVE, Suit.DIAMONDS), c(Rank.FOUR, Suit.CLUBS));
		assertTrue(better.compareTo(highCard()) > 0);
	}

	@Test
	void testPairTieBreakByPairRank() {
		Hand pairOfSevens = hand(c(Rank.SEVEN, Suit.CLUBS), c(Rank.SEVEN, Suit.HEARTS), c(Rank.ACE, Suit.SPADES),
				c(Rank.QUEEN, Suit.DIAMONDS), c(Rank.JACK, Suit.CLUBS));
		assertTrue(onePair().compareTo(pairOfSevens) > 0);
	}

	@Test
	void testPairTieBreakByKicker() {
		Hand betterKicker = hand(c(Rank.KING, Suit.DIAMONDS), c(Rank.KING, Suit.SPADES), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.FIVE, Suit.DIAMONDS), c(Rank.FOUR, Suit.CLUBS));
		assertTrue(betterKicker.compareTo(onePair()) > 0);
	}

	@Test
	void testTwoPairTieBreak() {
		Hand betterSecondPair = hand(c(Rank.KING, Suit.DIAMONDS), c(Rank.KING, Suit.SPADES),
				c(Rank.EIGHT, Suit.SPADES), c(Rank.EIGHT, Suit.DIAMONDS), c(Rank.THREE, Suit.HEARTS));
		assertTrue(betterSecondPair.compareTo(twoPair()) > 0);
	}

	@Test
	void testThreeOfAKindTieBreak() {
		Hand tripleQueens = hand(c(Rank.QUEEN, Suit.CLUBS), c(Rank.QUEEN, Suit.HEARTS), c(Rank.QUEEN, Suit.SPADES),
				c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.THREE, Suit.CLUBS));
		assertTrue(threeOfAKind().compareTo(tripleQueens) > 0);
	}

	@Test
	void testFullHouseTieBreakByTriple() {
		Hand tripleSevens = hand(c(Rank.SEVEN, Suit.CLUBS), c(Rank.SEVEN, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.ACE, Suit.DIAMONDS), c(Rank.ACE, Suit.CLUBS));
		assertTrue(fullHouse().compareTo(tripleSevens) > 0);
	}

	@Test
	void testFlushTieBreak() {
		Hand lowerFlush = hand(c(Rank.TWO, Suit.SPADES), c(Rank.FIVE, Suit.SPADES), c(Rank.NINE, Suit.SPADES),
				c(Rank.JACK, Suit.SPADES), c(Rank.QUEEN, Suit.SPADES));
		assertTrue(flush().compareTo(lowerFlush) > 0);
	}

	@Test
	void testFourOfAKindTieBreak() {
		Hand quadSevens = hand(c(Rank.SEVEN, Suit.CLUBS), c(Rank.SEVEN, Suit.HEARTS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.SEVEN, Suit.DIAMONDS), c(Rank.ACE, Suit.CLUBS));
		assertTrue(fourOfAKind().compareTo(quadSevens) > 0);
	}

	@Test
	void testAceHighStraightBeatsAceLowStraight() {
		assertTrue(aceHighStraight().compareTo(aceLowStraight()) > 0);
	}

	@Test
	void testAceLowStraightIsTheWeakestStraight() {
		// The five is the highest card of A-2-3-4-5, so it loses to 2-3-4-5-6.
		Hand sixHigh = hand(c(Rank.TWO, Suit.CLUBS), c(Rank.THREE, Suit.HEARTS), c(Rank.FOUR, Suit.SPADES),
				c(Rank.FIVE, Suit.DIAMONDS), c(Rank.SIX, Suit.CLUBS));
		assertTrue(sixHigh.compareTo(aceLowStraight()) > 0);
	}

	@Test
	void testStraightFlushTieBreak() {
		Hand lowerStraightFlush = hand(c(Rank.FOUR, Suit.HEARTS), c(Rank.FIVE, Suit.HEARTS), c(Rank.SIX, Suit.HEARTS),
				c(Rank.SEVEN, Suit.HEARTS), c(Rank.EIGHT, Suit.HEARTS));
		assertTrue(straightFlush().compareTo(lowerStraightFlush) > 0);
	}

	// ---- Equality of strength

	@Test
	void testSuitsDoNotBreakTies() {
		Hand otherSuits = hand(c(Rank.ACE, Suit.HEARTS), c(Rank.NINE, Suit.CLUBS), c(Rank.SEVEN, Suit.DIAMONDS),
				c(Rank.FIVE, Suit.SPADES), c(Rank.THREE, Suit.HEARTS));
		assertEquals(0, highCard().compareTo(otherSuits));
		assertEquals(0, otherSuits.compareTo(highCard()));
	}

	@Test
	void testCardOrderInHandDoesNotMatter() {
		Hand reversed = hand(c(Rank.THREE, Suit.CLUBS), c(Rank.FIVE, Suit.DIAMONDS), c(Rank.SEVEN, Suit.SPADES),
				c(Rank.NINE, Suit.HEARTS), c(Rank.ACE, Suit.CLUBS));
		assertEquals(0, highCard().compareTo(reversed));
	}

	// ---- Hands that cannot be evaluated

	@Test
	void testIsPokerHandWithFiveCards() {
		assertTrue(highCard().isPokerHand());
	}

	@Test
	void testIsPokerHandWithFewerThanFiveCards() {
		assertFalse(hand(c(Rank.ACE, Suit.CLUBS), c(Rank.TWO, Suit.CLUBS)).isPokerHand());
		assertFalse(new Hand(5).isPokerHand());
	}

	@Test
	void testIsPokerHandWithMoreThanFiveCards() {
		Hand six = hand(c(Rank.ACE, Suit.CLUBS), c(Rank.TWO, Suit.CLUBS), c(Rank.THREE, Suit.CLUBS),
				c(Rank.FOUR, Suit.CLUBS), c(Rank.FIVE, Suit.CLUBS), c(Rank.SIX, Suit.CLUBS));
		assertFalse(six.isPokerHand());
	}

	@Test
	void testIsPokerHandWithDuplicateCard() {
		Hand duplicate = hand(c(Rank.ACE, Suit.CLUBS), c(Rank.ACE, Suit.CLUBS), c(Rank.THREE, Suit.CLUBS),
				c(Rank.FOUR, Suit.CLUBS), c(Rank.FIVE, Suit.CLUBS));
		assertFalse(duplicate.isPokerHand());
	}
}