import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class BreadthFirstSearch {
    private static Deque<FifteenPuzzle> states = new ArrayDeque<>();
    private char[] moves = new char[4];
    private long startTime;
    private int amountOfProcessedBoards = 1;

    public BreadthFirstSearch(String filename,String moves) throws IOException {
        startTime = System.nanoTime();
        for (int i = 0; i < 4; i++) {
            this.moves[i] = moves.charAt(i);
        }
        states.add(new FifteenPuzzle(filename));
    }

    public void breadthAlgorithm() throws IOException {
        FifteenPuzzle firstState = states.getFirst();
        states.removeFirst();
        if (firstState.checkIfItIsASolution()) {
            FifteenPuzzle.saveToFile(firstState,System.nanoTime() - startTime,amountOfProcessedBoards);
            return;
        }

        for (int i = 0; i < 4; i++) {
            try {
                states.add(firstState.doMoveOperation(moves[i]));
                amountOfProcessedBoards++;
            } catch (Exception e) {

            }
        }

        breadthAlgorithm();
    }

}
