/* ---------------------------------------------
File Name:      BaccaratGame.java
Author:         Joshua Hontanosas
Description:    The class where all the game elements of Baccarat are contained.
--------------------------------------------- */

import java.util.ArrayList;

public class BaccaratGame {
	// --- Members (Required for project) ---
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	double currentBet;
	double totalWinnings;
	// --- Members (Additional) ---
	private String betChoice;
	private boolean gotNaturalWin;
	
	// --- Methods (Required for project) ---
	// evaluateWinnings() - Return the amount won or lost based on the current bet.
	public double evaluateWinnings() {
		if(betChoice == "Player" && gameLogic.whoWon(playerHand, bankerHand) == "Player")
			return currentBet;
		else if((betChoice == "Banker" && gameLogic.whoWon(playerHand, bankerHand) == "Banker"))
			return currentBet * 0.95;
		else if(betChoice == "Draw" && gameLogic.whoWon(playerHand, bankerHand) == "Draw")
			return currentBet * 8.0; 
		else
			return currentBet * -1.0;
	}
	// --- Methods (Additional) ---
	// Default Constructor
	BaccaratGame() {
		playerHand = new ArrayList<Card>();
		bankerHand = new ArrayList<Card>();
		theDealer = new BaccaratDealer();
		gameLogic = new BaccaratGameLogic();
		currentBet = 0.0;
		totalWinnings = 0.0;
		betChoice = "";
		gotNaturalWin = false;
	}
	
	// getBetChoice() - Getter for betChoice
	String getBetChoice() {
		return betChoice;
	}
	
	// setBetChoice() - Setter for betChoice
	void setBetChoice(String s) {
		betChoice = s;
	}
	
	// getGotNaturalWin() - Getter for gotNaturalWin
	boolean getGotNaturalWin() {
		return gotNaturalWin;
	}
	
	// setGotNaturalWin() - Setter for gotNaturalWin
	void setGotNaturalWin(boolean b) {
		gotNaturalWin = b;
	}
}
