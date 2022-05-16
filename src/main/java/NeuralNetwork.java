public class NeuralNetwork {

    private boolean isUsingBiasH = true;
    private boolean isUsingBiasO = true;

    private final double learningRate;
    private final double momenutm;
    private Matrix weightsIH;
    private Matrix weightsHO;
    private Matrix biasH;
    private Matrix biasO;

    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, double learningRate, double momentum) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        this.learningRate = learningRate;
        this.momenutm = momentum;

        this.weightsIH = new Matrix(hiddenNodes, inputNodes);
        this.weightsHO = new Matrix(outputNodes, hiddenNodes);

        this.biasH = new Matrix(hiddenNodes, 1);
        this.biasO = new Matrix(outputNodes, 1);

        this.weightsIH = MatrixHelpers.randomizeDataValues(weightsIH);
        this.weightsHO = MatrixHelpers.randomizeDataValues(weightsHO);
    }

    public NeuralNetwork(double learningRate, double momenutm, Matrix weightsIH, Matrix weightsHO, Matrix biasH, Matrix biasO) {
        this.learningRate = learningRate;
        this.momenutm = momenutm;

        this.weightsIH = weightsIH;
        this.weightsHO = weightsHO;

        this.biasH = biasH;
        this.biasO = biasO;
    }

    public Double[] predict(double[] inputArray) {

        // Generating the Hidden Outputs
        Matrix inputs = MatrixHelpers.fromArray(inputArray);
        Matrix hidden = MatrixHelpers.multiplyByMatrix(this.weightsIH, inputs);
        if(isUsingBiasH)
            hidden = MatrixHelpers.addMatrixToMatrix(hidden, this.biasH);

        // activation function
        hidden = MatrixHelpers.sigmaOperationOnMatrix(hidden);

        // Generating the output
        Matrix output = MatrixHelpers.multiplyByMatrix(this.weightsHO, hidden);
        if(isUsingBiasO)
            output = MatrixHelpers.addMatrixToMatrix(output, this.biasO);
        output = MatrixHelpers.sigmaOperationOnMatrix(output);

        return MatrixHelpers.toArray(output);
    }

    public void train(double[] inputArray, double[] targetArray) {

        // Generating the Hidden Outputs
        Matrix inputs = MatrixHelpers.fromArray(inputArray);
        Matrix hidden = MatrixHelpers.multiplyByMatrix(this.weightsIH, inputs);
        Matrix prevHidden = this.weightsIH;
        if(isUsingBiasH)
            hidden = MatrixHelpers.addMatrixToMatrix(hidden, this.biasH);

        // Activation function
        hidden = MatrixHelpers.sigmaOperationOnMatrix(hidden);

        // Generating the output
        Matrix outputs = MatrixHelpers.multiplyByMatrix(this.weightsHO, hidden);
        Matrix prevOutputs = this.weightsHO;
        if(isUsingBiasO)
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

        // Momentum
        if(momenutm != 0) {
            Matrix momentumFactor = MatrixHelpers.subtract(this.weightsHO, prevOutputs);
            momentumFactor = MatrixHelpers.multiplyByScalar(momentumFactor, momenutm);
            this.weightsHO = MatrixHelpers.addMatrixToMatrix(this.weightsHO, momentumFactor);
        }

        // Adjust the bias by its deltas (which is just the gradients)
        if(isUsingBiasO)
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

        // Momentum
        if(momenutm != 0) {
            Matrix momentumFactor = MatrixHelpers.subtract(this.weightsIH, prevHidden);
            momentumFactor = MatrixHelpers.multiplyByScalar(momentumFactor, momenutm);
            this.weightsIH = MatrixHelpers.addMatrixToMatrix(this.weightsIH, momentumFactor);
        }

        if(isUsingBiasH)
            this.biasH = MatrixHelpers.addMatrixToMatrix(this.biasH, hiddenGradient);
    }

    public Matrix getWeightsIH() {
        return weightsIH;
    }

    public Matrix getWeightsHO() {
        return weightsHO;
    }

    public Matrix getBiasH() {
        return biasH;
    }

    public Matrix getBiasO() {
        return biasO;
    }

    public int getInputNodes() {
        return inputNodes;
    }

    public int getHiddenNodes() {
        return hiddenNodes;
    }

    public int getOutputNodes() {
        return outputNodes;
    }

}
