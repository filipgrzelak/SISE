import java.io.IOException;
import java.util.ArrayList;

public class Hamming {
    private static ArrayList<FifteenPuzzle> states = new ArrayList<>();
    private long startTime = System.nanoTime();
    private int amountOfProcessedBoards = 1;
    private char[] moves = {'L', 'U', 'R', 'D'};
    private String fileStats;
    private String fileSol;
    private int visitedBoards = 0;
    private int maxRecursionLevel = 0;

    public Hamming(String filename, String fileSol, String fileStats) throws IOException {
        this.fileSol = fileSol;
        this.fileStats = fileStats;
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(filename);
        states.add(fifteenPuzzle);
    }

    public void hamming() throws IOException {
        while (!states.isEmpty()) {
            FifteenPuzzle firstState = states.get(0);
            visitedBoards++;
            states.remove(0);

            if (firstState.getDepthLevel() > maxRecursionLevel && firstState.getDepthLevel() < 21) {
                maxRecursionLevel = firstState.getDepthLevel();
            }

            if (firstState.checkIfItIsASolution()) {
                Saving.saveToFile(firstState, System.nanoTime() - startTime, amountOfProcessedBoards, fileSol, fileStats, visitedBoards, maxRecursionLevel);
                return;
            }

            if (firstState.getDepthLevel() < 21) {
                for (int i = 0; i < 4; i++) {
                    int temp = states.size();
                    SwichClass.doMoveOperationHam(moves[i], firstState, states);
                    if (temp != states.size()) {
                        amountOfProcessedBoards++;
                    }
                }
            }
        }
        Saving.saveNoSolution(fileSol);
    }
}
