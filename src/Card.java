import java.util.Objects;

/**
 * A model of the card used in UNO, with an output color
 * a name, and an effect, where in most cards effect is almost empty
 * and is implemented in the Round class instead
 * @author Roham Hayedi
 */
public abstract class Card {

//    The color with which the card will be printed
    private String outputColor;
//    The boolean indicator which will later change when a card has made its effect
    protected boolean effectMade = false;

    /**
     * The rules which apply to each card are implemented here
     * @param card which {@code this} will be checked with
     * @param player who is going to play a card
     * @return {@code true} if the rules meet, {@code false} otherwise
     */
    public abstract boolean rulesCheck(Card card, Player player);

    /**
     * @return {@code true} if the card has made its effect, {@code false} otherwise
     */
    public boolean getEffectMade(){
        return effectMade;
    }

    /**
     * Sets the output color of a card according to its kind
     * and returns it
     * @return the output color of the card
     */
    public String setOutputColor() {
        if (this instanceof WildCard)
            outputColor = Color.BLACK_BOLD.toString();
        else if (this instanceof ColoredCard) {
            char cardColor = ((ColoredCard) this).getColor();
            if (cardColor == 'R')
                outputColor = Color.RED.toString();
            else if (cardColor == 'G')
                outputColor = Color.GREEN_BOLD.toString();
            else if (cardColor == 'B')
                outputColor = Color.BLUE_BOLD.toString();
            else if (cardColor == 'Y')
                outputColor = Color.YELLOW_BOLD.toString();
        }
        return outputColor;
    }

    /**
     * @return the card's name
     */
    public abstract String getCardName();

    /**
     * A part of the effect each card will have on the match is implemented here
     * @param player
     */
    public abstract void effect(Player player);

    /**
     * The overridden equal class which compares cards with their name and output color
     * @param o the object which {@code this} will be compared with
     * @return {@code true} if they are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return Objects.equals(outputColor, card.outputColor) &&
                Objects.equals(getCardName(), ((Card) o).getCardName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputColor, getCardName());
    }
}
