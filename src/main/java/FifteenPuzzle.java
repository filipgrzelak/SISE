import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.tan;

public class FifteenPuzzle implements Cloneable {
    private byte[][] board;
    private byte depthLevel = 0;
    private List<Character> allMovesList = new ArrayList<>();
    private char lastMove = ' ';
    private byte x = 0;
    private byte y = 0;
    private int countValue;

    public FifteenPuzzle(String filename) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(filename));
        String line;
        byte counter = 0;
        while ((line = bf.readLine()) != null) {
            String[] row = line.split(" ");
            if (counter == 0) {
                board = new byte[Integer.parseInt(row[0])][Integer.parseInt(row[1])];
                counter++;
                continue;
            }
            for (int i = 0; i < row.length; i++) {
                board[counter - 1][i] = Byte.parseByte(row[i]);
                if (board[counter - 1][i] == 0) {
                    y = (byte) (counter - 1);
                    x = (byte) i;
                }
            }
            counter++;
        }
        bf.close();
    }

    public boolean checkIfItIsASolution() {
        int counter = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == board.length - 1 && j == board[i].length - 1) {
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

    public List<Character> getAllMovesList() {
        return allMovesList;
    }


    public char getLastMove() {
        return lastMove;
    }

    public void setLastMove(char lastMove) {
        this.lastMove = lastMove;
    }

    @Override
    public FifteenPuzzle clone() {
        try {
            FifteenPuzzle clone = (FifteenPuzzle) super.clone();
            clone.board = new byte[board.length][board[0].length];
            clone.allMovesList = new ArrayList<>();
            clone.allMovesList.addAll(this.allMovesList);
            clone.depthLevel = (byte) (this.getDepthLevel() + 1);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    clone.board[i][j] = this.board[i][j];
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void countDistance() {
        int distance = 0;
        int value = 1;

        distance += abs(board.length - 1 - x) + abs(board[0].length - 1 - y);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    value++;
                    continue;
                }
                if (board[i][j] != value) {
                    distance += abs(((board[i][j] - 1) / board[i].length) - i) + abs(((board[i][j] - 1) % board[i].length) - j);
                }
                value++;
            }
        }
        countValue = distance + depthLevel/2;
    }

    public void countWrongPlaces() {
        int wrong = 0;
        int value = 1;
        if (x != board.length - 1 || y != board[0].length - 1) {
            wrong++;
        }
        for (byte[] bytes : board) {
            for (int j = 0; j < bytes.length; j++) {
                if (bytes[j] == 0) {
                    value++;
                    continue;
                }
                if (bytes[j] != value) {
                    wrong++;
                }
                value++;
            }
        }
        countValue = wrong + depthLevel/2;
    }

    public int getCountValue() {
        return countValue;
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
        return " depth level: " + depthLevel + " moves: " + allMovesList;
    }
}
