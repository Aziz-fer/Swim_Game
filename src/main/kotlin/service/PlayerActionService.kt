package service

import entity.*


/**
 *  player ACTION Services methods are implemented in this Class
 */
class PlayerActionService(private val rootService: RootService):AbstractRefreshingService(){
    /**
     * exchange a card from the players hand with a one of the 3 open cards in the table
     */
    fun exchangeOneCard (player: Player, playercard: Card, openCard: Card){
        val game=rootService.currentGame
        checkNotNull(game)
        rootService.gameService.resetPassCounter()
        val handIndex= player.handCard.indexOf(playercard)
        val openCardIndex= game.openCards.openCard.indexOf(openCard)
        game.openCards.openCard[openCardIndex] = playercard
        player.handCard[handIndex] = openCard

        onAllRefreshable { refreshAfterExchangeOneCard()  }
        rootService.gameService.nextPlayer(player)

    }

    /**
     * exchange the entire cards in the playershand with the 3 open cards in the table
     */
    fun exchangeAllCards (player: Player){
        val game=rootService.currentGame
        checkNotNull(game)
        val hand=player.handCard
        val openCard=game.openCards.openCard
        player.handCard = openCard
        game.openCards.openCard = hand

            onAllRefreshable { refreshAfterExchangeAllCards() }
        rootService.gameService.nextPlayer(player)

    }

    /**
     * this function represents the knock
     */
    fun knock (player: Player){
        val game=rootService.currentGame
        checkNotNull(game)
        player.hasKnocked()
        rootService.gameService.resetPassCounter()
        rootService.gameService.nextPlayer(player)
        rootService.gameService.disableKnockButton()
       onAllRefreshable { refreshAfterKnock() }
    }
    /**
     * this function represents the pass
     */
    fun pass (player: Player){
        val game=rootService.currentGame
        rootService.gameService.setPassCounter()

        val playerNbr=game!!.players.size
        if(rootService.gameService.passCounter==playerNbr){
            if(game.unusedCards .size>=3){
                game.openCards= OpenCards(rootService.gameService.distributeNewCards() as MutableList<Card>)
                onAllRefreshable { refreshOpenCards() }
                rootService.gameService.resetPassCounter()
                rootService.gameService.nextPlayer(player)
            }

            else rootService.gameService.endGame()
        }
        else{
            onAllRefreshable { refreshAfterPass() }
            rootService.gameService.nextPlayer(player)
        }

    }
    fun updateScore (player: Player){
        val game = rootService.currentGame
        checkNotNull(game)
       player.score = rootService.gameService.calculateScore(player)
    }
}

