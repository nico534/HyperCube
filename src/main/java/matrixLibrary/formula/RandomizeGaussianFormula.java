package matrixLibrary.formula;

import matrixLibrary.formula.Formula;
import matrixLibrary.matrix.LinearElement;

import java.util.Random;

public class RandomizeGaussianFormula implements Formula {

    @Override
    public void applyGeneralFormula(LinearElement e) {
        Random r = new Random();
        for(int i = 0; i < e.size(); i++){
            e.set(i, r.nextGaussian());
        }
    }
}
