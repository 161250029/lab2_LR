package Tool;

import model.Production;

public class Matrix {
    private int row;
    private int column;
    private Production[][] matrix;
    public Matrix(int row , int column) {
         this.row = row;
         this.column = column;
         this.matrix = new Production[row][column];
    }
    public void add(int i, int j, Production production) {
        matrix[i][j] = production;
    }

    public Production get(int i , int j) {
        return matrix[i][j];
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Production[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Production[][] matrix) {
        this.matrix = matrix;
    }
}
