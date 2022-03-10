import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class FifteenPuzzle implements Cloneable {
    private static int createdState = 1;
    private byte[][] board = new byte[4][4];
    private byte depthLevel = 0;
    private int currentState;
    private List<Character> allMovesList = new ArrayList<>();
    private char lastMove = ' ';
    private byte x = 0;
    private byte y = 0;

    public FifteenPuzzle(String filename) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(filename));
        String line;
        byte counter = 0;
        while ((line = bf.readLine()) != null) {
            String[] row = line.split(",");
            for (int i = 0; i < row.length; i++) {
                board[counter][i] = Byte.parseByte(row[i]);
                if (board[counter][i] == 0) {
                    y = counter;
                    x = (byte) i;
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

    public void setX(byte x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(byte y) {
        this.y = y;
    }

    public byte[][] getBoard() {
        return this.board;
    }

    public int getDepthLevel() {
        return depthLevel;
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
            clone.board = new byte[4][4];
            clone.allMovesList = new ArrayList<>();
            clone.allMovesList.addAll(this.allMovesList);
            createdState++;
            clone.currentState = createdState;
            clone.depthLevel = (byte) (this.getDepthLevel() + 1);
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

    public int countDistance() {
        int distance = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    distance += abs(3 - i) + abs(3 - j);
                } else if (board[i][j] % 4 == 0) {
                    distance += abs((board[i][j] / 4) - i - 1) + 3 - j;
                } else {
                    distance += abs((board[i][j] / 4) - i) + abs((board[i][j] % 4 - 1) - j);
                }
            }
        }
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FifteenPuzzle that = (FifteenPuzzle) o;

        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        return "Current state: " + currentState + " depth level: " + depthLevel + " moves: " + allMovesList;
    }
}
