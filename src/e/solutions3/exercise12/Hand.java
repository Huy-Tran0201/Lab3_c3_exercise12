package e.solutions3.exercise12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import e.chapter3.Card;
import e.chapter3.Rank;
import e.chapter3.Suit;

class Hand implements Iterable<Card>, Comparable<Hand> {

	/**
	 * The number of cards in a hand that can be evaluated as a poker hand.
	 */
	public static final int POKER_HAND_SIZE = 5;

	/* The value of an Ace when it is high (A K Q J 10), or low (A 2 3 4 5) */
	private static final int ACE_HIGH = 14;
	private static final int ACE_LOW = 1;

	private List<Card> aCards = new ArrayList<>();
	private int aMaxCards;

	/**
	 * Creates a new, empty hand, which can hold a maximum of pMaxCards.
	 *
	 * @param pMaxCards The maximum number of cards allowed in this hand.
	 * @pre pMaxCards > 0;
	 */
	public Hand(int pMaxCards) {
		assert pMaxCards > 0;
		aMaxCards = pMaxCards;
	}

	/**
	 * Add pCard to the hand.
	 *
	 * @param pCard The card to add.
	 * @pre !isFull()
	 * @pre pCard != null;
	 */
	public void add(Card pCard) {
		assert pCard != null;
		assert !isFull();
		aCards.add(pCard);
	}

	/**
	 * @return True if the number of cards in the hand is the maximum number of
	 *         cards allowable, as specified in the constructor.
	 */
	public boolean isFull() {
		return aCards.size() == aMaxCards;
	}

	/**
	 * @return True if there are no cards in this hand.
	 */
	public boolean isEmpty() {
		return aCards.isEmpty();
	}

	/**
	 * Removes pCards if it is in the hand. If it is not in the hand, does nothing.
	 *
	 * @param pCard The card to remove.
	 * @pre pCards != null;
	 */
	public void remove(Card pCard) {
		assert pCard != null;
		aCards.remove(pCard);
	}

	/**
	 * Determines if pCard is in the hand according to the definition of equality
	 * provided by {@link Hand#equals(Object)}.
	 *
	 * @param pCard A card to check for containment.
	 * @return True if pCard is a card in this hand.
	 * @pre pCard != null
	 */
	public boolean contains(Card pCard) {
		assert pCard != null;
		return aCards.contains(pCard);
	}

	public Iterator<Card> iterator() {
		return aCards.iterator();
	}

	/**
	 * @return The number of cards currently in the hand.
	 */
	public int size() {
		return aCards.size();
	}

	/**
	 * Compares this hand with pHand in terms of poker strength: first by category
	 * (e.g., a flush beats a straight), then, for two hands in the same category,
	 * by the values of the cards that matter for that category (e.g., a pair of
	 * kings beats a pair of sevens). Suits never break ties, so two hands that are
	 * equally strong compare as 0. Aces count as high, except in the straight
	 * Ace-2-3-4-5, where the five is the highest card.
	 *
	 * Note that this ordering is not consistent with equals: two different hands can
	 * be equally strong.
	 *
	 * Hands that do not have exactly five cards are not comparable as poker hands,
	 * so having five cards is a precondition. We prefer this to returning an
	 * arbitrary result because a hand with fewer cards cannot be a straight, a
	 * flush or a full house, and a hand with more cards would require choosing
	 * which five cards to play. The caller can test for this with
	 * {@link #isPokerHand()}.
	 *
	 * @param pHand The hand to compare to.
	 * @return A negative number if this hand is weaker than pHand, a positive
	 *         number if it is stronger, and 0 if they are equally strong.
	 * @pre pHand != null;
	 * @pre isPokerHand() && pHand.isPokerHand();
	 */
	@Override
	public int compareTo(Hand pHand) {
		assert pHand != null;
		assert isPokerHand() && pHand.isPokerHand();
		Evaluation mine = evaluate();
		Evaluation theirs = pHand.evaluate();
		int result = mine.type().compareTo(theirs.type());
		if (result != 0) {
			return result;
		}
		// Same category: same number of distinct ranks, so the lists have the same size.
		for (int i = 0; i < mine.tieBreakers().size(); i++) {
			result = Integer.compare(mine.tieBreakers().get(i), theirs.tieBreakers().get(i));
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

	/**
	 * @return True if this hand has exactly five cards, all different, and can
	 *         therefore be evaluated as a poker hand.
	 */
	public boolean isPokerHand() {
		// Cards are compared by rank and suit, not by identity, because Card does not override equals().
		Set<Integer> distinct = new HashSet<>();
		for (Card card : aCards) {
			distinct.add(card.suit().ordinal() * Rank.values().length + card.rank().ordinal());
		}
		return aCards.size() == POKER_HAND_SIZE && distinct.size() == POKER_HAND_SIZE;
	}

	/**
	 * @return The category of this poker hand.
	 * @pre isPokerHand()
	 */
	public PokerHand pokerHand() {
		assert isPokerHand();
		return evaluate().type();
	}

	/*
	 * The category of a hand, plus the card values that decide ties inside that
	 * category, from the most to the least significant.
	 */
	private record Evaluation(PokerHand type, List<Integer> tieBreakers) {
	}

	private Evaluation evaluate() {
		Map<Integer, Integer> counts = new HashMap<>();
		Set<Suit> suits = EnumSet.noneOf(Suit.class);
		for (Card card : aCards) {
			counts.merge(pokerValue(card.rank()), 1, Integer::sum);
			suits.add(card.suit());
		}
		// Values ordered by how many times they appear, then by value, both descending.
		// For example, K K 7 7 3 gives [13, 7, 3], and 5 5 5 9 2 gives [5, 9, 2].
		List<Integer> values = new ArrayList<>(counts.keySet());
		values.sort(Comparator.<Integer>comparingInt(counts::get).thenComparingInt(v -> v).reversed());

		boolean flush = suits.size() == 1;
		boolean straight = false;
		List<Integer> tieBreakers = values;
		if (values.size() == POKER_HAND_SIZE) {
			if (values.get(0) - values.get(4) == POKER_HAND_SIZE - 1) {
				straight = true;
			}
			else if (values.equals(List.of(ACE_HIGH, 5, 4, 3, 2))) {
				// The "wheel": the Ace plays low, and the five is the highest card.
				straight = true;
				tieBreakers = List.of(5, 4, 3, 2, ACE_LOW);
			}
		}

		int highestCount = counts.get(values.get(0));
		PokerHand type;
		if (straight && flush) {
			type = PokerHand.STRAIGHT_FLUSH;
		}
		else if (highestCount == 4) {
			type = PokerHand.FOUR_OF_A_KIND;
		}
		else if (highestCount == 3 && values.size() == 2) {
			type = PokerHand.FULL_HOUSE;
		}
		else if (flush) {
			type = PokerHand.FLUSH;
		}
		else if (straight) {
			type = PokerHand.STRAIGHT;
		}
		else if (highestCount == 3) {
			type = PokerHand.THREE_OF_A_KIND;
		}
		else if (highestCount == 2 && values.size() == 3) {
			type = PokerHand.TWO_PAIR;
		}
		else if (highestCount == 2) {
			type = PokerHand.ONE_PAIR;
		}
		else {
			type = PokerHand.HIGH_CARD;
		}
		return new Evaluation(type, tieBreakers);
	}

	/* In poker, the Ace is normally the highest card. The other ranks keep their order. */
	private static int pokerValue(Rank pRank) {
		return pRank == Rank.ACE ? ACE_HIGH : pRank.ordinal() + 1;
	}

	public static Comparator<Hand> createAscendingComparator() {
		return new Comparator<Hand>() {
			public int compare(Hand pHand1, Hand pHand2) {
				return pHand1.aCards.size() - pHand2.aCards.size();
			}
		};
	}

	public static Comparator<Hand> createDescendingComparator() {
		return new Comparator<Hand>() {
			public int compare(Hand pHand1, Hand pHand2) {
				return Integer.compare(pHand2.aCards.size(), pHand1.aCards.size());
			}
		};
	}

	/**
	 * Creates a comparator that compares hands in terms of ascending number of
	 * cards of rank pRank in the hand.
	 *
	 * @param pRank The rank to test against.
	 * @return A new Comparator instance that can compare by number of cards of the
	 *         specified rank.
	 */
	public static Comparator<Hand> createByRankComparator(Rank pRank) {

		return new Comparator<Hand>() {

			public int compare(Hand pHand1, Hand pHand2) {
				return countCards(pHand1, pRank) - countCards(pHand2, pRank);
			}

			private int countCards(Hand pHand, Rank pRank) {
				int total = 0;
				for (Card card : pHand) {
					if (card.rank() == pRank) {
						total++;
					}
				}
				return total;
			}
		};
	}
}