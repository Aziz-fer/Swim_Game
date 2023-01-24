package entity
/**
  * @author Feriani
 * WE will import the assertion classes so we can use their functions
 */
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
/**
 *a class to test the class [Card]
 */

class testcard {

    /**
     * There are the Char Codes for typ Cards(Spades,Heart,Clubs,Diamond)
     * And Testing [Card] with some Cards
     */
    // unicode characters for the suits, as those should be used by []
    private val spadesChar = '\u2660'
    private val heartsChar = '\u2665'
    private val clubsChar = '\u2663'
    private val diamondsChar = '\u2666'

    // Some cards to perform the tests with
    private val aceSpades = Card(CardSuit.SPADES, CardValue.ACE)
    private val jackClubs = Card(CardSuit.CLUBS, CardValue.JACK)
    private val queenDiamonds = Card(CardSuit.DIAMONDS, CardValue.QUEEN)
    private val kingHearts = Card(CardSuit.HEARTS, CardValue.KING)

    /**
     * checking if the Cards we made are equal to the Cards
     * that we have in[CardSuit] & [CardValue]
     */
    @Test
    fun testToString() {
        assertEquals(spadesChar + "A", aceSpades.toString())
        assertEquals(clubsChar + "J", jackClubs.toString())
        assertEquals(heartsChar + "K", kingHearts.toString())
        assertEquals(diamondsChar + "Q", queenDiamonds.toString())
    }

    /**
     * Check if toString produces a 2 character string for every possible card
     * except the 10 (for which length=3 is ensured)
     */
    @Test
    fun testToStringLength() {
        CardSuit.values().forEach { suit ->
            CardValue.values().forEach { value ->
                if (value == CardValue.TEN)
                    assertEquals(3, Card(suit, value).toString().length)
                else
                    assertEquals(2, Card(suit, value).toString().length)
            }
        }
    }


    /**
     * Check with a few examples if the order introduced by [Card.compareTo] allows
     * to directly compare the value of two cards like `card1 > card2`.
     */
    @Test
    fun testCompareTo() {
        assertTrue(jackClubs < queenDiamonds)
        assertFalse(queenDiamonds < jackClubs)
        assertTrue(jackClubs <= jackClubs)
    }
    /**
     * Check if two cards with the same CardSuit/CardValue combination are equal
     * in the sense of the `==` operator, but not the same in the sense of
     * the `===` operator.
     */

    @Test
    fun testEquals() {
        assertEquals(jackClubs, jackClubs)
        assertNotSame(kingHearts, jackClubs)
    }
}
