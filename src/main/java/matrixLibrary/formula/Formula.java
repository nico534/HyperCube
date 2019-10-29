package matrixLibrary.formula;

import matrixLibrary.Exceptions.MatrixDontMatchException;
import matrixLibrary.matrix.LinearElement;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;

/**
 * Interface to apply a formula to a matrix
 */
public abstract interface Formula {
    /**
     * apply the formula to the element.
     *
     * @param e - the element to add the formula
     */
    default void applyGeneralFormula(LinearElement e){
        throw new MatrixDontMatchException("cant apply this Formula on" + e.getClass());
    }

    /**
     * applys Formula on a Matrix
     *
     * @param mtx - the Matrix to apply the formula
     */
    default void applyFormula(Matrix mtx){
        applyGeneralFormula(mtx);
    }

    /**
     * applys Formula on a Vector
     *
     * @param v - the Vector to apply the formula
     */
    default void applyFormula(Vector v){
        applyGeneralFormula(v);
    }
}
