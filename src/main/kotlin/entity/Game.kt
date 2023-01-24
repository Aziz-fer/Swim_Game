package entity

/**
 * Initialize and create the SwimGame
 * @param [players] list of Players playing in the game
 * @param [openCards] refers to the 3 Cards in the Mid table
 * @param [unusedCards] 32 cards in the Game
 */
class Game {
    var players = ArrayDeque<Player>()
    //var players : MutableList<Player> = ArrayList(3)
    var openCards = OpenCards()
    var unusedCards = ArrayDeque<Card>()
    var passCounter: Int=0
}
    class OpenCards (
        var openCard:MutableList<Card> = arrayListOf()
    )
