import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Float[][] trainigData = {
                {0f, 1f},
                {1f, 0f},
                {0f, 0f},
                {1f, 1f},
        };

        Float[] literalValues = {
                1f,1f,0f,0f
        };

        NeuralNetwork nn = new NeuralNetwork(2,2,1, 0.1f);

        for (int i = 0; i < 40000; i++) {
            int random = (int) (Math.random() * 4);
            nn.train(trainigData[random], new Float[]{literalValues[random]});
        }

        for (int i = 0; i < trainigData.length; i++) {
            System.out.println(Arrays.toString(nn.predict(trainigData[i])));
        }

    }
}
