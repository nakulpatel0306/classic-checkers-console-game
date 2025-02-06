/*
Authors: Nakul Patel & Mihir Patel
Start Date: June 6th, 2021
An abstract version of a computer, from which Ai will be extended
*/

public abstract class Computer {
    /*****
     Purpose: Gets a move, by asking the computer what move they want to do
     @parameters
     - board; the board where the move is applied to
     *****/
    public abstract Board getMove(Board board);
}