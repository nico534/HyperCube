package org.hyperCube;

import matrixLibrary.matrix.Matrix;

public class Point {
    Matrix point;

    public Point(Matrix point){
        this.point = point;
    }

    public double get(int dimension){
        return point.get(dimension);
    }

    public void set(int dimension, double value){
        this.point.set(dimension, value);
    }
}
