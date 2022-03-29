import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BreadthFirstSearch {
    private static Deque<FifteenPuzzle> states = new ArrayDeque<>();
    private char[] moves = new char[4];
    private long startTime = System.nanoTime();
    private int amountOfProcessedBoards = 1;
    private int visitedBoards = 0;
    private int maxRecursionLevel = 0;
    private List<Integer> hashes = new ArrayList<>();
    private String fileStats;
    private String fileSol;

    public BreadthFirstSearch(String filename, String moves, String fileSol, String fileStats) throws IOException {
        this.fileSol = fileSol;
        this.fileStats = fileStats;
        for (int i = 0; i < 4; i++) {
            this.moves[i] = moves.charAt(i);
        }
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(filename);
        states.add(fifteenPuzzle);
    }

    public void breadthAlgorithm() throws IOException {
        while (!states.isEmpty()) {
            FifteenPuzzle firstState = states.getFirst();
            visitedBoards++;
            states.removeFirst();
            if (firstState.getDepthLevel() > maxRecursionLevel) {
                maxRecursionLevel = firstState.getDepthLevel();
            }

            if (firstState.checkIfItIsASolution()) {
                Saving.saveToFile(firstState, System.nanoTime() - startTime, amountOfProcessedBoards, fileSol, fileStats, visitedBoards, maxRecursionLevel);
                return;
            }
            if(firstState.getDepthLevel() < 20) {
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
