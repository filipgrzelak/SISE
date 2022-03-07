import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FifteenPuzzle implements Cloneable {
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
        while ((line = bf.readLine()) != null) {
            String[] row = line.split(",");
            for (int i = 0; i < row.length; i++) {
                board[counter][i] = Integer.parseInt(row[i]);
            }
            counter++;
        }
        bf.close();
    }

    public boolean checkIfItIsASolution() {
        int counter = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!(board[i][j] == counter)) {
                    return false;
                }
                counter++;
                if (counter == 15) {
                    counter = 0;
                }
            }
        }
        return true;
    }

    public FifteenPuzzle leftMove() {
        FifteenPuzzle board = null;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.board[i][j] == 0) {
                    if (j == 0) {
                        throw new IllegalArgumentException("Can not go left");
                    } else {
                        board = this.clone();
                        int temp = this.board[i][j - 1];
                        this.board[i][j - 1] = temp;
                        this.board[i][j] = 0;
                    }
                    
                }
            }
        }
        return board;
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


    @Override
    public FifteenPuzzle clone() {
        try {
            FifteenPuzzle clone = (FifteenPuzzle) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
