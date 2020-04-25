/**
 * The model of a Reverse Card, which can change the game wise
 * @author Roham Hayedi
 */
public class ReverseCard extends SpecialCard {

//    The constructor of the class
    public ReverseCard(char color){
        super(color);
    }

    /**
     * Checks whether this and the given card are the same
     * @param card with which this is to be checked
     * @return {@code true} if they match, {@code false} otherwise
     */
    @Override
    public boolean matchingEffect(Card card) {
        return card instanceof ReverseCard;
    }

    /**
     * The effect this card has on the game,
     * to change the game wise
     * @param player
     */
    @Override
    public void effect(Player player) {
        this.effectMade = true;
        Order.change();
        UI.reverseNote();
    }

    /**
     * @return this card's name
     */
    @Override
    public String getCardName() {
        return "Reverse";
    }
}
