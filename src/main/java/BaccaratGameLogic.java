/* ---------------------------------------------
File Name:      BaccaratGameLogic.java
Author:         Joshua Hontanosas
Description:    Does calculations for Baccarat hands.
--------------------------------------------- */

import java.util.ArrayList;

public class BaccaratGameLogic {
	// --- Methods ---
	// whoWon() - Return a string telling who won or if there's a tie.
	public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		int playerHand = handTotal(hand1);
		int bankerHand = handTotal(hand2);
		
		if(playerHand > bankerHand)
			return "Player";
		else if(playerHand < bankerHand)
			return "Banker";
		else
			return "Draw";
	}
	
	// handTotal() - Returns the overall score of the hand based on the rules of Baccarat.
	public int handTotal(ArrayList<Card> hand) {
		// Error Handling: A null hand should return -1.
		if(hand == null)
			return -1;
		
		int score = 0;
		for(Card c : hand) {	// Add up the values of each card (10s and face cards are ignored)
			if(c.value < 10)
				score += c.value;
		}
		
		return score%10;	// Return the score (Remove the first number from the total)
	}
	
	// evaluateBankerDraw() - Return true if the banker should draw a card.
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		int bankerTotal = handTotal(hand);
		// 1. If banker's first two cards total 7 or more, return false.
		if(bankerTotal >= 7)
			return false;
		// 2. If banker's first two cards total 2 or less, return true.
		if(bankerTotal <= 2)
			return true;
		// 3. If the banker's first two card total between 3 and 6, draw based on the player's third drawn card.
		if(playerCard != null) {
			boolean b = true;
			if(bankerTotal == 3) {
				b = (playerCard.value != 8) ? true : false;
			}
			if(bankerTotal == 4) {
				b = (playerCard.value >= 2 && playerCard.value <= 7) ? true : false;
			}
			if(bankerTotal == 5) {
				b = (playerCard.value >= 4 && playerCard.value <= 7) ? true : false;
			}
			if(bankerTotal == 6) {
				b = (playerCard.value == 6 || playerCard.value == 7) ? true : false;
			}
			return b;
		}
		else
			return false;
	}
	
	// evaluatePlayerDraw() - Return true if player's 2-card hand totals 5 or less.
	public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		if(handTotal(hand) >= 6)
			return false;
		else
			return true;
	}
}
