/**
 * AIPlayer class implements the Minimax algorithm to find the optimal move
 * for the AI player in a Tic-Tac-Toe game.
 */

public class AIPlayer {
    // once the variable is assigned, it cannot be changed!
    private final char ai; // The symbol used by the AI player (e.g., 'O')
    private final char human; // The symbol used by the human player (e.g., 'X')

    // Constructor: initializes the AI and human player symbols
    public AIPlayer(char ai, char human) {
        this.ai = ai;
        this.human = human;
    }

    // Find the best possible move for the AI using the Minimax algorithm
    public int[] findBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE; // Initialize best score
        int[] bestMove = {-1, -1}; // Initialize best move

        // Try all available moves on a copy of the board
        for (int[] move : board.getAvailableMoves()) { 
            char[][] copy = board.getBoardCopy(); // Create a copy to simulate the move
            copy[move[0]][move[1]] = ai; // Simulate AI move
            int score = minimax(copy, false); // Recursively evaluate the move
            if (score > bestScore) { // If this move is better, update best move
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove; // Return the move with the highest score
    }

    // Minimax algorithm to evaluate board states
    private int minimax(char[][] board, boolean isMaximizing) {
        // Base cases: win, loss, draw
        if (checkWin(board, ai)) return 10; // AI wins
        if (checkWin(board, human)) return -10; // Human wins
        if (isFull(board)) return 0; // Draw

        // AI's turn (maximize score)
        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int[] move : getAvailableMoves(board)) {
                board[move[0]][move[1]] = ai; // Try AI move
                best = Math.max(best, minimax(board, false)); // Recurse for human turn
                board[move[0]][move[1]] = ' '; // Undo move
            }
            return best;

        // Human's turn (minimize score)
        } else {
            int best = Integer.MAX_VALUE;
            for (int[] move : getAvailableMoves(board)) {
                board[move[0]][move[1]] = human; // Try human move
                best = Math.min(best, minimax(board, true)); // Recurse for AI turn
                board[move[0]][move[1]] = ' '; // Undo move
            }
            return best;
        }
    }

    // Check if a player has won the given board
    private boolean checkWin(char[][] board, char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++)
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player))
                return true;

        // Check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player))
            return true;

        return false;
    }

    // Checks if the given board is full (no more valid moves)
    private boolean isFull(char[][] board) {
        for (char[] row : board)
            for (char cell : row)
                // Check every cell in the row
                if (cell == ' ') return false; // Found an empty cell
        return true;
    }

    // Get a list of available (empty) cells on the board
    private java.util.List<int[]> getAvailableMoves(char[][] board) {
        java.util.List<int[]> moves = new java.util.ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') moves.add(new int[]{i, j});
        return moves;
    }
}
