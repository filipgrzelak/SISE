

public class Main {

    public static void main(String[] args) {

        Data data = new Data(new NeuralNetwork(4,2,3,0.01, 0.0));
        data.train();
//        data.loadNeuralNetworkProperties();
        data.predict();
        data.saveNeuralNetworkProperties();
    }
}
