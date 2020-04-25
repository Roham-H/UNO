/**
 * The model of Draw2+ card, which's effect is mostly implemented in Round class
 * @author Roham Hayedi
 */
public class Draw2Plus extends SpecialCard {
    /**
     * The constructor of the class
     * @param color of the card
     */
    public Draw2Plus(char color) {
        super(color);
    }

    /**
     * Checks for matching effect between this and the given card
     * @param card with which this will be checked with
     * @return {@code true} if they are the same, {@code false} otherwise
     */
    @Override
    public boolean matchingEffect(Card card) {
        return card instanceof Draw2Plus;
    }

    /**
     * This cards effect is mostly implemented in Round class
     * @param player
     */
    @Override
    public void effect(Player player) {
        this.effectMade = true;
        UI.draw2PlusNote();
    }

    /**
     * @return the cards name
     */
    @Override
    public String getCardName() {
        return "+2";
    }

}
