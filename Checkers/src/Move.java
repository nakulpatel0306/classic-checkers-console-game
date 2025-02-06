/*
Authors: Nakul Patel & Mihir Patel
Start Date: June 7th, 2021
Responsible for representing a single move of a game piece
*/

import java.util.*;

public class Move {
    int x1, y1, x2, y2;
    boolean isJump;
    Move precedingMove;

    /*****
     Purpose: Constructor for objects of class Move; initializes the starting and final position
     @parameters
     - x1; starting x position
     - y1; starting y position
     - x2; ending x position
     - y2; ending y position
     - precedingMove;  the move preceeding this one
     *****/

    public Move(int x1, int y1, int x2, int y2, Move precedingMove, boolean isJump) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.precedingMove = precedingMove;
        this.isJump = isJump;
    }

    /*****
     Purpose: To return a two-part array representing the coordinates of this move's starting position
     *****/

    public int[] getStartingPosition() {
        int[] position = new int[2];
        position[0] = x1;
        position[1] = y1;

        return position;
    }

    /*****
     Purpose: To return a two-part array representing the coordinates of this move's ending position
     *****/

    public int[] getEndingPosition() {
        int[] position = new int[2];
        position[0] = x2;
        position[1] = y2;

        return position;
    }

    /*****
     Purpose: Find the pieces jumped within this move recursvily
     @parameters
     - board; the board to look for the pieces on
     *****/

    public Piece[] getJumpedPieces(Board board) {
        // if this move wasn't a jump, the didn't jump a piece
        if (isJump) {
            // create expandable list of all pieces
            ArrayList<Piece> pieces = new ArrayList<Piece>();

            // average of the start and end position should be the piece being jumped
            int pieceX = (x1 + x2) / 2;
            int pieceY = (y1 + y2) / 2;

            pieces.add(board.getValueAt(pieceX, pieceY));

            if (precedingMove != null) {
                /* RECURSION */
                pieces.addAll(Arrays.asList(precedingMove.getJumpedPieces(board)));
            }

            pieces.trimToSize();

            // convert the piece to an array
            return pieces.toArray(new Piece[1]);
        } else {
            return null;
        }
    }
}