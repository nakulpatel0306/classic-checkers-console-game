# Checkers - Java Console Game

Standard 8x8 Checkers in the terminal, against a friend or a computer opponent.

## At a Glance

- **Stack:** Java (no external libraries)
- **State:** Complete

## Features

- Two players on one machine, or play against the computer
- Captures, multi-jump turns and king promotion
- Coloured board output in the terminal
- Leaderboard saved between runs in `leaderboard.txt`

## Project Structure

```
Checkers/
└── src/
    ├── Main.java          # Entry point
    ├── Board.java         # Board state and rendering
    ├── Piece.java         # Men and kings
    ├── Move.java          # Move validation
    ├── Player.java        # Base player class
    ├── HumanPlayer.java   # Keyboard input
    ├── Computer.java      # Computer player
    ├── Ai.java            # Move selection for the computer
    ├── textColour.java    # Terminal colour helpers
    ├── codes.txt          # Game codes
    └── leaderboard.txt    # Saved scores
```

## Running Locally

1. Clone the repo and move into the source folder:
   ```bash
   git clone https://github.com/nakulpatel0306/classic-checkers-console-game.git
   cd classic-checkers-console-game/Checkers/src
   ```
2. Compile and run:
   ```bash
   javac *.java
   java Main
   ```

## How to Play

- Move pieces diagonally, one square at a time
- Capture by jumping over an opponent's piece, and chain jumps in one turn
- Reach the far side to become a king
- Win by capturing every enemy piece or leaving them with no moves
