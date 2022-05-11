import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineReader {

    private LineReader() {
    }

    public static List<String> readLinesFromFile(String fileName) {
        List<String> result = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = bf.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void saveNeuralNetworkPropertiesToFile(String fileName, NeuralNetwork nn) {
        try (FileWriter writer = new FileWriter(fileName);
             BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(Arrays.toString(MatrixHelpers.toArray(nn.getWeightsIH())));
            bw.write(",");
            bw.write(Arrays.toString(MatrixHelpers.toArray(nn.getWeightsHO())));
            bw.write(",");
            bw.write(Arrays.toString(MatrixHelpers.toArray(nn.getBiasH())));
            bw.write(",");
            bw.write(Arrays.toString(MatrixHelpers.toArray(nn.getBiasO())));
            bw.write(",[");
            bw.write(nn.getInputNodes());
            bw.write("],[");
            bw.write(nn.getHiddenNodes());
            bw.write("],[");
            bw.write(nn.getOutputNodes());
            bw.write("]");
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
