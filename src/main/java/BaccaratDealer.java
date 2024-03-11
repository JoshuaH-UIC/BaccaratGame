/* ---------------------------------------------
File Name:      BaccaratDealer.java
Author:         Joshua Hontanosas
Description:    Contains methods for the card deck.
--------------------------------------------- */

import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
	// --- Members ---
	ArrayList<Card> deck;
	
	// --- Methods ---
	// generateDeck() - Creates a standard 52 card deck, where each card is a Card object.
	public void generateDeck() {
		String suites[] = {"Clubs", "Diamonds", "Hearts", "Spades"};
		ArrayList<Card> tempDeck = new ArrayList<Card>();	// Placeholder for the new deck
		
		if(deck != null) {	// Clear deck before creating a new deck
			deck.clear();
		}	
		for(int i = 0; i < 4; i++) {	// Add cards to deck
			for(int j = 1; j <= 13; j++) {
				tempDeck.add(new Card(suites[i], j));	// Add card
			}
		}
		deck = tempDeck; // Set deck to tempDeck
	}
	
	// dealHand() - Returns an ArrayList<Card> that contains two drawn cards.
	public ArrayList<Card> dealHand(){
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(drawOne());	// Draw two cards for the hand
		hand.add(drawOne());
		return hand;
		//return null;
	}
	
	// drawOne() - Return a copy one card from the deck. Remove the original card from the deck.
	public Card drawOne(){
		if(deck != null && deckSize() != 0) {
			Card c = deck.get(0);	// Store a copy of the card
			deck.remove(0);			// Remove that card from the deck
			return c;				// Return the copy of the card
		}
		return null;
	}
	
	// shuffleDeck() - Randomizes the order of the cards in the deck
	public void shuffleDeck() {
		if(deck != null)				// First check if deck is not null
			Collections.shuffle(deck);	// Shuffle ArrayList<Card> deck
	}
	
	// deckSize() - Returns the amount of cards in the deck. -1 indicates deck has not been initialized.
	public int deckSize(){
		if(deck != null)
			return deck.size();
		else
			return -1;
	}

}
