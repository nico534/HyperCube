package org.hyperCube.KompositumCube;

import matrixLibrary.matrix.Matrix;

import java.util.ArrayList;

public interface Element {

    Construct[] get(int dimension);
    Line[] getLines();
    Element clone(ArrayList<Matrix> allMatrices);

}
