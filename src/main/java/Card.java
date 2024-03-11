/* ---------------------------------------------
File Name:      Card.java
Author:         Joshua Hontanosas
Description:    Defines the object for a standard playing card.
--------------------------------------------- */

public class Card {
	// --- Members ---
	String suite;
	int value;
	
	// --- Constructor ---
	Card(String theSuite, int theValue){
		suite = theSuite;
		value = theValue;
	}
	
	// --- Optional functions ---
	// isValidCard() - Checks if the values for the Card are valid. NOTE: Mainly used for testing
	// Return meanings: 1 is valid card, -1 is invalid value, -2 is invalid suit, -3 is both an invalid value and invalid suit.
	int isValidCard() {
		String suites[] = {"Clubs", "Diamonds", "Hearts", "Spades"};
		int minValue = 1;
		int maxValue = 13;
		
		for(String s: suites) {
			if(suite == s) {
				if(value >= minValue && value <= maxValue)
					return 1;
				else
					return -1;
			}
		}
		
		if(value >= minValue && value <= maxValue)
			return -2;
		else
			return -3;
	}
}