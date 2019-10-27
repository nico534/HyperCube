package org.hyperCube;

import matrixLibrary.matrix.Matrix;
import org.hyperCube.KompositumCube.Construct;
import org.hyperCube.KompositumCube.Element;
import org.hyperCube.KompositumCube.Line;

public class testMain {

    public static void main(String[] args) {
        Matrix[] cube = CubeCalculator.createBox(3);
        Construct c = new Construct(cube, 3);
        System.out.println();
        Element[] dim2 = c.get(2);

        for(int i = 0; i < dim2.length; i++){
            System.out.println("Face: " + i);
            Line[] allPoints = dim2[i].getLines();
            for(Line p: allPoints){
                p.printLine(5, 3);
            }
        }
    }
}
