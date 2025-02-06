/*
Authors: Nakul Patel & Mihir Patel
Start Date: June 7th, 2021
Responsible for representing a game piece and handling the piece's interactions
*/

import java.util.*;

public class Piece implements textColour {
    private int x;
    private int y;
    private boolean isKing = false;
    public boolean isWhite;

    /*****
     Purpose: Constructor for objects of class Piece; initializes position and color
     @parameters
     - x; the x position of the piece
     - y; the y position of the piece
     - isWhite; whether th epiece is white or black
     *****/

    public Piece(int x, int y, boolean isWhite) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
    }

    /*****
     Purpose: To return a two-part array representing the coordinates of this piece's position
     *****/

    public int[] getCoordinates() {
        int[] coordinates = new int[2];
        coordinates[0] = this.x;
        coordinates[1] = this.y;

        return coordinates;
    }

    /*****
     Purpose: To return a string reprensentation of this given piece
     *****/

    public String getString() {
        String baseSymbol;

        if (isWhite)
            baseSymbol = whiteTextColour+"W"+resetTextColour;
        else
            baseSymbol = blackTextColour+"B"+resetTextColour;

        if (isKing && isWhite)
            baseSymbol += whiteTextColour+"K"+resetTextColour;
        else if (isKing && !isWhite)
            baseSymbol += blackTextColour +"K"+resetTextColour;
        else
            baseSymbol += " ";

        return baseSymbol;
    }

    /*****
     Purpose: Switches the piece to a king
     *****/

    private void setKing() {
        isKing = true;
    }

    /*****
     Purpose: Switches the piece to be a king if it is at the end of the board
     *****/

    public void checkIfShouldBeKing(Board board) {
        // if the piece is white, it's a king if it's at the +y side
        // if the piece is black, it's a king if it's at the -y side
        if (isWhite && this.y == board.size - 1 || !isWhite && this.y == 0)
            this.setKing();
    }

    /*****
     Purpose: Moves this piece's reference of its position (does not actually move piece on the board)
     @parameters
     - x; the x coordinate of the move
     - y; the y coordinate of the move
     *****/

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*****
     Purpose: Generates all physically possible non-jumping moves of the given piece
     @parameters
     - board; the board to work with
     *****/

    public Move[] getAllPossibleMoves(Board board) {
        // create expandable list of all moves
        ArrayList<Move> moves = new ArrayList<Move>();

        int startingY, yIncrement;

        if (isWhite) {
            startingY = this.y + 1;
            yIncrement = -2;
        } else {
            startingY = this.y - 1;
            yIncrement = 2;
        }

        int rowsToCheck = 1;

        if (this.isKing)
            rowsToCheck = 2;

        // iterate over the four spaces where normal (non-jumping) moves are possible
        for (int x = this.x - 1; x <= this.x + 1; x += 2) {
            int y = startingY - yIncrement;

            for (int i = 0; i < rowsToCheck; i++) {
                y += yIncrement;

                if (board.isOverEdge(x, y))
                    continue;

                if (board.getValueAt(x, y) == null) {
                    moves.add(new Move(this.x, this.y, x, y, null, false));
                }
            }
        }

        // after we've checked all normal moves, look for and add all possible jumps
        Move[] possibleJumps = this.getAllPossibleJumps(board, null);

        if (possibleJumps != null)
            moves.addAll(Arrays.asList(possibleJumps));

        // if there are some moves, trim and return ArrayList as a normal array
        if (!moves.isEmpty()) {
            moves.trimToSize();

            return moves.toArray(new Move[1]);
        } else {
            return null;
        }
    }

    /*****
     Purpose: Finds all jumping moves originating from this piece recursivly
     @parameters
     - board; the board to work with
     - precedingMove; the moves preceding the call to search for moves off this piece
     *****/

    private Move[] getAllPossibleJumps(Board board, Move precedingMove) {
        // create expandable list of all moves
        ArrayList<Move> moves = new ArrayList<Move>();
        int startingY, yIncrement;

        if (isWhite) {
            startingY = this.y + 2;
            yIncrement = -4;
        } else {
            startingY = this.y - 2;
            yIncrement = 4;
        }

        int rowsToCheck = 1;

        if (this.isKing)
            rowsToCheck = 2;

        // iterate over the four spaces where normal (non-jumping) moves are possible
        for (int x = this.x - 2; x <= this.x + 2; x += 4) {
            int y = startingY - yIncrement;
            for (int i = 0; i < rowsToCheck; i++) {
                y += yIncrement;

                if (board.isOverEdge(x, y))
                    continue;

                if (precedingMove != null && x == precedingMove.getStartingPosition()[0] &&  y == precedingMove.getStartingPosition()[1])
                    continue;

                // test if there is a different-colored piece between the average of our position and the starting point and that there's no piece in the planned landing space
                Piece betweenPiece = board.getValueAt( (this.x + x)/2 , (this.y + y)/2 );

                if (betweenPiece != null && betweenPiece.isWhite != this.isWhite && board.getValueAt(x, y) == null) {
                    Move jumpingMove = new Move(this.x, this.y, x, y, precedingMove, true);
                    moves.add(jumpingMove);
                    // after jumping, create an imaginary piece as if it was there to look for more jumps
                    Piece imaginaryPiece = new Piece(x, y, this.isWhite);

                    if (this.isKing) imaginaryPiece.setKing();

                    // find possible subsequent moves recusivly
                    Move[] subsequentMoves = imaginaryPiece.getAllPossibleJumps(board, jumpingMove);

                    // add these moves to the list if they exist, otherwise just move on to other possibilities
                    if (subsequentMoves != null)
                        moves.addAll(Arrays.asList(subsequentMoves));
                }
            }
        }

        // if there are some moves, shorten and return ArrayList as a normal array
        if (!moves.isEmpty()) {
            moves.trimToSize();
            return moves.toArray(new Move[1]);
        } else {
            return null;
        }
    }
}