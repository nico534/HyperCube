package matrixLibrary.matrix;

import matrixLibrary.utils.MatrixCalc;

public class RotateVectorFormula implements Formula {

    Matrix rotationMatrix;
    public RotateVectorFormula(Matrix rotationMatrix){
        this.rotationMatrix = rotationMatrix;
    }

    @Override
    public void applyFormula(Matrix matrix) {
        if(rotationMatrix.cols() != rotationMatrix.rows() || rotationMatrix.rows() != matrix.size() || !matrix.getIsVector()){
            throw new MatrixDontMatchException("can just rotate a n vector with a nxn Matrix.");
        }
        Matrix rotate = MatrixCalc.multiply(rotationMatrix, matrix);
        matrix.copyAll(rotate);
    }
}
