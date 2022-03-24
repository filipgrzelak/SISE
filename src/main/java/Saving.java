import java.io.FileWriter;
import java.io.IOException;

public class Saving {
    public static void saveToFile(FifteenPuzzle board, long timeOfOperationInMiliSec, int processedBoards, String fileSol, String fileStats, int visitedBoards, int maxResurion) throws IOException {
        saveSolution(fileSol, board);
        saveStats(fileStats, board, timeOfOperationInMiliSec, processedBoards, visitedBoards, maxResurion);
    }

    private static void saveSolution(String filename, FifteenPuzzle board) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(board.getAllMovesList().size() + "\n");
        fw.write(board.getAllMovesList().toString());
        fw.close();
    }

    private static void saveStats(String filename, FifteenPuzzle board, long timeOfOperationInMiliSec, int processedBoards, int visitedBoards, int maxRecursion) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(board.getAllMovesList().size() + "\n");
        fw.write(visitedBoards + "\n");
        fw.write(processedBoards + "\n");
        fw.write(maxRecursion + "\n");
        fw.write(Math.round(timeOfOperationInMiliSec / 1000.0) / 1000.0 + "\n");
        fw.close();
    }

    public static void saveNoSolution(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write("-1");;
        fw.close();
    }
}
