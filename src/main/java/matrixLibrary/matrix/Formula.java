package matrixLibrary.matrix;

import matrixLibrary.matrix.Matrix;

/**
 * Interface to apply a formula to a matrix
 */
public interface Formula {
    /**
     * a Formula witch will be executed on a matrix.
     *
     * @param matrix - the matrix to add the formula
     */
    void applyFormula(Matrix matrix);
}
