import java.io.IOException;
import java.util.*;

public class DepthFirstSearch {
    private Stack<FifteenPuzzle> stack = new Stack<>();
    private char[] moves = new char[4];
    private long startTime = System.nanoTime();
    private int amountOfProcessedBoards = 0;
    private int visitedBoards = 0;
    private int maxRecursionLevel = 0;
    private List<Integer> hashes = new ArrayList<>();
    private String fileStats;
    private String fileSol;

    public DepthFirstSearch(String filename, String moves, String fileSol, String fileStats) throws IOException {
        this.fileSol = fileSol;
        this.fileStats = fileStats;
        for (int i = 0; i < 4; i++) {
            this.moves[i] = moves.charAt(i);
        }
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(filename);
        stack.add(fifteenPuzzle);
    }

    public void depthAlgorithm() throws IOException {
        while (!stack.isEmpty()) {
            FifteenPuzzle state = stack.pop();
            visitedBoards++;
            if (state.getDepthLevel() > maxRecursionLevel && state.getDepthLevel() < 21) {
                maxRecursionLevel = state.getDepthLevel();
            }
            if (state.checkIfItIsASolution()) {
                Saving.saveToFile(state, System.nanoTime() - startTime, amountOfProcessedBoards, fileSol, fileStats, visitedBoards, maxRecursionLevel);
                return;
            }
            if (state.getDepthLevel() < 21) {

                for (int i = 3; i >= 0; i--) {
                    int temp = stack.size();
                    SwichClass.doMoveOperation(moves[i], state, stack);
                    if (temp != stack.size()) {
                        amountOfProcessedBoards++;
                    }
                }
            }
        }
        Saving.saveNoSolution(fileSol);

    }


}
