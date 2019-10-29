package org.hyperCube;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.MatrixCalc;
import matrixLibrary.utils.VectorCalc;
import org.render3D.KompositumCube.Line;

import java.util.ArrayList;

public class CubeCalculator {

    /**
     * calculates the number of rotation axes in the given dimension
     * @param dimensions - the dimension
     * @return - the number of rotation axes.
     */
    public static int calcRotateAngels(int dimensions) {
        int erg = 0;
        for (int i = dimensions-1; i > 0; i--) {
            erg += i;
        }
        return erg;
    }

    public static Construct[] getAllFaces(Construct c){
        return null;
    }

    public static Construct createCube(int dimension){
        return new Construct(createBox(dimension), dimension);
    }

    private static Vector[] createBox(int dimension){
        Vector[] erg = new Vector[dimension];
        for (int i = 0; i < erg.length; i++) {
            erg[i] = new Vector((int) Math.pow(2, dimension));
        }

        for (int i = 0; i < erg.length; i++) {
            Matrix add = new Matrix((int)Math.pow(2, (i + 1)));
            for (int j = 0; j < add.rows() / 2; j++) {
                add.set(j, 1);
            }
            for (int j = add.rows() / 2; j < add.rows(); j++) {
                add.set(j, -1);
            }
            for (int j = 0; j < erg[i].rows(); j++) {
                erg[i].set(j, add.get(j % add.rows()));
            }
        }
        Vector[] endErg = new Vector[(int) Math.pow(2, dimension)];
        for(int i = 0; i <  Math.pow(2, dimension); i++){
            endErg[i] = new Vector(dimension);
            for(int j = 0; j < dimension; j++){
                endErg[i].set(j, erg[j].get(i));
            }
        }
        return endErg;
    }

    public static Vector getShadow(Vector vector, int toDimension, double distance){
        Vector erg = vector.clone();
        for(int i = vector.rows(); i > toDimension; i--){
            erg = VectorCalc.multiply(createOneDimDownMatrix(1.0/(distance - erg.get(i-1)), i), erg);
        }
        return erg;
    }

    private static Matrix createOneDimDownMatrix(double dis, int startDimension){
        Matrix projection = new Matrix(startDimension-1, startDimension);
        for(int j = 0; j < startDimension-1; j++){
            projection.set(j, j, 1);
        }
        projection.multiplyScalar(dis);
        return projection;
    }

    public static Vector[] recreatePointMtx(Construct c){
        Line[] allLines = c.getLines();
        ArrayList<Vector> allPoints = new ArrayList<>();
        for(Line l: allLines){
            if(!allPoints.contains(l.getP1())){
                allPoints.add(l.getP1());
            }
            if(!allPoints.contains(l.getP2())){
                allPoints.add(l.getP2());
            }
        }
        return allPoints.toArray(new Vector[0]);
    }

    public static Matrix calcRotationMatrix( int axes1, int axes2, double angle, int dimension){
        Matrix rotate = new Matrix(dimension, dimension);
        for(int i = 0; i < dimension; i++){
            rotate.set(i,i,1);
        }
        rotate.set(axes1, axes1, Math.cos(angle));
        rotate.set(axes1, axes2, -Math.sin(angle));
        rotate.set(axes2, axes1, Math.sin(angle));
        rotate.set(axes2, axes2, Math.cos(angle));
        return rotate;
    }
}