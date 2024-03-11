/* ---------------------------------------------
File Name:      BDealerTest.java
Author:         Joshua Hontanosas
Description:    Test cases for the BaccaratDealer class.
--------------------------------------------- */

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class BDealerTest {
	// --- Test cases ---
	
	// Test Case: DefaultContructor_BaccaratDealer
	// Description: Deck should be set to null when creating a BaccaratDeal object.
	@Test
	void defaultContructor_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		assertEquals(null, bd.deck, "deck should be null");
	}
	
	// Test Case: generateDeck_OneCall_BaccaratDealer
	// Description: generateDeck should create a standard 52-card deck.
	@Test
	void generateDeck_OneCall_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		assertNotEquals(null, bd.deck, "The deck should not be null");
		assertEquals(52, bd.deck.size(), "The deck should have a size of 52");
	}
	
	// Test Case: generateDeck_TwoCalls_BaccaratDealer
	// Description: deck should still have a standard 52-card deck when generateDeck() is called twice.
	@Test
	void generateDeck_TwoCalls_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		bd.generateDeck();
		assertNotEquals(null, bd.deck, "The deck should not be null");
		if(bd.deck.size() > 52) {
			fail("Deck is larger than 52 cards. Deck should reset when calling generateDeck() on a non-empty deck.");
		}
		assertEquals(52, bd.deck.size(), "The deck should have a size of 52");
	}
	
	// Test Case: dealHand_givesTwoCards_BaccaratDealer
	// Description: dealHand should give two unique cards from deck.
	@Test
	void dealHand_givesTwoCards_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		ArrayList<Card> c = bd.dealHand();
		
		assertEquals(2, c.size(), "Hand should have 2 cards.");
		assertEquals(50, bd.deck.size(), "Deck should have 50 cards aftering dealing a hand.");
		
		Card c1 = c.get(0);
		Card c2 = c.get(1);
		
		if(c1.suite == c2.suite && c1.value == c2.value)
			fail("Both card in the hand are the same. They should both be different.");
	}
	
	// Test Case: dealHand_multipleCalls_BaccaratDealer
	// Description: dealHand should continue to give valid hands after multiple calls.
	@Test
	void dealHand_multipleCalls_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		ArrayList<Card> c1 = bd.dealHand();
		ArrayList<Card> c2 = bd.dealHand();
		ArrayList<Card> c3 = bd.dealHand();
		
		assertEquals(46, bd.deck.size(), "Deck should have 46 cards after dealing 3 hands.");
		if(c1.get(0).suite == c1.get(1).suite && c1.get(0).value == c1.get(1).value)
			fail("Both card in a hand are the same. They should both be different.");
		if(c2.get(0).suite == c2.get(1).suite && c2.get(0).value == c2.get(1).value)
			fail("Both card in a hand are the same. They should both be different.");
		if(c3.get(0).suite == c3.get(1).suite && c3.get(0).value == c3.get(1).value)
			fail("Both card in a hand are the same. They should both be different.");
	}
	
	// Test Case: drawOne_oneCall_BaccaratDealer
	// Description: drawOne should give a valid card and remove a card from the deck
	@Test
	void drawOne_oneCall_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		Card c = bd.drawOne();
		
		assertEquals(1, c.isValidCard(), "drawOne does not give a valid card (suit or value is incorrect).");
		assertEquals(51, bd.deck.size(), "A card should have been removed from the deck.");
	}
	
	// Test Case: drawOne_onEmptyDeck_BaccaratDealer
	// Description: drawOne should return null if the deck hasn't been formed yet.
	@Test
	void drawOne_onEmptyDeck_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		Card c = bd.drawOne();
		
		assertNull(c, "drawOne should return null on a null deck.");
	}
	
	// Test Case: shuffleDeck_OnFullDeck_BaccaratDealer
	// Description: Shuffling a full deck should return the deck with a different ordering.
	@Test
	void shuffleDeck_OnFullDeck_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		BaccaratDealer bd2 = new BaccaratDealer();	// bd2 will store the original copy of bd's deck.
		bd.generateDeck();
		bd2.generateDeck();
		Collections.copy(bd2.deck, bd.deck);	// Copy bd's deck to bd2.
		bd.shuffleDeck();	// Shuffle deck
		
		assertNotEquals(bd2.deck, bd.deck, "The decks should have different orderings.");
		assertEquals(52, bd.deckSize(), "Deck should still have a size of 52 after shuffle.");
	}
	
	// Test Case: shuffleDeck_EmptyDeck_BaccaratDealer
	// Description: Shuffling on an empty deck should not throw an exception.
	@Test
	void shuffleDeck_EmptyDeck_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		try {
			bd.shuffleDeck();
		}
		catch(UnsupportedOperationException e){
			fail("shuffleDeck() should be able to run with null decks.");
		}
	}
	
	// Test Case: deckSize_UninitializedDeck_BaccaratDealer
	// Description: deckSize should return -1 to indicate an uninitialized deck.
	@Test
	void deckSize_UninitializedDeck_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		assertEquals(-1, bd.deckSize(), "deckSize() should return -1 for a null deck.");
	}
	
	// Test Case: deckSize_FullDeck_BaccaratDealer
	// Description: deckSize should return 52 for a newly generated deck.
	@Test
	void deckSize_FullDeck_BaccaratDealer() {
		BaccaratDealer bd = new BaccaratDealer();
		bd.generateDeck();
		assertEquals(52, bd.deckSize(), "deckSize() should return 52 after calling generateDeck() on a new BaccaratDealer object.");
	}
}
