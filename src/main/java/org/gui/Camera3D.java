package org.gui;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;

public class Camera3D {
    private Vector visionAxes;

    public Camera3D(Vector visionAxes){
        this.visionAxes = visionAxes;
    }

    public Vector getVisionAxes(){
        return this.visionAxes;
    }
}
