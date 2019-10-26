package org.hyperCube;


import matrixLibrary.matrix.Matrix;

import java.util.ArrayList;

public class Construct {
    private Construct[] constructs;
    private int dimension;
    private Matrix[] points;

    private int[] ignoreValues = new int[0];

    public Construct(Construct[] constructs, int dimension){
        this.constructs = constructs;
        this.dimension = dimension;
    }

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
        constructs = new Construct[2 * this.dimension];
        if(dimension > 1){
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
            this.points = pointsOfTheConstruct;
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
        for(int i = 0; i < this.ignoreValues.length; i++){
            newIgnore[i] = this.ignoreValues[i];
        }
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
        if(dimension == this.dimension){
            return constructs;
        }
        ArrayList<Construct> getConstructs = new ArrayList<>();
        for(Construct c: constructs){
            Construct[] newConstructs = c.get(dimension);
            for(Construct k: newConstructs){
                getConstructs.add(k);
            }
        }
        return getConstructs.toArray(new Construct[0]);
    }

    public Matrix[] getPoints(){
        if(dimension == 1){
            return this.points;
        }
        ArrayList<Matrix> getPoints = new ArrayList<>();
        for(Construct c: constructs){
            Matrix[] newPoints = c.getPoints();
            for(Matrix m: newPoints){
                getPoints.add(m);
            }
        }
        return getPoints.toArray(new Matrix[0]);
    }

    /**
     * magically it sets the clockwise draw direction of the faces.
     */
    private void updateFaces(){
        Construct[] faces = get(3);
        for(Construct f: faces){
            f.changeConstruct(0, 2);
            f.changeConstruct(2, 3);
            Construct[] lines = f.get(2);
            lines[2].changePoints();
            lines[3].changePoints();
        }
    }

    private void changeConstruct(int c1, int c2){
        Construct save = constructs[c1];
        constructs[c1] = constructs[c2];
        constructs[c2] = save;
    }

    private void changePoints(){
        Matrix save = points[0];
        points[0] = points[1];
        points[1] = save;
    }
}
