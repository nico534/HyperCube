package matrixLibrary.matrix;

import matrixLibrary.Exceptions.MatrixDontMatchException;
import matrixLibrary.formula.Formula;
import org.apache.commons.lang3.ArrayUtils;

/**
 * A matrix that is stored in a single array.
 */
public class Matrix extends LinearElement {
    private double[] matrix;
    private int rows;
    private int cols;
    private boolean transpose = false;

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
     * @return - a clone of the Matrix.
     */
    public Matrix clone() {

        Matrix copy = new Matrix(ArrayUtils.clone(this.matrix), this.rows);

        //standart is not transposed
        if (transpose) {
            copy.transpose();
        }
        return copy;
    }

    /**
     * add a Formula to the Matrix
     *
     * @param f - a Formula to apply on the matrix
     */
    public void addFormula(Formula f) {
        f.applyFormula(this);
    }


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

    /**
     * prints the matrix, maximum displayed is maxShownRowsCols, if bigger the middle
     * elements won't by shown.
     */
    public void print() {
        System.out.println(toString());
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
    private int getIndex(int row, int col) {
        if (transpose) {
            if (col > (this.rows - 1) || row > (this.cols - 1) || col < 0 || row < 0) {
                throw new IndexOutOfBoundsException("(" + row + "," + col + ") out of bounds for matrix (" + cols() + "," + rows() + ").");
            }
            return col * this.cols + row % this.cols;
        } else {
            if (col > (this.cols) || row > (this.rows) || col < 0 || row < 0) {
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
    public Vector convertToVector() {
        return new Vector(matrix);
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

    protected double[] getArray(){
        return this.matrix;
    }

    /**
     * set the matrix values to a deep copy of mtx values.
     *
     * @param mtx - matrix to deep copy.
     */
    public void deepCopy(Matrix mtx){
        this.matrix = ArrayUtils.clone(mtx.matrix);
        this.rows = mtx.rows;
        this.cols = mtx.cols;
        this.transpose = mtx.transpose;
    }

    /**
     * set the matrix values to a light copy of mtx values.
     *
     * @param mtx - matrix to deep copy.
     */
    public void copy(Matrix mtx){
        this.matrix = mtx.matrix;
        this.rows = mtx.rows;
        this.cols = mtx.cols;
        this.transpose = mtx.transpose;
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
            for (int j = 0; j < this.cols(); j++) {
                if (Math.abs(this.get(i, j) - toCompare.get(i, j)) > lambda) {
                    return false;
                }
            }

        }
        return true;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (rows() > maxShownRowsCols) {
            for (int i = 0; i < maxShownRowsCols / 2; i++) {
                addRow(i, sb);
            }

            addSeparator(" .. ", sb);
            for (int i = rows() - maxShownRowsCols / 2; i < rows(); i++) {
                addRow(i, sb);
            }
        } else {
            for (int i = 0; i < rows(); i++) {
                addRow(i, sb);
            }
        }
        addSeparator("-", sb);
        return sb.toString();
    }

    private void addSeparator(String separator, StringBuilder sb) {
        if (cols > maxShownRowsCols) {
            StringBuilder lineOfSeparators = new StringBuilder();
            while (lineOfSeparators.length() < maxShownRowsCols*shownDigits + (maxShownRowsCols - 2) + 4){
                lineOfSeparators.append(separator);
            }
            sb.append(lineOfSeparators);
        } else {
            StringBuilder lineOfSeparators = new StringBuilder();
            while (lineOfSeparators.length() < cols()*shownDigits + (cols() - 2) + 4){
                lineOfSeparators.append(separator);
            }
            sb.append(lineOfSeparators);
        }
        sb.append("\n");
    }

    private void addRow(int row, StringBuilder sb) {
        if (cols() > maxShownRowsCols) {
            for (int i = 0; i < maxShownRowsCols / 2; i++) {
                sb.append(setFixPrintLength(get(row, i)));
                if (i < maxShownRowsCols / 2 - 1) {
                    sb.append("  ");
                }
            }
            sb.append(" .. ");
            for (int i = cols() - maxShownRowsCols / 2; i < cols(); i++) {
                sb.append(setFixPrintLength(get(row, i)));
                if (i < cols() - 1) {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        } else {
            for (int i = 0; i < cols(); i++) {
                sb.append(setFixPrintLength(get(row, i)));
                if (i < cols() - 1) {
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }
    }
}
