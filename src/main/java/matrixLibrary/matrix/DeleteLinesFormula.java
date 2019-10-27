package matrixLibrary.matrix;

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
        Matrix newMtx = new Matrix(matrix.rows() - toDeleteLines.length, matrix.cols());
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
        matrix.setMatrixArray(newMtx.getArray());
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
