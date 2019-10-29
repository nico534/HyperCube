package matrixLibrary.formula;

import matrixLibrary.matrix.LinearElement;

public class AddUpFormula implements Formula {

    private LinearElement toAddUp;

    public AddUpFormula(LinearElement toAddUp) {
        this.toAddUp = toAddUp;
    }

    @Override
    public void applyGeneralFormula(LinearElement e) {
        if (e.rows() != toAddUp.rows() || e.cols() != toAddUp.cols()) {
            System.err.println("Can not multiply this elements, not the same shape. ("
                    + e.rows() + " | " + e.cols() + ") != (" + toAddUp.rows() + " | " + toAddUp.cols() + ").");
            return;
        }
        for (int i = 0; i < e.rows(); i++) {
            for (int j = 0; j < e.cols(); j++) {
                e.set(i, j, toAddUp.get(i, j) + e.get(i, j));
            }
        }
    }
}
