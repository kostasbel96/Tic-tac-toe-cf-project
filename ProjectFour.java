package gr.aueb.cf.ch10;

import java.util.Scanner;

/**
 * Παιχνίδι τρίλιζα
 *
 * @author kostasbel96*/
public class ProjectFour {

    public static void main(String[] args) {

        int choice = 0;
        do {
            gameLoop();
            do{
                printMenu();
                choice = getChoice(new Scanner(System.in));
            }while(choice != 1 && choice != 2);
        }while(choice != 2);

    }

    /**
     * Μέθοδος που Αρχικοποιεί τους πίνακες
     * */
    public static char[][] initArray(){
        char[][] array = new char[3][3];
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[0].length; j++){
                array[i][j] = ' ';
            }
        }

        return array;
    }

    /**
     * Μέθοδος που εμφανίζει το παιχνίδι
     * */
    public static void showGame(char[][] array){
        System.out.println("  1 2 3");
        System.out.println("A " + array[0][0] + "|" + array[0][1] + "|" + array[0][2]);
        System.out.println("  -----");
        System.out.println("B " + array[1][0] + "|" + array[1][1] + "|" + array[1][2]);
        System.out.println("  -----");
        System.out.println("C " + array[2][0] + "|" + array[2][1] + "|" + array[2][2]);
    }

    /**
     * Μέθοδος που εγκαθιστά τις συνεταγμένες "νίκης" στον πίνακα winnerCords
     * */
    public static int[][][] initWinnerCords(){
        return new int[][][]{ { {0, 0}, {0, 1}, {0, 2} }, //γραμμή 1
                              { {1, 0}, {1, 1}, {1, 2} }, //γραμμή 2
                              { {2, 0}, {2, 1}, {2, 2} }, //γραμμή 3
                              { {0, 0}, {1, 1}, {2, 2} }, //διαγώνιος 1
                              { {0, 2}, {1, 1}, {2, 0} }, //διαγώνιος 2
                              { {0, 0}, {1, 0}, {2, 0} }, //στήλη 1
                              { {0,1}, {1, 1}, {2, 1} }, //στήλη 2
                              { {0, 2}, {1, 2}, {2, 2} }  //στήλη 3
                            };
    }

    /**
     * Συνάρτηση που ελέγχει αν έχει γίνει νίκη για τον παίχτη 1
     * */
    public static boolean checkWinner1(char[][] game, char[][] player, int[][][] cords){
        boolean playerWins = false;
        int count = 0;
        for (int[][] element : cords) {
            for (int[] element2 : element) {
                if (game[element2[0]][element2[1]] == player[element2[0]][element2[1]] && game[element2[0]][element2[1]] == 'X') {
                    count++;
                    if(count == 3) {
                        playerWins = true;
                    }
                }
            }
            count = 0;
        }
        return playerWins;
    }

    /**
     * Συνάρτηση που ελέγχει αν έχει γίνει νίκη για τον παίχτη 2
     * */
    public static boolean checkWinner2(char[][] game, char[][] player, int[][][] cords){
        boolean playerWins = false;
        int count = 0;
        for (int[][] element : cords) {
            for (int[] element2 : element) {
                if (game[element2[0]][element2[1]] == player[element2[0]][element2[1]] && game[element2[0]][element2[1]] == 'O') {
                    count++;
                    if(count == 3) {
                        playerWins = true;
                    }
                }
            }
            count = 0;
        }
        return playerWins;
    }

    /**
     * Συνάρτηση που ελέγχει αν ο χρήστης δίνει έγκυρη τιμή συνεταγμένων για κίνηση
     * */
    public static boolean validOrdinates(String[] input){
        boolean flag = true;
        if (input.length != 2){
            flag = false;
        }
        else if (input[0].length() != 1){
            flag = false;
        }
        else if ( (((int)input[0].charAt(0)) < 65) || (((int)input[0].charAt(0)) > 67)){
            flag = false;
        }
        else if (Integer.parseInt(input[1]) < 1 || Integer.parseInt(input[1]) > 3){
            flag = false;
        }

        return flag;
    }

    /**
     * Λούπα του παιχνιδιού
     * */
    public static void gameLoop(){
        char[][] playerOne = initArray();
        char[][] playerTwo = initArray();
        char[][] game = initArray();
        int[][][] winnerCords = initWinnerCords();
        int turn = 0;
        int plays = 0;
        int x, y;
        String[] input;
        boolean playerOneWins = false;
        boolean playerTwoWins = false;
        Scanner in = new Scanner(System.in);
        showGame(game);

        //game
        while (plays < 9 && !playerTwoWins && !playerOneWins){
            do{
                if(turn == 0)
                    System.out.print("Παίχτη 1(X), Δώσε συντεταγμένες: ");
                else
                    System.out.print("Παίχτη 2(O), Δώσε συντεταγμένες: ");
                input = in.next().split(",");
            }while(!validOrdinates(input));
            x = (int)input[0].charAt(0) - 65;
            y = Integer.parseInt(input[1]);
            if (playerOne[x][y - 1] == ' ' && turn == 0 && playerTwo[x][y - 1] == ' '){
                playerOne[x][y - 1] = 'X';
                game[x][y - 1] = playerOne[x][y - 1];
                turn = 1;
                plays++;
                playerOneWins = checkWinner1(game, playerOne, winnerCords);
            }
            else if(playerTwo[x][y - 1] == ' ' && turn == 1 && playerOne[x][y - 1] == ' '){
                playerTwo[x][y - 1] = 'O';
                game[x][y - 1] = 'O';
                turn = 0;
                plays++;
                playerTwoWins = checkWinner2(game, playerTwo, winnerCords);
            }
            showGame(game);
        }

        if(playerOneWins){
            System.out.println("Συγχαρητήρια Παίχτη 1 ΚΕΡΔΙΣΕΣ!");
        }
        else if(playerTwoWins){
            System.out.println("Συγχαρητήρια Παίχτη 2 ΚΕΡΔΙΣΕΣ!");
        }
        else {
            System.out.println("ΙΣΟΠΑΛΙΑ!");
        }
    }

    public static void printMenu(){
        System.out.println("Επιλέξτε μία από τις παρακάτω επιλογές");
        System.out.println("1. Επανεκκίνηση");
        System.out.println("2. Τερματισμός");
        System.out.println("Δώστε αριθμό επιλογής: ");
    }

    public static int getChoice(Scanner in){
        while(!in.hasNextInt()){
            System.out.println("Δώσε ακέραιο αριθμό!");
            in.next();
        }
        return in.nextInt();
    }

}
