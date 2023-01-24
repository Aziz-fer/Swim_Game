package view


import entity.*
import service.*
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

private const val R = "/A.jpg"
/**
 * Game Scene where the whole Game will be played
 */
class SwimGameScene(private val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable  {
    private var card1: Card? = null
    private var card2: Card? = null
    private val image : BufferedImage = ImageIO.read(CardImageLoader::class.java.getResource(R))

    /**
     * label to show the current player
     */
    private val playerTurn = Label(
        posX = 800, posY = 20,
        width=300,
        text = "",
        font = Font(size = 50 , Color(255,255,255))
    )

    /**
     * label to show the current player's score
     */
    private val scoreLabel = Label(
        posX = 1300, posY = 850,
        text = "",
        font = Font(size = 15 , Color(255,255,255))
    )
    /**
     * Knock Button
     */
    private val knockButton = Button(width = 140, height = 35, posX = 50, posY = 950, text = "Knock").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->
                rootService.playerActionService.knock(game.players.first())
            }
        }
        if(rootService.gameService.disableKnock) this.isDisabled=true
    }

    /**
     * SwapAllCards Button
     */
    private val swapOneCard = Button(width = 140, height = 35, posX = 50, posY = 1000, text = "Swap one Card")
    /**
     * SwapAllCards Button
     */
    private val swapAllCards = Button(width = 140, height = 35, posX = 50, posY = 1050, text = "Swap All Cards").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->
                rootService.playerActionService.exchangeAllCards(game.players.first())
            }
        }
    }

    /**
     * Pass Button
     */
    private val passButton = Button(width = 140, height = 35, posX = 50, posY = 900, text = "pass").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->
                rootService.playerActionService.pass(game.players.first())
            }
        }
    }

    /**
     * At the Turn's begin the cards back will be shown, after clicking on this button the front will be shown
     */
    private val showCards = Button(width = 250, height = 70, posX = 1600, posY = 850, text = "Show Cards").apply {
        onMouseClicked = {
            playerCard1.showFront()
            playerCard2.showFront()
            playerCard3.showFront()
        }
    }

    /**
     * Label to show how many cards are left in the Stack
     */
    private val stackLeft = Label(
        width = 100, height = 20, posX = 1600, posY = 300,
        text = "",
        font = Font(size = 15 , Color(255,255,255))
    )
    private val cardImageLoader = CardImageLoader()

    /**
     * Stack view
     */
    private val deckViewBack = CardView(
        height = 200,
        width = 130,
        posX = 1600, posY = 400,
        front = ImageVisual(cardImageLoader.backImage)
    )

    /**
     * first card of the current player
     */
    private val playerCard1 = CardView(
        height = 200,
        width = 130,
        posX = 720, posY = 800,
        front = ImageVisual(cardImageLoader.frontImageFor(CardSuit.CLUBS, CardValue.ACE)),
        back=ImageVisual(cardImageLoader.backImage)

    ).apply {
        onMouseClicked = {
            if (card2==null) card1 = rootService.currentGame!!.players.first().handCard[0]
            else {
                card1 = rootService.currentGame!!.players.first().handCard[0]
                rootService.playerActionService.exchangeOneCard(rootService.currentGame!!.players.first(), card1!!,card2!!)
            }

        }
    }
    /**
     * second card of the current player
     */
    private val playerCard2 = CardView(
        height = 200,
        width = 130,
        posX = 880, posY = 800,
        front = ImageVisual(cardImageLoader.frontImageFor(CardSuit.DIAMONDS, CardValue.ACE)),
        back=ImageVisual(cardImageLoader.backImage)
    ).apply {
        onMouseClicked = {
            if (card2==null) card1 = rootService.currentGame!!.players.first().handCard[1]
            else {
                card1 = rootService.currentGame!!.players.first().handCard[1]
                rootService.playerActionService.exchangeOneCard(rootService.currentGame!!.players.first(), card1!!,card2!!)
            }

        }
    }
    /**
     * third card of the current player
     */
    private val playerCard3 = CardView(
        height = 200,
        width = 130,
        posX = 1040, posY = 800,
        front = ImageVisual(cardImageLoader.frontImageFor(CardSuit.HEARTS, CardValue.ACE)),
        back=ImageVisual(cardImageLoader.backImage)

    ).apply {
        onMouseClicked = {
            if (card2==null) card1 = rootService.currentGame!!.players.first().handCard[2]
            else {
                card1 = rootService.currentGame!!.players.first().handCard[2]
                rootService.playerActionService.exchangeOneCard(rootService.currentGame!!.players.first(), card1!!,card2!!)
            }
        }
    }
    /**
     * first card of the table Cards
     */
    private val tableCard1 = CardView(
        height = 200,
        width = 130,
        posX = 720, posY = 400,
        front = ImageVisual(cardImageLoader.frontImageFor(CardSuit.CLUBS, CardValue.ACE))
    ).apply {
        onMouseClicked = {
            if (card1==null) card2 = rootService.currentGame!!.openCards.openCard[0]
            else {
                card2 = rootService.currentGame!!.openCards.openCard[0]
                rootService.playerActionService.exchangeOneCard(rootService.currentGame!!.players.first(), card1!!,card2!!)
            }
        }
    }
    /**
     * second card of the table Cards
     */
    private val tableCard2 = CardView(
        height = 200,
        width = 130,
        posX = 880, posY = 400,
        front = ImageVisual(cardImageLoader.frontImageFor(CardSuit.HEARTS, CardValue.ACE))
    ).apply {
        onMouseClicked = {
            if (card1==null) card2 = rootService.currentGame!!.openCards.openCard[1]
            else {
                card2 = rootService.currentGame!!.openCards.openCard[1]
                rootService.playerActionService.exchangeOneCard(rootService.currentGame!!.players.first(), card1!!,card2!!)
            }
        }
    }
    /**
     * third card of the table Cards
     */
    private val tableCard3 = CardView(
        height = 200,
        width = 130,
        posX = 1040, posY = 400,
        front = ImageVisual(cardImageLoader.blankImage)
    ).apply {
        onMouseClicked = {
            if (card1==null) card2 = rootService.currentGame!!.openCards.openCard[2]
            else {
                card2 = rootService.currentGame!!.openCards.openCard[2]
                rootService.playerActionService.exchangeOneCard(rootService.currentGame!!.players.first(), card1!!,card2!!)
            }
        }
    }

    /**
     * initialize the complete GUI after starting the game
     */
    override fun refreshAfterGameStart() {
        val game = rootService.currentGame
        checkNotNull(game)
        playerTurn.text = game.players.first().name+"'s Turn"
        stackLeft.text = ""+rootService.currentGame!!.unusedCards.size+" cards left"
        val currentPlayer = game.players.first()
        playerCard1.frontVisual=ImageVisual(
                cardImageLoader.frontImageFor(currentPlayer.handCard[0].suit,
                currentPlayer.handCard[0].value))
        playerCard2.frontVisual=ImageVisual(
                cardImageLoader.frontImageFor(currentPlayer.handCard[1].suit,
                currentPlayer.handCard[1].value))
        playerCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[2].suit,
                currentPlayer.handCard[2].value))
        tableCard1.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[0].suit,
                game.openCards.openCard[0].value))
        tableCard2.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[1].suit,
                game.openCards.openCard[1].value))
        tableCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[2].suit,
                game.openCards.openCard[2].value))
        scoreLabel.text="Your Score : "+rootService.gameService.calculateScore(game.players.first())
    }

    /**
     * refresh once a player has passed
     */
    override fun refreshAfterPass() {
        val game = rootService.currentGame
        val currentPlayer = game!!.players.first()
        showScore()
        playerCard1.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[0].suit,
                currentPlayer.handCard[0].value))
        playerCard2.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[1].suit,
                currentPlayer.handCard[1].value))
        playerCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[2].suit,
                currentPlayer.handCard[2].value))
        stackLeft.text = ""+rootService.currentGame!!.unusedCards.size+" cards left"

    }

    /**
     * refresh once a player changed his whole hand
     */
    override fun refreshAfterExchangeAllCards() {
        stackLeft.text = ""+rootService.currentGame!!.unusedCards.size+" cards left"
        val game = rootService.currentGame
        checkNotNull(game)

        val currentPlayer = game.players.first()
        playerCard1.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[0].suit,
                currentPlayer.handCard[0].value))
        playerCard2.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[1].suit,
                currentPlayer.handCard[1].value))
        playerCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[2].suit,
                currentPlayer.handCard[2].value))
        tableCard1.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[0].suit,
                game.openCards.openCard[0].value))
        tableCard2.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[1].suit,
                game.openCards.openCard[1].value))
        tableCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[2].suit,
                game.openCards.openCard[2].value))

    }

    private fun showScore(){
        val game= rootService.currentGame
        scoreLabel.text="Your Score : "+rootService.gameService.calculateScore(game!!.players.first())
    }

    /**
     * refresh after Turn start
     */
    override fun refreshAfterNextPlayer() {
        playerCard1.showBack()
        playerCard2.showBack()
        playerCard3.showBack()
        val game = rootService.currentGame
        checkNotNull(game)
        stackLeft.text = ""+rootService.currentGame!!.unusedCards.size+" cards left"
        playerTurn.text = game.players.first().name+"'s Turn"
        val currentPlayer = game.players.first()
        showScore()
        playerCard1.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[0].suit,
                currentPlayer.handCard[0].value))
        playerCard2.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[1].suit,
                currentPlayer.handCard[1].value))
        playerCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[2].suit,
                currentPlayer.handCard[2].value))
    }

    /**
     * refresh the midCard
     */
    override fun refreshOpenCards() {
        val game = rootService.currentGame
        checkNotNull(game)
        tableCard1.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[0].suit,
                game.openCards.openCard[0].value))
        tableCard2.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[1].suit,
                game.openCards.openCard[1].value))
        tableCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[2].suit,
                game.openCards.openCard[2].value))
    }

    /**
     * refresh once a player changed a card from his hand
     */
    override fun refreshAfterExchangeOneCard () {
        stackLeft.text = ""+rootService.currentGame!!.unusedCards.size+" cards left"
        val game = rootService.currentGame
        checkNotNull(game)
        val currentPlayer = game.players.first()
        card1=null
        card2=null
        playerCard1.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[0].suit,
                currentPlayer.handCard[0].value))
        playerCard2.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[1].suit,
                currentPlayer.handCard[1].value))
        playerCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(currentPlayer.handCard[2].suit,
                currentPlayer.handCard[2].value))
        tableCard1.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[0].suit,
                game.openCards.openCard[0].value))
        tableCard2.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[1].suit,
                game.openCards.openCard[1].value))
        tableCard3.frontVisual=ImageVisual(
            cardImageLoader.frontImageFor(game.openCards.openCard[2].suit,
                game.openCards.openCard[2].value))
        showScore()
    }

    /**
     * refresh once a player knocked
     */
    override fun refreshAfterKnock() {
        stackLeft.text = ""+rootService.currentGame!!.unusedCards.size+" cards left"
        val game = rootService.currentGame
        knockButton.isDisabled=true
        scoreLabel.text="Your Score : "+rootService.gameService.calculateScore(game!!.players.first())
    }
    init {
        background = ImageVisual(image)
        opacity = .5
        addComponents(
            knockButton,
            swapAllCards,
            swapOneCard,
            passButton,
            showCards,
            playerTurn,
            deckViewBack,
            playerCard1,
            playerCard2,
            playerCard3,
            tableCard1,
            tableCard2,
            tableCard3,
            stackLeft,
            scoreLabel
        )

        tableCard1.showFront()
        tableCard2.showFront()
        tableCard3.showFront()

    }




}