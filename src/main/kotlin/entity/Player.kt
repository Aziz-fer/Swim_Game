package entity

/**
 * create a player and gives it a name
 * @param [name] each player should have a name
 * @param [handCard] each player should have a Hand Card
 */
class Player (
    val name: String,
    var handCard:MutableList<Card> = arrayListOf(),
) { var score = 0.0
    var hasKnocked = false

    /**
     * Once the player has knocked it will set the hasKnocked parameter to true
     */
    fun hasKnocked() {
        hasKnocked = true
    }
}