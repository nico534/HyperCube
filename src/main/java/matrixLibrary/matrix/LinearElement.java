package matrixLibrary.matrix;

import matrixLibrary.formula.Formula;

public abstract class LinearElement {
    // the max distance value to say the value is equal
    protected static double lambda = 0.000001f;
    // a few values for printing the matrix
    protected static int shownDigits = 9;
    protected static int maxShownRowsCols = 10;

    /**
     * Sets the max shown rows and columns.
     * If the element has more rows or columns, print will down't show the numbers in the middle.
     *
     * @param number - the max siz of rows and columns.
     */
    public void setMaxShownRowsCols(int number){
        maxShownRowsCols = number;
    }

    /**
     * sets the shown digits of one Value py printing the matrix to the given number size.
     *
     * @param number - the number size.
     */
    public void setShownDigitCount(int number){
        shownDigits = number;
    }

    /**
     * Sets lambda to the new Value.
     * Lambda is just used to say if two matrix values are the same.
     *
     * @param newLambda - the new lambda
     */
    public void setLambda(double newLambda){
        lambda = newLambda;
    }

    /**
     * creates a copy of the Matrix ore Vector.
     *
     * @return - the copy.
     */
    public abstract LinearElement clone();

    /**
     * add a Formula to the linear Element.
     *
     * @param f - a Formula to apply on the linear Element.
     */
    public abstract void addFormula(Formula f);

    /**
     * multiplys linear element by scalar
     *
     * @param scalar - a scalar
     */
    public abstract void multiplyScalar(double scalar);

    /**
     * prints the element.
     */
    public abstract void print();

    String setFixPrintLength(double input) {
        String strInput = String.format(("%." + (shownDigits-2) + "f"), input);
        StringBuilder printer = new StringBuilder();
        for (int i = 0; i < shownDigits; i++) {
            if (i < strInput.length()) {
                printer.append(strInput.charAt(i));
            } else {
                printer.append(' ');
            }
        }
        return printer.toString();
    }

    /**
     * transposes the element.
     */
    public abstract void transpose();

    /**
     * @return - true if the matrix is listed as transposed.
     */
    public abstract boolean isTranspose();

    /**
     * @param row - the row value.
     * @param col - the col value.
     * @return - the value at row, col.
     */
    public abstract double get(int row, int col);

    /**
     * Sets the Value ad Matrix(row, col).
     *
     * @param row   - the row of the matrix.
     * @param col   - the column of the matrix.
     * @param value - the value witch will get stored at the position.
     */
    public abstract void set(int row, int col, double value);

    /**
     * returns the value at that index.
     *
     * @param index - the double[] matrix index, if it is a Vector the rowValue.
     * @return - the value at that index.
     */
    public abstract double get(int index);

    /**
     * if it is just a Vector you can set Values just with the index.
     * Use carefully, it will set the value even if it is a matrix and
     * might get unwonted results.
     *
     * @param index - the index of the element.
     * @param value - the value witch will get stored at the position.
     */
    public abstract void set(int index, double value);

    /**
     * @return - the number of rows.
     */
    public abstract int rows();

    /**
     * @return - the number of columns.
     */
    public abstract int cols();

    /**
     * @return - the size of the Matrix/Vector array.
     */
    public abstract int size();

    /**
     * resets all linear relevant values.
     */
    public abstract void reset();
}
