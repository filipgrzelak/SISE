import org.jfree.data.json.impl.JSONArray;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Data {

    private final Scanner scanner;
    private NeuralNetwork nn;

    List<double[][]> trainigData;
    List<double[]> unknownData;
    List<double[]> unknownDataWithSpiecies;

    private double TP = 0;
    private double TN = 0;
    private double FP = 0;
    private double FN = 0;

    private double accuracy = 0;


    public Data(NeuralNetwork neuralNetwork) {
        this.scanner = new Scanner(System.in);
        this.nn = neuralNetwork;
        this.trainigData = loadTrainingData();
        this.unknownData = loadUnknownData();
        this.unknownDataWithSpiecies = loadUnknownDataWithSpiecies();
    }

    public void train() {
        int ages = 40000;
        ArrayList<Double> calculatedNetworkErrors = new ArrayList<>();
        ArrayList<Integer> agesCounter = new ArrayList<>();
        List<double[][]> data = trainigData;
        for (int i = 0; i < ages; i++) {
            agesCounter.add(i);
            Collections.shuffle(data);
            for (double[][] sample : data) {
                nn.train(sample[0], sample[1]);
            }
            calculatedNetworkErrors.add(nn.getCurrentNeuralNetworkError());
        }

        drawChart(calculatedNetworkErrors, agesCounter);
    }

    private void drawChart(ArrayList<Double> errs, ArrayList<Integer> ages) {
        SwingUtilities.invokeLater(() -> {
            Chart errorChart = new Chart("Neural Network errors", ages, errs);
            errorChart.setAlwaysOnTop(true);
            errorChart.pack();
            errorChart.setSize(600, 400);
            errorChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            errorChart.setVisible(true);
        });
    }

    public void predict() {

        // 0 - 1spiecies, 1 - 2spiecies, 2 - 3spiecies
        double[] statistics = new double[3];
        double ij = 0;
        double all = 0;

        for (int i = 0; i < unknownData.size() - 1; i++) {
            Double[] prediction = nn.predict(unknownData.get(i));
            System.out.println(Arrays.toString(prediction));

            int typedSpecies = Arrays.asList(prediction).indexOf(Collections.max(Arrays.asList(prediction)));
            int actualSpiecies = (int) unknownDataWithSpiecies.get(i)[4];

            int[] helperTyped = new int[3];
            int[] helperActual = new int[3];
            helperTyped[typedSpecies] = 1;
            helperActual[actualSpiecies] = 1;

            for (int j = 0; j < helperTyped.length; j++) {
                if (helperTyped[j] == 1) {
                    if (helperTyped[j] == helperActual[j]) this.TP++;
                    else if (helperTyped[j] != helperActual[j]) this.TN++;
                } else {
                    if (helperActual[j] == 1) this.FP++;
                    else if (helperActual[j] != 1) this.FN++;
                }
            }

            statistics[typedSpecies]++;
        }

        System.out.println(Arrays.toString(statistics));
        System.out.println("Accuracy: " + getAccuracy());
        System.out.println("Precision: " + getPrecision());
        System.out.println("Recall: " + getRecall());
        System.out.println("FMeasure: " + getFMeasure());
    }

    private double getPrecision() {
        return (this.TP / (this.TP + this.FP));
    }

    private double getAccuracy() {
        return ((this.TP + this.FN) / (this.TP + this.TN + this.FP + this.FN));
    }

    private double getRecall() {
        return (this.TP / (this.TP + this.FN));
    }

    private double getFMeasure() {
        return 2 * ((getPrecision() * getRecall()) / (getPrecision() + getRecall()));
    }

    public void saveNeuralNetworkProperties() {
        LineReader.saveNeuralNetworkPropertiesToFile("neuralNetworkProperties.txt", nn);
    }

    public void loadNeuralNetworkProperties() {
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

        this.nn = new NeuralNetwork(0.1, 0.9, weightsIH, weightsHO, biasH, biasO);
    }

    public List<double[][]> loadTrainingData() {
        List<String> props = LineReader.readLinesFromFile("trainData.txt");
        List<double[][]> data = new ArrayList<>();

        for (int i = 0; i < props.size(); i++) {
            List<String> temp = Arrays.stream(props.get(i).split(",")).toList();
            double[] secondPart;
            if(Double.parseDouble(temp.get(temp.size() - 1)) == 0.0) {
                secondPart = new double[] { 1.0, 0.0, 0.0 };
            } else if (Double.parseDouble(temp.get(temp.size() - 1)) == 1.0) {
                secondPart = new double[] { 0.0, 1.0, 0.0 };
            } else if (Double.parseDouble(temp.get(temp.size() - 1)) == 2.0) {
                secondPart = new double[] { 0.0, 0.0, 1.0 };
            } else {
                System.out.println("error in loadTrainingData");
                return null;
            }

            double[] firstPart = new double[temp.size() - 1];
            for (int j = 0; j < firstPart.length; j++) {
                firstPart[j] = Double.parseDouble(temp.get(j));
            }
            double[][] object = new double[][] { firstPart, secondPart };
            data.add(object);

        }

        return data;
    }

    public List<double[]> loadUnknownData() {
        List<String> props = LineReader.readLinesFromFile("irisUnknownData.txt");
        List<double[]> data = new ArrayList<>();

        for (int i = 0; i < props.size() - 1; i++) {
            List<String> temp = Arrays.stream(props.get(i).split(",")).toList();
            double[] firstPart = new double[temp.size() - 1];
            for (int j = 0; j < firstPart.length; j++) {
                firstPart[j] = Double.parseDouble(temp.get(j));
            }
            data.add(firstPart);
        }

        return data;
    }

    public List<double[]> loadUnknownDataWithSpiecies() {
        List<String> props = LineReader.readLinesFromFile("irisUnknownData.txt");
        List<double[]> data = new ArrayList<>();

        for (int i = 0; i < props.size() - 1; i++) {
            List<String> temp = Arrays.stream(props.get(i).split(",")).toList();
            double[] firstPart = new double[temp.size()];
            for (int j = 0; j < firstPart.length; j++) {
                firstPart[j] = Double.parseDouble(temp.get(j));
            }
            data.add(firstPart);
        }

        return data;
    }
}
