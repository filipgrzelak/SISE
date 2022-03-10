import java.io.IOException;
import java.util.*;

public class DepthFirstSearch {
    private Stack<FifteenPuzzle> stack = new Stack<>();
    private char[] moves = new char[4];
    private long startTime = System.nanoTime();
    private int distance = 0;
    private int amountOfProcessedBoards = 0;
    private List<Integer> hashes = new ArrayList<>();

    public DepthFirstSearch(String filename, String moves) throws IOException {
        for (int i = 0; i < 4; i++) {
            this.moves[i] = moves.charAt(i);
        }
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(filename);
        distance = fifteenPuzzle.countDistance();
        stack.add(fifteenPuzzle);
    }

    public void depthAlgorithm() throws IOException {
        FifteenPuzzle state = stack.pop();
        amountOfProcessedBoards++;
        if (state.checkIfItIsASolution()) {
            FifteenPuzzle.saveToFile(state, System.nanoTime() - startTime, amountOfProcessedBoards);
            return;
        }
        if (state.getDepthLevel() < 50) {
            for (int i = 3; i >= 0; i--) {
                SwichClass.doMoveOperation(moves[i], state, stack, distance);
            }
        }
        depthAlgorithm();
    }


}
