package Game;

public class Reversi {
    private static final int white = 1;
    private static final int black = 2;
    private final Move move = new Move(this);
    public int[][] board;

    public static void main(String[] args) {
        Reversi reversi = new Reversi();
        reversi.start();
    }

    public void start() {
        board = new int[8][8];
        board[3][3] = white;
        board[4][4] = white;
        board[3][4] = black;
        board[4][3] = black;
        gameLogic();
    }

    public void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7 ");
        for (int i = 0; i < 8; i++) {
            System.out.print(i);
            populateBoard(i);
            System.out.println("\n");
        }
        System.out.println();
    }

    public void populateBoard(int i) {
        for (int j = 0; j < 8; j++) {
            switch (board[j][i]) {
                case white -> System.out.print(" B");
                case black -> System.out.print(" J");
                default -> System.out.print(" _");
            }
        }
    }

    //grazinamas nugaletojas, jei nera grazinam 0, jei lygios grazinam 3
    public int checkWhoWon() {
        if (move.isThereValidMove(white) || move.isThereValidMove(black)) {
            return 0;
        }
        int count = 0;
        count = countPoints(count);
        if (count > 0) {
            return white;
        }
        if (count < 0) {
            return black;
        }
        return 3;
    }

    public int countPoints(int count) {
        int i = 0;
        while (i < 8) {
            int j = 0;
            while (j < 8) {
                count = countWhitePlayer(count, i, j);
                count = countBlackPlayer(count, i, j);
                j++;
            }
            i++;
        }
        return count;
    }

    public int countBlackPlayer(int count, int i, int j) {
        if (board[i][j] == black) {
            count--;
        }
        return count;
    }

    public int countWhitePlayer(int count, int i, int j) {
        if (board[i][j] == white) {
            count++;
        }
        return count;
    }

    public void gameLogic() {
        printBoard();
        while (checkWhoWon() == 0) {
            for (int i : new int[]{black, white}) {
                gameLogicMoveValidation(i);
                printBoard();
            }
        }
        printWinner();
    }

    public void printWinner() {
        if (checkWhoWon() != 3) whiteOrBlackWinner();
        else {
            System.out.println("Lygiosos");
        }
    }

    public void whiteOrBlackWinner() {
        if (checkWhoWon() == 1) System.out.println("baltas zaidejas laimejo");
        else System.out.println("juodas zaidejas laimejo");
    }

    public void gameLogicMoveValidation(int black) {
        boolean valid = false;
        while (!valid) {
            try {
                move.getMoveFromPlayer(black);
                valid = true;
            } catch (Exception exception) {
                System.out.println("Iveskite teisinga koordinate!");
            }
        }
    }
}
