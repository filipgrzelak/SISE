

public class Main {

    public static void main(String[] args) {

        Data data = new Data(new NeuralNetwork(4,4,3,0.1));
        data.train();
//        data.loadNeuralNetworkProperties();
        data.predict();
        data.saveNeuralNetworkProperties();
    }
}
