/* ---------------------------------------------
File Name:      CardTest.java
Author:         Joshua Hontanosas
Description:    Test cases for the Card class.
--------------------------------------------- */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CardTest {
	// --- Test cases ---
	
	// Test case: DefaultConstructor_Card
	// Description: Checks if the default constructor of Card assigned its members properly.
	@Test
	void DefaultConstructor_Card() {
		Card c = new Card("Hearts", 1);
		assertEquals("Hearts", c.suite, "The suite isn't assigned properly.");
		assertEquals(1, c.value, "The value isn't assigned properly.");
	}
}