package matrixLibrary.utils;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.MatrixDontMatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MatrixCalc {
    private static final int parallelLimit = 300;

    /**
     * multiplys all mtxA[i]*mtxB[i] for i in (0, mtxA.length)
     *
     * @param mtxA - mtxA array
     * @param mtxB - mtxB array
     * @return - all mtxA[i]*mtxB[i]
     * @throws InterruptedException - parallel multiplication
     */
    public static Matrix[] multiplyParallel(final Matrix[] mtxA, final Matrix[] mtxB) throws InterruptedException {
        // if Matrices to small linear multiplication is faster
        if (mtxA[0].rows() + mtxA[0].cols() < parallelLimit) {
            return multiplyLinear(mtxA, mtxB);
        }
        if (mtxA.length != mtxB.length) {
            System.err.println("Cant multiply Matrices, different numbers");
            return null;
        }
        final ExecutorService threadPool = Executors.newFixedThreadPool(mtxA.length);

        final Matrix[] allErgs = new Matrix[mtxA.length];

        List<Callable<Object>> allCallable = new ArrayList<Callable<Object>>();
        for (int i = 0; i < mtxA.length; i++) {
            final int pos = i;
            allErgs[i] = new Matrix(mtxA[i].rows(), mtxB[i].cols());
            allCallable.add(Executors.callable(new Runnable() {
                public void run() {
                    multiplyIJK(mtxA[pos], mtxB[pos], allErgs[pos]);
                }
            }));
        }
        threadPool.invokeAll(allCallable);
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
        }
        return allErgs;
    }

    private static Matrix[] multiplyLinear(Matrix[] mtxA, Matrix[] mtxB) {
        Matrix[] ergMtx = new Matrix[mtxA.length];
        for (int i = 0; i < mtxA.length; i++) {
            ergMtx[i] = multiply(mtxA[i], mtxB[i]);
        }
        return ergMtx;
    }

    /**
     * multipllys mtxA * mtxB with ikj alg.
     *
     * @param mtxA - mtxA
     * @param mtxB - mtxB
     * @return - mtxA * mtxB
     */
    public static Matrix multiply(Matrix mtxA, Matrix mtxB) {
        Matrix erg = new Matrix(mtxA.rows(), mtxB.cols());
        if (mtxB.isTranspose() && !mtxA.isTranspose()) {
            multiplyIJK(mtxA, mtxB, erg);
        } else if (mtxA.isTranspose() && !mtxB.isTranspose()) {
            mtxA.transpose();
            mtxB.transpose();
            multiplyIJK(mtxA, mtxB, erg);
            erg.transpose();
            mtxA.transpose();
            mtxB.transpose();

        } else if (mtxA.isTranspose() && mtxB.isTranspose()) {
            mtxA.transpose();
            mtxB.transpose();
            multiplyJIK(mtxB, mtxA, erg);
            mtxA.transpose();
            mtxB.transpose();
            erg.transpose();
        } else {
            multiplyJIK(mtxA, mtxB, erg);
        }
        return erg;
    }

    private static void multiplyIJK(Matrix mtxA, Matrix mtxB, Matrix mtxC) {
        for (int i = 0; i < mtxA.rows(); i++) {
            for (int j = 0; j < mtxB.cols(); j++) {
                double sum = 0;
                for (int k = 0; k < mtxB.rows(); k++) {
                    sum = sum + mtxA.get(i, k) * mtxB.get(k, j);
                }
                mtxC.set(i, j, sum);
            }
        }
    }

    private static void multiplyJIK(Matrix mtxA, Matrix mtxB, Matrix mtxC) {
        for (int j = 0; j < mtxB.cols(); j++) {
            for (int i = 0; i < mtxA.rows(); i++) {
                double sum = 0;
                for (int k = 0; k < mtxB.rows(); k++) {
                    sum = sum + mtxA.get(i, k) * mtxB.get(k, j);
                }
                mtxC.set(i, j, sum);
            }
        }
    }

    /**
     * calculates the inverse of the given Matrix.
     *
     * @param mtx - the matrix to inverse.
     * @return - the inverse of the given matrix.
     */
    public static Matrix inverse(Matrix mtx) {
        if (mtx.rows() != mtx.cols()) {
            throw new MatrixDontMatchException("the matrix can't be inverted: rows=" + mtx.rows() + "!=" + mtx.cols() +".");
        }
        Matrix workMtx = new Matrix(mtx.rows(), 2*mtx.cols());
        for (int i = 0; i < mtx.rows(); i++) {
            for (int j = 0; j < mtx.cols(); j++) {
                workMtx.set(i, j, mtx.get(i, j));
            }
            workMtx.set(i, mtx.cols() + i, 1.0);
        }
        workMtx = gauss(workMtx);
        Matrix inverse = new Matrix(mtx.rows(), mtx.cols());
        for (int i = 0; i < mtx.rows(); i++) {
            for (int j = 0; j < mtx.cols(); j++) {
                inverse.set(i, j, workMtx.get(i, mtx.cols() + j));
            }
        }
        return inverse;
    }

    private static Matrix gauss(Matrix gaussMtx) {
        for (int line = 0; line < gaussMtx.rows(); line++) {

            gaussMtx = changeToPivot(gaussMtx, line);

            if (gaussMtx == null) {
                return null;
            }
            // create pivot one
            double multiplyer = 1 / gaussMtx.get(line, line);
            for (int i = line; i < gaussMtx.cols(); i++) {
                gaussMtx.set(line, i, gaussMtx.get(line, i) * multiplyer);
            }
            // eliminate numbers under and over pivot one
            for (int i = 0; i < gaussMtx.rows(); i++) {
                // check if pivot-line
                if (i == line) {
                    continue;
                }
                double multiply = gaussMtx.get(i, line);
                for (int j = line; j < gaussMtx.cols(); j++) {
                    gaussMtx.set(i, j, gaussMtx.get(i, j) - (multiply * gaussMtx.get(line, j)));
                }
            }
        }
        return gaussMtx;
    }

    private static Matrix changeToPivot(Matrix inverse, int round) {
        for (int i = round; i < inverse.rows(); i++) {
            if (inverse.get(i, round) != 0) {
                inverse.switchRows(round, i);
                return inverse;
            }
        }
        return null;
    }
}