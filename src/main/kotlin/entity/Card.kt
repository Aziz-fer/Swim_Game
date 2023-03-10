package entity

/**
 *  * @author Feriani
 * Data class for the single typ of game elements that the game "swim" knows: cards.
 * It is characterized by a [CardSuit] and a [CardValue]
 */
data class Card(val suit: CardSuit, val value: CardValue) {

    override fun toString() = "$suit$value"

    /**
     * compares two [Card]s according to the [Enum.ordinal] value of their [CardSuit]
     * (i.e., the order in which the suits are declared in the enum class)
     */
    operator fun compareTo(other: Card) = this.value.ordinal - other.value.ordinal

}
