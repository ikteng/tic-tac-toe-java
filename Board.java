import java.util.ArrayList;
import java.util.List;

/**
 * Represents the 3x3 Tic-Tac-Toe game board.
 * Provides functionality to make moves, check win/draw conditions, 
 * reset the board, and get available moves for the AI.
 */

public class Board {
    private final char[][] board; // 2D array to hold board state: 'X', 'O', or ' '

    // Constructor: initializes the board with empty spaces
    public Board() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    // Attempts to make a move on the board
    public boolean makeMove(int row, int col, char player) {
        // Check if the move is valid
        if (row < 0 || row >= 3 || col < 0 || col >= 3) return false; // out of bound
        if (board[row][col] != ' ') return false; // cell already taken => not empty
        
        // apply move
        board[row][col] = player;
        return true;
    }

    // Checks if the board is completely filled (no move valid moves)
    public boolean isFull() {
        for (char[] row : board)
            for (char cell : row)
                // Check every row & column
                if (cell == ' ') return false; // found an empty cell => board is not completely filled
        return true;
    }

    // Check if given player has won the game
    public boolean checkWin(char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++)
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player))
                return true;

        // Check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player))
            return true;

        return false; // no win found
    }

    // Get a list of available (empty) cells on the board
    public List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') moves.add(new int[]{i, j});
        return moves;
    }

    // Print the current state of the board
    public void print() {
        System.out.println("   0   1   2");
        System.out.println("  -------------");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n  -------------");
        }
    }

    // Get a copy of the board => AI simulation
    // @see AIPlayer
    public char[][] getBoardCopy() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(board[i], 0, copy[i], 0, 3);
        return copy;
    }

    // Resets the board to initial empty state
    public void reset() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

}
