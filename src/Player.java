import java.util.ArrayList;
import java.util.Objects;

/**
 * The model of a player of UNO, each one has a hand, and a name
 * @author Roham Hayedi
 */
public abstract class Player {

//    The player's name
    private String playerName;
//    The list of the cards the player has
    private final ArrayList<Card> hand = new ArrayList<>();
//    The list of the valid cards to play according to the hand and the top card
    private final ArrayList<Card> validMove = new ArrayList<>();

    /**
     * @param playerName to be assigned to the player
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the player's name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Adds cards to the player's hand
     * @param card to be added to the player's hand
     */
    public void addCard(Card card){
        hand.add(card);
    }

    /**
     * Used to add validated cards to the player's validMoves
     * @param card
     */
    public void addValidMove(Card card){
        validMove.add(card);
    }

    /**
     * Clears the list of valid cards
     */
    public void clearValidMove(){
        validMove.clear();
    }

    /**
     * @return the list of valid cards
     */
    public ArrayList<Card> getValidMove() { return validMove; }

    /**
     * @return the player's hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * @return the name of the player
     */
    @Override
    public String toString() {
        return playerName;
    }

    /**
     * The overridden equals which compares players with their name, and their hand
     * @param o the player to be compared with
     * @return {@code true} if they match, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(playerName, player.playerName) &&
                Objects.equals(hand, player.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, hand);
    }
}
