import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Data {

    private final Scanner scanner;
    private NeuralNetwork nn;

    List<double[][]> trainigData;
    List<double[]> unknownData;

    public Data(NeuralNetwork neuralNetwork) {
        this.scanner = new Scanner(System.in);
        this.nn = neuralNetwork;
        this.trainigData = loadTrainingData();
        this.unknownData = loadUnknownData();
    }

    public void train() {
        List<double[][]> data = trainigData;
        for (int i = 0; i < 200000; i++) {
            Collections.shuffle(data);
            for (double[][] sample : data) {
                nn.train(sample[0], sample[1]);
            }
        }
    }

    public void predict() {

        // 0 - 1spiecies, 1 - 2spiecies, 2 - 3spiecies, 3 - unknown
        double[] statistics = new double[4];

        for (int i = 0; i < unknownData.size() - 1; i++) {
            Double[] prediction = nn.predict(unknownData.get(i));
            System.out.println(Arrays.toString(prediction));

            if(
                    prediction[0] > 0.8 && prediction[1] > 0.8 ||
                    prediction[1] > 0.8 && prediction[2] > 0.8 ||
                    prediction[0] > 0.8 && prediction[2] > 0.8
            ) {
                statistics[3]++;
            } else if (prediction[0] > 0.8) {
                statistics[0]++;
            } else if (prediction[1] > 0.8) {
                statistics[1]++;
            } else if (prediction[2] > 0.8) {
                statistics[2]++;
            }
        }

        System.out.println(Arrays.toString(statistics));
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

        this.nn = new NeuralNetwork(0.1, weightsIH, weightsHO, biasH, biasO);
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
        List<String> props = LineReader.readLinesFromFile("iris.txt");
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
}
