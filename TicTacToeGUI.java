import javax.swing.*;
import java.awt.*;

/**
 * A simple GUI-based Tic-Tac-Toe game (Player vs AI) using Java Swing.
 */

public class TicTacToeGUI extends JFrame {
    private final JButton[][] buttons = new JButton[3][3]; // 3x3 grid of buttons for the board
    private final Board board = new Board(); // Game logic board
    private final AIPlayer ai = new AIPlayer('O', 'X'); // AI player ('O'), Human player ('X')
    private final JLabel statusLabel = new JLabel("Your turn (X)", SwingConstants.CENTER); // Game status display

    // Constructor: initializes and lays out the GUI components.
    public TicTacToeGUI() {
        setTitle("Tic-Tac-Toe: Player vs AI");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.decode("#f4f4f4"));

        // Create board panel
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(Color.decode("#f4f4f4"));
        Font font = new Font("Arial", Font.BOLD, 48);

        // Initialize and style each button in the 3x3 grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton(" ");
                button.setFont(font);
                button.setFocusPainted(false);
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                button.setOpaque(true);
                buttons[row][col] = button;

                // Handle clicks by the player
                final int r = row, c = col;
                button.addActionListener(e -> handlePlayerMove(r, c));
                boardPanel.add(button);
            }
        }

        // Reset button to restart the game
        JButton resetButton = new JButton("Reset");
        resetButton.setFocusPainted(false);
        resetButton.setBackground(Color.decode("#eeeeee"));
        resetButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resetButton.addActionListener(e -> resetGame());

        // Status label styling
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bottom panel: status + reset button
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.decode("#f4f4f4"));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(resetButton, BorderLayout.EAST);

        // Add everything to the main frame
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Handles a move made by the human player
    private void handlePlayerMove(int row, int col) {
        // Ignore invalid moves
        if (!board.makeMove(row, col, 'X')) return;

        JButton button = buttons[row][col];
        button.setText("X");
        button.setEnabled(false);
        button.setForeground(new Color(0x0074D9)); // Blue for X

        // Check for human win
        if (board.checkWin('X')) {
            statusLabel.setText("✅ You win!");
            disableBoard();
            return;
        }

        // Check if draw
        if (board.isFull()) {
            statusLabel.setText("🤝 It's a draw.");
            return;
        }

        // Let the AI play next
        statusLabel.setText("AI's turn (O)");
        SwingUtilities.invokeLater(this::aiTurn);
    }

    // Performs the AI's move and updates the board
    private void aiTurn() {
        int[] move = ai.findBestMove(board); // Get the best next move for AI
        if (move[0] == -1) return; // No more available (shouldn't happen)

        // Let AI to make the given best move
        board.makeMove(move[0], move[1], 'O');
        JButton button = buttons[move[0]][move[1]];
        button.setText("O");
        button.setEnabled(false);
        button.setForeground(new Color(0xFF4136)); // Red for O

        // Check if AI win
        if (board.checkWin('O')) {
            statusLabel.setText("😈 AI wins!");
            disableBoard();
        
        // Check if draw
        } else if (board.isFull()) {
            statusLabel.setText("🤝 It's a draw.");

        // Let the player play next
        } else {
            statusLabel.setText("Your turn (X)");
        }
    }

    // Resets the game state and UI to allow a new game to begin
    private void resetGame() {
        board.reset();
        statusLabel.setText("Your turn (X)");
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                JButton btn = buttons[i][j];
                btn.setText(" ");
                btn.setEnabled(true);
                btn.setForeground(Color.BLACK);
            }
    }

    // Disables all buttons on the board to prevent further interaction
    private void disableBoard() {
        for (JButton[] row : buttons)
            for (JButton b : row)
                b.setEnabled(false);
    }

    // Main function: Lauches the game window
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI gui = new TicTacToeGUI();
            gui.setVisible(true);
        });
    }
}
