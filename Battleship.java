import java.util.*;
import java.io.*;

public class Battleship {
	public static void main(String[] args) {
        System.out.println("Welcome to Battleship!");
        System.out.println();
        Scanner sc = new Scanner(System.in);
        /*
        This game of Battleship will be played by two players. We'll start by prompting Player 1 
        to enter coordinates for five ships of length one. The user input for both players will be 
        two ints separated by a space. The first int represents the row number and the second int 
        represents the column number.
        */
        System.out.println("PLAYER 1, ENTER YOUR SHIPS’ COORDINATES.");
        /*
        Instantiating array that will hold coordinates for all 5 ships. Java does not allow you to 
        append to an empty array, but we can still change values. So this array will initially hold
        -1 at every position. -1 is outside of the accepted range of values that a player can input 
        so we won't run into search issues when validating user inputs.
        */
        int[][] ships = new int[5][2];
        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                ships[i][j] = -1;
            }  
        }
        //See method created below          
        playerCoordinates(1, sc, ships);
        playerCoordinates(2, sc, ships);
        playerCoordinates(3, sc, ships);
        playerCoordinates(4, sc, ships);
        playerCoordinates(5, sc, ships);
        sc.nextLine();
        //Creating a location board that will show the player’s ship locations
        char[][] playerOneCoordBoard = new char[5][5];
        //Filling location board with '-' characters. '-' represents an empty space.
        for (int i = 0; i < playerOneCoordBoard.length; i++) {
            for (int j = 0; j < playerOneCoordBoard[i].length; j++) {
                playerOneCoordBoard[i][j] = '-';
            }  
        }     
        /* 
        Now adding '@' characters. '@' represents a ship that is not hit.
        Using the array holding ship coordinates to map said coordinates
        to the location board.
        */       
        int count = 0;
        do {
            playerOneCoordBoard[ships[count][0]][ships[count + 0][1]] = '@';
            count++;
        } while (count < 5);
        printBattleShip(playerOneCoordBoard); //see method created below
        //Printing 100 new lines so that Player 2 can't see Player 1's location board
        int printCount =0;
        do {
            System.out.println();
            printCount++;
        } while (printCount < 100);
        //Creating a target history board for player 1, will print after each hit or miss attempt
        char[][] playerOneTargetBoard = new char[5][5];
        for (int i = 0; i < playerOneTargetBoard.length; i++) {
            for (int j = 0; j < playerOneTargetBoard[i].length; j++) {
                playerOneTargetBoard[i][j] = '-';
            }  
        }    
        System.out.println("PLAYER 2, ENTER YOUR SHIPS’ COORDINATES.");
        int[][] shipsTwo = new int[5][2];
        for (int i = 0; i < shipsTwo.length; i++) {
            for (int j = 0; j < shipsTwo[i].length; j++) {
                shipsTwo[i][j] = -1;
            }  
        }           
        playerCoordinates(1, sc, shipsTwo);
        playerCoordinates(2, sc, shipsTwo);
        playerCoordinates(3, sc, shipsTwo);
        playerCoordinates(4, sc, shipsTwo);
        playerCoordinates(5, sc, shipsTwo);
        sc.nextLine();
        char[][] playerTwoCoordBoard = new char[5][5];
        for (int i = 0; i < playerTwoCoordBoard.length; i++) {
            for (int j = 0; j < playerTwoCoordBoard[i].length; j++) {
                playerTwoCoordBoard[i][j] = '-';
            }  
        }            
        int count2 = 0;
        do {
            playerTwoCoordBoard[shipsTwo[count2][0]][shipsTwo[count2 + 0][1]] = '@';
            count2++;
        } while (count2 < 5);
        printBattleShip(playerTwoCoordBoard);
        int printCount2 =0;
        do {
            System.out.println();
            printCount2++;
        } while (printCount2 < 100);
        char[][] playerTwoTargetBoard = new char[5][5];
        for (int i = 0; i < playerTwoTargetBoard.length; i++) {
            for (int j = 0; j < playerTwoTargetBoard[i].length; j++) {
                playerTwoTargetBoard[i][j] = '-';
            }  
        }      
        //Playing game until there's a winner
        int playerOneHitCount = 0;
        int playerTwoHitCount = 0;
        outerloop:
        while (true) {
            playerAttemptHit(1, sc, playerTwoCoordBoard, playerOneTargetBoard, playerOneCoordBoard); //see method created below
            for (int i = 0; i < playerOneTargetBoard.length; i++) {
                for (int j = 0; j < playerOneTargetBoard[i].length; j++) { //searching through every element in player one's target board
                    if (playerOneTargetBoard[i][j] == 'X') {
                        playerOneHitCount++;
                        if (playerOneHitCount == 5) { //if player 1 hit all 5 of player 2's ships, they have won the game. We will break out of this loop.
                            break outerloop;
                        }
                    }  
                }
            }
            //After leaving the for loop (i.e., after ending a player's round), we need to reset hit counter. If we don't, it will not properly count new instances of hits.
            playerOneHitCount = 0; 
            playerAttemptHit(2, sc, playerOneCoordBoard, playerTwoTargetBoard, playerTwoCoordBoard);
            for (int i = 0; i < playerTwoTargetBoard.length; i++) {
                for (int j = 0; j < playerTwoTargetBoard[i].length; j++) {
                    if (playerTwoTargetBoard[i][j] == 'X') {
                        playerTwoHitCount++;
                        if (playerTwoHitCount == 5) {
                            break outerloop;
                        }
                    }  
                }
            }
            playerTwoHitCount = 0;    
        }
        if (playerOneHitCount == 5) {
            System.out.println("PLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!");
            System.out.println();
            System.out.println("Final boards:"); //Player 1's coord board will be printed first
            System.out.println();
            printBattleShip(playerOneCoordBoard);
            System.out.println();
            printBattleShip(playerTwoCoordBoard);
        } else {
            System.out.println("PLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!");
            System.out.println();
            System.out.println("Final boards:"); //Player 1's coord board will be printed first
            System.out.println();
            printBattleShip(playerOneCoordBoard);
            System.out.println();
            printBattleShip(playerTwoCoordBoard);
        }        

        sc.close();
    }

    //This method develops coordinates for ships
    public static void playerCoordinates(int shipNo, Scanner sc, int[][] shipArray) {
        System.out.println("Enter ship " + shipNo + " location:");
        int row = sc.nextInt();
        int col = sc.nextInt(); 
        //Need to make sure user inputs are valid. They cannot be outside the bounds of the opponent's location board.
        while (row > 4 || row < 0 || col > 4 || col < 0) {
            System.out.println("Invalid coordinates. Choose different coordinates.");
            sc.nextLine();
            row = sc.nextInt();
            col = sc.nextInt();
        } 
        /*
        Also need to make sure the user is not firing on a coordinate they've already fired at. So we'll search through 
        the array we created early on that was meant to hold ship coordinates (holds all -1s to start out). If there's a 
        match, we'll ask for new coordinates. Then, we'll repeat the validity check we just did above.
        */
        for (int i = 0; i < shipArray.length; i++) {
            for (int j = 0; j < shipArray[i].length; j++) {
                if (row == shipArray[i][0]) {
                    if (col == shipArray[i][1]) {
                        System.out.println("You already have a ship there. Choose different coordinates.");
                        sc.nextLine();
                        row = sc.nextInt();
                        col = sc.nextInt();
                        while (row > 4 || row < 0 || col > 4 || col < 0) {
                            System.out.println("Invalid coordinates. Choose different coordinates.");
                            sc.nextLine();
                            row = sc.nextInt();
                            col = sc.nextInt();
                        }
                        
                    }
                }
            }
        }
        //Replacing the negative ones in the array holding ship coordinates.
        shipArray[shipNo - 1][0] = row;
        shipArray[shipNo - 1][1] = col;        
    }

    //This method prompts a player to fire upon the other player's ships
    public static void playerAttemptHit(int playerNo, Scanner sc, char[][] otherPlayerCoordBoard, char[][] playerTargetBoard, char[][] playerCoordBoard) {
        System.out.println();
        System.out.println("Player " + playerNo + ", enter hit row/column:");
        int x = sc.nextInt();
        int y = sc.nextInt();
        while (x > 4 || x < 0 || y > 4 || y < 0) {
            System.out.println("Invalid coordinates. Choose different coordinates.");
            sc.nextLine();
            x = sc.nextInt();
            y = sc.nextInt();    
        }
        /*
        - We can look at either the player's target board or the opponent's location board 
        to make sure the user is not firing on a coordinate they've already fired at (both 
        boards have the same dimensions and same characters representing a hit or miss, 
        'X' & 'O'). If it is an already fired spot, we'll ask for new coordinates. Then, repeat 
        the validity check we just did above. 
        - We have to specifically look at the opponent's location board to see if the user is 
        firing at a ship. If they are, we'll map an 'X' on both the player's target board and 
        the opponent's location board at the fired spot.
        - If the player uses valid coordinates, but misses, we'll map a 'O' on both the player's 
        target board and the opponent's location board at the fired spot.
        */
        for (int i = 0; i < otherPlayerCoordBoard.length; i++) {
            for (int j = 0; j < otherPlayerCoordBoard[i].length; j++) {
                if (x == i && y == j) {
                    if (playerTargetBoard[x][y] == 'X' || playerTargetBoard[x][y] == 'O') {
                        System.out.println("You already fired on this spot. Choose different coordinates.");
                        sc.nextLine();
                        x = sc.nextInt();
                        y = sc.nextInt();
                        while (x > 4 || x < 0 || y > 4 || y < 0) {
                            System.out.println("Invalid coordinates. Choose different coordinates.");
                            sc.nextLine();
                            x = sc.nextInt();
                            y = sc.nextInt();    
                        }
                        i = 0;
                        j = 0;
                    } else if ('@' == otherPlayerCoordBoard[x][y]) {
                        if (playerNo == 1) {
                            System.out.println("PLAYER " + playerNo + " HIT PLAYER " + (playerNo + 1) + "'s SHIP!");
                        } else {
                            System.out.println("PLAYER " + playerNo + " HIT PLAYER " + (playerNo - 1) + "'s SHIP!");
                        }
                        otherPlayerCoordBoard[i][j] = 'X';
                        playerTargetBoard[i][j] = 'X';
                    } else {
                        System.out.println("PLAYER " + playerNo + " MISSED!");
                        otherPlayerCoordBoard[i][j] = 'O';
                        playerTargetBoard[i][j] = 'O';
                    } 
                }  
            }
        } 
        //Finally, we'll use the method right down below to print the player's target board       
        printBattleShip(playerTargetBoard);
    }

    // Use this method to print game boards to the console.
	private static void printBattleShip(char[][] player) {
		System.out.print("  ");
		for (int row = -1; row < 5; row++) {
			if (row > -1) {
				System.out.print(row + " ");
			}
			for (int column = 0; column < 5; column++) {
				if (row == -1) {
					System.out.print(column + " ");
				} else {
					System.out.print(player[row][column] + " ");
				}
			}
			System.out.println("");
		}
	}
}