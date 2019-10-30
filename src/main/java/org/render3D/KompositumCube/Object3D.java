package org.render3D.KompositumCube;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.MatrixCalc;
import org.render3D.utils.Renderer;

import java.io.*;
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
            Matrix rotation = Renderer.calcRotationMatrix(1, 2, degree);
        }else if(axes == 0){
            Matrix rotation = Renderer.calcRotationMatrix(1, 2, degree);
        }else if(axes == 0){
            Matrix rotation = Renderer.calcRotationMatrix(1, 2, degree);
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

    public Vector[] getPoints(){
        return points.toArray(new Vector[0]);
    }

    public Element clone(ArrayList<Vector> allMatrices) {
        return null;
    }

}