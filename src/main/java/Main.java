import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Witaj Pawle :P");
        FifteenPuzzle board = new FifteenPuzzle("unsolvedGame");
        printBoard(board);
    }

    public static void printBoard(FifteenPuzzle board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(board.getBoard()[i][j]/10 == 0) {
                    System.out.print(board.getBoard()[i][j] + "  ");
                } else {
                    System.out.print(board.getBoard()[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
