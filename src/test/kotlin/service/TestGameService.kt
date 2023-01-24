package service

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import entity.*

/**
 * Test the [GameService]
 */
class TestGameService {
    private val rootService = RootService()
    private val c1=Card(CardSuit.HEARTS, CardValue.SEVEN)
    private val c2=Card(CardSuit.HEARTS, CardValue.EIGHT)
    private val c3=Card(CardSuit.HEARTS, CardValue.NINE)
    private val playerCard:MutableList<Card> = arrayListOf(c1,c2,c3)
    private val listNameValid= arrayListOf("Mohamed","Aziz","Feriani")
    private val listNameLess= arrayListOf("Mohamed")
    private val listNameMore= arrayListOf("Mohamed","Aziz","Feriani","Wiem","")

    /**
     * test the startGame function
     * first case: not enough names
     * second case: too many names
     * third case: a valid number of names
     */
    @Test
    fun testStartGame(){

        rootService.gameService.startGame(listNameLess)
        assertNull(rootService.currentGame)
        rootService.gameService.startGame(listNameMore)
        assertNull(rootService.currentGame)
        rootService.gameService.startGame(listNameValid)
        assertNotNull(rootService.currentGame)
        assertEquals(3, rootService.currentGame!!.players[0].handCard.size)
        assertEquals(20, rootService.currentGame!!.unusedCards .size)

    }

    /**
     * test the nextPlayer function
     * it should give us the next player to play
     */
    @Test
    fun testNextPlayer(){
        rootService.gameService.startGame(listNameValid)
        val nextPlayer = rootService.currentGame?.players?.let { rootService.gameService.nextPlayer(it.first()) }
        if (nextPlayer != null) {
            assertEquals(nextPlayer.name,"Aziz")
        }
    }

    /**
     * test the calculateScore function
     * first case: 3 cards with same suit then it should give us the sum of all cards points
     * seconds case: 3 cards with same value then it should give us 30.5
     * third case: 3 cards with different suits and different values then it should give us the max cards point
     */
    @Test
    fun testCalculateScore(){
        val c1 = Card(CardSuit.DIAMONDS, CardValue.KING)
        val c2 = Card(CardSuit.CLUBS, CardValue.JACK)
        val c3 = Card(CardSuit.CLUBS, CardValue.SEVEN)
        val s = Player("Sara")

        val handCards = mutableListOf(c1,c2,c3)
        s.handCard=handCards

        val spiel=rootService.gameService.startGame(listNameValid)
        assertNotNull(spiel)

        assertEquals(17.0,rootService.gameService.calculateScore(s))
    }

    /**
     * tests the resetPassCounter
     */
    @Test
    fun testResetPassCounter(){
        rootService.gameService.startGame((listNameValid))
        rootService.currentGame?.players?.let { rootService.playerActionService.pass(it.first()) }
        assertEquals(1,rootService.gameService.passCounter)
        rootService.gameService.resetPassCounter()
        assertEquals(0,rootService.gameService.passCounter)
    }

    /**
     * test the setPassCounter
     */
    @Test
    fun testSetPassCounter(){
        rootService.gameService.startGame((listNameValid))
        rootService.currentGame?.players?.let { rootService.playerActionService.pass(it.first()) }
        rootService.gameService.setPassCounter()
        assertEquals(2,rootService.gameService.passCounter)
    }

    /**
     * test the disableButton
     */
    @Test
    fun testDisableKnockButton(){
        rootService.gameService.startGame((listNameValid))
        rootService.gameService.disableKnockButton()
        assertEquals(true,rootService.gameService.disableKnock)
    }

    /**
     * test the endGame
     */
    @Test
    fun testEndGame(){
        rootService.gameService.startGame((listNameValid))
        rootService.gameService.endGame()
        assertEquals(null,rootService.currentGame)
    }

    /**
     * test the distributeCards
     */
    @Test
    fun testDistributeCards(){
        rootService.gameService.startGame(listNameValid)
        rootService.currentGame?.openCards?.openCard = playerCard
        rootService.currentGame?.openCards?.openCard = rootService.gameService.distributeNewCards() as MutableList<Card>
        rootService.currentGame?.openCards?.let { assertNotEquals(playerCard, it.openCard) }

    }

}