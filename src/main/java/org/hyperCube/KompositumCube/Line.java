package org.hyperCube.KompositumCube;

import matrixLibrary.matrix.Matrix;

import java.util.ArrayList;

public class Line implements Element {
    private Matrix p1;
    private Matrix p2;

    private int[] ignoreValues;

    protected Line(Matrix p1, Matrix p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    Line(Matrix[] points, int[] ignoreValues) {
        this.p1 = points[0];
        this.p2 = points[1];
        this.ignoreValues = ignoreValues;
    }

    @Override
    public Construct[] get(int dimension) {
        return null;
    }

    @Override
    public Line[] getLines() {
        return new Line[]{this};
    }

    @Override
    public Element clone(ArrayList<Matrix> allMatrices) {

        Matrix newP1 = allMatrices.get(allMatrices.indexOf(p1));
        Matrix newP2 = allMatrices.get(allMatrices.indexOf(p2));
        return new Line(newP1, newP2);
    }

    public void changeElement() {
        Matrix save = p1;
        p1 = p2;
        p2 = save;
    }

    public void printLine(int maxValueCount, int maxCountOfDigits){
        p1.setShownNumbers(maxCountOfDigits);
        p1.setMaxShownRowsCols(maxValueCount);
        p2.setShownNumbers(maxCountOfDigits);
        p2.setMaxShownRowsCols(maxValueCount);

        System.out.print("{");
        p1.printVector();
        System.out.print(" | ");
        p2.printVector();
        System.out.println("}");
    }

    public void printLine(){
        System.out.print("{");
        p1.printVector();
        System.out.print("/");
        p2.printVector();
        System.out.println("}");
    }

    public Matrix getP1(){
        return this.p1;
    }

    public Matrix getP2(){
        return this.p2;
    }

    public void setP1(Matrix p1){
        this.p1 = p1;
    }

    public void setP2(Matrix p2){
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
