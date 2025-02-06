/*
Authors: Nakul Patel & Mihir Patel
Start Date: June 6th, 2021
Resposible for communicating with the human player and serving as a interface for the main game
*/

import java.util.Scanner;

public class HumanPlayer extends Player implements textColour {
    /* GLOBAL VARIABLES */
    Scanner input = new Scanner(System.in);
    boolean isWhite;

    String moveTextColour = "";

    /*****
     Purpose: Constructor for the human player
     @parameters
     - isWhite; the color of this player, to be used to identify the user
     *****/

    public HumanPlayer(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /*****
     Purpose: Gets a move by asking the user what move they want to do
     @parameters
     - board; the board to apply the move to
     *****/

    public Board getMove(Board board) {
        displayBoard(board, null);
        Move[] possibleMoves;

        // keep asking until user gives a valid move
        while (true) {
            Piece pieceMoving = getPieceFromUser(board);

            if (pieceMoving == null)
                return board;


            // finds all possible moves the user can make
            possibleMoves = pieceMoving.getAllPossibleMoves(board);

            if (possibleMoves == null)
                System.out.println(whiteTextColour+"\nThat Piece Has No Possible Moves! Please Choose Another!");
            else {
                // show the user all the possible moves
                displayBoard(board, possibleMoves);
                Move move = getMoveFromUser(possibleMoves);

                // apply move to board and return it if the user entered a valid one
                if (move != null) {
                    board.applyMove(move, pieceMoving);
                    return board;
                }
            }
        }
    }

    /*****
     Purpose: Responsible for displaying the game board to the user
     @parameters
     - board; the board to be displayed
     - possibleMoves; an array of possible moves to display while printing the board
     *****/

    private void displayBoard(Board board, Move[] possibleMoves) {
        Main.clearScreen();

        /* DESIGNS & PRINTS THE BOARD */
        for (int y = -1; y < board.size; y++) {
            for (int x = -1; x < board.size; x++) {
                if (y == -1) {
                    if (x != -1)
                        System.out.print(whiteTextColour+"-" + (char)(x + 65) + "- "+resetTextColour);
                    else
                        System.out.print("     ");
                } else if (x == -1) {
                    if (y != -1)
                        System.out.print(whiteTextColour+"-" + (y + 1) + "- "+resetTextColour);
                } else {
                    Piece thisPiece = board.getValueAt(x, y);

                    // if there are any, loop over the possible moves and see if any end at this space
                    if (possibleMoves != null) {
                        boolean moveFound = false;

                        for (int i = 0; i < possibleMoves.length; i++) {
                            int[] move = possibleMoves[i].getEndingPosition();
                            if (move[0] == x && move[1] == y) {
                                if(getColor().equals("White"))
                                    moveTextColour = whiteTextColour;
                                else if(getColor().equals("Black"))
                                    moveTextColour = blackTextColour;

                                System.out.print("| "+moveTextColour+Integer.toString(i+1) +" "+resetTextColour);
                                moveFound = true;
                            }
                        }

                        // if a move is found here, skip our other possible printings
                        if (moveFound)
                            continue;
                    }

                    if (thisPiece != null){
                        System.out.print(whiteTextColour+"| "+resetTextColour + thisPiece.getString());
                    }
                    else if (board.isCheckerboardSpace(x, y))
                        System.out.print(whiteTextColour+"| . "+resetTextColour);
                    else
                        System.out.print(whiteTextColour+"|   "+resetTextColour);
                }
            }
            System.out.println();
        }
    }

    /*****
     Purpose: Asks the user for a piece on the board (makes sure it is an actual piece of their color)
     @parameters
     - board; the board to check against
     *****/

    private Piece getPieceFromUser(Board board) {
        // keep trying to get a piece until we get a valid peice chosen
        while (true) {
            String coordinateInput;

            System.out.print(whiteTextColour+"\n" + getColor() +", Please Select A Piece By Its Coordinates (i.e. A3): ");

            try {
                coordinateInput = input.nextLine().toLowerCase();

                if (coordinateInput.equalsIgnoreCase("exit")) {
                    Main.endGameNow();
                    return null;
                    // ensure a valid coordinate input
                } else if (coordinateInput.length() < 2)
                    throw new Exception();

                //  if the user entered the # coordinate first flip them if it's the other way around
                char letterChar = coordinateInput.charAt(0);
                char numberChar = coordinateInput.charAt(1);

                if (letterChar < 97)  {
                    letterChar = numberChar;
                    numberChar = coordinateInput.charAt(0);
                }

                int x = letterChar - 97;
                int y = numberChar - 48 - 1;

                System.out.println(whiteTextColour+"D: "+x+" "+y);


                // ensure there's no out-of-bounds entries
                if (board.isOverEdge(x, y))
                    throw new Exception();

                // get the actual piece there and see if it is valid
                Piece userPiece = board.getValueAt(x, y);

                if (userPiece == null)
                    System.out.println(whiteTextColour+"\nThere Is No Piece There!"+resetTextColour);
                else if (userPiece.isWhite != this.isWhite)
                    System.out.println(whiteTextColour+"\nThat's Not Your Piece!"+resetTextColour);
                else
                    return userPiece;
            } catch (Exception e) {
                System.out.println(whiteTextColour+"\nPlease Enter A Coordinate On The Board In The Form '[letter][number]'!"+resetTextColour);
                continue;
            }
        }
    }

    /*****
     Purpose: Asks the user for a number representing a move of a particular piece (makes sure it is an available move)
     @parameters
     - possibleMoves; the list of possible moves the user can request
     *****/

    private Move getMoveFromUser(Move[] possibleMoves) {
        int moveNum;

        // keep trying again until we get a valid move chosen
        while (true) {
            System.out.print("\n"+whiteTextColour + getColor() + ", Please Select A Move By Its Number (Enter 0 To Go Back): ");

            try {
                moveNum = input.nextInt();
                input.nextLine();

                if (moveNum == 0) {
                    return null;
                    // ensure the user enters a move that we printed
                } else if (moveNum > possibleMoves.length)
                    throw new Exception();

                // return the move the user entered once we get a valid entry
                return possibleMoves[moveNum - 1];
            } catch (Exception e) {
                System.out.println(whiteTextColour+"\nPlease Enter One Of The Numbers On The Board Or 0 To Exit.");
                input.nextLine();
            }
        }
    }

    private String getColor() {
        return isWhite ? "White" : "Black";
    }
}