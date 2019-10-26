package org.hyperCube;

import matrixLibrary.matrix.Matrix;

public class testMain {

    public static void main(String[] args) {
        Matrix[] cube = CubeCalculator.createBox(3);
        Construct c = new Construct(cube, 3);
        System.out.println();
        Construct[] dim2 = c.get(3);

        for(int i = 0; i < dim2.length; i++){
            System.out.println("Face: " + i);
            Matrix[] allPoints = dim2[i].getPoints();
            for(Matrix p: allPoints){
                p.setShownNumbers(3);
                p.printVector();
            }
        }
    }
}
