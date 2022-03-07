import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class BreadthFirstSearch {
    private static Deque<FifteenPuzzle> states = new ArrayDeque<>();

    public BreadthFirstSearch(String filename) throws IOException {
        System.out.println("cos");
        states.add(new FifteenPuzzle(filename));
    }

    public FifteenPuzzle breadthAlgorithm() throws IOException {
        if (states.getFirst().checkIfItIsASolution()) {
            return states.getFirst();
        }
        FifteenPuzzle firstState = states.getFirst();
        states.removeFirst();

        try {
            states.add(new FifteenPuzzle(firstState.upMoveChangePlaces()));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            states.add(new FifteenPuzzle(firstState.leftMoveChangePlaces()));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            states.add(new FifteenPuzzle(firstState.rightMoveChangePlaces()));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            states.add(new FifteenPuzzle(firstState.downMoveChangePlaces()));
        } catch (Exception e) {
            System.out.println(e);
        }


        breadthAlgorithm();
        return null;
    }


}
