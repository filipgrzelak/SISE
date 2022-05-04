public class Matrix implements Cloneable {

    public int rows;
    public int cols;
    public float data[][];

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new float[rows][cols];
    }

    @Override
    public Matrix clone() {
        try {
            Matrix clone = (Matrix) super.clone();
            clone.data = new float[clone.rows][clone.cols];
            for (int i = 0; i < clone.rows; i++) {
                if (clone.cols >= 0) System.arraycopy(this.data[i], 0, clone.data[i], 0, clone.cols);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
