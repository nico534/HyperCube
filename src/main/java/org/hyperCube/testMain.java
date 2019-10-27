package org.hyperCube;

import matrixLibrary.matrix.Matrix;
import org.gui.Camera3D;
import org.gui.Display2D;
import org.hyperCube.KompositumCube.Construct;
import org.hyperCube.KompositumCube.Element;
import org.hyperCube.KompositumCube.Line;

public class testMain {

    public static void main(String[] args) {
        HyperCube c = new HyperCube(3);
        Matrix camMatrix = new Matrix(3);
        camMatrix.set(0, -2);
        Camera3D cam = new Camera3D(camMatrix);
        Display2D d = new Display2D(600, 600);
        Line[] drawLines = c.getToDrawLines(cam);
        d.drawAllLines(drawLines);
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
