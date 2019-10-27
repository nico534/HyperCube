package matrixLibrary.matrix;

public class ShortenVectorToSizeFormula implements Formula {
    private int size;
    public ShortenVectorToSizeFormula(int size){
        this.size = size;
    }

    @Override
    public void applyFormula(Matrix matrix) {
        while (matrix.size() > size){
            matrix.pull();
        }
    }
}
