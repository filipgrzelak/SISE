import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filename1 = "C:\\SISE\\unsolvedGame";
        String filename2 = "C:\\SISE\\text.txt";
//        BreadthFirstSearch bfs = new BreadthFirstSearch(filename2,"RDLU");
//        bfs.breadthAlgorithm();
        DepthFirstSearch dfs = new DepthFirstSearch(filename1,"RDLU");
        dfs.depthAlgorithm();
    }



    public static void printBoard(FifteenPuzzle board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getBoard()[i][j] / 10 == 0) {
                    System.out.print(board.getBoard()[i][j] + "  ");
                } else {
                    System.out.print(board.getBoard()[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
