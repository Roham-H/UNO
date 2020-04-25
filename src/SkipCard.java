/**
 * The model of a Skip card, which's effect is implemented in Round class
 * @author Roham Hayedi
 */
public class SkipCard extends SpecialCard {
    /**
     * The constructor of the class
     * @param color of the card
     */
    public SkipCard(char color) {
        super(color);
    }

    /**
     * Checks whether the cards are the same in terms of effect
     * @param card to be checked with
     * @return {@code true} if they are, {@code false} otherwise
     */
    @Override
    public boolean matchingEffect(Card card) {
        return card instanceof SkipCard;
    }

    /**
     * The almost empty method of this card's effect
     * @param player
     */
    @Override
    public void effect(Player player) {
        this.effectMade = true;
        UI.skipCardNote();
    }

    /**
     * @return the card's name
     */
    @Override
    public String getCardName() {
        return "Skip";
    }

}
