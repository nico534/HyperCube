package matrixLibrary.Exceptions;

public class MatrixDontMatchException extends RuntimeException {
    public MatrixDontMatchException(String msc){
        super(msc);
    }
}
