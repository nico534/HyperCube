package matrixLibrary.utils;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.MatrixDontMatchException;

public class VectorCalc {

    public static Matrix crossProduct(Matrix mtx1, Matrix mtx2) {
        if (!mtx1.getIsVector() || !mtx2.getIsVector()) {
            throw new MatrixDontMatchException("just (n|1) vectors are allowed.");
        }
        if (mtx1.size() != 3 || mtx2.size() != 3) {
            throw new MatrixDontMatchException("the crossproduct is just defined in 3 dimensions. Mtx1.size() = " + mtx1.size() + " Mtx2.size() = " + mtx2.size());
        }
        Matrix crossProd = new Matrix(3);
        crossProd.set(0, mtx1.get(1) * mtx2.get(2) - (mtx1.get(2) * mtx2.get(1)));
        crossProd.set(1, mtx1.get(2) * mtx2.get(0) - (mtx1.get(0) * mtx2.get(2)));
        crossProd.set(2, mtx1.get(0) * mtx2.get(1) - (mtx1.get(1) * mtx2.get(0)));
        return crossProd;
    }

    public static double getLength(Matrix vector) {
        if (!vector.getIsVector()) {
            throw new MatrixDontMatchException("(n|1) vectors are allowed, can not calculate length.");
        }
        double length = 0;
        for (int i = 0; i < vector.rows(); i++) {
            length += Math.pow(vector.get(i), 2);
        }
        return Math.sqrt(length);
    }

    public static Matrix getNormalizeVector(Matrix vector) {
        if (!vector.getIsVector()) {
            throw new MatrixDontMatchException("Jus (n|1) vectors are allowed, can not normalize it..");
        }
        Matrix newVector = vector.copy();
        double length = getLength(newVector);
        if(length == 0){
            throw new MatrixDontMatchException("The length of the vector is 0, can not normalize it.");
        }
        newVector.multiplyScalar(1/length);
        return newVector;
    }

    public static double dotProduct(Matrix vec1, Matrix vec2) {
        if (!vec1.getIsVector() || !vec2.getIsVector()) {
            throw new MatrixDontMatchException("Just (n|1) vectors are allowed.");
        }
        if (vec1.size() != vec2.size()) {
            throw new MatrixDontMatchException("cant calculate the dot product with a " + vec1.size() + " dimensional and a " + vec2.size() + " dimensional vector.");
        }
        double product = 0;
        for (int i = 0; i < vec1.size(); i++) {
            product += (vec1.get(i) * vec2.get(i));
        }
        return product;
    }
}
