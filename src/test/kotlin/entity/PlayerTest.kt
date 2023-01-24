package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Test for the Player class
 */
class Testplayer {
    private val c1=Card(CardSuit.HEARTS, CardValue.SEVEN)
    private val c2=Card(CardSuit.HEARTS, CardValue.EIGHT)
    private val c3=Card(CardSuit.HEARTS, CardValue.NINE)
    private val playerCard = arrayListOf(c1,c2,c3)


    /**
     * test the name of the player
     */
    @Test
    fun testName(){
        val p1=Player("michael scott",playerCard)
        assertEquals(p1.name,"michael scott")
    }

    /**
     * test the handCard of the player
     */
    @Test
    fun testHandCard(){
        val p1=Player("michael Scott",playerCard)
        assertEquals(p1.handCard,playerCard)
    }
}