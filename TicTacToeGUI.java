import javax.swing.*;
import java.awt.*;

public class TicTacToeGUI extends JFrame {
    private final JButton[][] buttons = new JButton[3][3];
    private final Board board = new Board();
    private final AIPlayer ai = new AIPlayer('O', 'X');
    private final JLabel statusLabel = new JLabel("Your turn (X)", SwingConstants.CENTER);

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

        // Initialize 3x3 buttons
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

                final int r = row, c = col;
                button.addActionListener(e -> handlePlayerMove(r, c));
                boardPanel.add(button);
            }
        }

        // Reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setFocusPainted(false);
        resetButton.setBackground(Color.decode("#eeeeee"));
        resetButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resetButton.addActionListener(e -> resetGame());

        // Status label styling
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.decode("#f4f4f4"));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(resetButton, BorderLayout.EAST);

        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handlePlayerMove(int row, int col) {
        if (!board.makeMove(row, col, 'X')) return;

        JButton button = buttons[row][col];
        button.setText("X");
        button.setEnabled(false);
        button.setForeground(new Color(0x0074D9)); // Blue for X

        if (board.checkWin('X')) {
            statusLabel.setText("✅ You win!");
            disableBoard();
            return;
        }
        if (board.isFull()) {
            statusLabel.setText("🤝 It's a draw.");
            return;
        }

        statusLabel.setText("AI's turn (O)");
        SwingUtilities.invokeLater(this::aiTurn);
    }

    private void aiTurn() {
        int[] move = ai.findBestMove(board);
        if (move[0] == -1) return;

        board.makeMove(move[0], move[1], 'O');
        JButton button = buttons[move[0]][move[1]];
        button.setText("O");
        button.setEnabled(false);
        button.setForeground(new Color(0xFF4136)); // Red for O

        if (board.checkWin('O')) {
            statusLabel.setText("😈 AI wins!");
            disableBoard();
        } else if (board.isFull()) {
            statusLabel.setText("🤝 It's a draw.");
        } else {
            statusLabel.setText("Your turn (X)");
        }
    }

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

    private void disableBoard() {
        for (JButton[] row : buttons)
            for (JButton b : row)
                b.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI gui = new TicTacToeGUI();
            gui.setVisible(true);
        });
    }
}
