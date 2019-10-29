package org.render3D.KompositumCube;

import matrixLibrary.matrix.Vector;

import java.util.ArrayList;

public interface Element {
    Line[] getLines();
    Element[] getElements();
    Element clone(ArrayList<Vector> allMatrices);
}
