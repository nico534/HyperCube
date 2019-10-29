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
        Vector camMatrix = new Vector(3);
        camMatrix.set(0, -2);
        Camera3D cam = new Camera3D(camMatrix);
        Object3D o;
        Vector light = new Vector(3);
        light.set(2, 1);
        try {
            o = new Object3D(new File("F:\\Bibliothek\\Desktop\\untitled.obj"));
            Face[] toPrint = Renderer.getRendered(o, cam, light, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        Construct cube = CubeCalculator.createCube(3);

        Construct[] allFaces = cube.get(2);
        int counter = 0;
        for (Construct f: allFaces){
            System.out.println("Face: " + counter++);
            Line[] lines = f.getLines();
            for(Line l: lines){
                l.printLine(5, 3);
            }
        }

         */
    }
}
