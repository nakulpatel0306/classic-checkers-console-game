/*
Authors: Nakul Patel & Mihir Patel
Start Date: June 6th, 2021
An abstract version of a player, from which Human Players will be extended
*/

public abstract class Player {
    /*****
     Purpose: Gets a move, by asking the given player what move they want to do
     @parameters
     - board; the board where the move is applied to
     *****/
    public abstract Board getMove(Board board);
}