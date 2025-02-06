/*
Authors: Nakul Patel & Mihir Patel
Start Date: June 5th, 2021
Responsible for the interface of the program. Here are some variables that only some classes require, so in those classes we implement this interface
*/

interface textColour {
    // These variables are used to change the text colour in the console,so only classes that have an console output implement this interface.
    String whiteTextColour = "\u001B[37m";
    String blackTextColour = "\u001B[30m";
    String resetTextColour = "\u001B[36m";
}