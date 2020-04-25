/**
 * The main characteristics of a colored card
 * @author Roham Hayedi
 */
public abstract class ColoredCard extends Card {

//    The color the card has
    private final char color;

    /**
     * The constructor of this class
     * @param color of the card
     */
    public ColoredCard(char color){
        this.color = color;
    }

    /**
     * Checks if the card's color matches the required color of the board
     * @param card with which this card will be compared with
     * @return {@code true} if the cards have the same color
     */
    public boolean matchingColor(Card card) {
        if (card instanceof ColoredCard)
            return ((ColoredCard) card).getColor() == color;
        else {
            if (card instanceof WildCard)
                return ((WildCard) card).getNextColor() == color;
        }
        return true;
    }

    /**
     * @return the color of the card
     */
    public char getColor(){return color;}

    /**
     * @param card which {@code this} will be checked with
     * @param player who is going to play a card
     * @return {@code true} if the colors or numbers or effects match
     */
    public abstract boolean rulesCheck(Card card, Player player);

}
