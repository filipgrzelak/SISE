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
            saveToFile(firstState);
            return;
        }
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

        breadthAlgorithm();
        return;
    }

    public void saveToFile(FifteenPuzzle board) throws IOException {
        FileWriter fw = new FileWriter("wynik.txt");
        fw.write(board.toString() + "\n");

        String line = "";
        for (int i = 0; i < 4; i++) {
            line = "";
            for (int j = 0; j < 4; j++) {
                if (board.getBoard()[i][j] / 10 == 0) {
                    line += board.getBoard()[i][j] + "  ";
                } else {
                    line += board.getBoard()[i][j] + " ";
                }
            }
            fw.write(line + "\n");
        }
        fw.close();
    }


}
