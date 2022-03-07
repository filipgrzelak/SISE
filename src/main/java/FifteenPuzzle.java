import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FifteenPuzzle implements Cloneable {
    private static int createdState = 0;
    private int[][] board = new int[4][4];
    private int deepthLevel;
    private int currentState;

    public FifteenPuzzle(FifteenPuzzle board) {
        createdState++;
        this.board = board.getBoard();
        this.deepthLevel = board.getDeepthLevel() + 1;
        this.currentState = createdState;
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
        deepthLevel = 0;
        createdState++;
        this.currentState = createdState;
    }

    public boolean checkIfItIsASolution() {
        int counter = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j == 3) {
                    return true;
                }
                if (board[i][j] != counter) {
                    return false;
                }
                counter++;
            }
        }
        return true;
    }

    private int returnIOrJValue(String choose) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.board[i][j] == 0) {
                    switch (choose) {
                        case "i" -> {
                            return i;
                        }
                        case "j" -> {
                            return j;
                        }
                        default -> {
                            throw new IllegalArgumentException("Wrong argument");
                        }
                    }
                }
            }
        }
        return -1;
    }

    public boolean leftMove() {
        return returnIOrJValue("j") != 0;
    }

    public boolean upMove() {
        return returnIOrJValue("i") != 0;
    }

    public boolean rightMove() {
        return returnIOrJValue("j") != 3;
    }

    public boolean downMove() {
        return returnIOrJValue("i") != 3;
    }

    public FifteenPuzzle leftMoveChangePlaces() {
        FifteenPuzzle board = this.clone();
        if (this.leftMove()) {
            int i = returnIOrJValue("i");
            int j = returnIOrJValue("j");
            int temp = board.getBoard()[i][j - 1];
            board.getBoard()[i][j - 1] = 0;
            board.getBoard()[i][j] = temp;
            return board;
        } else {
            throw new IllegalArgumentException("Can not move left");
        }
    }

    public FifteenPuzzle upMoveChangePlaces() {
        FifteenPuzzle board = this.clone();
        if (this.upMove()) {
            int i = returnIOrJValue("i");
            int j = returnIOrJValue("j");
            int temp = board.getBoard()[i - 1][j];
            board.getBoard()[i - 1][j] = 0;
            board.getBoard()[i][j] = temp;
            return board;
        } else {
            throw new IllegalArgumentException("Can not move up");
        }
    }

    public FifteenPuzzle rightMoveChangePlaces() {
        FifteenPuzzle board = this.clone();
        if (this.rightMove()) {
            int i = returnIOrJValue("i");
            int j = returnIOrJValue("j");
            int temp = board.getBoard()[i][j + 1];
            board.getBoard()[i][j + 1] = 0;
            board.getBoard()[i][j] = temp;
            return board;
        } else {
            throw new IllegalArgumentException("Can not move right");
        }
    }

    public FifteenPuzzle downMoveChangePlaces() {
        FifteenPuzzle board = this.clone();
        if (this.downMove()) {
            int i = returnIOrJValue("i");
            int j = returnIOrJValue("j");
            int temp = board.getBoard()[i + 1][j];
            board.getBoard()[i + 1][j] = 0;
            board.getBoard()[i][j] = temp;
            return board;
        } else {
            throw new IllegalArgumentException("Can not move down");
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getDeepthLevel() {
        return deepthLevel;
    }

    public int getCurrentState() {
        return createdState;
    }


    @Override
    public FifteenPuzzle clone() {
        try {
            FifteenPuzzle clone = (FifteenPuzzle) super.clone();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    clone.getBoard()[i][j] = this.getBoard()[i][j];
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Current state:" + currentState + " depth level: " + deepthLevel;
    }
}
