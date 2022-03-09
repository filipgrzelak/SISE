import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class DepthFirstSearch {
    private Stack<FifteenPuzzle> stack = new Stack<>();
    private String[] moves = new String[4];
    private long startTime = System.nanoTime();
    private int amountOfProcessedBoards = 1;

    public DepthFirstSearch(String filename, String moves) throws IOException {
        for (int i = 0; i < 4; i++) {
            this.moves[i] = String.valueOf(moves.charAt(i));
        }

        stack.add(new FifteenPuzzle(filename));
    }

    public void depthAlgorithm() throws IOException {
        FifteenPuzzle state = stack.pop();
        if (state.checkIfItIsASolution()) {
            FifteenPuzzle.saveToFile(state, System.nanoTime() - startTime,amountOfProcessedBoards);
            return;
        }
        if (state.getDeepthLevel() < 20) {
            for (int i = 3; i >= 0; i--) {
                try {
                    stack.add(new FifteenPuzzle(state.doMoveOperation(moves[i])));
                    amountOfProcessedBoards++;
                } catch (Exception e) {

                }
            }
        }


        depthAlgorithm();
    }
}
