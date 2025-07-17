public class AIPlayer {
    private final char ai;
    private final char human;

    public AIPlayer(char ai, char human) {
        this.ai = ai;
        this.human = human;
    }

    public int[] findBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int[] move : board.getAvailableMoves()) {
            char[][] copy = board.getBoardCopy();
            copy[move[0]][move[1]] = ai;
            int score = minimax(copy, false);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimax(char[][] board, boolean isMaximizing) {
        if (checkWin(board, ai)) return 10;
        if (checkWin(board, human)) return -10;
        if (isFull(board)) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int[] move : getAvailableMoves(board)) {
                board[move[0]][move[1]] = ai;
                best = Math.max(best, minimax(board, false));
                board[move[0]][move[1]] = ' ';
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int[] move : getAvailableMoves(board)) {
                board[move[0]][move[1]] = human;
                best = Math.min(best, minimax(board, true));
                board[move[0]][move[1]] = ' ';
            }
            return best;
        }
    }

    private boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++)
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
        for (int i = 0; i < 3; i++)
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return true;
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }

    private boolean isFull(char[][] board) {
        for (char[] row : board)
            for (char cell : row)
                if (cell == ' ') return false;
        return true;
    }

    private java.util.List<int[]> getAvailableMoves(char[][] board) {
        java.util.List<int[]> moves = new java.util.ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') moves.add(new int[]{i, j});
        return moves;
    }
}
