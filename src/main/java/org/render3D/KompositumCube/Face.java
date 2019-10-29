package org.render3D.KompositumCube;

import matrixLibrary.formula.Formula;
import matrixLibrary.formula.SubtractFormula;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.VectorCalc;
import org.gui.Camera3D;
import org.hyperCube.Construct;
import org.hyperCube.CubeCalculator;

import java.util.ArrayList;

public class Face implements Element {
    Vector p1;
    Vector p2;
    Vector p3;
    private double lightIntensity = 1.0;
    private Vector normal;

    public Face(Line l1, Line l2){
        if(l1.getP2() != l2.getP1()){
            System.out.println("triangle needs to be drown clockwise.");
            return;
        }
        p1 = l1.getP1();
        p2 = l1.getP2();
        p3 = l2.getP2();
        calcNormal();
    }

    public Face(Line l1, Line l2, Vector normal){
        if(l1.getP2() != l2.getP1()){
            System.out.println("triangle needs to be drown clockwise.");
            return;
        }
        p1 = l1.getP1();
        p2 = l1.getP2();
        p3 = l2.getP2();
        this.normal = normal;
    }

    public Face(Vector p1, Vector p2, Vector p3, Vector normal){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.normal = normal;
    }

    public Face(Vector p1, Vector p2, Vector p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public void calcLightIntensity(Vector light){
        lightIntensity = VectorCalc.dotProduct(normal, VectorCalc.getNormalizeVector(light));
        if(lightIntensity < 0){
            lightIntensity = 0;
        }
    }

    public double getLightIntensity(){
        return this.lightIntensity;
    }

    public void calcNormal(){
        Vector endPoint1 = p2.clone();
        Vector endPoint2 = p3.clone();
        Formula f = new SubtractFormula(p1);
        endPoint1.addFormula(f);
        endPoint2.addFormula(f);
        endPoint1 = VectorCalc.getNormalizeVector(endPoint1);
        endPoint2 = VectorCalc.getNormalizeVector(endPoint2);
        this.normal =  VectorCalc.getNormalizeVector(VectorCalc.crossProduct(endPoint1, endPoint2));
    }

    public void calc2DShadow(Camera3D cam){
        p1 = CubeCalculator.getShadow(p1, 2, VectorCalc.getLength(cam.getVisionAxes()));
        p2 = CubeCalculator.getShadow(p2, 2, VectorCalc.getLength(cam.getVisionAxes()));
        p3 = CubeCalculator.getShadow(p3, 2, VectorCalc.getLength(cam.getVisionAxes()));
    }

    public void scale(double scale){
        p1.multiplyScalar(scale);
        p2.multiplyScalar(scale);
        p3.multiplyScalar(scale);
    }

    public Vector getP1() {
        return p1;
    }

    public Vector getP2() {
        return p2;
    }

    public Vector getP3() {
        return p3;
    }

    @Override
    public Line[] getLines() {
        Line l1 = new Line(p1, p2);
        Line l2 = new Line(p2, p3);
        return new Line[]{l1, l2};
    }

    @Override
    public Element clone(ArrayList<Vector> allMatrices) {
        return new Face(p1.clone(), p2.clone(), p3.clone());
    }

    @Override
    public Element[] getElements() {
        return new Element[0];
    }

    public Vector getNormal(){
        return normal;
    }
}
