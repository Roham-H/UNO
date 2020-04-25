import java.util.Scanner;

public class UNO {
    public static void main(String[] args) throws InterruptedException {

        Scanner reader = new Scanner(System.in);
        int choice;
        int playerNumber;

        do {
            UI.soloOrNot();
            choice = reader.nextInt();
        }while (choice != 1 && choice != 2);

        if (choice == 1) {
            do {
                UI.askForPlayerNumberSolo();
                playerNumber = reader.nextInt();
            } while (playerNumber > 5 || playerNumber < 3);
        }

        else{
            do {
                UI.askForPlayerNumberMulti();
                playerNumber = reader.nextInt();
            }while (playerNumber < 2 || playerNumber > 14);
        }

        if (choice == 1) {
            Round newRound = Round.getNewRound();
            Player player = new HumanPlayer();
            newRound.addPlayer(player);
            for (int i = 0; i < playerNumber - 1; i++) {
                player = new AIPlayer();
                newRound.addPlayer(player);
            }
            newRound.play();
        }
        else {
            Round newRound = Round.getNewRound();
            for (int i = 0; i < playerNumber; i++) {
                Player player = new HumanPlayer();
                newRound.addPlayer(player);
            }
            newRound.play();
        }
    }
}
