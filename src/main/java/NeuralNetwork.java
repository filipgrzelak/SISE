public class NeuralNetwork {

    private boolean isUsingBiasH = true;
    private boolean isUsingBiasO = true;

    private final double learningRate;
    private final double momenutm;
    public double currentNeuralNetworkError = 0.0;

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
        Matrix savedWeightsIH = this.weightsIH;
        if(isUsingBiasH) {
            hidden = MatrixHelpers.addMatrixToMatrix(hidden, this.biasH);
        }

        // Activation function
        hidden = MatrixHelpers.sigmaOperationOnMatrix(hidden);

        // Generating the output
        Matrix outputs = MatrixHelpers.multiplyByMatrix(this.weightsHO, hidden);
        Matrix savedWeightsHO = this.weightsHO;
        if(isUsingBiasO) {
            outputs = MatrixHelpers.addMatrixToMatrix(outputs, this.biasO);
        }

        outputs = MatrixHelpers.sigmaOperationOnMatrix(outputs);

        // Convert array to matrix object
        Matrix targets = MatrixHelpers.fromArray(targetArray);

        // Calculate the error
        // ERROR = TARGETS - OUTPUTS
        Matrix outputErrors = MatrixHelpers.calculateError(targets, outputs);

        // let gradient = outputs * (1 - outputs);
        // Calculate gradient
        Matrix gradients = MatrixHelpers.dSigmaOperationOnMatrix(outputs);

        Matrix temp = MatrixHelpers.subtract(targets , outputs);
        temp = MatrixHelpers.multiplyByScalar(temp, -1);
        gradients = MatrixHelpers.multiplyByScalarMatrix(gradients, temp);

        Matrix gradientClone = gradients.clone();

        Matrix hiddenT = MatrixHelpers.transpose(hidden);
        Matrix weightHODeltas = MatrixHelpers.multiplyByMatrix(gradients, hiddenT);

//        gradients = MatrixHelpers.multiplyTempCos(gradients, hidden);
//        gradients = MatrixHelpers.multiplyByScalar(gradients, this.learningRate);

        weightHODeltas = MatrixHelpers.multiplyByScalar(weightHODeltas, this.learningRate);
        weightHODeltas = MatrixHelpers.multiplyByScalar(weightHODeltas, -1);

        // Adjust the weights by deltas
        this.weightsHO = MatrixHelpers.addMatrixToMatrix(this.weightsHO, weightHODeltas);

        // Momentum
        if(momenutm != 0) {
            Matrix momentumFactor = MatrixHelpers.subtract(this.weightsHO, savedWeightsHO);
            momentumFactor = MatrixHelpers.multiplyByScalar(momentumFactor, momenutm);
            this.weightsHO = MatrixHelpers.addMatrixToMatrix(this.weightsHO, momentumFactor);
        }

        // Adjust the bias by its deltas (which is just the gradients)
        if(isUsingBiasO) {
            this.biasO = MatrixHelpers.subtract(this.biasO, gradients);
//            this.biasO = MatrixHelpers.addMatrixToMatrix(this.biasO, gradients);
        }


        // Calculate the hidden layer errors
        Matrix whoT = MatrixHelpers.transpose(savedWeightsHO);
        Matrix hiddenErrors = MatrixHelpers.multiplyByMatrix(whoT, gradientClone);

        // Calculate hidden gradient
        Matrix hiddenGradient = MatrixHelpers.dSigmaOperationOnMatrix(hidden);
        hiddenGradient = MatrixHelpers.multiplyByScalarMatrix(hiddenGradient, hiddenErrors);
//        hiddenGradient = MatrixHelpers.multiplyByScalar(hiddenGradient, this.learningRate);

        // Calcuate input->hidden deltas
        Matrix inputsT = MatrixHelpers.transpose(inputs);
        Matrix weightIHDeltas = MatrixHelpers.multiplyByMatrix(hiddenGradient, inputsT);

        weightIHDeltas = MatrixHelpers.multiplyByScalar(weightIHDeltas, this.learningRate);

        weightIHDeltas = MatrixHelpers.multiplyByScalar(weightIHDeltas, -1);

        this.weightsIH = MatrixHelpers.addMatrixToMatrix(this.weightsIH, weightIHDeltas);

        // Momentum
        if(momenutm != 0) {
            Matrix momentumFactor = MatrixHelpers.subtract(this.weightsIH, savedWeightsIH);
            momentumFactor = MatrixHelpers.multiplyByScalar(momentumFactor, momenutm);
            this.weightsIH = MatrixHelpers.addMatrixToMatrix(this.weightsIH, momentumFactor);
        }

        if(isUsingBiasH) {
            this.biasH = MatrixHelpers.subtract(this.biasH, hiddenGradient);
//            this.biasH = MatrixHelpers.addMatrixToMatrix(this.biasH, hiddenGradient);
        }


        calculateNetworkError(outputErrors.data);
    }

    private void calculateNetworkError(double[][] outErrors) {
        double avgFirst = 0.0;
        for (int i = 0; i < outErrors.length; i++) {
            for (int j = 0; j < outErrors[0].length; j++) {
                avgFirst += outErrors[i][j];
            }
        }

        this.currentNeuralNetworkError += avgFirst;
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

    public double getCurrentNeuralNetworkError() {
        return currentNeuralNetworkError;
    }
}
