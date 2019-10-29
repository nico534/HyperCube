package matrixLibrary.formula;

import matrixLibrary.matrix.LinearElement;

public class SubtractFormula implements Formula {
    private LinearElement toSubtract;

    public SubtractFormula(LinearElement toSubtract) {
        this.toSubtract = toSubtract;
    }

    @Override
    public void applyGeneralFormula(LinearElement e) {
        if (e.rows() != toSubtract.rows() || e.cols() != toSubtract.cols()) {
            System.err.println("Can not multiply this elements, not the same shape. ("
                    + e.rows() + " | " + e.cols() + ") != (" + toSubtract.rows() + " | " + toSubtract.cols() + ").");
            return;
        }
        for (int i = 0; i < e.rows(); i++) {
            for (int j = 0; j < e.cols(); j++) {
                e.set(i, j, e.get(i, j) - toSubtract.get(i, j));
            }
        }
    }
}
