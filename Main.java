import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {
    public static Scanner scanner;
    public static Random rnd;


    /**
     * the func will receive the sizes as a string and retrive an int arr with the info
     *
     * @param str the given string of the board size
     * @return an arr with the relevant values in 0,1
     */
    public static int[] numExtract(String str) {
        String[] arr = str.split("X");
        int[] intarray = new int[2];
        for (int i = 0; i < 2; i++) {
            intarray[i] = Integer.parseInt(str);
        }
        return intarray;
    }

    /**
     * the func will receive the sizes of the board and will create the board game
     *
     * @param m,n will be int of the sizes of the board +1
     * @return a matrix that is already with 0-M and 0-N and '-' everywhere in the index lines made of strings
     */
    public static String[][] createBoard(int m, int n) {
        String[][] matrix = new String[m + 1][n + 1];
        matrix[0][0] = " ";
        for (int j = 1; j <= m; j++) {
            for (int k = 1; k <= n; k++) {
                matrix[j][k] = "–";
            }
        }
        for (int i = 0; i <= n; i++) {
            matrix[0][i + 1] = Integer.toString(i);
        }
        for (int i = 0; i <= m; i++) {
            matrix[i + 1][0] = Integer.toString(i);
        }
        return matrix;
    }

    /**
     * receiving from the user the size and quantity for each battleship
     *
     * @param str recieves a sring of the battleship sizes and quantitiy
     * @return histogram of the battleship sizes and quantity – each section represents the size of the battleship
     */
    public static int[] battleShipSize(String str) {
        String[] arr = str.split("[X\\s]+");
        int[] sizes = new int[arr.length];
        for (int i = 0; i < arr.length - 1; i++) {
            sizes[i] = Integer.parseInt(arr[i]);
        }
        int lenHistogram = sizes[sizes.length - 1];
        int[] Histogram = new int[lenHistogram];
        for (int j = 0; j <= sizes.length - 2; j += 2) {
            Histogram[sizes[j + 1]] += Histogram[sizes[j]];
        }
        return Histogram;
    }

    /**
     * receiving from the user the location and orientation for each battleship
     *
     * @param str recieves a sring of the location and orientation
     * @return an array of the location and orientation in the form: [x,y,orientation]
     */

    public static int[] LocationAndOrientationArr(String str) {
        String[] arr = str.split("[\\s,]+");
        int[] intarray = new int[3];
        for (int i = 0; i < 3; i++) {
            intarray[i] = Integer.parseInt(str);
        }
        return (intarray);
    }

    /**
     * receiving from the user the location,orientation and size of each battleship and the current boardGame
     *
     * @param intarr    of location and orientation
     * @param size      int of the battleship size
     * @param boardGame char matrix of the current boardGame
     * @return the boardGame with the battleship placed in it
     */
    public static String[][] placeBattleShip(int[] intarr, int size, String[][] boardGame) {
        int x = intarr[0];
        int y = intarr[1];
        if (intarr[2] == 0) {
            for (int i = x; i <= x + size - 1; i++) {
                boardGame[i][y] = "#";
            }
        }
        // edge cases are in the validity - thomas.#################################
        if (intarr[2] == 1) {
            for (int i = y; i <= y + size - 1; i++) {
                boardGame[x][i] = "#";
            }
        }
        return boardGame;
    }

    /**
     * receive location and orientation of the battleships and check for validity
     *
     * @param coordinates an int array in the format of (x,y,orientation)
     * @param boardinfo   an int that is the size of the ship we are placing
     * @param s           size of the ship
     * @param currntBoard matrix of strings that is the board
     * @return boolean of is the input valid
     */
    public static boolean isValid(int[] coordinates, int[] boardinfo, int s, String[][] currntBoard) {
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
            if (coordinates[2] == 1) {
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
            if ((currntBoard[coordinates[0]][coordinates[1]] == "–") && (coordinates[2] == 0)) {
                for (int i = 0; i < s; i++) {
                    if (currntBoard[coordinates[0]][coordinates[1] + i] != "–") {
                        System.out.println("Battleship overlaps the another battleship, try again!");
                        break;
                    }
                }
            }
            if ((currntBoard[coordinates[0]][coordinates[1]] == "–") && (coordinates[2] == 1)) {
                for (int j = 0; j < s; j++) {
                    if (currntBoard[coordinates[0] + j][coordinates[1]] != "–") {
                        System.out.println("Battleship overlaps the another battleship, try again!");
                        break;
                    }
                }
            }
            //######################################## בדיקות עבור מאוזן #####################################
            if (coordinates[2] == 0) {
                if ((coordinates[0] < x) && (coordinates[0] > 1)) {
                    if ((coordinates[1] < y - 1) && (coordinates[1] > 0)) {
                        for (int i = 0; i < s; i++) {
                            if (currntBoard[coordinates[0] - 1][coordinates[1] - 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1][coordinates[1] + i + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0]][coordinates[1] - 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0]][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1][coordinates[1] - 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }

                        }
                    }
                    if (coordinates[1] == 1) {
                        for (int i = 0; i < s; i++) {
                            if (currntBoard[coordinates[0] - 1][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0]][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                        }
                    }
                    if (coordinates[1] == y - 1) {
                        for (int i = 0; i < s; i++) {
                            if (currntBoard[coordinates[0] - 1][coordinates[1] - 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }

                            if (currntBoard[coordinates[0]][coordinates[1] - 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }

                            if (currntBoard[coordinates[0] + 1][coordinates[1] - 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                        }
                    }
                }
                //בודק את הפינה של 0,0
                if (coordinates[0] == 1) {
                    if (coordinates[1] == 1) {
                        for (int i = 0; i < s; i++) {
                            if (currntBoard[coordinates[0]][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0]][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                        }
                    }
                }
                // בודק את המקרים של הפאה העליונה בלי סופ השורה
                if (coordinates[0] == 1) {
                    if (coordinates[1] > 1) {
                        if (coordinates[1] < y - 1) {
                            for (int i = 0; i < s; i++) {

                                if (currntBoard[coordinates[0]][coordinates[1] - 1 + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0]][coordinates[1] + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0]][coordinates[1] + 1 + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + 1][coordinates[1] - 1 + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + 1][coordinates[1] + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + 1][coordinates[1] + 1 + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                            }
                        }
                    }
                }
                // בודק את הפינה הימנית העליונה
                if (coordinates[0] == 1) {
                    if (coordinates[1] == y - 1) {
                        if (currntBoard[coordinates[0]][coordinates[1]] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0]][coordinates[1] - 1] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0] + 1][coordinates[1] - 1] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0] + 1][coordinates[1]] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                    }
                }


                //בודק את הפינה של x,0
                if (coordinates[0] == x - 1) {
                    if (coordinates[1] == 1) {
                        for (int i = 0; i < s; i++) {

                            if (currntBoard[coordinates[0]][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0]][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1][coordinates[1] + 1 + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1][coordinates[1] + i] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                        }
                    }
                }
                // בודק את המקרים של הפאה התחתונה בלי סופ השורה
                if (coordinates[0] == x - 1) {
                    if (coordinates[1] > 1) {
                        if (coordinates[1] < y - 1) {
                            for (int i = 0; i < s; i++) {
                                if (currntBoard[coordinates[0]][coordinates[1] - 1 + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0]][coordinates[1] + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0]][coordinates[1] + 1 + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] - 1][coordinates[1] - 1 + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] - 1][coordinates[1] + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] - 1][coordinates[1] + 1 + i] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                            }

                        }
                    }
                }
                // בודק את הפינה הימנית התחתונה
                if (coordinates[0] == x - 1) {
                    if (coordinates[1] == y - 1) {
                        if (currntBoard[coordinates[0]][coordinates[1]] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0]][coordinates[1] - 1] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0] - 1][coordinates[1] - 1] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0] - 1][coordinates[1]] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                    }
                }
            }
            //#################################### בדיקות עבור מאונך#################################
            if (coordinates[2] == 1) {
                if ((coordinates[0] < x) && (coordinates[0] > 1)) {
                    if ((coordinates[1] < y - 1) && (coordinates[1] > 0)) {
                        for (int i = 0; i < s; i++) {
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1] - 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + i][coordinates[1] - 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1 + i][coordinates[1] - 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1 + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1 + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }

                        }
                    }
                    if (coordinates[1] == 1) {
                        for (int i = 0; i < s; i++) {
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + i + 1][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1 + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                        }
                    }
                    if (coordinates[1] == y - 1) {
                        for (int i = 0; i < s; i++) {
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1] - 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }

                            if (currntBoard[coordinates[0] + i][coordinates[1] - 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }

                            if (currntBoard[coordinates[0] + i + 1][coordinates[1] - 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1 + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                        }
                    }
                }
                //בודק את הפינה של 0,0
                if (coordinates[0] == 1) {
                    if (coordinates[1] == 1) {
                        for (int i = 0; i < s; i++) {
                            if (currntBoard[coordinates[0] + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1 + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + 1 + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                        }
                    }
                }
                // בודק את המקרים של הפאה העליונה בלי סופ השורה
                if (coordinates[0] == 1) {
                    if (coordinates[1] > 1) {
                        if (coordinates[1] < y - 1) {
                            for (int i = 0; i < s; i++) {

                                if (currntBoard[coordinates[0] + i][coordinates[1] - 1] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + i][coordinates[1]] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + i][coordinates[1] + 1] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + 1 + i][coordinates[1] - 1] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + 1 + i][coordinates[1]] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + 1 + i][coordinates[1] + 1] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                            }
                        }
                    }
                }
                // בודק את הפינה הימנית העליונה
                if (coordinates[0] == 1) {
                    if (coordinates[1] == y - 1) {
                        if (currntBoard[coordinates[0]][coordinates[1]] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0]][coordinates[1] - 1] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0] + 1][coordinates[1] - 1] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0] + 1][coordinates[1]] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                    }
                }


                //בודק את הפינה של x,0
                if (coordinates[0] == x - 1) {
                    if (coordinates[1] == 1) {
                        for (int i = 0; i < s; i++) {

                            if (currntBoard[coordinates[0] + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1] + 1] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                            if (currntBoard[coordinates[0] - 1 + i][coordinates[1]] != "–") {
                                System.out.println("Adjacent battleship detected, try again!");
                                break;
                            }
                        }
                    }
                }
                // בודק את המקרים של הפאה התחתונה בלי סופ השורה
                if (coordinates[0] == x - 1) {
                    if (coordinates[1] > 1) {
                        if (coordinates[1] < y - 1) {
                            for (int i = 0; i < s; i++) {
                                if (currntBoard[coordinates[0] + i][coordinates[1] - 1] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + i][coordinates[1]] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] + i][coordinates[1] + 1] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] - 1 + i][coordinates[1] - 1] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] - 1 + i][coordinates[1]] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                                if (currntBoard[coordinates[0] - 1 + i][coordinates[1] + 1] != "–") {
                                    System.out.println("Adjacent battleship detected, try again!");
                                    break;
                                }
                            }

                        }
                    }
                }
                // בודק את הפינה הימנית התחתונה
                if (coordinates[0] == x - 1) {
                    if (coordinates[1] == y - 1) {
                        if (currntBoard[coordinates[0]][coordinates[1]] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0]][coordinates[1] - 1] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0] - 1][coordinates[1] - 1] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                        if (currntBoard[coordinates[0] - 1][coordinates[1]] != "–") {
                            System.out.println("Adjacent battleship detected, try again!");
                            break;
                        }
                    }
                }
            }

            isValid = true;
            if (isValid) {
                return true;
            }
        }
    }

    /**
     * adds up the number of ship
     *
     * @param ship_array gets the array with the info about the ships
     * @return an int of the sum
     */
    public static int numOfShips(int[] ship_array) {
        int sum = 0;
        for (int i = 0; i <= ship_array.length - 1; i = i + 2) {
            sum += ship_array[i];
        }
        return sum;
    }

    public static int[] userAttack() {
        System.out.println("Your current guessing board: ");
        System.out.println(guessboard); //print user's guessing board
        //we need to have flexible guessing board
        System.out.println("Enter a tile to attack");
        String str = scanner.nextLine();
        String[] attack = str.split(",");
        int[] userAttack = new int[2]; //check problem
        for (int i = 0; i < 2; i++) {
            userAttack[i] = Integer.parseInt(str);
        }
        return userAttack;

        public static String attacks (String str ,char[][] guessboard, int r){
            //borders - x,y
            boolean flag = false;
            for (int i = 0; i <= x; i++) {
                for (int j = 0; j <= y; j++) {
                    //need to make sure we still have ships..some array?
                    if (guessboard[i][j] == '#') {
                        flag = true;
                        int[] attack = userAttack();
                        if (not validity1){
                            System.out.println("Illegal tile, try again!");
                        }
                    }
                    if (guessboard[i][j] == 'X' || guessboard[i][j] == 'V') {
                        System.out.println("Tile already attacked, try again!");
                    }
                    if (guessboard[i][j] == '-') {
                        System.out.println("That is a miss!");
                    }

                    System.out.println("That is a hit!");
                    //r represents int we need to recieve - how much ships left
                    // V instead of #, X insted of -
                    System.out.println("The computer's battleship has been drowned, " + ( int r)
                    "more battleships to go");
                }
                else{
                    flag = false;
                }
            }
        }
    }

    /**
     * a func that checks if the attack is ok
     * @param coordinats the attacked tile
     * @param geussingBoard the board that needs to be checked
          * @return boolean of is the attack ok
     */
    public static boolean isAttackValid(int[] coordinats, String[][] geussingBoard) {
        boolean check = true;
        int xLocation = coordinats[0] + 1;
        int yLocation = coordinats[1] + 1;
        if (xLocation > geussingBoard.length) {
            System.out.println("Illegal tile, try again!");
            check = false;
        }
        if (yLocation > geussingBoard[0].length) {
            System.out.println("Illegal tile, try again!");
            check = false;
        }
        if (geussingBoard[xLocation][yLocation] != "–") {
            System.out.println("Tile already attacked, try again!");
            check = false;
        }
        if (!check) {
            return false;
        }
        return check;
    }

    /**
     * prints the board in the specific way that is asked
     * @param board4Print the board you want to print
     */
    public static void boardPrint(String[][] board4Print) {
        for (int i = 0; i < board4Print.length; i++) {
            for (int j = 0; j < board4Print[i].length; j++) {
                System.out.print(board4Print[i][j] + " ");
            }
            System.out.println();
        }
    }











    public static void battleshipGame() {
        System.out.println("Enter the board size");
        String boardSize = scanner.nextLine();
        int[] infoArray = numExtract(boardSize);
        String[][] gameBoard = createBoard(infoArray[0], infoArray[1]);
        String[][] guessingBoard = createBoard(infoArray[0], infoArray[1]);
        System.out.println("Enter the battleships sizes");
        String battleShipSize = scanner.nextLine();
        int[] battleShipSizeHistogram = battleShipSize(battleShipSize);
        System.out.println("Enter location and orientation for battleship of sizes");
        String LocataionAndOrientationInfo = scanner.nextLine();
        int[] LocataionAndOrientation = LocationAndOrientationArr(LocataionAndOrientationInfo);
        int sumOfShips = numOfShips(battleShipSizeHistogram);
    }


}
 /*
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