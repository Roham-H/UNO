/**
 * The superclass of SpecialCards, which defines that each of them must have an effect
 */
public abstract class SpecialCard extends ColoredCard {
    /**
     * The constructor of this class
     * @param color of the card
     */
    public SpecialCard(char color){
        super(color);
    }

    /**
     * To check whether the cards are the same in terms of effect or not
     * @param card to be checked with
     * @return {@code true} if they are, {@code false} otherwise
     */
    public abstract boolean matchingEffect(Card card);

    /**
     * The implementation of each card's effect on the game
     * @param player
     */
    public abstract void effect(Player player);

    /**
     * The rules of Special cards, matching effect, or matching color
     * @param card which {@code this} will be checked with
     * @param player who is going to play a card
     * @return {@code true} if they match in either way, {@code false} otherwise
     */
    public boolean rulesCheck(Card card, Player player){
        return super.matchingColor(card) || matchingEffect(card);
    }

}
