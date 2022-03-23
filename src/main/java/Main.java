import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filename1 = "C:\\SISE\\unsolvedGame";
        String filename2 = "C:\\SISE\\text.txt";
        String filename3 = "C:\\SISE\\latwy.txt";
        //       BreadthFirstSearch bfs = new BreadthFirstSearch(filename1, "RDLU");
        //   bfs.breadthAlgorithm();
        FifteenPuzzle f = new FifteenPuzzle(filename1);
        int cos = f.countDistance();
        System.out.println(cos);
        System.out.println(f.countWrongPlaces());
        System.out.println(f.checkIfItIsASolution());
//        DepthFirstSearch dfs = new DepthFirstSearch(filename1, "RULD");
//        dfs.depthAlgorithm();
    }


//    public static void printBoard(FifteenPuzzle board) {
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if (board.getBoard()[i][j] / 10 == 0) {
//                    System.out.print(board.getBoard()[i][j] + "  ");
//                } else {
//                    System.out.print(board.getBoard()[i][j] + " ");
//                }
//            }
//            System.out.println();
//        }
//    }
}
