package view

import service.*
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.system.exitProcess

private const val R = "/R.jpg"

/**
 * start Screen where players name will be given
 */
class NewGameMenuScene (private val rootService: RootService) : MenuScene(1920, 1080), Refreshable{
    private val image : BufferedImage = ImageIO.read(CardImageLoader::class.java.getResource(R))
    private val playerNames = arrayListOf<String>()

    /**
     *label for the application's name
     */
    private val headlineLabel = Label(
        alignment = Alignment.CENTER,
        width = 300, height = 50, posX = 800, posY = 50,
        text = "NEW GAME",
        font = Font(size = 50)
    )

    /**
     * label for first player's name
     */
    private val p1Label = Label(
        width = 100, height = 35,
        posX = 750, posY = 175,
        text = "Player 1:"
    )
    /**
     * label for second player's name
     */
    private val p2Label = Label(
        width = 100, height = 35,
        posX = 750, posY = 220,
        text = "Player 2:"
    )
    /**
     * label for third player's name
     */
    private val p3Label = Label(
        width = 100, height = 35,
        posX = 750, posY = 265,
        text = "Player 3:"
    )
    /**
     * label for fourth player's name
     */
    private val p4Label = Label(
        width = 100, height = 35,
        posX = 750, posY = 310,
        text = "Player 4:"
    )
    /**
     * TextInput where the first player's name will be written
     */
    private val p1Input: TextField = TextField(
        width = 200, height = 35,
        posX = 850, posY = 175,
        prompt = "Please click here,if you want to play!"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
        }
    }
    /**
     * TextInput where the second player's name will be written
     */
    private val p2Input: TextField = TextField(
        width = 200, height = 35,
        posX = 850, posY = 220,
        prompt = "Please click here,if you want to play!"
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p1Input.text.isBlank()
        }
    }
    /**
     * TextInput where the third player's name will be written
     */
    private val p3Input: TextField = TextField(
        width = 200, height = 35,
        posX = 850, posY = 265,
        prompt = ""
    )
    /**
     * TextInput where the fourth player's name will be written
     */
    private val p4Input: TextField = TextField(
        width = 200, height = 35,
        posX = 850, posY = 310,
        prompt = ""
    )
    /**
     * Quit Button
     */
    val quitButton = Button(
        width = 140, height = 35,
        posX = 800, posY = 380,
        text = "End Game"
    ).apply {
        visual = ColorVisual(221, 136, 136)
        onMouseClicked = {
            exitProcess(0)
        }
    }

    /**
     * Start Game Button
     */
    private val startButton = Button(
        width = 140, height = 35,
        posX = 960, posY = 380,
        text = "Start"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            if(p1Input.text.isNotBlank()) playerNames.add(p1Input.text)
            if(p2Input.text.isNotBlank()) playerNames.add(p2Input.text)
            if(p3Input.text.isNotBlank()) playerNames.add(p3Input.text)
            if(p4Input.text.isNotBlank()) playerNames.add(p4Input.text)
            if(playerNames.size in 2..4) rootService.gameService.startGame(playerNames)
        }
    }
    init {
        background = ImageVisual(image)
        opacity = 0.5
        addComponents(
            headlineLabel,
            p1Label, p1Input,
            p2Label, p2Input,
            p3Label, p3Input,
            p4Label, p4Input,
            startButton, quitButton
        )
    }
}