import java.util.Random;
import java.util.Scanner;

public class expi {
    public static Scanner scanner;
    public static Random rnd;
    /**
     * the func will receive the sizes as a string and retrive an int arr with the info
     * @param str the given string of the board size
     * @return  an arr with the relevant values in 0,1
     */
    public static int[]  numExtract(String str) {
        String[] arr = str.split("X");
        int[] intarray = new int[2];
        for (int i=0; i < 2; i++ ) {
            intarray[i]=Integer.parseInt(str);
        }
        return intarray;
    }
    /**
     * the func will receive the sizes of the board and will create the board game
     * @param m,n  will be int of the sizes of the board +1
     * @return  a matrix that is already with 0-M and 0-N and '-' everywhere in the index lines made of chars
     */
    public static char[][] createBoard(int m, int n) {
        char[][] matrix = new char[m+1][n+1];
        matrix[0][0] =' ';
        for(int j = 1 ; j<=m; j++) {
            for (int k = 1; k <= n; k++) {
                matrix[j][k] = '-';
            }
        }
        for (int i = 0; i <= n ;i++) {
            matrix[0][i+1] = (char) i;
        }
        for (int i = 0; i <= m ;i++) {
            matrix[i+1][0]=(char) i;
        }
        return matrix;
    }

    //#############arava#####################

    /**
     * receiving from the user the size and quantity for each battleship
     * @param battleShipSize recieves a sring of the battleship sizes and quantitiy
     * @return histogram of the battleship sizes and quantity - each section represents the size of the battleship
     */
    public static int [] battleShipSize(String battleShipSize) {
        int lenHistogram = Integer.parseInt(battleShipSize.substring(-1, 0)); //-1 doesn't work
        int[] battleShipSizeHistogram = new int[lenHistogram];
        for (int i = 0; i < battleShipSize.length() - 2; i += 3) {
            for (int j = 2; j < battleShipSize.length(); j += 3)
                battleShipSizeHistogram[i] += 2; }
        return battleShipSizeHistogram;

    }
    public static void battleshipGame() {
        // take input of bord size (N,M)(line,colomb)
        System.out.println("Enter the board size");
        String boardSize = scanner.nextLine();
        int[] infoArray = numExtract(boardSize);
        char[][] gameBoard = createBoard(infoArray[0],infoArray[1]);

        System.out.println("Enter the battleships sizes");
        String battleShipSize = scanner.nextLine();
        int [] battleShipSizeHistogram = battleShipSize((String) battleShipSize);
        System.out.println("Enter location and orientation for battleship of sizes");

        String bshipLocataionAndOrientation = scanner.nextLine();
    }}
    /*
//(x,y,orientation)
    public static String validity( String word) {
       // if (word.charAt(6) != '0' && (word.charAt(6) != '1') {
         //   System.out.println("Illegal orientation, try again!");
        if ()
            System.out.println("Illegal tile orientation, try again!");
            System.out.println("Battleship exceeds the boundaries of the board");
            System.out.println("Battleship exceeds the boundaries of the board, try again!");
            System.out.println("Battleship overlaps another battleship, try again!");
            System.out.println("Adjacent battleship detected, try again!");
        }
    }
        /*
        check that it fits the board
        print "enter loc and ori
        take input as (x,y,oriantation) ( 0 hry ,1 perp)(x,y cordination)
       */


        /*
        check vaild input for ideat

        for each valid input print the stat of your board

        ##################################

        for computer

        there will be 3 rand num
        x,y,oriant

        check rand choise for valid

        if yes good if not start again

        ########################################

        attacks

        user first turn

        make gessing bord for user as matrix and mark
        V for every hit and - for every miss

        take input of atack tile

        check valid choise

        check that not hit already

        make hit and miss anunsments

        if intire ship announce ship down X more left

        for computer same but with rand attacks

        ############################33
        when player or computer finish all ship print you win you lose

        ########################################

        creating board game

        we wiil make a running board and change every turn the relavent tile

        we will print it every turn

        (N,M)(line,colomb)

        make matrix
        of first line (0,1,..,M-1)
        and colomb (0,1,...,N-1)

        # will mark a ship
        - will mark a the sea
        X will mark a ship thats boombed


        after each board make a line of space

        make func of attack
        make func of current board
        make func of num of ships

        ############################################
         writing stnd
         and google cunvention
         לבדוק שמת אבא שלו





         */

/*
}


    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}


*/
