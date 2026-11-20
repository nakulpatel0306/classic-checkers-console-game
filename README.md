# Checkers - Java Console Game

Standard 8x8 Checkers in the terminal, against a friend on the same machine or a computer opponent that plays random legal moves.

## At a Glance

- **Stack:** Java (no external libraries)
- **Context:** ICS4U summative project, built with Mihir Patel in 2021
- **State:** Complete

## Features

- Two players on one machine, or one player against the computer
- Captures, multi-jump turns and king promotion
- Legal moves numbered on the board, so you pick a piece and then a move number
- Coloured board output in the terminal
- Winners get a code that adds their name and move count to a leaderboard saved in `leaderboard.txt`
- Built-in rules screen

## The Computer Opponent

The computer is not a search-based AI. On its turn it picks one of its pieces at random, then a random move from that piece's legal moves. It's there so you can play alone, not to be hard to beat.

## Project Structure

```
Checkers/
├── Checkers.iml           # IntelliJ module file
└── src/
    ├── Main.java          # Menu, game loop, win checks, leaderboard and codes
    ├── Board.java         # Board state and applying moves
    ├── Piece.java         # Men and kings, legal moves and chained jumps
    ├── Move.java          # A single move and the pieces it jumps
    ├── Player.java        # Base class for human players
    ├── HumanPlayer.java   # Keyboard input and board display
    ├── Computer.java      # Base class for the computer player
    ├── Ai.java            # Random move selection for the computer
    ├── textColour.java    # Terminal colour codes
    ├── codes.txt          # Codes handed out to winners
    └── leaderboard.txt    # Saved names and move counts
```

## Running Locally

1. Clone the repo and move into the source folder:
   ```bash
   git clone https://github.com/nakulpatel0306/classic-checkers-console-game.git
   cd classic-checkers-console-game/Checkers/src
   ```
2. Compile and run (any recent JDK). Run from `src` so the game finds its text files:
   ```bash
   javac *.java
   java Main
   ```

## How to Play

- White moves first; in one-player mode you are White
- Select a piece by its coordinates (for example `C3`), then pick one of the numbered moves, or `0` to choose again
- Capture by jumping over an opponent's piece, and chain jumps in one turn
- Reach the far side to become a king, which can move and capture backwards
- Win by capturing every enemy piece or leaving them with no moves; fewer moves means a better leaderboard score
- Type `exit` at the piece prompt to leave a game

## Team

Nakul Patel and Mihir Patel
