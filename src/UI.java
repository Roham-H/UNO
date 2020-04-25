import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * The user interface of UNO, "almost" every message is printed through this class
 */
public abstract class UI {

    public static void askForPlayerNumberSolo(){
        System.out.println("Enter the number of Players (From 3, Up to 5): ");
    }

    public static void askForPlayerNumberMulti(){
        System.out.println("Enter the number of Players(From 2, Up to 14): ");
    }

    public static void soloOrNot(){
        System.out.println("[1] Solo\n[2] Multiplayer");
    }

    public static void cardDisplay(Card card){
        System.out.println(card.setOutputColor());

        System.out.print("|");
        for (int i = 0; i < 25; i++) {
            System.out.print("$");
        }
        System.out.println("|");
        for (int i = 0; i < 5; i++) {
            System.out.print("|");
            for (int j = 0; j < 25; j++) {
                if (j == 12 && i == 2) {
                    for (int k = 0; k < ((card.getCardName().length() - 1)/2); k++) {
                        System.out.print("\b");
                    }
                    System.out.print(card.getCardName());
                }else
                System.out.print(" ");
            }
            if (i == 2)
                for (int j = 0; j < ((card.getCardName().length() )/2); j++) {
                    System.out.print("\b");
                }
            System.out.println("|");
        }
        System.out.print("|");
        for (int i = 0; i < 25; i++) {
            System.out.print("$");
        }
        System.out.println("|");

        if (card instanceof WildCard){
            UI.showNextColor(((WildCard) card).getNextColor());
        }
    }

    private static void firstAndFinalRowDisplay(ArrayList<Card> hand){
        int handSize = hand.size();
        for (int i = 0; i < handSize; i++) {
            if (hand.get(i).setOutputColor() != null)
                System.out.print(hand.get(i).setOutputColor());
            System.out.print("|");
            for (int j = 0; j < 18; j++) {
                System.out.print("$");
            }
        }
        System.out.println("$$$$$$$|");
    }

    private static void midRowsDisplay(ArrayList<Card> hand){
        int handSize = hand.size();
        for (int k = 0; k < 5; k++) {
            int previousLen = 0;
            for (int i = 0; i < handSize; i++) {
                String currentCardName = hand.get(i).getCardName();
                int len = currentCardName.length();
                if (hand.get(i).setOutputColor() != null)
                    System.out.print(hand.get(i).setOutputColor());
                for (int j = 0; j < previousLen  ; j++) {
                    System.out.print("\b");
                }
                System.out.print("|");
                for (int j = 0; j < 18; j++) {
                    if (k == 2 && j == 12) {
                        for (int l = 0; l < ((len-1)/2); l++) {
                            System.out.print("\b");
                        }
                        System.out.print(currentCardName);
                        for (int l = 0; l < ((len-1)/2); l++) {
                            System.out.print(" ");
                        }
                    }
                        System.out.print(" ");
                }

                if (k == 2)
                    previousLen = currentCardName.length();

                if (i == handSize - 1 && k != 2)
                    System.out.print("       ");
                if (i == handSize - 1 && k == 2)
                    for (int j = 0; j < len; j++) {
                        System.out.print(" ");
                    }
            }
            if (k != 2)
                System.out.println("|");
            else
                System.out.println();
        }
    }

    public static void handDisplay(Player player){
        ArrayList<Card> hand = player.getHand();
        firstAndFinalRowDisplay(hand);
        midRowsDisplay(hand);
        firstAndFinalRowDisplay(hand);
        System.out.print(Color.RESET);
        for (int i = 0; i < player.getHand().size() - 1; i++) {
            for (int j = 0; j < 18; j++) {
                if (j == 9)
                    System.out.printf("\b(%d)", i+1);
                else
                    System.out.print(" ");
            }
        }
        for (int j = 0; j < 27; j++) {
            if (j == 12)
                System.out.printf("(%d)", player.getHand().size());
            else
                System.out.print(" ");
        }
    }

    public static void gameWiseDisplay(){
        if (Order.getGameWise() == 1)
            System.out.println("\n\t\t>>>>>>>>>>Clockwise>>>>>>>>>>");
        else
            System.out.println("\n\t\t<<<<<<<<<Anti-Clockwise<<<<<<<<");
    }

    public static void showNextColor(char nextColor){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Chosen Color: ");
        if (nextColor == 'R'){
            stringBuilder.append("Red");
        }else if (nextColor == 'G') {
            stringBuilder.append("Green");
        }
        else if (nextColor == 'B')
            stringBuilder.append("Blue");
        else if (nextColor == 'Y')
            stringBuilder.append("Yellow");
        System.out.println(stringBuilder);
    }

    public static void drawAndPass(){
        System.out.println("No moves, Pass!");
    }

    public static void askForNextMove(){
        System.out.println("Choose a Card, or Draw by 0");
    }

    public static void warnForDraw(){
        System.out.println("You can Draw only if you have no playable cards!");
    }

    public static void reverseNote(){
        System.out.println("ReverseCard's effect: game wise reversed");
    }

    public static void wildCardNote(){
        System.out.println("Set next color: [1]Red [2]Green [3]Blue [4]Yellow");
    }

    public static void skipCardNote(){
        System.out.println("Skip card effect: next player will be skipped");
    }

    public static void draw2PlusNote(){
        System.out.println("Draw2+ card effect ");
    }

    private static void showTurn(boolean AIFound, Player currentPlayer){
        if (AIFound)
            if (currentPlayer instanceof HumanPlayer)
                System.out.println("\nYour Turn");
            else
                System.out.println("\n" + currentPlayer + "'s Turn");
        else {
            System.out.println("\n" + currentPlayer + "'s Turn");
        }
    }

    public static void boardDisplay(ArrayList<Player> players, Player currentPlayer){
        boolean AIFound = false;
        Player me = null;
        System.out.println("\n" + Color.RESET);

        for (Player player : players) {
//            Check if there are any AI players, meaning it's solo mode
            if (player instanceof AIPlayer)
                AIFound = true;
            playerInfoDisplay(player, AIFound);
            if (AIFound && !(currentPlayer instanceof HumanPlayer)) {
                if (player instanceof HumanPlayer)
                    me = player;
            }
        }
        if (me == null)
            me = currentPlayer;
        System.out.println();
        UI.showTurn(AIFound, currentPlayer);
        playerInfoDisplay(me,AIFound);
        if (me instanceof HumanPlayer)
            handDisplay(me);
        System.out.println();
    }

    private static void playerInfoDisplay(Player player, boolean AIFound){
        if (AIFound)
            if (player instanceof AIPlayer)
                System.out.println(player.getPlayerName() + ": " + player.getHand().size() + "-Cards ");
            else
                System.out.println("You" + ": " + player.getHand().size() + "-Cards ");
        else
            System.out.println(player.getPlayerName() + ": " + player.getHand().size() + "-Cards ");
    }

    public static void winnerDisplay(Player player){
        System.out.println(player.getPlayerName() + " Won!");
    }

    public static void scoreboardDisplay(TreeMap<Integer, Player> scoreList) {
        System.out.println("Players   |   Scores");
        for (Map.Entry entry : scoreList.entrySet()){
            if ((Integer)entry.getKey() != 0)
                System.out.println(entry.getValue().toString() + "  :  " + entry.getKey().toString());
        }
    }
}
