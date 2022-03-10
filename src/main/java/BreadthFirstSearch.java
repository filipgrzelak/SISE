import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BreadthFirstSearch {
    private static Deque<FifteenPuzzle> states = new ArrayDeque<>();
    private char[] moves = new char[4];
    private long startTime = System.nanoTime();
    private int amountOfProcessedBoards = 1;
    private List<Integer> hashes = new ArrayList<>();
    private int distance = 0;

    public BreadthFirstSearch(String filename, String moves) throws IOException {
        for (int i = 0; i < 4; i++) {
            this.moves[i] = moves.charAt(i);
        }
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(filename);
        distance = fifteenPuzzle.countDistance();
        states.add(fifteenPuzzle);
    }

    public void breadthAlgorithm() throws IOException {
        FifteenPuzzle firstState = states.getFirst();
        states.removeFirst();
        if(firstState.getCurrentState()%10000 ==0) {
            distance -= 1;
        }
        if (firstState.checkIfItIsASolution()) {
            FifteenPuzzle.saveToFile(firstState, System.nanoTime() - startTime, amountOfProcessedBoards);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int temp = states.size();
            SwichClass.doMoveOperation(moves[i], firstState, states, distance);
            if (temp != states.size()) {
                amountOfProcessedBoards++;
            }
        }

        breadthAlgorithm();
    }

}
