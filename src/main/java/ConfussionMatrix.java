import java.util.Arrays;

public class ConfussionMatrix {

    private double hypothesis;
    private double TP = 0;
    private double TN = 0;
    private double FP = 0;
    private double FN = 0;

    public ConfussionMatrix(double hypothesis) {
        this.hypothesis = hypothesis;
    }

    public double getPrecision() {
        return (this.TP / (this.TP + this.FP));
    }

    public double getAccuracy() {
        return ((this.TP + this.FN) / (this.TP + this.TN + this.FP + this.FN));
    }

    public double getRecall() {
        return (this.TP / (this.TP + this.FN));
    }

    public double getFMeasure() {
        return 2 * ((getPrecision() * getRecall()) / (getPrecision() + getRecall()));
    }

    public void calculateConditions(double prediction, double actual) {

        // true, false -> actual condition
        // positive, negative -> predicion condition
        boolean predictionCondition = prediction == this.hypothesis;
        boolean actualCondition = actual == this.hypothesis;

        if (predictionCondition && actualCondition) this.TP++;
        if (!predictionCondition && actualCondition) this.TN++;
        if (predictionCondition && !actualCondition) this.FP++;
        if (!predictionCondition && !actualCondition) this.FN++;
    }

    public void showResults() {
        System.out.println("============ " + this.hypothesis + " spiecies ===============");
        System.out.println("TP: " + this.TP);
        System.out.println("TN: " + this.TN);
        System.out.println("FP: " + this.FP);
        System.out.println("FN: " + this.FN);
        System.out.println("Accuracy: " + getAccuracy());
        System.out.println("Precision: " + getPrecision());
        System.out.println("Recall: " + getRecall());
        System.out.println("FMeasure: " + getFMeasure());
        System.out.println("=========================================");
    }

}
