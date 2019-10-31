package org.hyperCube;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import org.gui.Camera3D;
import org.render3D.KompositumCube.Face;
import org.render3D.KompositumCube.Object3D;
import org.render3D.utils.Renderer;

import java.io.File;
import java.io.IOException;

public class testMain {

    public static void main(String[] args) {
        Vector camMtx = new Vector(3);
        Vector light = new Vector(3);
        light.set(2, -1);
        camMtx.set(2,-2);
        Camera3D cam = new Camera3D(camMtx);
        Object3D o;
        try {
            o = new Object3D(new File("C:\\Users\\Nicolai\\Desktop\\cube.obj"));
            o.rotate(1, 1.3709677437300323);
            Face[] renderFaces = Renderer.getRendered(o, cam, light, 500);
            for(Face f: renderFaces){
                System.out.println("Face");
                f.getP1().print();
                f.getP2().print();
                f.getP3().print();
                System.out.println("normal");
                f.getNormal().print();
                System.out.println("light: " + f.getLightIntensity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
