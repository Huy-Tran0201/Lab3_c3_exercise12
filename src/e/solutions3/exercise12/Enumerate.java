package e.solutions3.exercise12;

import java.util.EnumMap;
import java.util.Map;

import e.chapter3.Card;
import e.chapter3.Rank;
import e.chapter3.Suit;

public class Enumerate {
	public static void main(String[] args) {
		Card[] all = new Card[52];
		int n = 0;
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				all[n++] = new Card(r, s);
			}
		}
		Map<PokerHand, Integer> counts = new EnumMap<>(PokerHand.class);
		int total = 0;
		for (int a = 0; a < 52; a++)
			for (int b = a + 1; b < 52; b++)
				for (int c = b + 1; c < 52; c++)
					for (int d = c + 1; d < 52; d++)
						for (int e = d + 1; e < 52; e++) {
							Hand h = new Hand(5);
							h.add(all[a]);
							h.add(all[b]);
							h.add(all[c]);
							h.add(all[d]);
							h.add(all[e]);
							counts.merge(h.pokerHand(), 1, Integer::sum);
							total++;
						}
		System.out.println("total=" + total);
		counts.forEach((k, v) -> System.out.println(k + " " + v));
	}
}