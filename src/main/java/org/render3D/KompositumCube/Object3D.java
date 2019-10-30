package org.render3D.KompositumCube;

import matrixLibrary.formula.Formula;
import matrixLibrary.formula.RotateVectorFormula;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.MatrixCalc;
import org.render3D.utils.Renderer;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

public class Object3D {

    private ArrayList<Face> faces = new ArrayList<>();
    private ArrayList<Vector> points = new ArrayList<>();
    private String name;

    private Vector origin;
    private Matrix rotateTranslate;

    private double[] rotate = new double[3];

    public Object3D(Face[] faces, Vector[] points, String name){
        this.faces.addAll(Arrays.asList(faces));
        this.points.addAll(Arrays.asList(points));
        this.name = name;
        this.rotateTranslate = MatrixCalc.getIdentityMatrix(4);
    }

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
    }

    public Object3D(ArrayList<Face> faces, ArrayList<Vector> points, String name){
        this.faces = faces;
        this.points = points;
        this.name = name;

        this.rotateTranslate = MatrixCalc.getIdentityMatrix(4);
    }

    public Object3D(File f) throws IOException {
        loadObj(f);
        this.rotateTranslate = MatrixCalc.getIdentityMatrix(4);
    }

    public void loadObj(File file) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(file));

        String line = "";
        while ((line = br.readLine()) != null){
            String[] splittedLine = line.split(" ");
            switch (splittedLine[0]) {
                case "v":
                    Vector p = new Vector(3);
                    p.set(0, Double.parseDouble(splittedLine[1]));
                    p.set(1, Double.parseDouble(splittedLine[2]));
                    p.set(2, Double.parseDouble(splittedLine[3]));
                    points.add(p);
                    break;
                case "f":
                    int index1 = Integer.parseInt(splittedLine[1]) - 1;
                    int index2 = Integer.parseInt(splittedLine[2]) - 1;
                    int index3 = Integer.parseInt(splittedLine[3]) - 1;
                    Face f = new Face(points.get(index1), points.get(index2), points.get(index3));
                    f.calcNormal();
                    faces.add(f);
                    break;
                case "o":
                    name = splittedLine[1];
                    break;
            }
        }
    }

    public ArrayList<Face> getFaces(){
        return faces;
    }

    public String toString(){
        return name;
    }

    public Vector[] getPoints() {
        return points.toArray(new Vector[0]);
    }

    public void calcOrigin(){
        double[] origin = new double[3];
        for(Vector v: points){
            origin[0] += v.get(0);
            origin[1] += v.get(1);
            origin[2] += v.get(2);
        }
        origin[0] /= points.size();
        origin[1] /= points.size();
        origin[2] /= points.size();
        this.origin = new Vector(origin);
    }

}