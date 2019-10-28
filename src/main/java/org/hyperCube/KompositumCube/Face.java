package org.hyperCube.KompositumCube;

import matrixLibrary.matrix.Matrix;
import matrixLibrary.utils.VectorCalc;
import org.gui.Camera3D;
import org.hyperCube.CubeCalculator;

import java.util.ArrayList;

public class Face implements Element {
    Matrix p1;
    Matrix p2;
    Matrix p3;
    private double lightIntensity = 1.0;
    private Matrix normal;

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

    public Face(Line l1, Line l2, Matrix normal){
        if(l1.getP2() != l2.getP1()){
            System.out.println("triangle needs to be drown clockwise.");
            return;
        }
        p1 = l1.getP1();
        p2 = l1.getP2();
        p3 = l2.getP2();
        this.normal = normal;
    }

    public void calcLightIntensity(Matrix light){
        lightIntensity = VectorCalc.dotProduct(normal, light);
        if(lightIntensity < 0){
            lightIntensity = 0;
        }
    }

    public double getLightIntensity(){
        return this.lightIntensity;
    }

    private void calcNormal(){
        Matrix endPoint1 = p2.copy();
        Matrix endPoint2 = p3.copy();
        endPoint1.subtract(p1);
        endPoint2.subtract(p1);
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

    private Face(Matrix p1, Matrix p2, Matrix p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Matrix getP1() {
        return p1;
    }

    public Matrix getP2() {
        return p2;
    }

    public Matrix getP3() {
        return p3;
    }

    @Override
    public Construct[] get(int dimension) {
        return null;
    }

    @Override
    public Line[] getLines() {
        Line l1 = new Line(p1, p2);
        Line l2 = new Line(p2, p3);
        return new Line[]{l1, l2};
    }

    @Override
    public Element clone(ArrayList<Matrix> allMatrices) {
        return new Face(p1.copy(), p2.copy(), p3.copy());
    }
}
