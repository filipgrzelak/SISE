import java.util.Arrays;

public class NeuralNetwork {

    private final float learningRate;
    public Matrix weightsIH;
    public Matrix weightsHO;
    public Matrix biasH;
    public Matrix biasO;

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, float learningRate) {
        this.learningRate = learningRate;

        this.weightsIH = new Matrix(hiddenNodes, inputNodes);
        this.weightsHO = new Matrix(outputNodes, hiddenNodes);

        this.biasH = new Matrix(hiddenNodes, 1);
        this.biasO = new Matrix(outputNodes, 1);

        weightsIH = MatrixHelpers.randomizeDataValues(weightsIH);
        weightsHO = MatrixHelpers.randomizeDataValues(weightsHO);
    }

    public Float[] predict(Float[] inputArray) {

        // Generating the Hidden Outputs
        Matrix inputs = MatrixHelpers.fromArray(inputArray);
        Matrix hidden = MatrixHelpers.multiplyByMatrix(this.weightsIH, inputs);
        hidden = MatrixHelpers.addMatrixToMatrix(hidden, this.biasH);

        // activation function
        hidden = MatrixHelpers.sigmaOperationOnMatrix(hidden);

        // Generating the output
        Matrix output = MatrixHelpers.multiplyByMatrix(this.weightsHO, hidden);
        output = MatrixHelpers.addMatrixToMatrix(output, this.biasO);
        output = MatrixHelpers.sigmaOperationOnMatrix(output);

        return MatrixHelpers.toArray(output);
    }

    public void train(Float[] inputArray, Float[] targetArray) {

        // Generating the Hidden Outputs
        Matrix inputs = MatrixHelpers.fromArray(inputArray);
        Matrix hidden = MatrixHelpers.multiplyByMatrix(this.weightsIH, inputs);
        hidden = MatrixHelpers.addMatrixToMatrix(hidden, this.biasH);

        // Activation function
        hidden = MatrixHelpers.sigmaOperationOnMatrix(hidden);

        // Generating the output
        Matrix outputs = MatrixHelpers.multiplyByMatrix(this.weightsHO, hidden);
        outputs = MatrixHelpers.addMatrixToMatrix(outputs, this.biasO);
        outputs = MatrixHelpers.sigmaOperationOnMatrix(outputs);

        // Convert array to matrix object
        Matrix targets = MatrixHelpers.fromArray(targetArray);

        // Calculate the error
        // ERROR = TARGETS - OUTPUTS
        Matrix outputErrors = MatrixHelpers.subtract(targets, outputs);

        // let gradient = outputs * (1 - outputs);
        // Calculate gradient
        Matrix gradients = MatrixHelpers.dSigmaOperationOnMatrix(outputs);
        gradients = MatrixHelpers.multiplyByScalarMatrix(gradients, outputErrors);
        gradients = MatrixHelpers.multiplyByScalar(gradients, this.learningRate);

        // Calculate deltas
        Matrix hiddenT = MatrixHelpers.transpose(hidden);
        Matrix weightHODeltas = MatrixHelpers.multiplyByMatrix(gradients, hiddenT);

        // Adjust the weights by deltas
        this.weightsHO = MatrixHelpers.addMatrixToMatrix(this.weightsHO, weightHODeltas);

        // Adjust the bias by its deltas (which is just the gradients)
        this.biasO = MatrixHelpers.addMatrixToMatrix(this.biasO, gradients);

        // Calculate the hidden layer errors
        Matrix whoT = MatrixHelpers.transpose(this.weightsHO);
        Matrix hiddenErrors = MatrixHelpers.multiplyByMatrix(whoT, outputErrors);

        // Calculate hidden gradient
        Matrix hiddenGradient = MatrixHelpers.dSigmaOperationOnMatrix(hidden);
        hiddenGradient = MatrixHelpers.multiplyByScalarMatrix(hiddenGradient, hiddenErrors);
        hiddenGradient = MatrixHelpers.multiplyByScalar(hiddenGradient, this.learningRate);

        // Calcuate input->hidden deltas
        Matrix inputsT = MatrixHelpers.transpose(inputs);
        Matrix weightIHDeltas = MatrixHelpers.multiplyByMatrix(hiddenGradient, inputsT);

        this.weightsIH = MatrixHelpers.addMatrixToMatrix(this.weightsIH, weightIHDeltas);
        this.biasH = MatrixHelpers.addMatrixToMatrix(this.biasH, hiddenGradient);
    }
}
