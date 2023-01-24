package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication
/**
 * Implementation of the BGW [BoardGameApplication] for the example card game "SwimGame"
 */
class SwimApplication : BoardGameApplication("Swim Game"), Refreshable{

        private val rootService = RootService()
        private val swimGameScene = SwimGameScene(rootService)
        private val finishedGameMenuScene = FinishedGameMenuScene(rootService).apply {

                quitButton.onMouseClicked = {
                        exit()
                }

        }

        private val newGameMenuScene = NewGameMenuScene(rootService).apply {
                quitButton.onMouseClicked = {
                        exit()
                }


        }
        init {
                rootService.addRefreshable(
                        this,
                        swimGameScene,
                        newGameMenuScene,
                        finishedGameMenuScene
                )
                this.showGameScene(swimGameScene)
                this.showMenuScene(newGameMenuScene)

        }

        override fun refreshAfterGameStart() {
                this.hideMenuScene()
        }
        override fun refreshAfterGameEnd(){
                this.showMenuScene(finishedGameMenuScene)
        }


}