/* 
Authors: Nakul Patel & Mihir Patel
Start Date: June 5th, 2021
Responsible for determining the gamemode, running the game, and handling the game exit.
*/

import java.util.*;
import java.io.*;

public class Main {
    /* GAME CONSTANTS */
    public static final int SIZE = 8;

    /* GLOBAL VARIABLES */
    private static Scanner input = new Scanner(System.in);
    private static boolean isPlayer1 = true;
    private static int back;
    public static String name = null;
    public static String name2 = null;
    public static int moves = 0;
    public static String wordToEncrypt;
    public static String encryptedWord;
    public static boolean gameEnd = false;
    private static String result = null;
    private static boolean nameEntered = false;

    private static boolean endGameNow = false;

    public static void main(String[] args) {
        Board board = new Board(SIZE); // generates a board
        System.out.println("\033[47m\033[46m");

        // defines abstract classes, defined to a concrete class after chosen the gameboard
        Player player1 = null;
        Player player2 = null;

        Computer AI;


        //Here we have a while loop that runs the actually game
        while(true) {
            result = gameMode();


            if(result.equals("2Players")) {
                // runs the two player game mode
                player1 = new HumanPlayer(true);
                player2 = new HumanPlayer(false);

                do {
                    System.out.print("\nPlease Enter Your Name Player 2: ");
                    name2 = input.nextLine();
                } while(name2 == "");

                clearScreen();

                gameEnd = (endGame(board));

                while ( gameEnd == false ) {

                    if (isPlayer1) {
                        board = player1.getMove(board);
                    } else {
                        board = player2.getMove(board);
                    }
                    moves += 1;
                    System.out.println("Moves: "+moves);
                    isPlayer1 = (!isPlayer1);

                    gameEnd = endGame(board);
                }
            } else if(result.equals("1Player")) {
                // runs the one player game mode
                player1 = new HumanPlayer(true);
                AI = new Ai(true);

                clearScreen();

                gameEnd = endGame(board);

                while ( gameEnd == false ) {
                    if (isPlayer1) {
                        board = player1.getMove(board);
                    } else {
                        board = AI.getMove(board);
                    }
                    moves += 1;
                    System.out.println("Moves: "+moves);
                    isPlayer1 = (!isPlayer1);

                    gameEnd = endGame(board);
                }

                //Here if the user writes exit, then the program ends.
            } else if(result.equals("exit")) {
                System.out.println("\n<->-<->-<->-<->-<->-<->-<->-<->-<->-<->");
                System.out.println("\nThanks For Playing! Have A Nice Day " + name + "!");
                System.out.println("\nFeel Free To Check Out Our Website: https://ics4u-summative.nakulpatel0306.repl.co/index.php");
                System.out.println("\nAlso, Your Feedback About Our Game Is Very Much Appreciated!");
                System.out.println("\nYou Can Do So By Filling Out A Form That Is Available On Our Website!");
                System.exit(0);
            } else if(result.equals("gameRules")) {
                // the game rules for checkers, these can also be found on the summative website
                clearScreen();
                System.out.println("******* Checkers Game Rules! *******\n");
                System.out.println("- If you are playing the two player game mode, the white pieces will move first followed by the black pieces. For the single player mode, the player's pieces (white ones) will go first followed by the computer's pieces.\n\n- At the beginning of the game, each piece may only move one diagonal space foward towards their opponents pieces. All pieces must stay on the dark squares on the board. In our console game, the dark squares are represented by dots.\n\n- To capture the opposing player's pieces, you have to 'jump' over it by moving two diagonol spaces in the direction of the opponent's piece. Keep in mind, the space on the other side of your opponent’s piece must be empty in order for you to capture it.\n\n- Take note, when capturing opponents pieces, you can capture multiple pieces if there is space in between each of the opponents piece for you to make the 'jump'.\n\n- If your piece reaches the last row on your opponent's side, you may re-take one of your captured pieces and 'crown' the piece that made it to the final row which makes that piece a 'King Piece'.\n\n- King pieces may still only move one space at a time during a non-capturing move. However, when capturing an opponent's piece(s) it may move diagonally forward or backwards.\n\n- The goal of the game is to eliminate all your opponents pieces. In order to get a higher score, you must win the game in less moves.");

                System.out.println("\n<->-<->-<->-<->-<->-<->-<->-<->-<->-<->");

                // go back to the gameMode screen

                System.out.print("\nPress 1 and Enter to Go Back To The Main Screen!\n");

                do {
                    System.out.print("\nWaiting for Input... ");
                    back = Integer.parseInt(input.nextLine());
                    if (back == 1) {
                        break;
                    } else
                        System.out.println("\nNot A Proper Entry. Please Try Again.");
                } while(back != 1);

                //Here is the code if the leaderboard option is entered.
            } else if(result.equals("leaderboard")) {
                String code = null;
                String decryptedCode = null;
                boolean codeExist = false;

                clearScreen();
                System.out.println("******* Welcome To The Leaderboard! *******");

                do {
                    System.out.println("\nPlease Enter The Code You Were Given When You Finished The Game To Add It To The Leaderboard Or Enter 1 To Go Back To The Menu!");
                    System.out.print("\nCode: ");
                    code = input.nextLine();
                    codeExist = checkForCode(code);

                    if(code.equals("1"))
                        gameMode();

                    if(code.equals("") || code.length() <= 2)
                        System.out.println("\nPlease Enter A Proper Code!");

                    if(codeExist == false)
                        System.out.println("\nThe Code Entered Is Not In The System!");
                } while(code.equals("") || code.length() <= 2 || codeExist == false);

                decryptedCode = decryptCode(code, 0);
                updateLeaderboard(decryptedCode);
            }
        }
    }

    /*****
     Purpose: Determine the proper menu option (i.e game mode, rules, leaderboard)
     *****/

    private static String gameMode() {
        boolean menuOn = true;
        // keep asking until you get a valid response
        while (menuOn) {
            // display menu options
            clearScreen();
            System.out.println("******* Welcome To Checkers! *******\n");
            System.out.println("Enter 'exit' To Exit At Any Point (Or 0 When Moving A Piece)\n");
            System.out.println("Here Are All The Possible Menu Options?");
            System.out.println("\t- [1], 1 Player Mode");
            System.out.println("\t- [2], 2 Player Mode");
            System.out.println("\t- [3], Game Rules");
            System.out.println("\t- [4], Leaderboard");
            System.out.println("\t- [5], Exit Game");

            if(nameEntered == false){
                do {
                    System.out.print("\nPlease Enter Your Name: ");
                    name = input.nextLine();
                } while(name == "");
                nameEntered = true;
            }

            System.out.print("\nSelect The Option You Would Like To Play? Enter A Number: ");
            String response = input.nextLine().trim();

            if (response.equals("2")) {
                return "2Players";
            } else if (response.equals("1")) {
                return "1Player";
            } else if (response.equals("5")) {
                return "exit";
            } else if (response.equals("3")) {
                return "gameRules";
            } else if (response.equals("4")) {
                return "leaderboard";
            } else {
                System.out.println("\nInvalid choice. Please enter a number between 1 and 5.");
                System.out.print("\nPress Enter to try again...");
                input.nextLine(); // Wait for user input before retrying
            }
        }
        return "";
    }

    /*****
     Purpose: Determines whether or not the game is finished, or if the game is in stalemate
     @parameters
     - board; the board to check to determine if we're at an endgame point
     *****/

    private static boolean endGame(Board board) {
        if (endGameNow) // immediate end game
            return true;
        else {
            int movableWhiteNum = 0;
            int movableBlackNum = 0;

            // search the board for pieces of both colors, and if none of one color are present then the other player has won

            for (int pos = 0; pos < board.size * board.size; pos++) {
                Piece pieceHere = board.getValueAt(pos);
                if (pieceHere != null) {
                    Move[] movesHere = pieceHere.getAllPossibleMoves(board);
                    if (movesHere != null && movesHere.length > 0) {
                        if (pieceHere.isWhite)
                            movableWhiteNum++;
                        else if (!pieceHere.isWhite)
                            movableBlackNum++;
                    }
                }
            }

            // print message if anyone has won or has any moves left

            if (movableWhiteNum + movableBlackNum == 0) {
                System.out.println("\nThe Game Was A Stalemate!"); goToMenuScreen();
            } else if (movableWhiteNum == 0) {

                if(result.equals("1Player"))
                    System.out.println("\nWOW, You Seriously Lost To The AI! Pathetic! Anyways, Your Score Will Not Be Updated To The Leaderboard! You Should Try To Beat The Game Next Time!");

                else if(result.equals("2Players")) {
                    System.out.println("\nCongratulations, Black, You Have Won The Game In " + moves + " Moves!");
                    wordToEncrypt = name2 + moves;
                    encryptedWord = encryptedCode(wordToEncrypt, 0);
                    System.out.println("\nYour Name and Score Has Been Saved! Use This Code In The Leaderboard Option On The Menu Screen To Find It Faster On The Leaderboard: " + encryptedWord);
                    addCodeToCodesFile(encryptedWord);
                }

                goToMenuScreen();
            } else if (movableBlackNum == 0) {
                System.out.println("\nCongratulations, White, You Have Won The Game In " + moves + " Moves!");
                wordToEncrypt = name + moves;
                encryptedWord = encryptedCode(wordToEncrypt, 0);
                System.out.println("\nYour Name and Score Has Been Saved! Use This Code In The Leaderboard Option On The Menu Screen To Find It Faster On The Leaderboard: " + encryptedWord);
                addCodeToCodesFile(encryptedWord);

                goToMenuScreen();
            } else
                return false;
            return true;
        }
    }

    /*****
     Purpose: Responsible for quickly ending the game.
     *****/

    public static void endGameNow() {
        endGameNow = true;
    }

    /*****
     Purpose: Clears the terminal screen
     *****/

    final public static void clearScreen() {
        // http://stackoverflow.com/a/32008479/3155372
        System.out.print("\033[2J\033[1;1H");
    }

    /*****
     Purpose: Here is a function that encrypts a word with the player's name and moves at the end.
     *****/
    private static String encryptedCode(String word, int counter) {
        char character;
        char encryptedCharacter;
        int asciiNum = 0;

        if(counter+1>word.length())
            return "";

        else {
            character = word.charAt(counter);
            asciiNum = (int)character;
            asciiNum = asciiNum + 5;
            encryptedCharacter = (char)asciiNum;

            return encryptedCharacter + encryptedCode(word, counter+1);
        }
    }

    /*****
     Purpose: Here we are decrypting the code word that contains the player's name and score.
     *****/

    private static String decryptCode(String word, int counter) {
        char character;
        char decryptedCharacter;
        int asciiNum = 0;

        if(counter+1>word.length())
            return "";

        else {
            character = word.charAt(counter);
            asciiNum = (int)character;
            asciiNum = asciiNum - 5;
            decryptedCharacter = (char)asciiNum;

            return decryptedCharacter + decryptCode(word, counter+1);
        }
    }

    /*****
     Purpose: Here we ar uploading the decoded word into the leaderbaord with the proper styling/formatting.
     *****/

    private static void updateLeaderboard(String data) {
        String filename = "leaderboard.txt";
        String moves = null;
        String name = null;

        try {
            BufferedWriter BW = new BufferedWriter(new FileWriter(filename, true));

            moves = data.substring(data.length()-2);
            name = data.substring(0, data.length()-2);

            BW.write(name+": "+moves+"\r\n");
            BW.close();
        } catch(IOException iox) {
            // statement
        }

        sortLeaderboard(name, moves);
    }

    /*****
     Purpose: Here we are using bubble sort to sort the updated leaderboard and make it so that the lowest score is at the top and highest at the bottom.
     *****/

    public static void sortLeaderboard(String name, String score) {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> moves = new ArrayList<String>();
        String[] line;
        String temp1;
        String temp2;
        String data;
        String filename = "leaderboard.txt";

        try {
            BufferedReader BR = new BufferedReader(new FileReader(filename));

            data = BR.readLine();

            while(data != null) {
                line = data.split(": ");
                moves.add(line[1]);
                names.add(line[0]);
                data = BR.readLine();
            }

            for (int x=0; x < moves.size(); x++)
            {
                for (int i=0; i < moves.size()-1; i++) {
                    if (moves.get(i).compareTo(moves.get(i+1)) > 0)
                    {
                        temp1 = moves.get(i);
                        moves.set(i,moves.get(i+1) );
                        moves.set(i+1, temp1);

                        temp2 = names.get(i);
                        names.set(i,names.get(i+1) );
                        names.set(i+1, temp2);
                    }
                }
            }

            BR.close();

        } catch(IOException iox) {
            // statement
        }

        System.out.println("\n*** Checker's Leaderboard ***");
        System.out.println("\n** Remember The Lower The Score The Better! **\n");

        for(int x = 0; x < names.size(); x++) {
            System.out.println(names.get(x)+": "+moves.get(x));
        }

        try {
            BufferedWriter BW = new BufferedWriter(new FileWriter(filename));

            for(int x = 0; x < names.size(); x++){
                BW.write(names.get(x) + ": " + moves.get(x) + "\r\n");
            }

            BW.close();
        } catch(IOException iox) {
            // statement
        }

        goToMenuScreen();
    }

    /*****
     Purpose: Here is a function that is called whenever we need to go back to the menu screen.
     *****/

    public static void goToMenuScreen() {
        String goBackVal = null;
        do {
            System.out.print("\nTo Go Back To The Menu, Please Enter 1: ");
            goBackVal = input.nextLine();

        } while(!goBackVal.equals("1"));
    }

    /*****
     Purpose: Here when the code is created at the end of the game, the code is uploading to a separate text file with a list of codes from previous winners.
     ****/

    private static void addCodeToCodesFile(String code) {
        String filename = "codes.txt";

        try {
            BufferedWriter BW = new BufferedWriter(new FileWriter(filename, true));

            BW.write(code+"\r\n");

            BW.close();

        } catch(IOException iox) {
            // statement
        }
    }

    /*****
     Purpose: Here is a search algorithm that checks if a code entered.
     *****/

    private static boolean checkForCode(String checkCode) {
        String filename = "codes.txt";
        String line = null;
        boolean codeFound = false;

        try {

            BufferedReader BR = new BufferedReader(new FileReader(filename));

            line = BR.readLine();

            while(line!=null) {
                if(line.equals(checkCode)){
                    codeFound = true;
                }
                line = BR.readLine();
            }

            BR.close();

        } catch(IOException iox) {
            // statement
        }

        if(codeFound == true)
            return true;
        else
            return false;
    }
}