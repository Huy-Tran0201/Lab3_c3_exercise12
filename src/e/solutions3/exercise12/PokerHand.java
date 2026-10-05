package e.solutions3.exercise12;

/**
 * The categories of five-card poker hands, declared from the weakest to the
 * strongest, so that the natural order of the constants is the order of
 * strength. A royal flush is just the highest possible STRAIGHT_FLUSH.
 */
enum PokerHand {
	HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH
}