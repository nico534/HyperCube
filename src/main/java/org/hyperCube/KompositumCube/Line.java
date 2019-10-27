package org.hyperCube.KompositumCube;

import matrixLibrary.matrix.Matrix;

public class Line implements Element {
    private Matrix p1;
    private Matrix p2;

    private int[] ignoreValues;

    Line(Matrix[] points, int[] ignoreValues) {
        this.p1 = points[0];
        this.p2 = points[1];
        this.ignoreValues = ignoreValues;
    }

    @Override
    public Element[] get(int dimension) {
        return new Element[]{this};
    }

    @Override
    public Line[] getLines() {
        return new Line[]{this};
    }

    @Override
    public void changeElement(int e1, int e2) {
    }

    @Override
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
}
