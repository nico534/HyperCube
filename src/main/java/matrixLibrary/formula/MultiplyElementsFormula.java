package matrixLibrary.formula;

import matrixLibrary.matrix.LinearElement;

public class MultiplyElementsFormula implements Formula {

    private LinearElement toMultiplyFrom;

    public MultiplyElementsFormula(LinearElement toMultiplyFrom) {
        this.toMultiplyFrom = toMultiplyFrom;
    }

    @Override
    public void applyGeneralFormula(LinearElement e) {
        if (e.rows() != toMultiplyFrom.rows() || e.cols() != toMultiplyFrom.cols()) {
            System.err.println("Can not multiply this elements, not the same shape. ("
                    + e.rows() + " | " + e.cols() + ") != (" + toMultiplyFrom.rows() + " | " + toMultiplyFrom.cols() + ").");
            return;
        }
        for (int i = 0; i < e.rows(); i++) {
            for (int j = 0; j < e.cols(); j++) {
                e.set(i, j, toMultiplyFrom.get(i, j) * e.get(i, j));
            }
        }
    }
}
