import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        String filename1 = "C:\\SISE\\unsolvedGame";
//        String filename2 = "C:\\SISE\\text.txt";
//        String filename3 = "C:\\SISE\\latwy.txt";
//        Manhattan man1 = new Manhattan(filename1,"sol.txt","stats.txt");
//        man1.manhattan();
//
//        FifteenPuzzle f = new FifteenPuzzle(filename1);
//        f.countWrongPlaces();
//        System.out.println(f.getCountValue());
//
//        Hamming ham1 = new Hamming(filename1, "rozwiazanie.txt", "statystyki.txt");
//        ham1.hamming();

//        BreadthFirstSearch bfs = new BreadthFirstSearch(filename1, "RDLU");
//        bfs.breadthAlgorithm();

//        DepthFirstSearch dfs = new DepthFirstSearch(filename1, "LURD","sol.txt","stats.txt");
//        dfs.depthAlgorithm();

        switch (args[0]) {
            case "bfs" : {
                BreadthFirstSearch bfs = new BreadthFirstSearch(args[2], args[1], args[3], args[4]);
                bfs.breadthAlgorithm();
                break;
            }
            case "dfs" : {
                DepthFirstSearch dfs = new DepthFirstSearch(args[2], args[1], args[3], args[4]);
                dfs.depthAlgorithm();
                break;
            }
            case "astr" : {
                switch (args[1]) {
                    case "manh": {
                        Manhattan man = new Manhattan(args[2], args[3], args[4]);
                        man.manhattan();
                        break;
                    }
                    case "hamm": {
                        Hamming ham = new Hamming(args[2], args[3], args[4]);
                        ham.hamming();
                        break;
                    }
                    default: {
                        System.out.println("Zla wartosc heurystyki");
                        break;
                    }
                }
                break;
            }
            default: {
                System.out.println("Bledne dane");
            }
        }
    }


}
