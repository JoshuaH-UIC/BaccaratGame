/* ---------------------------------------------
File Name:      BGameLogicTest.java
Author:         Joshua Hontanosas
Description:    Test cases for the BaccaratGameLogic class.
--------------------------------------------- */

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

public class BGameLogicTest {
	// --- Static Variables ---
	BaccaratGameLogic bd = new BaccaratGameLogic();
	static ArrayList<Card> sampleCards = new ArrayList<Card>();
	
	@BeforeAll
	static void setup() {
		String sampleSuite = "Hearts";	// Suite doesn't matter in terms of adding the score.
		for(int i = 0; i <= 13; i++) {
			sampleCards.add(new Card(sampleSuite, i));
		}
	}
	
	// --- Test cases ---
	// Test case: whoWon_playerWins_BaccaratGameLogic
	// Description: Ensure whoWon() return proper String for a player win.
	@Test
	void whoWon_playerWins_BaccaratGameLogic() {
		ArrayList<Card> pHand = new ArrayList<Card>();
		ArrayList<Card> bHand = new ArrayList<Card>();
		Collections.addAll(pHand, new Card("Hearts", 2), new Card("Clubs", 6));
		Collections.addAll(bHand, new Card("Hearts", 3), new Card("Clubs", 4));
		assertEquals(bd.whoWon(pHand, bHand), "Player", "whoWon() should return \"Player\"");
	}
	
	// Test case: whoWon_bankerWins_BaccaratGameLogic
	// Description: Ensure whoWon() return proper String for a banker win.
	@Test
	void whoWon_bankerWins_BaccaratGameLogic() {
		ArrayList<Card> pHand = new ArrayList<Card>();
		ArrayList<Card> bHand = new ArrayList<Card>();
		Collections.addAll(pHand, new Card("Hearts", 2), new Card("Clubs", 3));
		Collections.addAll(bHand, new Card("Hearts", 3), new Card("Clubs", 5));
		assertEquals(bd.whoWon(pHand, bHand), "Banker", "whoWon() should return \"Banker\"");
	}
	
	// Test case: whoWon_draw_BaccaratGameLogic
	// Description: Ensure whoWon() return proper String for a tie win.
	@Test
	void whoWon_draw_BaccaratGameLogic() {
		ArrayList<Card> pHand = new ArrayList<Card>();
		ArrayList<Card> bHand = new ArrayList<Card>();
		Collections.addAll(pHand, new Card("Hearts", 3), new Card("Clubs", 5));
		Collections.addAll(bHand, new Card("Spades", 3), new Card("Diamonds", 5));
		assertEquals(bd.whoWon(pHand, bHand), "Draw", "whoWon() should return \"Draw\"");
	}
	
	// Test case: handTotal_nullHand_BaccaratGameLogic
	// Description: Should return -1 if a null hand is passed in the function.
	@Test
	void handTotal_nullHand_BaccaratGameLogic() {
		assertEquals(-1, bd.handTotal(null), "handTotal() should return -1 on a null hand.");
	}
	
	// Test case: handTotal_underTen_BaccaratGameLogic
	// Description: handTotal() should return the appropriate values when the card sums are under 10.
	@Test
	void handTotal_underTen_BaccaratGameLogic() {
		
		for(int i = 1; i <= 9; i++) {
			for(int j = 1; j <= (9-i); j++) {
				int sum = i + j;
				ArrayList<Card> hand = new ArrayList<Card>();
				Collections.addAll(hand, sampleCards.get(i), sampleCards.get(j));
				assertTrue(bd.handTotal(hand) == sum, "A hand with card values " + i + " and " + j + " should result in a score of " + sum);
			}
		}
	}
	
	// Test case: handTotal_aboveTen_BaccaratGameLogic
	// Description: handTotal() should return the appropriate values when the card sums are above 10.
	@Test
	void handTotal_aboveTen_BaccaratGameLogic() {
		int num1 = 0;
		int num2 = 0;
		for(int i = 10; i <= 13; i++) {
			num1 = i;
			for(int j = 1; j <= 13; j++) {
				num2 = j;
				if(i >= 10)
					num1 = 0;
				if(j >= 10)
					num2 = 0;
				int sum = (num1 + num2)%10;
				ArrayList<Card> hand = new ArrayList<Card>();
				Collections.addAll(hand, sampleCards.get(i), sampleCards.get(j));
				assertTrue(bd.handTotal(hand) == sum, "A hand with card values " + i + " and " + j + " should result in a score of " + sum);
			}
		}
	}
	
	// Test case: evaluateBankerDraw_totalSevenOver_BaccaratGameLogic
	// Description: Return false when banker's total is equal or over 7.
	@Test
	void evaluateBankerDraw_totalSevenOver_BaccaratGameLogic() {
		// Make hands with totals ranging from 7 to 9
		ArrayList<Card> handTotal7 = new ArrayList<Card>();
		ArrayList<Card> handTotal8 = new ArrayList<Card>();
		ArrayList<Card> handTotal9 = new ArrayList<Card>();
		Collections.addAll(handTotal7, sampleCards.get(1), sampleCards.get(6));
		Collections.addAll(handTotal8, sampleCards.get(3), sampleCards.get(5));
		Collections.addAll(handTotal9, sampleCards.get(2), sampleCards.get(7));
		
		assertFalse(bd.evaluateBankerDraw(handTotal7, null), "Should return false on a banker score of 7.");
		assertFalse(bd.evaluateBankerDraw(handTotal8, null), "Should return false on a banker score of 8.");
		assertFalse(bd.evaluateBankerDraw(handTotal9, null), "Should return false on a banker score of 9.");
	}
	
	// Test case: evaluateBankerDraw_totalTwoUnder_BaccaratGameLogic
	// Description: Return true when banker's total is equal or under 2.
	@Test
	void evaluateBankerDraw_totalTwoUnder_BaccaratGameLogic() {
		// Make hands with totals ranging from 0 to 2
		ArrayList<Card> handTotal0 = new ArrayList<Card>();
		ArrayList<Card> handTotal1 = new ArrayList<Card>();
		ArrayList<Card> handTotal2 = new ArrayList<Card>();
		Collections.addAll(handTotal0, sampleCards.get(10), sampleCards.get(11));
		Collections.addAll(handTotal1, sampleCards.get(1), sampleCards.get(12));
		Collections.addAll(handTotal2, sampleCards.get(2), sampleCards.get(13));
		
		assertTrue(bd.evaluateBankerDraw(handTotal0, null), "Should return true on a banker score of 0.");
		assertTrue(bd.evaluateBankerDraw(handTotal1, null), "Should return true on a banker score of 1.");
		assertTrue(bd.evaluateBankerDraw(handTotal2, null), "Should return true on a banker score of 2.");
	}
	
	// Test case: evaluateBankerDraw_totalThree_BaccaratGameLogic
	// Description: Return false when the player's third card is an 8, or if the player doesn't draw a third card
	@Test
	void evaluateBankerDraw_totalThree_BaccaratGameLogic() {
		// Make hands with totals of 3
		ArrayList<Card> handTotal3_1 = new ArrayList<Card>();	// A score of 3 without having to remove first number
		ArrayList<Card> handTotal3_2 = new ArrayList<Card>();	// A score of 3 after removing first number
		Collections.addAll(handTotal3_1, sampleCards.get(1), sampleCards.get(2));
		Collections.addAll(handTotal3_2, sampleCards.get(10), sampleCards.get(3));
		
		// First ensure handTotal() is calculated properly.
		assertEquals(bd.handTotal(handTotal3_1), 3, "Error: A hand containing an ace and 2 should result in a score of 3.");
		assertEquals(bd.handTotal(handTotal3_2), 3, "Error: A hand containing a 10 and 3 should result in a score of 3.");
		
		// Should return false when player does not pull a third card (When Card parameter is null).
		assertFalse(bd.evaluateBankerDraw(handTotal3_1, null), "Banker should not draw if the player didn't draw a third card.");
		assertFalse(bd.evaluateBankerDraw(handTotal3_2, null), "Banker should not draw if the player didn't draw a third card.");
		
		// Return false when player's third card is an 8. Otherwise, return true.
		for(int i = 1; i <= 13; i++) {
			if(i == 8) {
				assertFalse(bd.evaluateBankerDraw(handTotal3_1, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should not draw.");
				assertFalse(bd.evaluateBankerDraw(handTotal3_2, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should not draw.");
			}
			else {
				assertTrue(bd.evaluateBankerDraw(handTotal3_1, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should draw.");
				assertTrue(bd.evaluateBankerDraw(handTotal3_2, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should draw.");
			}
		}
	}
	
	// Test case: evaluateBankerDraw_totalFour_BaccaratGameLogic
	// Description: Return true when the player's third card is between 2 and 7.
	@Test
	void evaluateBankerDraw_totalFour_BaccaratGameLogic() {
		// Make hands with totals of 4
		ArrayList<Card> handTotal4_1 = new ArrayList<Card>();	// A score of 4 without having to remove first number
		ArrayList<Card> handTotal4_2 = new ArrayList<Card>();	// A score of 4 after removing first number
		Collections.addAll(handTotal4_1, sampleCards.get(1), sampleCards.get(3));
		Collections.addAll(handTotal4_2, sampleCards.get(10), sampleCards.get(4));
		
		// First ensure handTotal() is calculated properly.
		assertEquals(bd.handTotal(handTotal4_1), 4, "Error: A hand containing an ace and 3 should result in a score of 4.");
		assertEquals(bd.handTotal(handTotal4_2), 4, "Error: A hand containing a 10 and 4 should result in a score of 4.");
		
		// Should return false when player does not pull a third card (When Card parameter is null).
		assertFalse(bd.evaluateBankerDraw(handTotal4_1, null), "Banker should not draw if the player didn't draw a third card.");
		assertFalse(bd.evaluateBankerDraw(handTotal4_2, null), "Banker should not draw if the player didn't draw a third card.");
		
		// Return true if the player's third card is between 2 and 7. Otherwise, return false.
		for(int i = 1; i <= 13; i++) {
			if(i < 2 || i > 7) {
				assertFalse(bd.evaluateBankerDraw(handTotal4_1, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should not draw.");
				assertFalse(bd.evaluateBankerDraw(handTotal4_2, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should not draw.");
			}
			else {
				assertTrue(bd.evaluateBankerDraw(handTotal4_1, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should draw.");
				assertTrue(bd.evaluateBankerDraw(handTotal4_2, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should draw.");
			}
		}
	}
	
	// Test case: evaluateBankerDraw_totalFive_BaccaratGameLogic
	// Description: Return true when the player's third card is between 4 and 7.
	@Test
	void evaluateBankerDraw_totalFive_BaccaratGameLogic() {
		// Make hands with totals of 5
		ArrayList<Card> handTotal5_1 = new ArrayList<Card>();	// A score of 5 without having to remove first number
		ArrayList<Card> handTotal5_2 = new ArrayList<Card>();	// A score of 5 after removing first number
		Collections.addAll(handTotal5_1, sampleCards.get(2), sampleCards.get(3));
		Collections.addAll(handTotal5_2, sampleCards.get(10), sampleCards.get(5));
		
		// First ensure handTotal() is calculated properly.
		assertEquals(bd.handTotal(handTotal5_1), 5, "Error: A hand containing a 2 and 3 should result in a score of 5.");
		assertEquals(bd.handTotal(handTotal5_2), 5, "Error: A hand containing a 10 and 5 should result in a score of 5.");
		
		// Should return false when player does not pull a third card (When Card parameter is null).
		assertFalse(bd.evaluateBankerDraw(handTotal5_1, null), "Banker should not draw if the player didn't draw a third card.");
		assertFalse(bd.evaluateBankerDraw(handTotal5_2, null), "Banker should not draw if the player didn't draw a third card.");
		
		// Return true if the player's third card is between 4 and 7. Otherwise, return false.
		for(int i = 1; i <= 13; i++) {
			if(i < 4 || i > 7) {
				assertFalse(bd.evaluateBankerDraw(handTotal5_1, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should not draw.");
				assertFalse(bd.evaluateBankerDraw(handTotal5_2, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should not draw.");
			}
			else {
				assertTrue(bd.evaluateBankerDraw(handTotal5_1, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should draw.");
				assertTrue(bd.evaluateBankerDraw(handTotal5_2, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should draw.");
			}
		}
	}
	
	// Test case: evaluateBankerDraw_totalSix_BaccaratGameLogic
	// Description: Return true if the player's third card is 6 or 7.
	@Test
	void evaluateBankerDraw_totalSix_BaccaratGameLogic() {
		// Make hands with totals of 6
		ArrayList<Card> handTotal6_1 = new ArrayList<Card>();	// A score of 6 without having to remove first number
		ArrayList<Card> handTotal6_2 = new ArrayList<Card>();	// A score of 6 after removing first number
		Collections.addAll(handTotal6_1, sampleCards.get(2), sampleCards.get(4));
		Collections.addAll(handTotal6_2, sampleCards.get(10), sampleCards.get(6));
		
		// First ensure handTotal() is calculated properly.
		assertEquals(bd.handTotal(handTotal6_1), 6, "Error: A hand containing a 2 and 4 should result in a score of 6.");
		assertEquals(bd.handTotal(handTotal6_2), 6, "Error: A hand containing a 10 and 6 should result in a score of 6.");
		
		// Should return false when player does not pull a third card (When Card parameter is null).
		assertFalse(bd.evaluateBankerDraw(handTotal6_1, null), "Banker should not draw if the player didn't draw a third card.");
		assertFalse(bd.evaluateBankerDraw(handTotal6_2, null), "Banker should not draw if the player didn't draw a third card.");
		
		// Return true if the player's third card is 6 and 7. Otherwise, return false.
		for(int i = 1; i <= 13; i++) {
			if(i == 6 || i == 7) {
				assertTrue(bd.evaluateBankerDraw(handTotal6_1, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should draw.");
				assertTrue(bd.evaluateBankerDraw(handTotal6_2, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should draw.");
			}
			else {
				assertFalse(bd.evaluateBankerDraw(handTotal6_1, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should not draw.");
				assertFalse(bd.evaluateBankerDraw(handTotal6_2, sampleCards.get(i)), "If the player's third card value is " + i + ", banker should not draw.");
			}
		}
	}
	
	// Test case: evaluatePlayerDraw_totalSixOver_BaccaratGameLogic
	// Description: Return false if the player's first two cards total 6 or more.
	@Test
	void evaluatePlayerDraw_totalSixOver_BaccaratGameLogic() {
		int firstCards[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		int secondCards[] = {5,5,5,5,1,1,1,1,10,7,8,9,7};
		int i = 0;
		int j = 0;
		
		for(int k = 0; k < 13; k++) {
			i = firstCards[k];
			j = secondCards[k];
			ArrayList<Card> playerHand = new ArrayList<Card>();
			Collections.addAll(playerHand, sampleCards.get(i), sampleCards.get(j));
			assertFalse(bd.evaluatePlayerDraw(playerHand), "Player should not draw a third card if their first two cards are a " + i + " and " + j + ".");
		}
	}
	
	// Test case: evaluatePlayerDraw_totalFiveUnder_BaccaratGameLogic
	// Description: Return true if the player's first two cards total 5 or less.
	@Test
	void evaluatePlayerDraw_totalFiveUnder_BaccaratGameLogic() {
		int firstCards[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		int secondCards[] = {4,3,2,1,10,8,7,6,6,4,3,2,1};
		int i = 0;
		int j = 0;
		
		for(int k = 0; k < 13; k++) {
			i = firstCards[k];
			j = secondCards[k];
			ArrayList<Card> playerHand = new ArrayList<Card>();
			Collections.addAll(playerHand, sampleCards.get(i), sampleCards.get(j));
			assertTrue(bd.evaluatePlayerDraw(playerHand), "Player should draw a third card if their first two cards are a " + i + " and " + j + ".");
		}
	}
}
