import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class BreadthFirstSearch {
    private static Deque<FifteenPuzzle> states = new ArrayDeque<>();

    public BreadthFirstSearch(String filename) throws IOException {

        states.add(new FifteenPuzzle(filename));
    }

    public void breadthAlgorithm() throws IOException {
        FifteenPuzzle firstState = states.getFirst();
        states.removeFirst();
        if (firstState.checkIfItIsASolution()) {
            System.out.println("wszedlem");
            FifteenPuzzle.saveToFile(firstState);
            return;
        }
        if(firstState.getDeepthLevel() < 20) {
            try {
                states.add(new FifteenPuzzle(firstState.leftMoveChangePlaces()));
            } catch (Exception e) {

            }

            try {
                states.add(new FifteenPuzzle(firstState.upMoveChangePlaces()));
            } catch (Exception e) {

            }

            try {
                states.add(new FifteenPuzzle(firstState.rightMoveChangePlaces()));
            } catch (Exception e) {

            }

            try {
                states.add(new FifteenPuzzle(firstState.downMoveChangePlaces()));
            } catch (Exception e) {
            }
        }


        breadthAlgorithm();
        return;
    }

}
