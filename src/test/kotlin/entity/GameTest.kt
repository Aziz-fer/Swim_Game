package entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val c1=Card(CardSuit.HEARTS, CardValue.SEVEN)
val c2=Card(CardSuit.HEARTS, CardValue.EIGHT)
val c3=Card(CardSuit.HEARTS, CardValue.NINE)
val playerCard:MutableList<Card> = arrayListOf(c1,c2,c3)
val p1=Player("PlayerOne", playerCard)
val p2=Player("PlayerTwo", playerCard)
val players = ArrayDeque<Player>()

val c4=Card(CardSuit.HEARTS, CardValue.TEN)
val c5=Card(CardSuit.HEARTS, CardValue.KING)
val testTableCards:MutableList<Card> = arrayListOf(c1,c2,c3)
val openCards=OpenCards(testTableCards)
val unusedCards  = ArrayDeque<Card>()

/**
 * Test for the Swim Game class
 */


class TestSwimGame {
    /**
     * test the player in the swim game
     */
    @Test
    fun testPlayers(){
        players.add(p1)
        players.add(p2)
        val game=Game()
        game.players= players
        assertEquals(game.players, players)
    }

    /**
     * test the table cards in the swim game
     */
    @Test
    fun testMid(){
        val game=Game()
        game.openCards=openCards
        assertEquals(game.openCards,openCards)
    }

    /**
     * test the stack cards in the swim game
     */
    @Test
    fun testCardStack(){
        unusedCards .add(c4)
        unusedCards .add(c5)
        val game=Game()
        game.unusedCards  = unusedCards
        assertEquals(unusedCards ,game.unusedCards )
    }

}