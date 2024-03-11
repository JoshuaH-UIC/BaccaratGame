/* ---------------------------------------------
File Name:      BGameTest.java
Author:         Joshua Hontanosas
Description:    Test cases for the BaccaratGame class.
--------------------------------------------- */

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class BGameTest {
	// --- Test cases ---
	
	// Test case: evaluateWinnings_playerWin_BaccaratGame
	// Description: Checks if evaluateWinnings() returns the bet amount.
	@Test
	void evaluateWinnings_playerWin_BaccaratGame() {
		BaccaratGame bg = new BaccaratGame();
		Collections.addAll(bg.playerHand, new Card("Hearts", 2), new Card("Clubs", 6));
		Collections.addAll(bg.bankerHand, new Card("Hearts", 3), new Card("Clubs", 4));
		bg.setBetChoice("Player");
		bg.currentBet = 1000.0;
		double winnings = bg.evaluateWinnings();
		assertEquals(winnings, 1000.0, "evaluateWinnings() should return currentBet.");
	}
	
	// Test case: evaluateWinnings_bankerWin_BaccaratGame
	// Description: Checks if evaluateWinnings() returns the bet amount minus the 5% commission fee.
	@Test
	void evaluateWinnings_bankerWin_BaccaratGame() {
		BaccaratGame bg = new BaccaratGame();
		Collections.addAll(bg.playerHand, new Card("Hearts", 2), new Card("Clubs", 4));
		Collections.addAll(bg.bankerHand, new Card("Hearts", 3), new Card("Clubs", 6));
		bg.setBetChoice("Banker");
		bg.currentBet = 1000.0;
		double winnings = bg.evaluateWinnings();
		assertEquals(winnings, 950.0, "evaluateWinnings() should return 95% of currentBet.");
	}
	
	// Test case: evaluateWinnings_tieWin_BaccaratGame
	// Description: Checks if evaluateWinnings() returns eight times the bet amount.
	@Test
	void evaluateWinnings_tieWin_BaccaratGame() {
		BaccaratGame bg = new BaccaratGame();
		Collections.addAll(bg.playerHand, new Card("Hearts", 5), new Card("Clubs", 4));
		Collections.addAll(bg.bankerHand, new Card("Spades", 5), new Card("Diamonds", 4));
		bg.setBetChoice("Draw");
		bg.currentBet = 1000.0;
		double winnings = bg.evaluateWinnings();
		assertEquals(winnings, 8000.0, "evaluateWinnings() should return eight times of the currentBet.");
	}
	
	// Test case: evaluateWinnings_noWin_BaccaratGame
	// Description: Checks if evaluateWinnings() returns negative the bet amount.
	@Test
	void evaluateWinnings_noWin_BaccaratGame() {
		BaccaratGame bg = new BaccaratGame();
		Collections.addAll(bg.playerHand, new Card("Hearts", 5), new Card("Clubs", 4));
		Collections.addAll(bg.bankerHand, new Card("Spades", 5), new Card("Diamonds", 4));
		bg.setBetChoice("Player");
		bg.currentBet = 1000.0;
		double winnings = bg.evaluateWinnings();
		assertEquals(winnings, -1000.0, "evaluateWinnings() should return negative currentBet.");
	}
}
