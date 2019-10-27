package org.hyperCube.KompositumCube;


import matrixLibrary.matrix.Matrix;
import java.util.ArrayList;
import java.util.Arrays;

public class Construct implements Element {
    private Element[] constructs;
    private int dimension;

    private int[] ignoreValues = new int[0];

    private Construct(Matrix[] pointsOfTheConstruct, int dimension, int[] ignoreValues){
        this.ignoreValues = ignoreValues;
        init(pointsOfTheConstruct, dimension);
    }

    public Construct(Matrix[] pointsOfTheConstruct, int dimension){
        init(pointsOfTheConstruct, dimension);
        updateFaces();
    }

    private void init(Matrix[] pointsOfTheConstruct, int dimension){
        this.dimension = dimension;
        if(dimension > 2){
            constructs = new Construct[2 * this.dimension];
            int counter = 0;
            for(int i = 0; i < pointsOfTheConstruct[0].size(); i++){
                if(inIgnored(i)){
                    continue;
                }
                Matrix[] newPoints = findTogetherAt(i, 1, pointsOfTheConstruct);
                Matrix[] newPoint2 = findTogetherAt(i, -1, pointsOfTheConstruct);
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
                Matrix[] newPoints = findTogetherAt(i, 1, pointsOfTheConstruct);
                Matrix[] newPoint2 = findTogetherAt(i, -1, pointsOfTheConstruct);
                constructs[counter] = new Line(newPoints,  adToIgnore(i));
                counter++;
                constructs[counter] = new Line(newPoint2, adToIgnore(i));
                counter++;
            }
        }
    }

    private Matrix[] findTogetherAt(int dimension, float number, Matrix[] allPoints){
        Matrix[] rightPoints = new Matrix[(int)Math.pow(2, (this.dimension-1))];
        int counter = 0;
        for(Matrix p: allPoints){
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

    public Element[] get(int dimension){
        if(dimension == this.dimension){
            return new Construct[]{this};
        }
        ArrayList<Element> getConstructs = new ArrayList<>();
        for(Element c: constructs){
            Element[] newConstructs = c.get(dimension);
            getConstructs.addAll(Arrays.asList(newConstructs));
        }
        return getConstructs.toArray(new Element[0]);
    }

    public Line[] getLines(){
        ArrayList<Line> getLines = new ArrayList<>();
        for(Element c: constructs){
            Line[] newLines = c.getLines();
            getLines.addAll(Arrays.asList(newLines));
        }
        return getLines.toArray(new Line[0]);
    }

    /**
     * magically it sets the clockwise draw direction of the faces.
     */
    private void updateFaces(){
        Element[] faces = get(3);
        for(Element f: faces){
            f.changeElement(0, 2);
            f.changeElement(2, 3);
            Element[] lines = f.get(2);
            lines[2].changeElement();
            lines[3].changeElement();
        }
    }

    public void changeElement(int c1, int c2){
        Element save = constructs[c1];
        constructs[c1] = constructs[c2];
        constructs[c2] = save;
    }

    @Override
    public void changeElement() {
    }
}
