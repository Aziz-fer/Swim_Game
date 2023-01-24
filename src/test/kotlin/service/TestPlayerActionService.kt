package service

import entity.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

/**
 * Test [PlayerActionService]
 */

class TestPlayerActionService {
    private val rootService = RootService()
    private val c1=Card(CardSuit.HEARTS, CardValue.SEVEN)
    private val c2=Card(CardSuit.HEARTS, CardValue.EIGHT)
    private val c3=Card(CardSuit.HEARTS, CardValue.NINE)
    private val c4=Card(CardSuit.SPADES, CardValue.SEVEN)
    private val c5=Card(CardSuit.SPADES, CardValue.EIGHT)
    private val c6=Card(CardSuit.SPADES, CardValue.NINE)
    private val listNameValid= arrayListOf("Player1","Player2","Player3")
    private val openCards:MutableList<Card> = arrayListOf(c4,c5,c6)
    private val playerCard:MutableList<Card> = arrayListOf(c1,c2,c3)

    /**
     * tests the functionality of exchangeOneCard
     */
    @Test
    fun testExchangeOneCard() {
        rootService.gameService.startGame(listNameValid)
        rootService.currentGame?.openCards?.openCard = openCards
        rootService.currentGame!!.players[0].handCard = playerCard
        rootService.currentGame?.players?.first()?.let { rootService.playerActionService.exchangeOneCard(it,c1,c4) }
        rootService.currentGame?.openCards?.let { assertContains(it.openCard,c1) }

    }
    /**
     * tests the functionality of exchangeAllCard
     */
    @Test
    fun testExchangeAllCards(){
        rootService.gameService.startGame(listNameValid)
        rootService.currentGame?.openCards?.openCard = openCards
        rootService.currentGame!!.players[0].handCard = playerCard
        rootService.playerActionService.exchangeAllCards(rootService.currentGame!!.players[0])
        assertEquals(playerCard,rootService.currentGame!!.openCards.openCard)
    }
    /**
     * tests the functionality of Knock
     */
    @Test
    fun testKnock(){
        rootService.gameService.startGame(listNameValid)
        rootService.currentGame?.players?.let { rootService.playerActionService.knock(it.first()) }
        assertEquals(rootService.gameService.passCounter,0)
        assertEquals(rootService.gameService.disableKnock,true)

    }
    /**
     * tests the functionality of Pass
     */
    @Test
    fun testPass(){
        rootService.gameService.startGame(listNameValid)
        rootService.currentGame?.players?.let { rootService.playerActionService.pass(it.first()) }
        assertEquals(rootService.gameService.passCounter,1)
    }
}