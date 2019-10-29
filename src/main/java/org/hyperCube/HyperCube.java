package org.hyperCube;

import matrixLibrary.formula.AddUpFormula;
import matrixLibrary.formula.SubtractFormula;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.MatrixCalc;
import matrixLibrary.utils.VectorCalc;
import org.gui.Camera3D;
import org.render3D.KompositumCube.Face;
import org.render3D.KompositumCube.Line;
import org.render3D.KompositumCube.Object3D;

import java.util.ArrayList;

public class HyperCube {
    //private static int distance = 2;
    public static int scale = 500;
    public Construct cube;
    private int dimensions;

    private RotateJob[] rotateJobs;

    public HyperCube(int dimension) {
        cube = CubeCalculator.createCube(dimension);
        this.dimensions = dimension;
        initRotateJobs();
    }

    private void initRotateJobs(){
        rotateJobs = new RotateJob[CubeCalculator.calcRotateAngels(dimensions)];
        int counter = 0;
        for (int i = 0; i < dimensions - 1; i++) {
            for (int j = i + 1; j < dimensions; j++) {
                rotateJobs[counter] = new RotateJob(i, j, 0);
                System.out.println(i + "|" + j);
                System.out.println("Counter: " + counter);
                counter++;
            }
        }
    }

    public void rotate(){
        boolean isRotating = false;
        Matrix allRotations = MatrixCalc.getIdentityMatrix(this.dimensions);
        for(RotateJob r: this.rotateJobs){
            if(r.getIsRotating()){
                Matrix rotationMatrix = CubeCalculator.calcRotationMatrix(r.getAxes1(), r.getAxes2(), r.getRotateValue(), dimensions);
                isRotating = true;
                allRotations = MatrixCalc.multiply(allRotations, rotationMatrix);
            }
        }
        if(isRotating) {
            cube.rotate(allRotations);
        }
    }

    public RotateJob getRotateJob(int jobIndex) {
        return rotateJobs[jobIndex];
    }

    public Line[] getToDrawLines(Camera3D cam) {
        Construct workerCube = cube.clone();
        Vector[] allPoints = workerCube.getAllPoints();
        for(Vector p: allPoints){
            // normalize vector length
            p.multiplyScalar(0.5);
            Vector temp = CubeCalculator.getShadow(p, 3, VectorCalc.getLength(cam.getVisionAxes()));
            p.copy(temp);
        }
        Construct[] allFaces = workerCube.get(2);
        ArrayList<Construct> allFacesToPrint = new ArrayList<>();
        for(Construct f: allFaces){
            Line[] allLines = f.getLines();
            Vector onePoint = allLines[0].getP1().clone();
            onePoint.addFormula(new SubtractFormula(cam.getVisionAxes()));
            Vector normal = f.calculateNormalVector();
            if(VectorCalc.dotProduct(normal,onePoint) <= 0) {
                allFacesToPrint.add(f);
            }
        }
        ArrayList<Line> toPrintLines = new ArrayList<>();
        for(Construct f: allFacesToPrint){
            Line[] linesInF = f.getLines();
            for(Line l: linesInF){
                if(!toPrintLines.contains(l)){
                    toPrintLines.add(l);
                }
            }
        }
        for (Line l: toPrintLines){
            l.setP1(CubeCalculator.getShadow(l.getP1(), 2, VectorCalc.getLength(cam.getVisionAxes())));
            l.setP2(CubeCalculator.getShadow(l.getP2(), 2, VectorCalc.getLength(cam.getVisionAxes())));
            l.getP1().multiplyScalar(scale);
            l.getP2().multiplyScalar(scale);
        }
        return toPrintLines.toArray(new Line[0]);
    }

    /*
    public Face[] getToDrawFaces(Camera3D cam, Vector light) {
        Construct workerCube = cube.clone();
        Vector[] allPoints = workerCube.getAllPoints();
        for(Vector p: allPoints){
            // normalize vector length
            p.multiplyScalar(0.5);
            Vector temp = CubeCalculator.getShadow(p, 3, VectorCalc.getLength(cam.getVisionAxes()));
            p.copy(temp);
        }
        Construct[] allFaces = workerCube.get(2);
        ArrayList<Matrix> allPointsToDraw = new ArrayList<>();
        ArrayList<Face> allDrownFaces = new ArrayList<>();
        for(Construct f: allFaces){
            Line[] allLines = f.getLines();
            Vector onePoint = allLines[0].getP1().clone();
            onePoint.addFormula(new SubtractFormula(cam.getVisionAxes()));
            Vector normal = f.calculateNormalVector();
            if(VectorCalc.dotProduct(normal,onePoint) <= 0) {
                Line[] linesOfTheFace = f.getLines();
                allDrownFaces.add(new Face(linesOfTheFace[0], linesOfTheFace[1]));
                allDrownFaces.add(new Face(linesOfTheFace[2], linesOfTheFace[3]));
            }
        }

        for(Face f: allDrownFaces){
            f.calc2DShadow(cam);
            f.scale(scale);
        }

        return allDrownFaces.toArray(new Face[0]);
    }

     */

    public Object3D getAsObject3D(Camera3D cam) {
        Construct workerCube = cube.clone();
        Vector[] allPoints = workerCube.getAllPoints();
        ArrayList<Vector> allPointsA = new ArrayList<>();
        for(Vector p: allPoints){
            // normalize vector length
            p.multiplyScalar(0.5);
            Vector temp = CubeCalculator.getShadow(p, 3, VectorCalc.getLength(cam.getVisionAxes()));
            p.copy(temp);
            allPointsA.add(p);
        }
        Construct[] allFaces = workerCube.get(2);
        ArrayList<Face> allDrownFaces = new ArrayList<>();
        for(Construct f: allFaces){
            Vector normal = f.calculateNormalVector();
            Line[] linesOfTheFace = f.getLines();
            Face newF = new Face(linesOfTheFace[0], linesOfTheFace[1], normal);
            allDrownFaces.add(newF);
            newF = new Face(linesOfTheFace[2], linesOfTheFace[3], normal);
            allDrownFaces.add(newF);
        }

        return new Object3D(allDrownFaces, allPointsA, "Hypercupe");
    }
}
