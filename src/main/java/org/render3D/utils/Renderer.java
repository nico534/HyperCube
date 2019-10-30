package org.render3D.utils;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.MatrixCalc;
import matrixLibrary.utils.VectorCalc;
import org.gui.Camera3D;
import org.render3D.KompositumCube.Face;
import org.render3D.KompositumCube.Object3D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Renderer {

    public static Face[] getRendered(Object3D o, Camera3D cam, Vector light, double scale) {
        List<Vector> toRenderVectors = new ArrayList<>();
        List<Face> toRenderFaces = new ArrayList<>();

        for (Face f : o.getFaces()) {
            if (VectorCalc.dotProduct(cam.getVisionAxes(), f.getNormal()) < 0) {
                Vector p1 = f.getP1();
                Vector p2 = f.getP2();
                Vector p3 = f.getP3();
                if (toRenderVectors.contains(p1)) {
                    p1 = toRenderVectors.get(toRenderVectors.indexOf(p1));
                } else {
                    toRenderVectors.add(p1.clone());
                    p1 = toRenderVectors.get(toRenderVectors.indexOf(p1));
                }
                if (toRenderVectors.contains(p2)) {
                    p2 = toRenderVectors.get(toRenderVectors.indexOf(p2));
                } else {
                    toRenderVectors.add(p2.clone());
                    p2 = toRenderVectors.get(toRenderVectors.indexOf(p2));
                }
                if (toRenderVectors.contains(p3)) {
                    p3 = toRenderVectors.get(toRenderVectors.indexOf(p3));
                } else {
                    toRenderVectors.add(p3.clone());
                    p3 = toRenderVectors.get(toRenderVectors.indexOf(p3));
                }
                Face newFaceToRender = new Face(p1, p2, p3, f.getNormal());
                newFaceToRender.calcLightIntensity(light);
                toRenderFaces.add(newFaceToRender);
            }
        }

        double distance = VectorCalc.getLength(cam.getVisionAxes());
        for(Vector v: toRenderVectors){
            v.copy(create2DShadow(v, distance));
            v.multiplyScalar(scale);
        }

        return toRenderFaces.toArray(new Face[0]);
    }

    private static Vector create2DShadow(Vector point, double distance) {
        point = VectorCalc.multiply(createOneDimDownMatrix(1.0 / (distance - point.get(2)), 3), point);
        return point;
    }

    private static Matrix createOneDimDownMatrix(double dis, int startDimension) {
        Matrix projection = new Matrix(startDimension - 1, startDimension);
        for (int j = 0; j < startDimension - 1; j++) {
            projection.set(j, j, 1);
        }
        projection.multiplyScalar(dis);
        return projection;
    }

    public static Matrix calcRotationMatrix( int axes1, int axes2, double angle){
        Matrix rotate = MatrixCalc.getIdentityMatrix(4);
        rotate.set(axes1, axes1, Math.cos(angle));
        rotate.set(axes1, axes2, -Math.sin(angle));
        rotate.set(axes2, axes1, Math.sin(angle));
        rotate.set(axes2, axes2, Math.cos(angle));
        return rotate;
    }
}