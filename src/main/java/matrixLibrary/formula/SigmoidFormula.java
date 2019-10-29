package matrixLibrary.formula;

import matrixLibrary.formula.Formula;
import matrixLibrary.matrix.LinearElement;

public class SigmoidFormula implements Formula {

    @Override
    public void applyGeneralFormula(LinearElement e) {
        for (int i = 0; i < e.size(); i++) {
            e.set(i, (1.0 / (1.0 + Math.exp(-e.get(i)))));
        }
    }
}
