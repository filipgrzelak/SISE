import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Data {

    private final Scanner scanner;
    private NeuralNetwork nn;

    double[][][] trainigData = {
            {{0.0, 1.0}, {1.0}},
            {{1.0, 0.0}, {1.0}},
            {{0.0, 0.0}, {0.0}},
            {{1.0, 1.0}, {0.0}},
    };

    double[][] unknownData = {
            {0.0, 1.0},
            {1.0, 0.0},
            {0.0, 0.0},
            {1.0, 1.0},
    };

    public Data(NeuralNetwork neuralNetwork) {
        this.scanner = new Scanner(System.in);
        this.nn = neuralNetwork;
    }

    public void train() {
        for (int i = 0; i < 40000; i++) {
            int random = (int) (Math.random() * 4);
            nn.train(trainigData[random][0], trainigData[random][1]);
        }
    }

    public void predict() {
        for (double[] unknownDatum : unknownData) {
            System.out.println(Arrays.toString(nn.predict(unknownDatum)));
        }
    }

    public void saveNeuralNetworkProperties() {
        LineReader.saveNeuralNetworkPropertiesToFile("neuralNetworkProperties.txt", nn);
    }

    public NeuralNetwork loadNeuralNetworkProperties() {
        int counter = 0;
        Matrix weightsIH;
        Matrix weightsHO;
        Matrix biasH;
        Matrix biasO;

        List<String> props = LineReader.readLinesFromFile("neuralNetworkProperties.txt");
        List<String> splitted = Arrays.stream(props.get(0).split("]")).toList();
        splitted = splitted.stream().map(x -> x.replace("[", "")).collect(Collectors.toList());
        splitted = splitted.stream().map(x -> x.replace(",", "")).collect(Collectors.toList());
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < splitted.size(); i++) {
            result.add(Arrays.stream(splitted.get(i).split(" ")).toList());
        }

        int inputNodesCount = result.get(4).get(0).getBytes(StandardCharsets.UTF_8)[0];
        int hiddenNodesCount = result.get(5).get(0).getBytes(StandardCharsets.UTF_8)[0];
        int outputNodesCount = result.get(6).get(0).getBytes(StandardCharsets.UTF_8)[0];

        weightsIH = new Matrix(hiddenNodesCount, inputNodesCount);
        weightsHO = new Matrix(outputNodesCount, hiddenNodesCount);
        biasH = new Matrix(hiddenNodesCount, 1);
        biasO = new Matrix(outputNodesCount, 1);


        for (int i = 0; i < hiddenNodesCount; i++) {
            for (int j = 0; j < inputNodesCount; j++) {
                weightsIH.data[i][j] = Double.parseDouble(result.get(0).get(counter));
                counter++;
            }
        }
        counter = 0;

        for (int i = 0; i < outputNodesCount; i++) {
            for (int j = 0; j < hiddenNodesCount; j++) {
                weightsHO.data[i][j] = Double.parseDouble(result.get(1).get(counter));
                counter++;
            }
        }
        counter = 0;

        for (int i = 0; i < hiddenNodesCount; i++) {
            for (int j = 0; j < 1; j++) {
                biasH.data[i][j] = Double.parseDouble(result.get(2).get(counter));
                counter++;
            }
        }
        counter = 0;

        for (int i = 0; i < outputNodesCount; i++) {
            for (int j = 0; j < 1; j++) {
                biasO.data[i][j] = Double.parseDouble(result.get(3).get(counter));
                counter++;
            }
        }

        return new NeuralNetwork(0.1, weightsIH, weightsHO, biasH, biasO);
    }
//    public void setAccuracy() {
//        boolean shouldContinue = true;
//
//        while (shouldContinue) {
//            System.out.println();
//            try {
//                System.out.print("Podaj dokładność: ");
//                accuracy = Double.parseDouble(scanner.nextLine());
//
//                shouldContinue = false;
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Podano nieprawidłową liczbę!");
//            }
//        }
//    }
}
