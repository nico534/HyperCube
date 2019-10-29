package org.hyperCube;

import matrixLibrary.formula.Formula;
import matrixLibrary.formula.SubtractFormula;
import matrixLibrary.matrix.Matrix;
import matrixLibrary.formula.RotateVectorFormula;
import matrixLibrary.formula.ShortenVectorToSizeFormula;
import matrixLibrary.matrix.Vector;
import matrixLibrary.utils.VectorCalc;
import org.render3D.KompositumCube.Element;
import org.render3D.KompositumCube.Line;

import java.util.ArrayList;
import java.util.Arrays;

public class Construct implements Element {
    private Element[] constructs;
    private int dimension;
    private Vector[] allPoints;

    private int[] ignoreValues = new int[0];

    public Construct(Vector[] pointsOfTheConstruct, int dimension){
        init(pointsOfTheConstruct, dimension);
        this.allPoints = pointsOfTheConstruct;
        updateFaces();
    }

    private Construct(Element[] elements, int dimension, int[] ignoreValues){
        this.constructs = elements;
        this.dimension = dimension;
        this.ignoreValues = ignoreValues;
    }

    private Construct(Vector[] pointsOfTheConstruct, int dimension, int[] ignoreValues){
        this.ignoreValues = ignoreValues;
        init(pointsOfTheConstruct, dimension);
        this.allPoints = pointsOfTheConstruct;
    }

    private void init(Vector[] pointsOfTheConstruct, int dimension){
        this.dimension = dimension;
        if(dimension > 2){
            constructs = new Construct[2 * this.dimension];
            int counter = 0;
            for(int i = 0; i < pointsOfTheConstruct[0].size(); i++){
                if(inIgnored(i)){
                    continue;
                }
                Vector[] newPoints = findTogetherAt(i, 1, pointsOfTheConstruct);
                Vector[] newPoint2 = findTogetherAt(i, -1, pointsOfTheConstruct);
                constructs[counter] = new Construct(newPoints, dimension -1, adToIgnore(i));
                counter++;
                constructs[counter] = new Construct(newPoint2, dimension -1, adToIgnore(i));
                counter++;
            }
        }else {
            constructs = new Line[2 * this.dimension];
            int counter = 0;
            for(int i = 0; i < pointsOfTheConstruct[0].size(); i++){
                if(inIgnored(i)){
                    continue;
                }
                Vector[] newPoints = findTogetherAt(i, 1, pointsOfTheConstruct);
                Vector[] newPoint2 = findTogetherAt(i, -1, pointsOfTheConstruct);
                constructs[counter] = new Line(newPoints,  adToIgnore(i));
                counter++;
                constructs[counter] = new Line(newPoint2, adToIgnore(i));
                counter++;
            }
        }
    }

    private Vector[] findTogetherAt(int dimension, float number, Vector[] allPoints){
        Vector[] rightPoints = new Vector[(int)Math.pow(2, (this.dimension-1))];
        int counter = 0;
        for(Vector p: allPoints){
            if(p.get(dimension) == number){
                rightPoints[counter] = p;
                counter++;
            }
        }
        if (counter != rightPoints.length){
            throw new RuntimeException("Fatal error, can not find enough Points for dimension " + dimension);
        }
        return rightPoints;
    }

    private int[] adToIgnore(int newIgnoreValue){
        int[] newIgnore = new int[this.ignoreValues.length + 1];
        System.arraycopy(this.ignoreValues, 0, newIgnore, 0, this.ignoreValues.length);
        newIgnore[newIgnore.length-1] = newIgnoreValue;
        return newIgnore;
    }

    private boolean inIgnored(int check){
        for(int i: this.ignoreValues){
            if(i == check){
                return true;
            }
        }
        return false;
    }

    public Construct[] get(int dimension){
        if(dimension < 2){
            throw new IndexOutOfBoundsException("there are no constructs under dim 2.");
        }
        if(dimension == this.dimension){
            return new Construct[]{this};
        }
        ArrayList<Construct> getConstructs = new ArrayList<>();
        for(Element c: constructs){
            Construct c2 = (Construct)c;
            Construct[] newConstructs = c2.get(dimension);
            getConstructs.addAll(Arrays.asList(newConstructs));
        }
        return getConstructs.toArray(new Construct[0]);
    }

    public Line[] getLines(){
        ArrayList<Line> getLines = new ArrayList<>();
        for(Element c: constructs){
            Line[] newLines = c.getLines();
            getLines.addAll(Arrays.asList(newLines));
        }
        return getLines.toArray(new Line[0]);
    }

    @Override
    public Element[] getElements() {
        return new Element[0];
    }

    public void updateFaces(){
        if(this.dimension < 3){
            updateCubeFaces();
            return;
        }
        Construct[] allCubes = get(3);
        for(Construct c: allCubes){
            c.updateCubeFaces();
        }
    }

    private void updateCubeFaces(){
        Construct[] faces = get(2);
        int counter = 0;
        for(Construct f: faces){
            f.changeElement(0, 2);
            f.changeElement(2, 3);
            Line[] lines = f.getLines();
            lines[2].changeElement();
            lines[3].changeElement();
            if(counter == 1 || counter == 2 || counter == 5){
                lines[0].changeElement();
                lines[1].changeElement();
                lines[2].changeElement();
                lines[3].changeElement();
                f.changeElement(0,3);
                f.changeElement(1,2);
            }
            counter++;
        }
    }

    private double getCommonValue(Construct f){
        int[] pointCounter = new int[f.allPoints[0].size()];
        for(int i = 0; i < f.allPoints.length; i++){
            for(int j = 0; j < f.allPoints[i].size(); j++){
                pointCounter[j] += f.allPoints[i].get(j);
            }
        }
        for(int i = 0; i < pointCounter.length; i++){
            if(Math.abs(pointCounter[i]) == f.allPoints.length && !inIgnored(i)){
                return f.allPoints[0].get(i);
            }
        }
        return 0;
    }

    public Vector[] getAllPoints(){
        return this.allPoints;
    }

    private void setAllPoints(Vector[] newAllPoints){
        this.allPoints = newAllPoints;
    }

    public void rotate(Matrix rotationMatrix){
        for(int i = 0; i < allPoints.length; i++){
            allPoints[i].addFormula(new RotateVectorFormula(rotationMatrix));
        }
    }

    public void changeElement(int c1, int c2){
        Element save = constructs[c1];
        constructs[c1] = constructs[c2];
        constructs[c2] = save;
    }

    public Vector calculateNormalVector(){
        Line[] allLines = this.getLines();
        if(allLines.length != 4){
            System.out.println("you down't use a Face");
            return null;
        }
        Line l1 = allLines[0];
        Line l2 = allLines[1];

        Vector startPoint = l1.getP1();
        Vector endPoint1 = l1.getP2().clone();
        Vector endPoint2 = l2.getP2().clone();

        Formula f = new SubtractFormula(startPoint);
        endPoint1.addFormula(f);
        endPoint2.addFormula(f);

        endPoint1.addFormula(new ShortenVectorToSizeFormula(3));
        endPoint2.addFormula(new ShortenVectorToSizeFormula(3));

        endPoint1 = VectorCalc.getNormalizeVector(endPoint1);
        endPoint2 = VectorCalc.getNormalizeVector(endPoint2);
        Vector crossProd = VectorCalc.crossProduct(endPoint1, endPoint2);
        if(VectorCalc.getLength(crossProd) == 0){
            return crossProd;
        }
        return VectorCalc.getNormalizeVector(crossProd);
    }

    public Construct clone(ArrayList<Vector> allMatrices){
        Element[] newElements = new Element[constructs.length];
        for(int i = 0; i < constructs.length; i++){
            newElements[i] = this.constructs[i].clone(allMatrices);
        }
        return new Construct(newElements, this.dimension, this.ignoreValues);
    }

    public Construct clone(){
        Element[] newElements = new Element[constructs.length];
        ArrayList<Vector> clonePoints = new ArrayList<>();
        for(int i = 0; i < this.allPoints.length; i++){
            clonePoints.add(this.allPoints[i].clone());
        }
        for(int i = 0; i < constructs.length; i++){
            newElements[i] = this.constructs[i].clone(clonePoints);
        }

        Construct newC = new Construct(newElements, this.dimension, this.ignoreValues);
        newC.setAllPoints(clonePoints.toArray(new Vector[0]));
        return newC;
    }
}