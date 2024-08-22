import java.util.Arrays;

public class Data {
    public final static int N = 16;
    public final static int P = 4;
    public final static int H = N / P;

    public static int[][] MV = new int[N][N];
    public static int[][] MM = new int[N][N];
    public static int[][] MC = new int[N][N];
    public static int[] A = new int[N];
    public static int[] Z = new int[N];
    public static int[] B = new int[N];
    public static int[] X = new int[N];
    public static int a;
    public static int e;


    public static int writeScalar() {
        return 1;
    }

    public static int[] writeVector(int N) {
        int[] vector = new int[N];
        Arrays.fill(vector, 1);
        return vector;
    }

    public static int[][] writeMatrix(int N) {
        int[][] matrix = new int[N][N];
        for(int i = 0; i < N; i++) {
            for (int t = 0; t < N; t++) {
                matrix[i][t] = 1;
            }
        }
        return matrix;
    }

    public static int findMinVector(int[] vector) {
        int min = vector[0];
        for(int i = 0; i < vector.length; i++) {
            min = Math.min(vector[i], min);
        }
        return min;
    }

    public static int[][] submatrixFromRangeOfColumns(int[][] matrix, int start, int end) {
        int rows = matrix.length;
        int columns = end - start;

        int[][] submatrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = start; j < end; j++) {
                submatrix[i][j - start] = matrix[i][j];
            }
        }

        return submatrix;
    }

    private static int[] multVectorByMatrix(int[] vector, int[][] matrix) {
        int[] res = new int[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {
            int sum = 0;
            for (int j = 0; j < vector.length; j++) {
                sum += vector[j] * matrix[j][i];
            }
            res[i] = sum;
        }

        return res;
    }

    private static int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix) {
        int y = firstMatrix.length;
        int x = secondMatrix.length;
        int z = secondMatrix[0].length;

        int[][] res = new int[y][z];

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < z; j++) {
                res[i][j] = 0;
                for (int t = 0; t < x; t++) {
                    res[i][j] += firstMatrix[i][t] * secondMatrix[t][j];
                }
            }
        }
        return res;
    }

    private static int[] multScalarByVector(int scalar, int[] vector) {
        int[] res = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            res[i] = scalar * vector[i];
        }
        return res;
    }
    public static int[] addVectors(int[] firstVector, int[] secondVector) {
        int[] result = new int[firstVector.length];
        for (int i = 0; i < firstVector.length; i++) {
            result[i] = firstVector[i] + secondVector[i];
        }
        return result;
    }


    public static int[] calculationThree(int a, int e, int[][] MV_h, int[][] MC_h) {
        return addVectors(multScalarByVector(a, multVectorByMatrix(B, MV_h)), multScalarByVector(e, multVectorByMatrix(X, multiplyMatrices(MM, MC_h))));
    }

    public static void combineA(int[] subVector, int start, int end) {
        for (int i = start; i < end; i++) {
            A[i] = subVector[i - start];
        }
    }


}
