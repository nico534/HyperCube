package org.hyperCube;

import matrixLibrary.matrix.Matrix;

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

    public static Construct createCube(int dimension){

        return null;
    }

    public static Matrix[] createBox(int dimension){
        Matrix[] erg = new Matrix[dimension];
        for (int i = 0; i < erg.length; i++) {
            erg[i] = new Matrix((int) Math.pow(2, dimension));
        }

        for (int i = 0; i < erg.length; i++) {
            Matrix add = new Matrix((int)Math.pow((i + 1), 2));
            if(i == 0){
                add = new Matrix(2);
            }
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
        Matrix[] endErg = new Matrix[(int) Math.pow(2, dimension)];
        for(int i = 0; i <  Math.pow(2, dimension); i++){
            endErg[i] = new Matrix(dimension);
            for(int j = 0; j < dimension; j++){
                endErg[i].set(j, erg[j].get(i));
            }
        }
        return endErg;
    }
}
