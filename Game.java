import java.util.Scanner;

public class Game {
    private final Board board; // Represents the 3x3 game board
    private final Scanner scanner; // Used to capture user input
    private final AIPlayer ai; // Handles AI logic using Minimax

    public Game() {
        board = new Board(); // Initialize the board
        scanner = new Scanner(System.in); // Initialize the input scanner
        ai = new AIPlayer('O', 'X'); // Initialize AI Player => AI = 'O', Player = 'X'
    }

    // Starts the game loop
    public void start() {
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("You are 'X'. AI is 'O'.");

        while (true) { // loop for multiple games if player choose to replay
            board.reset(); // Reset the board before each new game
            board.print(); // Show empty board

            while (true) { // Loop for 1 round of the game => handle alternating turns & checks for win/draw
                playerTurn(); // Player make a move
                board.print(); // Print updated board
                if (board.checkWin('X')) { // Check if player won
                    board.print();
                    System.out.println("You win!");
                    break;
                }
                if (board.isFull()) { // Check for draw
                    board.print();
                    System.out.println("It's a draw!");
                    break;
                }

                // else, continue to AI's turn

                aiTurn(); // AI makes a move
                board.print(); // Print updated board
                if (board.checkWin('O')) { // Check if AI won
                    board.print();
                    System.out.println("AI wins!");
                    break;
                }
                if (board.isFull()) { // Check for draw
                    board.print();
                    System.out.println("It's a draw!");
                    break;
                }
            }

            // After 1 round of the game:

            // Ask user if they want to play again
            System.out.print("Play again? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (!input.equals("Y")) {
                System.out.println("Thanks for playing!");
                break; // Exit current game loop to start a new game
            }

        }
    }

    // Handles the player's turn: takes input, validates it, and updates the board
    private void playerTurn() { // only accessible inside the Game class
        int row = -1, col = -1;

        while (true) {
            System.out.print("Enter your move (row, column) [e.g. 1,1]: ");
            String line = scanner.nextLine().trim();

            // Split input based on comma
            String[] parts = line.split(",");

            // Check length of input
            if (parts.length != 2) {
                System.out.println("Invalid format. Please enter two numbers separated by a comma (e.g., '1,2').");
                continue;
            }

            // Try parsing the inputs into integers
            try {
                row = Integer.parseInt(parts[0]);
                col = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
                continue;
            }

            // Attempt to make the move; check if it's valid
            if (!board.makeMove(row, col, 'X')) {
                System.out.println("Invalid move. Cell may be out of bounds or already taken.");
                continue;
            }

            System.out.printf("You played at: (%d, %d)%n", row, col);
            break; // Exit loop if valid move
        }
    }

    /*
     * Handles the AI's move using Minimax and updates the board 
     * @see Board
     * @see AIPlayer#findBestMove(Board)
     */
    private void aiTurn() { // only accessible inside the Game class
        int[] move = ai.findBestMove(board);
        board.makeMove(move[0], move[1], 'O');
        System.out.printf("AI played at: (%d, %d)%n", move[0], move[1]);
    }
}
