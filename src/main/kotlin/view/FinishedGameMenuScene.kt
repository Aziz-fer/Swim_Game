package view

import entity.*
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

private const val R = "/R.jpg"
/**
 * Scene where final scores will be shown and r´the winner will be revealed
 */
class FinishedGameMenuScene(private val rootService: RootService) : MenuScene(1920, 1080), Refreshable {
    private val image : BufferedImage = ImageIO.read(CardImageLoader::class.java.getResource(R))
    /**
     * Game Over Label
     */
    private val headlineLabel = Label(
        width = 300, height = 50, posX = 800, posY = 50,
        text = "Game Over",
        font = Font(size = 50 , Color(255,255,255))
    )

    /**
     * Label to show the first player's score
     */
    private val p1Score = Label(
        width = 300, height = 35,
        posX = 800, posY = 175,
        font = Font(size =20 , Color(255,255,255))
    )
    /**
     * Label to show the second player's score
     */
    private val p2Score = Label(
        width = 300, height = 35,
        posX = 800, posY = 210,
        font = Font(size =20 , Color(255,255,255))
    )
    /**
     * Label to show the third player's score
     */
    private val p3Score = Label(
        width = 300, height = 35,
        posX = 800, posY = 245,
        font = Font(size =20 , Color(255,255,255))
    )
    /**
     * Label to show the fourth player's score
     */
    private val p4Score = Label(
        width = 300, height = 35,
        posX = 800, posY = 280,
        font = Font(size =20 , Color(255,255,255))
    )
    /**
     * Label to show the winner
     */
    private val gameResult = Label(
        width = 300, height = 35,
        posX = 800, posY = 315,
        font = Font(size =30 , Color(255,255,255))
    )
    /**
     * Quit button
     */
    val quitButton = Button(width = 140, height = 35, posX = 1025, posY = 385, text = "Quit").apply {
        visual = ColorVisual(Color(221,136,136))
    }

    val retartButton = Button(width = 140, height = 35, posX = 725, posY = 385, text = "Restart").apply {
        visual = ColorVisual(Color(221,136,136))
        onMouseClicked = {
            //exitProcess(0)
            
        }
    }
    init {
        background = ImageVisual(image)
        opacity = 0.5

    }

    init {
        background = ImageVisual(image)
        opacity = 1.0
        addComponents(headlineLabel, p1Score, p2Score,p3Score,p4Score, gameResult, quitButton,retartButton)
    }

    /**
     * refresh once the game finished
     */
    override fun refreshAfterGameEnd() {
        fun Player.showScore() : String = "${this.name} scored ${rootService.gameService.calculateScore(this)} points."

        /**
         * the winner will be calculated here
         */
        fun Game.gameResultString(): String {
            val p1Score = rootService.gameService.calculateScore(this.players[0])
            val p2Score = rootService.gameService.calculateScore(this.players[1])
            return if (this.players.size==2){
                if (p1Score-p2Score>0) "${this.players[0].name} Win !"
                else "${this.players[1].name} Win !."
            } else if(this.players.size == 3){
                val p3Score = rootService.gameService.calculateScore(this.players[2])
                if(p1Score-p2Score>0 && p1Score-p3Score>0) "${this.players[0].name} Win !"
                else if (p2Score-p1Score>0 && p2Score-p3Score>0) "${this.players[1].name} Win !"
                else "${this.players[2].name} Win !"
            } else{
                val p3Score = rootService.gameService.calculateScore(this.players[2])
                val p4Score = rootService.gameService.calculateScore(this.players[3])
                if(p1Score-p2Score>0 && p1Score-p3Score>0 && p1Score-p4Score>0) "${this.players[0].name} Win !"
                else if(p2Score-p1Score>0 && p2Score-p3Score>0 && p2Score-p4Score>0) "${this.players[1].name} Win !"
                else if(p3Score-p1Score>0 && p3Score-p2Score>0 && p3Score-p4Score>0) "${this.players[2].name} Win !"
                else "${this.players[3].name} Win !"
            }
        }
        val game = rootService.currentGame
        checkNotNull(game) { "No game running" }
        p1Score.text = game.players[0].showScore()
        p2Score.text = game.players[1].showScore()
        if(game.players.size==3) p3Score.text = game.players[2].showScore()
        if(game.players.size==4) {
            p4Score.text = game.players[3].showScore()
            p3Score.text = game.players[2].showScore()
        }
        gameResult.text = game.gameResultString()

    }

}