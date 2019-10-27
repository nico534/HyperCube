package matrixLibrary.matrix;

import java.util.Random;

public class RandomizeGaussianFormula implements Formula {
    @Override
    public void applyFormula(Matrix matrix) {
        Random r = new Random();
        for(int i = 0; i < matrix.size(); i++){
            matrix.set(i, r.nextGaussian());
        }
    }
}
