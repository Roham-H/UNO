/**
 * The model of wildcard4+, when played, it lets the players choose the next color
 * and give a penalty to the next player who doesn't have it
 * @author Roham Hayedi
 */
public class WildCard4Plus extends WildCard {

    public WildCard4Plus(){
        super();
    }

    /**
     * @param card which {@code this} will be checked with
     * @param player who is going to play a card
     * @return {@code true} if the player has no other moves to make, {@code false} otherwise
     */
    @Override
    public boolean rulesCheck(Card card, Player player) {
            return player.getValidMove().size() == 0;
    }

    /**
     * @return this cards name
     */
    @Override
    public String getCardName() {
        return "WildCard 4+";
    }
}
