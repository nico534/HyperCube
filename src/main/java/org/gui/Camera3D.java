package org.gui;

import matrixLibrary.matrix.Matrix;

public class Camera3D {
    Matrix visionAxes;

    public Camera3D(Matrix visionAxes){
        this.visionAxes = visionAxes;
    }

    public Matrix getVisionAxes(){
        return this.visionAxes;
    }
}
