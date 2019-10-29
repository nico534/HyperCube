package matrixLibrary.formula;

import matrixLibrary.formula.Formula;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;

public class DeleteLinesFormula implements Formula {
    private int[] toDeleteLines;

    public DeleteLinesFormula(int[] toDeleteLines) {
        this.toDeleteLines = toDeleteLines;
    }

    @Override
    public void applyFormula(Matrix matrix) {
        if (toDeleteLines.length > matrix.rows()) {
            System.out.println("Can not delete more lines than rows exist.");
            return;
        }
        Matrix newMtx = null;
        if(!matrix.isTranspose()){
            newMtx = new Matrix(matrix.rows() - toDeleteLines.length, matrix.cols());
        } else {
            newMtx = new Matrix(matrix.cols(), matrix.rows() - toDeleteLines.length);
            newMtx.transpose();
        }
        int newRowCounter = 0;
        for (int i = 0; i < matrix.rows(); i++) {
            if (isInToDeleteLines(i)) {
                continue;
            }
            for(int j = 0; j < matrix.cols(); j++){
                newMtx.set(newRowCounter, j, matrix.get(i, j));
                newRowCounter++;
            }
        }
        matrix.copy(newMtx);
    }

    public void applyFormula(Vector v){
        if (toDeleteLines.length > v.rows()) {
            System.out.println("Can not delete more lines than rows exist.");
            return;
        }
        if(v.rows() == toDeleteLines.length){
            v.copy(new Vector(0));
            return;
        }

        Vector newMtx = new Vector(v.size() - toDeleteLines.length);
        int newRowCounter = 0;
        for (int i = 0; i < v.rows(); i++) {
            if (isInToDeleteLines(i)) {
                continue;
            }
            for(int j = 0; j < v.cols(); j++){
                newMtx.set(newRowCounter, j, v.get(i, j));
                newRowCounter++;
            }
        }
        v.copy(newMtx);
    }

    private boolean isInToDeleteLines(int number) {
        for (int toDeleteLine : toDeleteLines) {
            if (number == toDeleteLine) {
                return true;
            }
        }
        return false;
    }
}
