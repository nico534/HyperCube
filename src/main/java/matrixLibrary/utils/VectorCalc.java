package matrixLibrary.utils;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.Exceptions.MatrixDontMatchException;
import matrixLibrary.matrix.Vector;
import org.render3D.KompositumCube.Element;

public class VectorCalc {

    public static Vector crossProduct(Vector vec1, Vector vec2) {
        if (vec1.rows() != 3 || vec2.rows() != 3) {
            throw new MatrixDontMatchException("the crossproduct is just defined 3x1 Vectors vec1.rows = " + vec1.rows() + " vec1.rows = " + vec2.rows());
        }
        Vector crossProd = new Vector(3);
        crossProd.set(0, vec1.get(1) * vec2.get(2) - (vec1.get(2) * vec2.get(1)));
        crossProd.set(1, vec1.get(2) * vec2.get(0) - (vec1.get(0) * vec2.get(2)));
        crossProd.set(2, vec1.get(0) * vec2.get(1) - (vec1.get(1) * vec2.get(0)));
        return crossProd;
    }

    public static double getLength(Vector vector) {
        double length = 0;
        for (int i = 0; i < vector.rows(); i++) {
            length += Math.pow(vector.get(i), 2);
        }
        return Math.sqrt(length);
    }

    public static Vector getNormalizeVector(Vector vector) {
        Vector newVector = vector.clone();
        double length = getLength(newVector);
        if(length == 0){
            throw new MatrixDontMatchException("The length of the vector is 0, can not normalize it.");
        }
        newVector.multiplyScalar(1/length);
        return newVector;
    }

    public static double dotProduct(Vector vec1, Vector vec2) {
        if (vec1.size() != vec2.size()) {
            throw new MatrixDontMatchException("cant calculate the dot product with a " + vec1.size() + " dimensional and a " + vec2.size() + " dimensional vector.");
        }
        double product = 0;
        for (int i = 0; i < vec1.size(); i++) {
            product += (vec1.get(i) * vec2.get(i));
        }
        return product;
    }

    public static Vector multiply(Matrix mtx, Vector v){
        Matrix vectorErg = MatrixCalc.multiply(mtx, v);
        return vectorErg.convertToVector();
    }

    public static Vector multiply(Vector v, Matrix mtx){
        v.transpose();
        mtx.transpose();
        Matrix mtxErg = MatrixCalc.multiply(v, mtx);
        v.transpose();
        mtx.transpose();
        Vector vErg = mtxErg.convertToVector();
        vErg.transpose();
        return vErg;
    }
}
