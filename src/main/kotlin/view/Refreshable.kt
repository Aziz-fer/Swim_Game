package view


import service.AbstractRefreshingService
/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * UI classes only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 *
 */
interface Refreshable {
    /**
     * perform refreshes that are necessary after a new game started
     */
    fun refreshAfterGameStart () {}
    /**
     * perform refreshes that are necessary after a turn started
     */
    fun refreshAfterNextPlayer () {}
    /**
     * perform refreshes that are necessary after a player swaps a single card
     */
    fun refreshAfterExchangeAllCards () {}
    /**
     * perform refreshes that are necessary after a player passes
     */
    fun refreshAfterExchangeOneCard () {}
    /**
     * perform refreshes that are necessary after a player swaps all the cards
     */
    fun refreshExchangeAllCards () {}
    /**
     * perform refreshes that are necessary after a player passes
     */
    fun refreshAfterPass () {}
    /**
     * perform refreshes that are necessary after a player knocks
     */
    fun refreshAfterKnock () {}
    /**
     * perform refreshes that are necessary after the midCards changed
     */
    fun refreshOpenCards () {}
    /**
     * perform refreshes that are necessary after a new game ended
     */
    fun refreshAfterGameEnd () {}

}