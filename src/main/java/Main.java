

public class Main {

    public static void main(String[] args) {

        Data data = new Data(new NeuralNetwork(2,2,1,0.1));
        data.train();
        data.predict();

        data.saveNeuralNetworkProperties();

        NeuralNetwork newNtwk = data.loadNeuralNetworkProperties();

    }
}
