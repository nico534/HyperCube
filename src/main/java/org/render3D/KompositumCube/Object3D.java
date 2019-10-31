package org.render3D.KompositumCube;

import matrixLibrary.formula.AddUpFormula;
import matrixLibrary.formula.Formula;
import matrixLibrary.formula.RotateVectorFormula;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.MatrixCalc;
import org.apache.commons.lang3.ArrayUtils;
import org.render3D.utils.Renderer;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Object3D {

    private Face[] faces;
    private Vector[] points;
    private String name;

    private Vector origin;

    private double[] rotate = new double[3];

    public Object3D(Face[] faces, Vector[] points, String name){
        this.points = points;
        this.faces = faces;
        this.name = name;
    }

    public Object3D(File f) throws IOException {
        loadObj(f);
    }

    //----------------------------------------------------------------

    public void rotate(int axes, double degree){
        this.rotate[axes] += degree;
        if(axes == 0){
            applyRotation(Renderer.calcRotationMatrix(1, 2, degree));
        }else if(axes == 1){
            applyRotation(Renderer.calcRotationMatrix(0, 2, degree));
        }else if(axes == 2){
            applyRotation(Renderer.calcRotationMatrix(0, 1, degree));
        }

    }

    private void applyRotation(Matrix rotation){
        Formula mtxRotation = new RotateVectorFormula(rotation);
        for(Vector v: points){
            v.addFormula(mtxRotation);
        }
        for(Face f: faces){
            f.calcNormal();
        }
    }

    public void loadObj(File file) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(file));
        List<Vector> allPoints = new ArrayList<>();
        List<Face> allFaces = new ArrayList<>();

        String line = "";
        while ((line = br.readLine()) != null){
            String[] splittedLine = line.split(" ");
            switch (splittedLine[0]) {
                case "v":
                    Vector p = new Vector(3);
                    p.set(0, Double.parseDouble(splittedLine[1]));
                    p.set(1, Double.parseDouble(splittedLine[2]));
                    p.set(2, Double.parseDouble(splittedLine[3]));
                    allPoints.add(p);
                    break;
                case "f":
                    int index1 = Integer.parseInt(splittedLine[1]) - 1;
                    int index2 = Integer.parseInt(splittedLine[2]) - 1;
                    int index3 = Integer.parseInt(splittedLine[3]) - 1;
                    Face f = new Face(allPoints.get(index1), allPoints.get(index2), allPoints.get(index3));
                    f.calcNormal();
                    allFaces.add(f);
                    break;
                case "o":
                    name = splittedLine[1];
                    break;
            }
        }
        points = allPoints.toArray(new Vector[0]);
        faces = allFaces.toArray(new Face[0]);
    }

    public Face[] getFaces(){
        return faces;
    }

    public String toString(){
        return name;
    }

    public Vector[] getPoints() {
        return points;
    }

    public void calcOrigin(){
        double[] origin = new double[3];
        for(Vector v: points){
            origin[0] += v.get(0);
            origin[1] += v.get(1);
            origin[2] += v.get(2);
        }
        origin[0] /= points.length;
        origin[1] /= points.length;
        origin[2] /= points.length;
        this.origin = new Vector(origin);
    }

    public  void move(Vector toMove){
        this.origin.addFormula(new AddUpFormula(toMove));
    }

    public void resetMove() {
        this.origin.reset();
    }

    public void resetRotation(){
        rotate(0, -rotate[0]);
        rotate(1, -rotate[1]);
        rotate(2, -rotate[2]);
    }
}