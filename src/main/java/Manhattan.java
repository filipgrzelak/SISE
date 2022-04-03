import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Manhattan {
    private static ArrayList<FifteenPuzzle> states = new ArrayList<>();
    private long startTime = System.nanoTime();
    private int amountOfProcessedBoards = 1;
    private char[] moves = {'L', 'R', 'D', 'U'};
    private String fileStats;
    private String fileSol;
    private int visitedBoards = 0;
    private int maxRecursionLevel = 0;

    public Manhattan(String filename, String fileSol, String fileStats) throws IOException {
        this.fileSol = fileSol;
        this.fileStats = fileStats;
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(filename);
        fifteenPuzzle.countDistance();
        states.add(fifteenPuzzle);
    }

    public void manhattan() throws IOException {
        while (!states.isEmpty()) {
            FifteenPuzzle firstState = states.remove(0);
            visitedBoards++;

            if (firstState.getDepthLevel() > maxRecursionLevel) {
                maxRecursionLevel = firstState.getDepthLevel();
            }
            if (firstState.checkIfItIsASolution()) {
                Saving.saveToFile(firstState, System.nanoTime() - startTime, amountOfProcessedBoards, fileSol, fileStats, visitedBoards, maxRecursionLevel);
                return;
            }

            if (firstState.getDepthLevel() < 20) {
                for (int i = 0; i < 4; i++) {
                    int temp = states.size();
                    SwichClass.doMoveOperation(moves[i], firstState, states);
                    if (temp != states.size()) {
                        amountOfProcessedBoards++;
                    }
                }
            }
        }
        Saving.saveNoSolution(fileSol);
    }
}
