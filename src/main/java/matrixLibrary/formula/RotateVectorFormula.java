package matrixLibrary.formula;

import matrixLibrary.formula.Formula;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.MatrixCalc;
import matrixLibrary.utils.VectorCalc;

public class RotateVectorFormula implements Formula {

    private Matrix rotationMatrix;

    public RotateVectorFormula(Matrix rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    @Override
    public void applyFormula(Vector v) {
        Vector rotate = VectorCalc.multiply(rotationMatrix, v);
        v.copy(rotate);
    }
}
