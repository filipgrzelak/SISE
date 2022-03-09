import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FifteenPuzzle implements Cloneable {
    private static int createdState = 1;
    private int[][] board = new int[4][4];
    private int deepthLevel = 0;
    private int currentState;
    private String allMoves = "";
    private int x = 0;
    private int y = 0;

    public FifteenPuzzle(FifteenPuzzle board) {
        createdState++;
        this.board = board.getBoard();
        this.deepthLevel = board.getDeepthLevel() + 1;
        this.currentState = createdState;

        this.allMoves = board.getAllMoves();
        this.x = board.getX();
        this.y = board.getY();
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
        this.currentState = createdState;
        y = returnIOrJValue("i");
        x = returnIOrJValue("j");
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public static void saveToFile(FifteenPuzzle board, long timeOfOperationInMiliSec, int processedBoards) throws IOException {
        FileWriter fw = new FileWriter("wynik.txt");
        fw.write("Time: " + Math.round(timeOfOperationInMiliSec / 1000.0) / 1000.0 + "ms\n");
        fw.write("Processed boards: " + processedBoards + "\n");
        fw.write(board.toString() + "\n");
        fw.write("Amount of moves: " + board.getAllMoves().length() + "\n");
        fw.close();
    }

    public FifteenPuzzle doMoveOperation(String s) {
        switch (s) {
            case "L":
                if (x != 0 && !allMoves.endsWith("R")) {
                    FifteenPuzzle board = this.clone();
                    int temp = board.getBoard()[y][x - 1];
                    board.getBoard()[y][x - 1] = 0;
                    board.getBoard()[y][x] = temp;
                    board.setAllMoves(allMoves + "L");
                    board.setX(x - 1);
                    return board;
                } else {
                    throw new IllegalArgumentException("Can not move left");
                }
            case "U":
                if (y != 0 && !allMoves.endsWith("D")) {
                    FifteenPuzzle board = this.clone();
                    int temp = board.getBoard()[y - 1][x];
                    board.getBoard()[y - 1][x] = 0;
                    board.getBoard()[y][x] = temp;
                    board.setAllMoves(allMoves + "U");
                    board.setY(y - 1);
                    return board;
                } else {
                    throw new IllegalArgumentException("Can not move up");
                }
            case "R":
                if (x != 3 && !allMoves.endsWith("L")) {
                    FifteenPuzzle board = this.clone();
                    int temp = board.getBoard()[y][x + 1];
                    board.getBoard()[y][x + 1] = 0;
                    board.getBoard()[y][x] = temp;
                    board.setAllMoves(allMoves + "R");
                    board.setX(x + 1);
                    return board;
                } else {
                    throw new IllegalArgumentException("Can not move right");
                }
            case "D":
                if (y != 3 && !allMoves.endsWith("U")) {
                    FifteenPuzzle board = this.clone();
                    int temp = board.getBoard()[y + 1][x];
                    board.getBoard()[y + 1][x] = 0;
                    board.getBoard()[y][x] = temp;
                    board.setAllMoves(allMoves + "D");
                    board.setY(y + 1);
                    return board;
                } else {
                    throw new IllegalArgumentException("Can not move down");
                }
            default:
                throw new IllegalArgumentException("Bad name of operation");

        }
    }

    @Override
    public FifteenPuzzle clone() {
        try {
            FifteenPuzzle clone = (FifteenPuzzle) super.clone();
            clone.board = new int[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    clone.board[i][j] = this.board[i][j];
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public String getAllMoves() {
        return allMoves;
    }

    public void setAllMoves(String allMoves) {
        this.allMoves = allMoves;
    }

    @Override
    public String toString() {
        return "Current state: " + currentState + " depth level: " + deepthLevel + " moves: " + allMoves;
    }
}
