package service

import entity.Game
import view.Refreshable

/**
 * Connection between the entity layer and the service layer
 *@param gameService instance from the GameService class
 *@param playerActionService instance from the PlayerActionService class
 *@param currentGame contains the SwimGame
 */
class RootService {
    val gameService = GameService(this)
    val playerActionService = PlayerActionService(this)

    /**
     * The currently active game. Can be `null`, if no game has started yet.
     */
    var currentGame : Game? = null

    /**
     * Adds the provided [newRefreshable] to all services connected
     * to this root service
     */
    private fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * Adds each of the provided [newRefreshable] to all services
     * connected to this root service
     */
    fun addRefreshable(vararg newRefreshable: Refreshable) {
        newRefreshable.forEach { addRefreshable(it) }
    }
}