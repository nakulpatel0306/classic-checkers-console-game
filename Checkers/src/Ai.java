/*** DISCLAIMER: THE AI IS AN AI, JUST NOT THE SMARTEST ***/

/*
Authors: Nakul Patel & Mihir Patel
Start Date: June 6th, 2021
Responsible for the AI class which is a child class of Computer which also implements an interface for console styling
*/

import java.util.Scanner;
import java.util.*;

public class Ai extends Computer implements textColour {
    Scanner input = new Scanner(System.in);
    boolean isWhite;

    public Ai(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /*****
     Purpose: This function will take a move by the AI and check if the piece selected has any possible moves.
     *****/

    public Board getMove(Board board) {
        displayBoard(board, null);
        Move[] possibleMoves;

        while (true) {
            Piece pieceMoving = getPieceFromComp(board);

            if (pieceMoving == null)
                return board;

            possibleMoves = pieceMoving.getAllPossibleMoves(board);

            if (possibleMoves != null){
                displayBoard(board, possibleMoves);
                Move move = getMoveFromComp(possibleMoves);

                if (move != null) {
                    board.applyMove(move, pieceMoving);
                    return board;
                }
            }
        }
    }

    /*****
     Purpose: This function will display the updated board with possible moves on the board.
     *****/

    private void displayBoard(Board board, Move[] possibleMoves) {
        Main.clearScreen();

        // Here we are displaying the board and adding all pieces, ushc as W, B, and King pieces.
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

                    if (possibleMoves != null) {
                        boolean moveFound = false;

                        for (int i = 0; i < possibleMoves.length; i++) {
                            int[] move = possibleMoves[i].getEndingPosition();
                            if (move[0] == x && move[1] == y) {
                                System.out.print(whiteTextColour+"| " + Integer.toString(i+1) + " "+resetTextColour);
                                moveFound = true;
                            }
                        }

                        if (moveFound)
                            continue;
                    }

                    // Here we are adding certain characters to style the board.
                    if (thisPiece != null){
                        System.out.print(whiteTextColour+"| " + thisPiece.getString()+resetTextColour);
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
     Purpose: This function will choose a random spot on the board over and over again until its own piece is found.
     *****/

    private Piece getPieceFromComp(Board board) {
        Piece compPiece = null;

        System.out.print(whiteTextColour+"\nComputer is Thinking...");

        // below is a brief timer before the computer makes it move to act like the computer is thinking of its move
        try {
            Thread.sleep(1500);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        while(true) {
            int letterCoor = (int)(Math.random() * 7) + 1;
            int numberCoor = (int)(Math.random() * 7) + 1;

            compPiece = board.getValueAt(letterCoor, numberCoor);

            if ((compPiece != null) && (compPiece.isWhite != this.isWhite))
                return compPiece;
        }
    }

    /*****
     Purpose: This method will choose a random number from all possible moves.
     *****/

    private Move getMoveFromComp(Move[] possibleMoves) {
        int moveNum = (int)(Math.random()*possibleMoves.length)+1;
        return possibleMoves[moveNum - 1];
    }
}