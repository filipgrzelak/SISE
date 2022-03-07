import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FifteenPuzzle {
    private int[][] board = new int[4][4];
    private int deepthLevel;
    private int createdState;

    public FifteenPuzzle(FifteenPuzzle board) {
        this.board = board.getBoard();
        this.deepthLevel = board.getDeepthLevel() + 1;
        this.createdState = board.getCreatedState() + 1;
    }

    public FifteenPuzzle(String filename) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(filename));
        String line;
        int counter = 0;
        while((line = bf.readLine()) != null) {
            String[] row = line.split(",");
            for (int i = 0; i< row.length; i++) {
                board[counter][i] = Integer.parseInt(row[i]);
            }
            counter++;
        }

    }

    public int[][] getBoard() {
        return board;
    }

    public int getDeepthLevel() {
        return deepthLevel;
    }

    public int getCreatedState() {
        return createdState;
    }
}
