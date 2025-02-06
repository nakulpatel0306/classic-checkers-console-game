/*
Authors: Nakul Patel & Mihir Patel
Start Date: June 5th, 2021
Stores and handles interactions with the board.
*/

public class Board {
    /* GLOBAL VARIABLES */
    public Piece[][] boardArray;
    public int size;

    /*****
     Purpose: Generates the board
     @parameters
     - size; the size of the board
     *****/
    public Board(int size) {
        this.boardArray = new Piece[size][size];
        this.size = size;
        setBoard();
    }

    /*****
     Purpose: Generates the board based on another board
     @parameters
     - board;
     *****/

    public Board(Board board) {
        this.size = board.size;
        this.boardArray = board.boardArray;
    }

    /*****
     Purpose: Fills the board with pieces at the starting positions
     *****/

    public void setBoard() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (y < 3 && isCheckerboardSpace(x, y)) { // add the white pieces at the top
                    this.boardArray[y][x] = new Piece(x, y, true);
                } else if (y >= size - 3 && isCheckerboardSpace(x, y)) { // add the black pieces at the bottom
                    this.boardArray[y][x] = new Piece(x, y, false);
                }
            }
        }
    }

    /*****
     Purpose: Uses the given move and piece to move the piece on the board
     @parameters
     - move; the Move object to execute on the piece and board
     - piece; the Piece object that will be moved
     *****/

    public void applyMove(Move move, Piece piece) {
        int[] moveStartingPos = piece.getCoordinates();
        int[] moveEndingPos = move.getEndingPosition();

        // find any pieces that have been jumped and remove them as well
        Piece[] jumpedPieces = move.getJumpedPieces(this);
        if (jumpedPieces != null) {
            // loop over all jumped pieces and remove them
            for (int i = 0; i < jumpedPieces.length; i++) {
                if (jumpedPieces[i] != null) {
                    this.setValueAt(jumpedPieces[i].getCoordinates()[0], jumpedPieces[i].getCoordinates()[1], null);
                }
            }
        }

        this.setValueAt(moveStartingPos[0], moveStartingPos[1], null);
        piece.moveTo(moveEndingPos[0], moveEndingPos[1]);

        piece.checkIfShouldBeKing(this);

        this.setValueAt(moveEndingPos[0], moveEndingPos[1], piece);
    }

    /*****
     Purpose: Sets the space at these coordinates to the given Piece object.
     @parameters
     - x; the x position of the Piece
     - y; the y position of the Piece
     - piece; the Piece to put in this space, but can be null to make the space empty
     *****/

    private void setValueAt(int x, int y, Piece piece) {
        this.boardArray[y][x] = piece;
    }

    /*****
     Purpose: Sets the space at this number position to the given Piece object
     @parameters
     - position; the number position, zero indexed at top left
     - piece; the Piece to put in this space, but can be null to make the space empty
     *****/

    private void setValueAt(int position, Piece piece) {
        int[] coords = getCoordinatesFromPosition(position);

        this.setValueAt(coords[0], coords[1], piece);
    }

    /*****
     Purpose: Get's the Piece object at this location.
     @parameters
     - x; the x position of the Piece
     - y; the y position of the Piece
     *****/

    public Piece getValueAt(int x, int y) {
        return this.boardArray[y][x];
    }

    /*****
     Purpose: Get's the Piece object at this location, but using a single number
     @parameters
     - position; this number, zero indexed at top left
     *****/

    public Piece getValueAt(int position) {
        int[] coords = getCoordinatesFromPosition(position);

        return this.getValueAt(coords[0], coords[1]);
    }

    /*****
     Purpose: Converts a single position value to x and y coordinates
     @parameters
     - position; the single position value, zero indexed at top left.
     *****/

    public int[] getCoordinatesFromPosition(int position) {
        int[] coords = new int[2];

        coords[0] = position % this.size;
        coords[1] = position / this.size;

        return coords;
    }

    /*****
     Purpose: Converts from x and y coordinates to a single position value
     @parameters
     - x; the x coordinate
     - y; the y coordinate
     *****/

    public int getPositionFromCoordinates(int x, int y) {
        return this.size * y + x;
    }

    /*****
     Purpose: Get's the dot position on the board representing the black square on a checkers board
     @parameters
     - x; the x location of the space
     - y; the y location of the space
     *****/

    public boolean isCheckerboardSpace(int x, int y) {
        return x % 2 == y % 2;
    }

    /*****
     Purpose: Checks if the given coordinates are over the game board edge
     @parameters
     - x; the x coordinate of the position
     - y; the y coordinate of the position
     *****/

    public boolean isOverEdge(int x, int y) {
        return (x < 0 || x >= this.size || y < 0 || y >= this.size);
    }

    /*****
     Purpose: Checks if the given position is over the game board edge
     @parameters
     - position; the given 0-indexed position value
     *****/

    public boolean isOverEdge(int position) {
        int[] coords = getCoordinatesFromPosition(position);

        return this.isOverEdge(coords[0], coords[1]);
    }
}