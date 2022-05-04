import java.util.ArrayList;
import java.util.List;

public class MatrixHelpers {
    private MatrixHelpers() {
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.cols != b.cols) {
            System.out.println("Columns and rows of matrix doesn't match");
            return null;
        }

        Matrix c = new Matrix(a.rows, a.cols);

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                c.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }

        return c;
    }

    public static Matrix randomizeDataValues(Matrix a) {
        Matrix b = a.clone();

        for (int i = 0; i < b.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                b.data[i][j] = (float)(Math.random() * 1) - 1;
            }
        }

        return b;
    }

    public static Matrix addScalar(Matrix a, int n) {
        Matrix b = a.clone();

        for (int i = 0; i < b.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                b.data[i][j] = (float)(Math.random() * 1) - 1;
            }
        }

        return b;
    }

    public static Matrix addMatrixToMatrix(Matrix a, Matrix b) {
        Matrix c = new Matrix(a.rows, a.cols);

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                c.data[i][j] = a.data[i][j] + b.data[i][j];
            }
        }

        return c;
    }

    public static Matrix transpose(Matrix a) {
        Matrix b = new Matrix(a.cols, a.rows);

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                b.data[j][i] = a.data[i][j];
            }
        }

        return b;
    }

    public static Matrix multiplyByScalar(Matrix a, float n) {
        Matrix b = new Matrix(a.rows, a.cols);

        for (int i = 0; i < b.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                b.data[i][j] = a.data[i][j] * n;
            }
        }

        return b;
    }

    public static Matrix multiplyByScalarMatrix(Matrix a, Matrix b) {
        Matrix c = new Matrix(a.rows, a.cols);

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                c.data[i][j] = a.data[i][j] * b.data[i][j];
            }
        }

        return c;
    }

    public static Matrix multiplyByMatrix(Matrix a, Matrix b) {
        if (a.cols != b.rows) {
            System.out.println("Columns of A must match rows of B.");
            return null;
        }

        Matrix c = new Matrix(a.rows, b.cols);

        for (int i = 0; i < c.rows; i++) {
            for (int j = 0; j < c.cols; j++) {
                Float sum = 0f;
                for (int k = 0; k < a.cols; k++) {
                    sum += a.data[i][k] * b.data[k][j];
                }
                c.data[i][j] = sum;
            }
        }

        return c;
    }

    public static Matrix fromArray(Float[] arr) {
        Matrix m = new Matrix(arr.length, 1);
        for (int i = 0; i < arr.length; i++) {
            m.data[i][0] = arr[i];
        }
        return m;
    }

    public static Float[] toArray(Matrix m) {
        List<Float> arr = new ArrayList<Float>();
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                arr.add(m.data[i][j]);
            }
        }
        return arr.toArray(Float[]::new);
    }

    public static Matrix sigmaOperationOnMatrix(Matrix a) {
        Matrix b = a.clone();

        for (int i = 0; i < b.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                b.data[i][j] = sigma(a.data[i][j]);
            }
        }

        return b;
    }

    public static Matrix dSigmaOperationOnMatrix(Matrix a) {
        Matrix b = a.clone();

        for (int i = 0; i < b.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                b.data[i][j] = dSigma(a.data[i][j]);
            }
        }

        return b;
    }

    private static float sigma(Float x) {
        return (float) (1 / (1 + Math.exp(-x)));
    }

    private static float dSigma(Float x) {
        return x * (1 - x);
    }
}
