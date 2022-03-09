import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class DepthFirstSearch {
    private Stack<FifteenPuzzle> stack = new Stack<>();
    private char[] moves = new char[4];
    private long startTime = System.nanoTime();
    private int amountOfProcessedBoards = 1;

    public DepthFirstSearch(String filename, String moves) throws IOException {
        for (int i = 0; i < 4; i++) {
            this.moves[i] = moves.charAt(i);
        }

        stack.add(new FifteenPuzzle(filename));
    }

    public void depthAlgorithm() throws IOException {
        FifteenPuzzle state = stack.pop();
        if (state.checkIfItIsASolution()) {
            FifteenPuzzle.saveToFile(state, System.nanoTime() - startTime, amountOfProcessedBoards);
            return;
        }
        if (state.getDeepthLevel() < 20) {
            for (int i = 3; i >= 0; i--) {
                doMoveOperation(moves[i], state);
            }
        }
        depthAlgorithm();
    }

    public void doMoveOperation(char s, FifteenPuzzle state) {
        switch (s) {
            case 'L':
                if (state.getX() != 0 && (state.getLastMove() != 'R')) {
                    FifteenPuzzle board = state.clone();
                    int temp = board.getBoard()[board.getY()][board.getX() - 1];
                    board.getBoard()[board.getY()][board.getX() - 1] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('L');
                    board.setLastMove('L');
                    board.setX(board.getX() - 1);
                    stack.add(board);
                }
                break;
            case 'U':
                if (state.getY() != 0 && (state.getLastMove() != 'D')) {
                    FifteenPuzzle board = state.clone();
                    int temp = board.getBoard()[board.getY() - 1][board.getX()];
                    board.getBoard()[board.getY() - 1][board.getX()] = 0;
                    board.getBoard()[board.getY()][board.getX()] = temp;
                    board.getAllMovesList().add('U');
                    board.setLastMove('U');
                    board.setY(board.getY() - 1);
                    stack.add(board);
                }
                break;
            case 'R':
                if (state.getX() != 3 && (state.getLastMove() != 'L')) {
                    FifteenPuzzle board = state.clone();
                    int temp = board.getBoard()[state.getY()][state.getX() + 1];
                    board.getBoard()[state.getY()][state.getX() + 1] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('R');
                    board.setLastMove('R');
                    board.setX(board.getX() + 1);
                    stack.add(board);
                }
                break;
            case 'D':
                if (state.getY() != 3 && (state.getLastMove() != 'U')) {
                    FifteenPuzzle board = state.clone();
                    int temp = board.getBoard()[state.getY() + 1][state.getX()];
                    board.getBoard()[state.getY() + 1][state.getX()] = 0;
                    board.getBoard()[state.getY()][state.getX()] = temp;
                    board.getAllMovesList().add('D');
                    board.setLastMove('D');
                    board.setY(state.getY() + 1);
                    stack.add(board);
                }
                break;
            default:
                throw new IllegalArgumentException("Bad name of operation");

        }
    }
}
