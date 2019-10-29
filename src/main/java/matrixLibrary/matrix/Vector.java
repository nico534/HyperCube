package matrixLibrary.matrix;

import matrixLibrary.formula.Formula;
import org.apache.commons.lang3.ArrayUtils;

public class Vector extends LinearElement {

    private double[] values;

    private boolean transpose;

    /**
     * creates a nX1 Vector.
     * @param vector - the values of the vector.
     */
    public Vector(double[] vector) {
        this.values = vector;
    }

    /**
     * creates a new Vector with de given length.
     * @param length - the length of the new Vector.
     */
    public Vector(int length){
        values = new double[length];
        transpose = false;
    }

    @Override
    public Vector clone() {
        Vector newVec = new Vector(ArrayUtils.clone(values));
        if(transpose){
            newVec.transpose();
        }
        return newVec;
    }

    /**
     * copy the values of Vector v deep to this vector.
     * @param v - the Vector to copy from.
     */
    public void deepCopy(Vector v) {
        this.values = ArrayUtils.clone(v.values);
        this.transpose = v.transpose;
    }

    /**
     * copy the values of Vector v light to this vector.
     * @param v - the Vector to copy from.
     */
    public void copy(Vector v) {
        this.values = v.values;
        this.transpose = v.transpose;
    }

    @Override
    public void addFormula(Formula f) {
        f.applyFormula(this);
    }

    @Override
    public void multiplyScalar(double scalar) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= scalar;
        }
    }

    @Override
    public void print() {
        System.out.println(toString());
    }

    /**
     * transposes the Vector, a (nx1) vector gets to a (1xn) vectore
     */
    @Override
    public void transpose() {
        this.transpose = !this.transpose;
    }

    @Override
    public boolean isTranspose() {
        return transpose;
    }

    @Override
    public double get(int row, int col) {
        if(transpose){
            if(row != 1 || col<0 || col > values.length){
                throw new IndexOutOfBoundsException("(" + row + " | " + col + "out of bounds for Vector (1 | " + (values.length-1) + " )");
            }
            return values[col];
        } else{
            if(col != 0 || row<0 || row > values.length){
                throw new IndexOutOfBoundsException("(" + row + " | " + col + ") out of bounds for Vector ( " + (values.length-1) + " | 1)");
            }
            return values[row];
        }
    }

    @Override
    public void set(int row, int col, double value) {
        if(transpose){
            if(row != 0 || col<0 || col > values.length){
                throw new IndexOutOfBoundsException("(" + row + " | " + col + "out of bounds for Vector (1 | " + (values.length-1) + " )");
            }
            values[col] = value;
        } else{
            if(col != 0 || row<0 || row > values.length){
                throw new IndexOutOfBoundsException("(" + row + " | " + col + ") out of bounds for Vector ( " + (values.length-1) + " | 1)");
            }
            values[row] = value;
        }
    }

    @Override
    public double get(int index) {
        return values[index];
    }

    @Override
    public void set(int index, double value) {
        values[index] = value;
    }

    @Override
    public int rows() {
        if(transpose){
            return 1;
        }
        return values.length;
    }

    @Override
    public int cols() {
        if(transpose){
            return values.length;
        }
        return 1;
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public void reset() {
        values = new double[values.length];
        transpose = false;
    }

    /**
     * enlarges the vector size by 1, adds the given Value at the last new field.
     * assumes that it is a vector, doesn't test it!!!!
     *
     * @param newValue - the new end Value.
     */
    public void push(double newValue) {
        double[] newVector= new double[values.length + 1];
        System.arraycopy(values, 0, newVector, 0, values.length);
        newVector[values.length] = newValue;
        this.values = newVector;
    }

    /**
     * decreases the vector size by 1, returns the last Value.
     * assumes that it is a vector, doesn't test it!!!!
     *
     * @return -  the last number.
     */
    public double pull() {
        double[] newVector = new double[values.length - 1];
        System.arraycopy(values, 0, newVector, 0, newVector.length);
        double valueToPull = values[values.length - 1];
        this.values = newVector;
        return valueToPull;
    }

    /**
     * Converts the vector to a Matrix,
     * (a, b, c, d) vector will be converted to a
     *
     * (a, b)
     * (c, d) Matrix if rows = 2.
     * @param rows - the rows the matrix should have,
     * @return - a matrix that has the values of the vector,
     */
    public Matrix convertToMatrix(int rows){
        return new Matrix(values, rows);
    }

    public boolean equals(Object o){
        if(o.getClass() != Vector.class){
            return false;
        }
        Vector toCompare = (Vector)o;
        if(toCompare.size() != this.size()){
            return false;
        }
        for(int i = 0; i < this.size(); i++){
            if(Math.abs(toCompare.get(i) - this.get(i)) > lambda){
                return false;
            }
        }
        return true;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (size() > maxShownRowsCols) {
            for (int i = 0; i < maxShownRowsCols / 2; i++) {
                sb.append(setFixPrintLength(values[i]));
                if (i < maxShownRowsCols / 2 - 1) {
                    sb.append(" ");
                }
            }
            sb.append(" ... ");
            for (int i = size() - maxShownRowsCols / 2; i < size(); i++) {
                sb.append(setFixPrintLength(values[i]));
                if (i < maxShownRowsCols / 2 - 1) {
                    sb.append(" ");
                }
            }
        } else {
            for (int i = 0; i < rows(); i++) {
                sb.append(setFixPrintLength(values[i]));
                if (i < rows()) {
                    sb.append(" ");
                }
            }
        }
        sb.append(")");
        if(!transpose){
            sb.append("^T");
        }
        return sb.toString();
    }
}