import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * A simple GUI-based Tic-Tac-Toe game (Player vs AI) using Java Swing.
 */
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

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(Color.decode("#f4f4f4"));
        Font font = new Font("Arial", Font.BOLD, 48);

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

        JButton resetButton = new JButton("Reset");
        resetButton.setFocusPainted(false);
        resetButton.setBackground(Color.decode("#eeeeee"));
        Border roundedLineBorder = BorderFactory.createLineBorder(Color.GRAY, 1, true);
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
        resetButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resetButton.setBorder(BorderFactory.createCompoundBorder(roundedLineBorder, paddingBorder));
        resetButton.addActionListener(e -> resetGame());

        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.decode("#f4f4f4"));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(resetButton, BorderLayout.EAST);

        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    } 

    private void handlePlayerMove(int row, int col) {
        JButton button = buttons[row][col];

        if (!board.makeMove(row, col, 'X')) return;
        if (!button.getText().trim().isEmpty()) return;  // Ignore clicks on occupied cells

        button.setText("X");
        button.setForeground(new Color(0x0074D9)); // Blue for X

        if (board.checkWin('X')) {
            highlightWinningLine('X'); 
            showGameOverDialog("You win!");
            return;
        }

        if (board.isFull()) {
            showGameOverDialog("It's a draw.");
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
        button.setForeground(new Color(0xFF4136)); // Red for O

        if (board.checkWin('O')) {
            highlightWinningLine('O'); 
            showGameOverDialog("AI wins!");
        } else if (board.isFull()) {
            showGameOverDialog("It's a draw.");
        } else {
            statusLabel.setText("Your turn (X)");
        }
    }

    private void highlightWinningLine(char player) {
        int[][] winningLine = board.getWinningLine(player);
        if (winningLine == null) return;

        // Define pastel colors
        Color pastelRed = new Color(0xFFB3B3);  
        Color pastelBlue = new Color(0xB3C7FF); 

        // Define text colors for contrast
        Color redText = new Color(0xCC0000);
        Color blueText = new Color(0x003399);

        for (int[] pos : winningLine) {
            JButton btn = buttons[pos[0]][pos[1]];
            if (player == 'X') {
                btn.setBackground(pastelBlue);
                btn.setForeground(blueText);
            } else {
                btn.setBackground(pastelRed);
                btn.setForeground(redText);
            }
        }
    }
    
    private void showGameOverDialog(String message) {
        String[] options = {"Reset", "Quit"};

        // Show a modal dialog with the message and custom buttons
        int choice = JOptionPane.showOptionDialog(
            this,
            message, // text displayed inside the dialog
            "Game Over", // title of the dialog window
            JOptionPane.DEFAULT_OPTION, // use default button configuration
            JOptionPane.INFORMATION_MESSAGE, // icon type
            null, // no custom icon
            options, // array of button labels to display
            options[0] // default selected button to Reset
        );

        // Check which button the user clicked (returns the index of selected option)
        if (choice == 0) { // Reset
            resetGame();
        } else if (choice == 1) { // Quit or close
            System.exit(0);
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
                btn.setBackground(Color.WHITE);
            }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI gui = new TicTacToeGUI();
            gui.setVisible(true);
        });
    }
}
