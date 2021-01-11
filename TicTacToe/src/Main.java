import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.print("Enter cells:");
        char[][] board = { { '_', '_', '_'}, { '_', '_', '_'}, { '_', '_', '_'} };
        printBoard(board);
        int x = 0, y = 0;

        // loop that plays the game
        while (!isGameWon(board)) {
            char player = whoseTurnIsIt(board);
            System.out.printf("player == %c %n", player);
            boolean valid;
            boolean occupied = true;
            while (occupied) {
                if (player == 'X') {
                    System.out.println("This cell is occupied! Choose another one!");
                    do {
                        System.out.print("Enter the coordinates:");
                        String s = scanner.nextLine();
                        try {
                            String sX = s.substring(0, 1);
                            String sY = s.substring(2, 3);
                            x = Integer.parseInt(sX);
                            y = Integer.parseInt(sY);
                            if ((x == 1 || x == 2 || x == 3) && (y == 1 || y == 2 || y == 3)) {
                                valid = true;
                            } else {
                                System.out.println("Coordinates should be from 1 to 3!");
                                valid = false;
                            }
                        } catch (Exception e) {
                            System.out.println("You should enter numbers!");
                            valid = false;
                        }
                    } while (!valid);
                } else if (player == 'O'){ //generates a move
                    x = random.nextInt(3) + 1;
                    y = random.nextInt(3) + 1;
                }
                occupied = isSpaceOccupied(board, x, y);
            }
            makeMove(board, x, y, player);
            if (player == 'O') {
                System.out.println("Making move level \"easy\" ");
            }
            printBoard(board);
        }
        char winner = whoWon(board);
        printWinnerStatement(winner);
    }

    public static char[][] readBoard(Scanner scanner) {
        char[][] board = new char[3][3];
        String line = scanner.nextLine();
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j){
                board[i][j] = line.charAt(board[i].length * i + j);
            }
        }
        return board;
    }

    public static void printBoard(char[][] board){
        System.out.println("---------");
        for (int i = 0; i < board.length; ++i) {
            System.out.print("| ");
            for (int j = 0; j < board[0].length; ++j){
                System.out.print(board[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    public static char  whoWon(char[][] board) {
        int numOfLegalMoves = isNumberOfMovesLegal(board);
        if (numOfLegalMoves > 1) {
            return 'I';
        }

        boolean xWins = isWinning(board, 'X');
        boolean oWins = isWinning(board, 'O');

        boolean emptyCells = false;
        for (int i = 0; i < board.length; ++i) { //still empty cells
            for (int j = 0; j < board[0].length; ++j) {
                if (board[i][j] == '_') {
                    emptyCells = true;
                    break;
                }
            }
        }

        //no empty cells
        if (xWins && oWins && !emptyCells || xWins && oWins && emptyCells) {
            return 'I'; //impossible
        } else if (!xWins && !oWins && !emptyCells) {
            return 'D'; //draw
        } else if (xWins) {
            return 'X'; //x wins
        } else if (oWins) {
            return 'O'; // o wins
        } else {
            return 'N'; //not finished
        }
    }

    public static int isNumberOfMovesLegal(char[][] board) {
        int diffInMoves;
        int xMoves = 0;
        int oMoves = 0;

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                if (board[i][j] == 'X') {
                    ++xMoves;
                } else if (board[i][j] == 'O') {
                    ++oMoves;
                }
            }
        }

        diffInMoves = Math.abs(xMoves - oMoves);
        return diffInMoves;
    }

    public static boolean isWinning(char[][] board, char player) {
        //board always square
        //winning diagonally
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player ||
                board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        //winning horizontally
        for (int i = 0; i < board.length; ++i) {
            int count = 0;
            for (int j = 0; j < board[0].length; ++j) {
                if (board[i][j] == player) {
                    ++count;
                }
                if (count == board.length) {
                    return true;
                }
            }
        }
        //winning vertically
        for (int i = 0; i < board.length; ++i) {
            int count = 0;
            for (int j = 0; j < board[0].length; ++j) {
                if (board[j][i] == player) {
                    ++count;
                }
                if (count == board.length) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void printWinnerStatement(char winner) {
        if (winner == 'X') {
            System.out.println("X wins");
        } else if (winner == 'O') {
            System.out.println("O wins");
        } else if (winner == 'I') {
            System.out.println("Impossible");
        } else if (winner == 'D') {
            System.out.println("Draw");
        } else if (winner == 'N') {
            System.out.println("Game not finished");
        }
    }

    public static boolean isSpaceOccupied(char[][] board, int x, int y) {
        if (board[3 - y][x - 1] == '_') {
            return false;
        } else {
            return true;
        }
    }

    public static void makeMove(char[][] board, int x, int y, char player) {
        board[3 - y][x - 1] = player;
    }

    public static boolean isGameWon (char[][] board) {
        char ch = whoWon(board);
        if (ch == 'X' || ch == 'O' || ch == 'D') {
            return true;
        } else {
            return false;
        }
    }

    public static char whoseTurnIsIt (char[][] board) {
        int x = 0;
        int o = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                if (board[i][j] == 'X') {
                    ++x;
                } else if (board[i][j] == 'O') {
                    ++o;
                }
            }
        }

        if (x <= o) {
            return 'X';
        } else {
            return 'O';
        }
    }
}

