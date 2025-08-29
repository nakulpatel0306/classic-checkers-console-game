# ♟️ Checkers - Console Game (Java)

A simple **Java-based console Checkers game**.  
Play against another human or a computer opponent in the terminal with standard Checkers rules.

---

## ✨ Features

- Standard 8x8 Checkers board  
- Two-player local or vs Computer (AI)  
- Piece capturing and multiple jumps  
- King promotion when reaching the opposite side  
- Console-based interface with colored text  
- Leaderboard tracking via `leaderboard.txt`  

---

## 🧱 Project Structure

```
Checkers/
├── src/
│   ├── Main.java          # Entry point of the game
│   ├── Board.java         # Board representation and logic
│   ├── Piece.java         # Piece rules (men and kings)
│   ├── Move.java          # Move validation and handling
│   ├── Player.java        # Abstract player class
│   ├── HumanPlayer.java   # Human player input
│   ├── Computer.java      # Simple AI opponent
│   ├── Ai.java            # AI logic for moves
│   ├── textColour.java    # Utility for colored console text
│   ├── codes.txt          # Game codes (optional features)
│   └── leaderboard.txt    # Stores player scores
└── out/                   # Compiled class files
```

---

## 🚀 Getting Started

1. **Compile the game** from the `src` folder:
   ```bash
   javac *.java
   ```

2. **Run the game**:
   ```bash
   java Main
   ```

---

## 🎮 How to Play

- Players take turns moving pieces diagonally.  
- Capture opponent pieces by jumping over them.  
- Multiple jumps are allowed in one turn.  
- Reach the opposite side to become a **King**.  
- Win by capturing all opponent pieces or blocking moves.  
