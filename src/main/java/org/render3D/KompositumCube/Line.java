package org.render3D.KompositumCube;

import matrixLibrary.matrix.LinearElement;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.matrix.Vector;
import org.hyperCube.Construct;

import java.util.ArrayList;

public class Line implements Element {
    private Vector p1;
    private Vector p2;

    private int[] ignoreValues;

    public Line(Vector p1, Vector p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line(Vector[] points, int[] ignoreValues) {
        this.p1 = points[0];
        this.p2 = points[1];
        this.ignoreValues = ignoreValues;
    }

    @Override
    public Line[] getLines() {
        return new Line[]{this};
    }

    @Override
    public Element[] getElements() {
        return new Element[0];
    }

    @Override
    public Element clone(ArrayList<Vector> allPoints) {

        Vector newP1 = allPoints.get(allPoints.indexOf(p1));
        Vector newP2 = allPoints.get(allPoints.indexOf(p2));
        return new Line(newP1, newP2);
    }

    public void changeElement() {
        Vector save = p1;
        p1 = p2;
        p2 = save;
    }

    public void printLine(int maxValueCount, int maxCountOfDigits){
        /*
        p1.setShownNumbers(maxCountOfDigits);
        p1.setMaxShownRowsCols(maxValueCount);
        p2.setShownNumbers(maxCountOfDigits);
        p2.setMaxShownRowsCols(maxValueCount);

        System.out.print("{");
        p1.printVector();
        System.out.print(" | ");
        p2.printVector();
        System.out.println("}");
         */
    }

    public void printLine(){
        System.out.println("{" + p1.toString() + " | " + p2.toString() + "}");
    }

    public Vector getP1(){
        return this.p1;
    }

    public Vector getP2(){
        return this.p2;
    }

    public void setP1(Vector p1){
        this.p1 = p1;
    }

    public void setP2(Vector p2){
        this.p2 = p2;
    }

    public boolean equals(Object o){
        if(o.getClass() != Line.class){
            return false;
        }
        Line l = (Line)o;
        return ((l.p1.equals(this.p1) && l.p2.equals(this.p2))) || ((l.p1.equals(this.p2) && l.p2.equals(this.p1)));
    }
}
