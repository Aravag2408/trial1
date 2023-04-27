import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Main {
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

    /**
     * receiving from the user the size and quantity for each battleship
     * @param battleShipSize recieves a sring of the battleship sizes and quantitiy
     * @return histogram of the battleship sizes and quantity - each section represents the size of the battleship
     */
//    public static int [] battleShipSize(String battleShipSize) {
//        int lenHistogram = Integer.parseInt(battleShipSize.substring(-1, 0)); //-1 doesn't work
//        int[] battleShipSizeHistogram = new int[lenHistogram];
//        for (int i = 0; i < battleShipSize.length() - 2; i += 3) {
//            for (int j = 2; j < battleShipSize.length(); j += 3)
//                battleShipSizeHistogram[i] += 2; }
//        return battleShipSizeHistogram;
//
//    }

    /**
     * receiving from the user the size and quantity for each battleship
     * @param str recieves a sring of the battleship sizes and quantitiy
     * @return histogram of the battleship sizes and quantity - each section represents the size of the battleship
     */
    public static int [] battleShipSize(String str) {
        String[] arr = str.split("[X\\s]+");
        int[] sizes = new int[arr.length];
        for (int i =0; i< arr.length -1; i++) {
            sizes[i] = Integer.parseInt(arr[i]);
        }
        int lenHistogram = sizes[sizes.length-1];
        int [] Histogram = new int[lenHistogram];
        for (int j=0; j <= sizes.length -2; j += 2) {
            Histogram[sizes[j+1]] += Histogram[sizes[j]];
        }
        return Histogram;
    }
    /**
     * receiving from the user the location and orientation for each battleship
     * @param str recieves a sring of the location and orientation
     * @return  an array of the location and orientation in the form: [x,y,orientation]
     */

    public static int[] LocationAndOrientationArr(String str) {
        String[] arr = str.split("[\\s,]+");
        int[] intarray = new int[3];
        for (int i=0; i < 3; i++ ) {
            intarray[i]=Integer.parseInt(str);
        }
        return (intarray);
    }
    /**
     * receiving from the user the location,orientation and size of each battleship and the current boardgame
     * @param intarr of location and orientation
     * @param size int of the battleship size
     * @param boardgame char matrix of the current boardgame
     * @returns the boardgame with the battleship placed in it
     */

    public static char [][] LocateBattleships(int [] intarr, int size, char [][] boardgame) {
        int x = intarr[0];
        int y = intarr[1];
        if (intarr[2] == 0) {
            for (int i = x - 1; i <= x + size; i++) {
                boardgame[i][y] = '#';
                boardgame[i][y + 1] = 'Z';
                boardgame[i][y - 1] = 'Z';
            }
            boardgame[x - 1][y] = 'Z';
            boardgame[x + size][y] = 'Z';
        }
        // remmber to check the edge cases#################################
        if (intarr[2] == 1) {
            for (int i = y - 1; i <= y + size; i++) {
                boardgame[x][i] = '#';
                boardgame[x + 1][i] = 'Z';
                boardgame[x - 1][i] = 'Z';
            }
            boardgame[x][y - 1] = 'Z';
            boardgame[x][y + size] = 'Z';
        }
        return boardgame;

        public static void battleshipGame () {
            // take input of board size (N,M)(line,colomb)
            System.out.println("Enter the board size");
            String boardSize = scanner.nextLine();
            int[] infoArray = numExtract(boardSize);
            char[][] gameBoard = createBoard(infoArray[0], infoArray[1]);
            char[][] guessingBoard = createBoard(infoArray[0], infoArray[1]);
            System.out.println("Enter the battleships sizes");
            String battleShipSize = scanner.nextLine();
            int[] battleShipSizeHistogram = battleShipSize((String) battleShipSize);
            System.out.println("Enter location and orientation for battleship of sizes");
            String LocataionAndOrientationInfo = scanner.nextLine();
            int[] LocataionAndOrientation = LocationAndOrientationArr(LocataionAndOrientationInfo);
        }



    /**
     * receive location and orientation of the battleships and check for validity
     * @param coordinates an int array in the format of (x,y,orientation)
     * @param boardinfo an int that is the size of the ship we are placing
     * @param s size of the ship
     * @param currntBoard
     * @return boolean of is the input valid
     */
    public static boolean validity1( int[] coordinates, int[] boardinfo,int s, char [][] currntBoard ){
            int x = boardinfo[0];
            int y = boardinfo[1];
            // check possibale problem in index here#########################################################################3
            coordinates[0] += 1;
            coordinates[1] += 1;
            boolean isValid = false;
            while (!isValid) {
                if ((coordinates[2] != 0) && (coordinates[2] != 1)) {
                    System.out.println("Illegal orientation, try again!");
                    break;
                }
                if ((coordinates[0] >= x) || (coordinates[1] >= y)) {
                    System.out.println("Illegal tile orientation, try again!");
                    break;
                }
                if (coordinates[2] == 0) {
                    if ((coordinates[0] + s - 1) > x) {
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                        break;
                    }
                }
                if (coordinates[2] == 1) {
                    if ((coordinates[1] + s - 1) > y) {
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                        break;
                    }
                }
                if (currntBoard[coordinates[0]][coordinates[1]] == '-' && coordinates[2] == 0) {
                    for (int i = 0; i < s; i++) {
                        if (currntBoard[coordinates[0]][coordinates[1] + i] != '-') {
                            System.out.println("Battleship overlaps the another battleship, try again!");
                            break;
                        }
                    }
                }
                if (currntBoard[coordinates[0]][coordinates[1]] == '-' && coordinates[2] == 1) {
                    for (int j = 0; j < s; j++) {
                        if (currntBoard[coordinates[0] + j][coordinates[1]] != '-') {
                            System.out.println("Battleship overlaps the another battleship, try again!");
                            break;
                        }
                    }
                }


                //(0,2,0) len 4
                if ((currntBoard[coordinates[0]][coordinates[1]] == '-') && (coordinates[2] == 0)) {
                    if (coordinates[0] == 1) {
                        if (coordinates[1] == 1) {
                            // corner case
                        }
                    }
                    if ((coordinates[0] - 1 == 1) || (coordinates[0] + 1 == x)) {
                        if (coordinates[0] - 1 < 0 && (coordinates[0] - 1 != '-' || coordinates[1] + s + 1 != '-')) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                    }

                    if ((coordinates[0] - 1 >= 1) && (coordinates[0] + 1 <= x)) {
                        if (coordinates[0] - 1 < 0 && (coordinates[0] - 1 != '-' || coordinates[1] + s + 1 != '-')) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                    }
                    if (coordinates[0] + 1 <= x && (coordinates[0] + 1 >= 1 && coordinates[1] + s + 1 <= y)) {
                        for (int i = coordinates[0] - 1; i <= s + 1; i++) {
                            if (currntBoard[i][coordinates[1] + 1] != '-') {
                                System.out.println("Adjacent battleship detected, try again!");
                            }
                        }
                    }
                    if (coordinates[1] - 1 >= 0 && coordinates[0] + s + 1 <= y) {
                        for (int j = coordinates[0] - 1; j <= s + 1; j++) {
                            if (currntBoard[j][coordinates[1] + 1] != '-') {
                                System.out.println("Adjacent battleship detected, try again!");

                            }
                        }


                        //עבור שוכב
                        //if (currntBoard[x][y]=='-'):
                        // for ( int i ; i<s ; i++ ):
                        //if (currntBoard[x][y]=='-'):
                        // if ((coo[0]-1>0)&&(coo[0]+1<=x));
                        //for ( int i=1 ; i<4 ; i++):
                        //for( int j =s+2 ; j=0 ;--j)
                        // ifelse(coo[0]-1=0):
                        //
                        //ifelse (coo[0]+1<=x)
                        //currntBoard[x+i][y]='#'

                        //ifelse ((x-1>0)
                        //ifelse (x+1<=y)
                        isValid = true
                    }

                    if (isValid == true) {
                        return true;
                    }
                }


    /*
        /*
        check that it fits the board
        print "enter loc and ori
        take input as (x,y,oriantation) ( 0 hry ,1 perp)(x,y cordination)
       */


        /*
        check vaild input for ideot

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


            }

        }




        public static char[][] (int [] Histogram, int [] LocationAndOrientation, char[][] gameBoard) {
            int i = 0;
            while (i<= Histogram.length -1){
                if (Histogram[i] > 0){
                    while (!(validity1(LocataionAndOrientation, infoArray,i , gameBoard)){
                        String LocataionAndOrientationInfo = scanner.nextLine();
                        int [] LocataionAndOrientation = LocationAndOrientationArr(LocataionAndOrientationInfo);
                    }
                    if ((validity1(LocataionAndOrientation, infoArray,i , gameBoard)){
                        LocateBattleships(LocataionAndOrientation,i, boardgame);
                        System.out.println(boardgame);
                        Histogram[i] -= 1; }
                }}
                else {
                i += 1;
            }
        }