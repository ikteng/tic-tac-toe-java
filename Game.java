import java.util.Scanner;

public class Game {
    private final Board board;
    private final Scanner scanner;
    private final AIPlayer ai;

    public Game() {
        board = new Board();
        scanner = new Scanner(System.in);
        ai = new AIPlayer('O', 'X'); // AI = 'O', Player = 'X'
    }

    public void start() {
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("You are 'X'. AI is 'O'.");

        while (true) {
            board.reset(); // Reset the board before each new game
            board.print(); // Show empty board

            while (true) {
                playerTurn();
                board.print();
                if (board.checkWin('X')) {
                    board.print();
                    System.out.println("You win!");
                    break;
                }
                if (board.isFull()) {
                    board.print();
                    System.out.println("It's a draw!");
                    break;
                }

                aiTurn();
                board.print();
                if (board.checkWin('O')) {
                    board.print();
                    System.out.println("AI wins!");
                    break;
                }
                if (board.isFull()) {
                    board.print();
                    System.out.println("It's a draw!");
                    break;
                }
            }

            System.out.print("Play again? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (!input.equals("Y")) {
                System.out.println("Thanks for playing!");
                break;
            }

        }
    }

    private void playerTurn() {
        int row = -1, col = -1;
        while (true) {
            System.out.print("Enter your move (row, column) [e.g. 1,1]: ");
            String line = scanner.nextLine().trim();

            // Split and check length
            String[] parts = line.split(",");
            if (parts.length != 2) {
                System.out.println("Invalid format. Please enter two numbers separated by a comma (e.g., '1,2').");
                continue;
            }

            // Try parsing integers
            try {
                row = Integer.parseInt(parts[0]);
                col = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
                continue;
            }

            // Check move validity
            if (!board.makeMove(row, col, 'X')) {
                System.out.println("Invalid move. Cell may be out of bounds or already taken.");
                continue;
            }

            System.out.printf("You played at: (%d, %d)%n", row, col);
            break; // Valid move
        }
    }

    private void aiTurn() {
        int[] move = ai.findBestMove(board);
        board.makeMove(move[0], move[1], 'O');
        System.out.printf("AI played at: (%d, %d)%n", move[0], move[1]);
    }
}
