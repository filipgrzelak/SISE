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
        fw.write("Amount of moves: " + board.getAllMovesList().size() + "\n");
        fw.close();
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

    @Override
    public String toString() {
        return "Current state: " + currentState + " depth level: " + deepthLevel + " moves: " + allMovesList;
    }
}
