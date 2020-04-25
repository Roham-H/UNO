/**
 * The model of numbered cards used in UNO, they have a number, and a color
 * and a rule which has to apply if this card is going to be played
 * @author Roham Hayedi
 */
public class NumberedCard extends ColoredCard {
//    The number of the card
    private final int number;
//    The constructor of this class
    public NumberedCard(int number, char color){
        super(color);
        this.number = number;
    }

    /**
     * @return the number of the card
     */
    public int getNumber() { return number; }

    /**
     * Checks if this card and the given one have the same number
     * @param card with which this card will be compared with
     * @return {@code true} if the numbers match, {@code false} otherwise
     */
    public boolean matchingNumber(Card card){
        if (card instanceof NumberedCard){
            return ((NumberedCard) card).getNumber() == number;
        }
        return false;
    }

    /**
     * Checks for either matching numbers or matching effects
     * @param card which {@code this} will be checked with
     * @param player who is going to play a card
     * @return {@code true} if they match, {@code false} otherwise
     */
    @Override
    public boolean rulesCheck(Card card, Player player) {
        return super.matchingColor(card) || matchingNumber(card);
    }

    /**
     * @return the name of the card, which is its number
     */
    @Override
    public String getCardName() {
        return ((Integer)number).toString();
    }

    /**
     * A number card has no effect, so this method is empty
     * @param player
     */
    @Override
    public void effect(Player player) {
        this.effectMade = true;
    }

}
