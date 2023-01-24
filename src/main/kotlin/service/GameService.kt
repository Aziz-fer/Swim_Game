package service

import java.util.*
import entity.*

/**
 * Game Services methods are implemented in this Class
 */

class GameService (private val rootService:RootService):AbstractRefreshingService(){
    var passCounter: Int=0
    var disableKnock=false

    /**
     * function startGAme initialise the game ,the playerList,the unusedCards and the openCards
     */
    fun startGame(playerName:MutableList<String>) {

        if(playerName.size in 2..4)
        {
            rootService.currentGame = Game()
            resetPassCounter()
            for (name in playerName) {
                val player=initializePlayers(name)
                rootService.currentGame?.players?.add(player)
            }
            rootService.currentGame?.unusedCards ?.addAll(createStack())
            rootService.currentGame!!.openCards.openCard.addAll(distributeNewCards())
            distributeCards()

            onAllRefreshable { refreshAfterGameStart() }
        }
    }

    /**
     * initialise the player with a name
     */
    private fun initializePlayers(name:String):Player{
        return Player(name)
    }



    /**
     * switch from the current player and returns the next player
     */
    fun nextPlayer(player: Player): Player {
        val game=rootService.currentGame
        checkNotNull(game)
        game.players.removeFirst()
        game.players.add(player)
        if(game.players.first().hasKnocked){
            endGame()
        }
        else onAllRefreshable { refreshAfterNextPlayer() }
        return game.players.first()
    }

    /**
     * ends the currentGame and resets it by null
     */
    fun endGame() {

        onAllRefreshable { refreshAfterGameEnd() }
        rootService.currentGame=null
    }

    /**
     * at the start of Game all players should have 3 cards
     */
    private fun distributeCards() {
        if(rootService.currentGame!!.unusedCards .size<3) {
            endGame()
        }
        rootService.currentGame!!.players.forEach {
            it.handCard.addAll(distributeNewCards())
        }
    }

    /**
     * returns a new list of 3 cards
     */
    fun distributeNewCards():List<Card>{
        val game=rootService.currentGame
        checkNotNull(game)
        return List(3){game.unusedCards .removeFirst()}
    }

    /**
     * calculates the score of the current player
     */
    fun calculateScore(player: Player):Double{
        val map :MutableMap<CardSuit,Double> = mutableMapOf(
            CardSuit.DIAMONDS to 0.0 ,
            CardSuit.CLUBS to 0.0 ,
            CardSuit.SPADES to 0.0 ,
            CardSuit.HEARTS to 0.0)
        val score: Double

        if (player.handCard[0].value==(player.handCard[1].value) && player.handCard[0].value==(player.handCard[2].value))
            score=30.5

        else {
            for(card in player.handCard)
            {
                map[card.suit] = map[card.suit]!!.plus(card.value.getPoints())
            }
            score=map.values.maxOrNull()!!
        }
        return score
    }
    /**
     * find the winnerslist
     */
    private fun findWinners():List<Player> {

        val game = rootService.currentGame
        val gs = rootService.gameService

        val map : MutableMap<Double, Player> = HashMap ()
        for (i in 0 until game!!.players.size) {

            map[gs.calculateScore(game.players[i])] = game.players[i]
        }
        val sortedMap: MutableMap<Double,Player> = LinkedHashMap()
        map.entries.sortedBy { it.key }.forEach { sortedMap[it.key] = it.value }
        val winnerList = ArrayList(sortedMap.values)
        //var winningPlayer = winnerList[0]
        return winnerList
    }
    /**
     * increments the passCounter
     */
    fun setPassCounter(){
        passCounter++
    }

    /**
     * resets the passCounter to 0
     */
    fun resetPassCounter(){
        passCounter=0

    }
    /**
     * create the cardStack with the 32 cards
     */
    private fun createStack(): MutableList<Card> {
        val stack = mutableListOf<Card>()
        stack.add(Card(CardSuit.DIAMONDS, CardValue.SEVEN))
        stack.add(Card(CardSuit.DIAMONDS, CardValue.EIGHT))
        stack.add(Card(CardSuit.DIAMONDS, CardValue.NINE))
        stack.add(Card(CardSuit.DIAMONDS, CardValue.TEN))
        stack.add(Card(CardSuit.DIAMONDS, CardValue.JACK))
        stack.add(Card(CardSuit.DIAMONDS, CardValue.QUEEN))
        stack.add(Card(CardSuit.DIAMONDS, CardValue.KING))
        stack.add(Card(CardSuit.DIAMONDS, CardValue.ACE))
        stack.add(Card(CardSuit.CLUBS, CardValue.SEVEN))
        stack.add(Card(CardSuit.CLUBS, CardValue.EIGHT))
        stack.add(Card(CardSuit.CLUBS, CardValue.NINE))
        stack.add(Card(CardSuit.CLUBS, CardValue.TEN))
        stack.add(Card(CardSuit.CLUBS, CardValue.JACK))
        stack.add(Card(CardSuit.CLUBS, CardValue.QUEEN))
        stack.add(Card(CardSuit.CLUBS, CardValue.KING))
        stack.add(Card(CardSuit.CLUBS, CardValue.ACE))
        stack.add(Card(CardSuit.HEARTS, CardValue.SEVEN))
        stack.add(Card(CardSuit.HEARTS, CardValue.EIGHT))
        stack.add(Card(CardSuit.HEARTS, CardValue.NINE))
        stack.add(Card(CardSuit.HEARTS, CardValue.TEN))
        stack.add(Card(CardSuit.HEARTS, CardValue.JACK))
        stack.add(Card(CardSuit.HEARTS, CardValue.QUEEN))
        stack.add(Card(CardSuit.HEARTS, CardValue.KING))
        stack.add(Card(CardSuit.HEARTS, CardValue.ACE))
        stack.add(Card(CardSuit.SPADES, CardValue.SEVEN))
        stack.add(Card(CardSuit.SPADES, CardValue.EIGHT))
        stack.add(Card(CardSuit.SPADES, CardValue.NINE))
        stack.add(Card(CardSuit.SPADES, CardValue.TEN))
        stack.add(Card(CardSuit.SPADES, CardValue.JACK))
        stack.add(Card(CardSuit.SPADES, CardValue.QUEEN))
        stack.add(Card(CardSuit.SPADES, CardValue.KING))
        stack.add(Card(CardSuit.SPADES, CardValue.ACE))
        stack.shuffle()
        return stack
    }

    /**
     * if a player knocks then the knockButton should be disabled
     */
    fun disableKnockButton(){
        disableKnock=true
    }

}

