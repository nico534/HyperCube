package matrixLibrary.formula;

import matrixLibrary.formula.Formula;
import matrixLibrary.matrix.Vector;

public class ShortenVectorToSizeFormula implements Formula {
    private int size;

    public ShortenVectorToSizeFormula(int size) {
        this.size = size;
    }

    @Override
    public void applyFormula(Vector v) {
        if (size < 0) {
            System.err.println("can not shorten the vector to a negative value.");
            return;
        }
        while (v.size() > size) {
            v.pull();
        }
    }
}
