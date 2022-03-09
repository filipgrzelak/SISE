import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FifteenPuzzle implements Cloneable {
    private static int createdState = 1;
    private int[][] board = new int[4][4];
    private int deepthLevel = 0;
    private int currentState;
    private List<Character> allMovesList = new ArrayList<>();
    private char lastMove = ' ';
    private String allMoves = "";
    private int x = 0;
    private int y = 0;

    public FifteenPuzzle(String filename) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(filename));
        String line;
        int counter = 0;
        while ((line = bf.readLine()) != null) {
            String[] row = line.split(",");
            for (int i = 0; i < row.length; i++) {
                board[counter][i] = Integer.parseInt(row[i]);
                if (board[counter][i] == 0) {
                    y = counter;
                    x = i;
                }
            }
            counter++;
        }
        bf.close();
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

    public List<Character> getAllMovesList() {
        return allMovesList;
    }



    public char getLastMove() {
        return lastMove;
    }

    public void setLastMove(char lastMove) {
        this.lastMove = lastMove;
    }

    public static void saveToFile(FifteenPuzzle board, long timeOfOperationInMiliSec, int processedBoards) throws IOException {
        FileWriter fw = new FileWriter("wynik.txt");
        fw.write("Time: " + Math.round(timeOfOperationInMiliSec / 1000.0) / 1000.0 + "ms\n");
        fw.write("Processed boards: " + processedBoards + "\n");
        fw.write(board.toString() + "\n");
        fw.write("Amount of moves: " + board.getAllMoves().length() + "\n");
        fw.close();
    }

    public FifteenPuzzle doMoveOperation(char s) {
        switch (s) {
            case 'L':
                if (x != 0 && (lastMove != 'R')) {
                    FifteenPuzzle board = this.clone();
                    int temp = board.getBoard()[y][x - 1];
                    board.getBoard()[y][x - 1] = 0;
                    board.getBoard()[y][x] = temp;
                    board.allMovesList.add('L');
                    board.lastMove = 'L';
                    board.setX(x - 1);
                    return board;
                } else {
                    throw new IllegalArgumentException("Can not move left");
                }
            case 'U':
                if (y != 0 && (lastMove != 'D')) {
                    FifteenPuzzle board = this.clone();
                    int temp = board.getBoard()[y - 1][x];
                    board.getBoard()[y - 1][x] = 0;
                    board.getBoard()[y][x] = temp;
                    board.allMovesList.add('U');
                    board.lastMove = 'U';
                    board.setY(y - 1);
                    return board;
                } else {
                    throw new IllegalArgumentException("Can not move up");
                }
            case 'R':
                if (x != 3 && (lastMove != 'L')) {
                    FifteenPuzzle board = this.clone();
                    int temp = board.getBoard()[y][x + 1];
                    board.getBoard()[y][x + 1] = 0;
                    board.getBoard()[y][x] = temp;
                    board.allMovesList.add('R');
                    board.lastMove = 'R';
                    board.setX(x + 1);
                    return board;
                } else {
                    throw new IllegalArgumentException("Can not move right");
                }
            case 'D':
                if (y != 3 && (lastMove != 'U')) {
                    FifteenPuzzle board = this.clone();
                    int temp = board.getBoard()[y + 1][x];
                    board.getBoard()[y + 1][x] = 0;
                    board.getBoard()[y][x] = temp;
                    board.allMovesList.add('D');
                    board.lastMove = 'D';
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
            clone.allMovesList = new ArrayList<>();
            clone.allMovesList.addAll(this.allMovesList);
            createdState++;
            clone.currentState = createdState;
            clone.deepthLevel = this.getDeepthLevel() + 1;
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
        return "Current state: " + currentState + " depth level: " + deepthLevel + " moves: " + allMovesList;
    }
}
