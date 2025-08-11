# Tic Tac Toe

A simple Java implementation of the classic Tic-Tac-Toe game featuring:

- **Console-based** play (Player vs AI)
- **GUI-based** play using Java Swing (Player vs AI with a graphical interface)
- AI powered by the **Minimax algorithm** for optimal moves

---

## Running the Game

You can build and run the project using the provided `run.sh` script.

```bash
./run.sh
```

Inside the run.sh script, you have 2 options of the game:
1. Console-based game
    Run the main console game class: java Main

2. GUI-based game (Java Swing)
    Run the GUI version: java TicTacToeGUI

## Features:
GUI Version (TicTacToeGUI)
- Interactive 3x3 grid using buttons with smooth rounded borders and extra padding on the Reset button
- Human player plays 'X' with blue colored moves
- AI plays 'O' with red colored moves
- Pastel red background highlights AI's winning line; pastel blue background highlights player's winning line
- Status messages display current turn, win, or draw
- Reset button to start a new game anytime with improved UI styling and spacing

Console Version (Game)
- Text-based 3x3 board display
- Human vs AI turn-based play
- Input moves by entering row and column (e.g., 1,1)
- Prompts to replay after each game

## Code Overview
- TicTacToeGUI.java: Java Swing GUI implementation with event-driven gameplay, colored moves, and custom reset button styling.
- Board.java: Represents the game board state, move validation, win/draw detection, and board reset.
- Game.java: Console-based game logic with text input handling and game loop.
- AIPlayer.java: AI implementation using Minimax algorithm for optimal Tic-Tac-Toe moves.