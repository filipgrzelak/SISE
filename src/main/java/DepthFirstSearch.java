import java.io.IOException;
import java.util.Stack;

public class DepthFirstSearch {
    private Stack<FifteenPuzzle> stack = new Stack<>();

    public DepthFirstSearch(String filename) throws IOException {
        stack.add(new FifteenPuzzle(filename));
    }

    public void depthAlgorithm() throws IOException {
        FifteenPuzzle state = stack.pop();
        if (state.checkIfItIsASolution()) {
            System.out.println("wszedlem");
            FifteenPuzzle.saveToFile(state);
            return;
        }
        if(state.getDeepthLevel() < 20) {
            try {
                stack.add(new FifteenPuzzle(state.leftMoveChangePlaces()));
            } catch (Exception e) {

            }

            try {
                stack.add(new FifteenPuzzle(state.upMoveChangePlaces()));
            } catch (Exception e) {

            }

            try {
                stack.add(new FifteenPuzzle(state.rightMoveChangePlaces()));
            } catch (Exception e) {

            }

            try {
                stack.add(new FifteenPuzzle(state.downMoveChangePlaces()));
            } catch (Exception e) {
            }
        }



        depthAlgorithm();
        return;
    }
}
