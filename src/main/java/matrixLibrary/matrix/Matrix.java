package matrixLibrary.matrix;

import matrixLibrary.formula.Formula;
import org.apache.commons.lang3.ArrayUtils;

/**
 * A matrix that is stored in a single array.
 */
public class Matrix {
    // the max distance value to say the value is equal
    private double lambda = 0.000001f;

    private double[] matrix;
    private int rows;
    private int cols;
    private boolean transpose = false;

    // a few values for printing the matrix
    private int shownNumbers = 9;
    private int maxShownRowsCols = 10;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        matrix = new double[rows * cols];
    }

    public Matrix(int rows) {
        this.rows = rows;
        this.cols = 1;
        matrix = new double[rows * cols];
    }

    public Matrix(double[] vector) {
        this.rows = vector.length;
        this.cols = 1;
        this.matrix = vector;
    }

    public Matrix(double[] matrix, int rows) {
        if (matrix.length % rows != 0) {
            throw new IndexOutOfBoundsException("not a valid Matrix");
        }
        this.matrix = matrix;
        this.cols = matrix.length / rows;
        this.rows = rows;
    }

    /**
     * creates a copy of the Matrix.
     *
     * @return - the copy.
     */
    public Matrix copy() {

        Matrix copy = new Matrix(ArrayUtils.clone(this.matrix), this.rows);

        //standart is not transposed
        if (transpose) {
            copy.transpose();
        }
        return copy;
    }

    /**
     * @return - the index withe the highest Values, if there are 2 maxValues it returns the firs.
     */
    public int getMaxIndex() {
        double max = matrix[0];
        int maxIndex = 0;
        for (int i = 1; i < rows * cols; i++) {
            if (matrix[i] > max) {
                max = matrix[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * adds the given Matrix to the Matrix.
     *
     * @param toAdd - the matrix to addition.
     */
    public void addUp(Matrix toAdd) {

        if (toAdd.rows() != this.rows() || toAdd.cols() != this.cols()) {
            throw new MatrixDontMatchException("the Matrices hav not the same shape: (" +
                    toAdd.rows() + "," + toAdd.cols() + ") != (" + this.rows() + "," + this.cols() + ")");
        }

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                this.set(i, j, this.get(i, j) + toAdd.get(i, j));
            }
        }
    }

    /**
     * subtracts the given matrix from the matrix.
     *
     * @param toSubtract - the matrix to subtract.
     */
    public void subtract(Matrix toSubtract) {
        if (toSubtract.rows() != this.rows() || toSubtract.cols() != this.cols()) {
            throw new MatrixDontMatchException("the Matrices hav not the same shape: (" +
                    toSubtract.rows() + "," + toSubtract.cols() + ") != (" + this.rows() + "," + this.cols() + ")");
        }

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                set(i, j, get(i, j) - toSubtract.get(i, j));
            }
        }
    }

    /**
     * multiply the given matrix values to the matrix
     *
     * @param toMultiply - the matrix to multiply the values
     */
    public void multiplyValues(Matrix toMultiply) {
        if (toMultiply.rows() != this.rows() || toMultiply.cols() != this.cols()) {
            throw new MatrixDontMatchException("Can not multiply these Vectors, not the same shape: (" +
                    toMultiply.rows() + "," + toMultiply.cols() + ") != (" + this.rows() + "," + this.cols() + ")");
        }

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < cols(); j++) {
                set(i, j, get(i, j) * toMultiply.get(i, j));
            }
        }
    }

    /**
     * add a Formula to the Matrix
     *
     * @param f - a Formula to apply on the matrix
     */
    public void addFormula(Formula f) {
        f.applyFormula(this);
    }

    /**
     * multiplys matrix by scalar
     *
     * @param scalar - a scalar
     */
    public void multiplyScalar(double scalar) {
        for (int i = 0; i < rows * cols; i++) {
            matrix[i] *= (double) scalar;
        }
    }

    /**
     * @return - true if the matrix is a vector.
     */
    public boolean getIsVector() {
        return (cols() == 1);
    }

    public void printVector(){
        System.out.print("(");
        if (rows() > maxShownRowsCols) {
            for (int i = 0; i < maxShownRowsCols / 2; i++) {
                System.out.print(setFixPrintLength(matrix[i]));
                if(i < maxShownRowsCols / 2-1){
                    System.out.print(" ");
                }
            }

            printSeparator("   ...   ");
            for (int i = rows() - maxShownRowsCols / 2; i < rows(); i++) {
                System.out.print(setFixPrintLength(matrix[i]));
                if(i < maxShownRowsCols / 2-1){
                    System.out.print(" ");
                }
            }
        } else {
            for (int i = 0; i < rows(); i++) {
                System.out.print(setFixPrintLength(matrix[i]));
                if(i < rows()){
                    System.out.print(" ");
                }
            }
        }
        System.out.println(")");
        printSeparator("---------");
    }

    /**
     * prints the matrix, maximum displayed is 10x10, if bigger the middle
     * elements won't by shown.
     */
    public void print() {
        if (rows() > maxShownRowsCols) {
            for (int i = 0; i < maxShownRowsCols / 2; i++) {
                printRow(i);
            }

            printSeparator("   ...   ");
            for (int i = rows() - maxShownRowsCols / 2; i < rows(); i++) {
                printRow(i);
            }
        } else {
            for (int i = 0; i < rows(); i++) {
                printRow(i);
            }
        }
        printSeparator("---------");
    }

    private void printSeparator(String separator) {
        if (cols > maxShownRowsCols) {
            for (int i = 0; i < maxShownRowsCols + 1; i++) {
                System.out.print(separator);
                if ((i) % maxShownRowsCols / 2 != 0) {
                    System.out.print("  ");
                }
            }
        } else {
            for (int i = 0; i < cols; i++) {
                System.out.print(separator);
                if (i + 1 != cols) {
                    System.out.print("  ");
                }
            }
        }
        System.out.println();
    }

    private void printRow(int row) {
        if (cols() > maxShownRowsCols) {
            for (int i = 0; i < maxShownRowsCols / 2; i++) {
                System.out.print(setFixPrintLength(get(row, i)));
                if (i < maxShownRowsCols / 2 - 1) {
                    System.out.print("  ");
                }
            }
            System.out.print("   ...   ");
            for (int i = cols() - maxShownRowsCols / 2; i < cols(); i++) {
                System.out.print(setFixPrintLength(get(row, i)));
                if (i < cols() - 1) {
                    System.out.print("  ");
                }
            }
            System.out.println();
        } else {
            for (int i = 0; i < cols(); i++) {
                System.out.print(setFixPrintLength(get(row, i)));
                if (i < cols() - 1) {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    private String setFixPrintLength(double input) {
        String strInput = String.format(("%." + (this.shownNumbers-2) + "f"), input);
        StringBuilder printer = new StringBuilder();
        for (int i = 0; i < this.shownNumbers; i++) {
            if (i < strInput.length()) {
                printer.append(strInput.charAt(i));
            } else {
                printer.append(' ');
            }
        }
        return printer.toString();
    }

    /**
     * Sets the max shown rows and columns.
     * If the matrix has more rows or columns, print will down't show the numbers in the middle.
     *
     * @param number - the max siz of rows and columns.
     */
    public void setMaxShownRowsCols(int number) {
        this.maxShownRowsCols = number;
    }

    /**
     * sets the shown numbers of one Value py printing the matrix to the given number size.
     *
     * @param number - the number size.
     */
    public void setShownNumbers(int number) {
        this.shownNumbers = number;
    }

    /**
     * transposes the Matrix.
     */
    public void transpose() {
        this.transpose = !this.transpose;
    }

    /**
     * @return - true if the matrix is listed as transposed.
     */
    public boolean isTranspose() {
        return this.transpose;
    }

    /**
     * @param row - the row value.
     * @param col - the col value.
     * @return - the value at row, col.
     */
    public double get(int row, int col) {
        return this.matrix[getIndex(row, col)];
    }

    /**
     * Returns the index in witch the Value is Stored in the double array.
     *
     * @param row - the row value.
     * @param col - the col value.
     * @return - the index.
     */
    public int getIndex(int row, int col) {
        if (transpose) {
            if (col > (this.rows - 1) || row > (this.cols - 1) || col < 0 || row < 0) {
                throw new IndexOutOfBoundsException("(" + row + "," + col + ") out of bounds for matrix (" + cols() + "," + rows() + ").");
            }
            return col * this.cols + row % this.cols;
        } else {
            if (col > (this.cols - 1) || row > (this.rows - 1) || col < 0 || row < 0) {
                throw new IndexOutOfBoundsException("(" + row + "," + col + ") out of bounds for matrix (" + rows() + "," + cols() + ").");
            }
            return row * this.cols + col % this.cols;
        }
    }

    /**
     * Sets the Value ad Matrix(row, col).
     *
     * @param row   - the row of the matrix.
     * @param col   - the column of the matrix.
     * @param value - the value witch will get stored at the position.
     */
    public void set(int row, int col, double value) {
        this.matrix[getIndex(row, col)] = value;
    }

    /**
     * returns the value at that index.
     *
     * @param index - the double[] matrix index, if it is a Vector the rowValue.
     * @return - the value at that index.
     */
    public double get(int index) {
        return this.matrix[index];
    }

    /**
     * if it is just a Vector you can set Values just with the index.
     * Use carefully, it will set the value even if it is a matrix and
     * might get unwonted results.
     *
     * @param index - the index of the matrix array.
     * @param value - the value witch will get stored at the position.
     */
    public void set(int index, double value) {
        this.matrix[index] = value;
    }

    /**
     * @return - the number of rows.
     */
    public int rows() {
        if (transpose) {
            return cols;
        }
        return rows;
    }

    /**
     * @return - the number of columns.
     */
    public int cols() {
        if (transpose) {
            return rows;
        }
        return cols;
    }

    /**
     * @return - the size of the Matrix array.
     */
    public int size() {
        return matrix.length;
    }

    /**
     * resets the matrix back to a zero-matrix.
     */
    public void reset() {
        this.matrix = new double[this.matrix.length];
    }

    /**
     * if convert to vector, the matrix will be converted to a vector.
     *           a b c
     * a Matrix  d e f    will be converted in (a b c d e f) transpose.
     *
     */
    public void convertToVector() {
        this.rows = this.rows * this.cols;
        this.cols = 1;
    }

    public void convertToMatrix(int rows){
        if (matrix.length % rows != 0) {
            throw new MatrixDontMatchException("not a valid Matrix");
        }
        this.cols = matrix.length / rows;
        this.rows = rows;
    }

    /**
     * Sets lambda to the new Value.
     * Lambda is just used to say if two matrix values are the same.
     *
     * @param newLambda - the new lambda
     */
    public void setLambda(double newLambda) {
        this.lambda = newLambda;
    }

    /**
     * Switch row one with row two.
     *
     * @param rowOne - one row
     * @param rowTwo - an other row.
     */
    public void switchRows(int rowOne, int rowTwo) {
        if (rowOne > rows() || rowTwo > rows() || rowOne < 0 || rowTwo < 0) {
            throw new IndexOutOfBoundsException("can not change row: " + rowOne + " and row " + rowTwo + " rows ca just be between 0 and" + (rows() - 1));
        }
        if(rowOne == rowTwo){
            return;
        }
        for(int i = 0; i < cols(); i++){
            double save = get(rowOne, i);
            set(rowOne, i, get(rowTwo, i));
            set(rowTwo, i, save);
        }
    }

    /**
     * enlarges the vector size by 1, adds the given Value at the last new field.
     * assumes that it is a vector, doesn't test it!!!!
     *
     * @param newValue - the new end Value.
     */
    public void push(double newValue){
        double[] newMatrix = new double[matrix.length + 1];
        for(int i = 0; i < matrix.length; i++){
            newMatrix[i] = matrix[i];
        }
        this.rows ++;
        newMatrix[matrix.length] = newValue;
        this.matrix = newMatrix;
    }

    /**
     * decreases the vector size by 1, returns the last Value.
     * assumes that it is a vector, doesn't test it!!!!
     *
     * @return -  the last number.
     */
    public double pull(){
        double[] newMatrix = new double[matrix.length - 1];
        for(int i = 0; i < newMatrix.length; i++){
            newMatrix[i] = matrix[i];
        }
        double valueToPull = matrix[matrix.length-1];
        this.matrix = newMatrix;
        this.rows--;
        return valueToPull;
    }

    public boolean equals(Object o) {
        if (o.getClass() != Matrix.class) {
            return false;
        }
        Matrix toCompare = (Matrix) o;
        if (this.rows() != toCompare.rows() || this.cols() != toCompare.cols()) {
            return false;
        }
        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.rows(); j++) {
                if (Math.abs(this.get(i, j) - toCompare.get(i, j)) > lambda) {
                    return false;
                }
            }

        }
        return true;
    }
}
