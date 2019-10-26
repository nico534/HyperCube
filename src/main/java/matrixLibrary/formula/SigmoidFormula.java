package matrixLibrary.formula;

import matrixLibrary.matrix.Matrix;

public class SigmoidFormula implements Formula {
    @Override
    public void applyFormula(Matrix matrix) {
        for(int i = 0; i < matrix.size(); i++){
            matrix.set(i, (1.0 / (1.0 + Math.exp(-matrix.get(i)))));
        }
    }
}
