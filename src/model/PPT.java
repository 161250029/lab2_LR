package model;

import Tool.Matrix;

import java.util.ArrayList;

public class PPT {
    private Matrix matrix;       //PPT预测分析表

    public PPT() {
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("T"); list1.add("E'");
       Production production1 = new Production("E" , list1);
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("+"); list2.add("T"); list2.add("E'");
       Production production2 = new Production("E'" , list2);
        ArrayList<String> list3 = new ArrayList<String>();
        list3.add("ε");
       Production production3 = new Production("E'" , list3);
        ArrayList<String> list4 = new ArrayList<String>();
        list4.add("F"); list4.add("T'");
       Production production4 = new Production("T" , list4);
        ArrayList<String> list5 = new ArrayList<String>();
        list5.add("*"); list5.add("F"); list5.add("T'");
       Production production5 = new Production("T'" , list5);
        Production production6 = new Production("T'" , list3);
        ArrayList<String> list6 = new ArrayList<String>();
        list6.add("("); list6.add("E"); list6.add(")");
        Production production7 = new Production("F" , list6);
        ArrayList<String> list7 = new ArrayList<String>();
        list7.add("id");
        Production production8 = new Production("F" , list7);

        matrix = new Matrix(5 , 6);
        matrix.add(0,0 , production1);
        matrix.add(0,3 , production1);
        matrix.add(1 , 1, production2);
        matrix.add(1, 4, production3);
        matrix.add(1 , 5 , production3);
        matrix.add(2, 0 , production4);
        matrix.add(2, 3 , production4);
        matrix.add(3, 1 , production6);
        matrix.add(3, 4 , production6);
        matrix.add(3, 5 , production6);
        matrix.add(3, 2 , production5);
        matrix.add(4 , 0 , production8);
        matrix.add(4 , 3 , production7);
        }

    public PPT(int row , int column) {
        this.matrix = new Matrix(row , column);
    }

    public void addProduction(int i , int j , Production pro) {
              this.matrix.add(i , j , pro);
        }
        public Production getSentence(int i , int j) {
              return this.matrix.get(i , j);
        }
        public int getSentenceRow(String X) {
        return this.matrix.getSentenceRow(X);
    }
        public Matrix getMatrix() {
        return this.matrix;
    }

        public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
    public void print() {
        for(int i = 0 ; i < matrix.getRow() ; i++) {
            for (int j = 0 ; j < matrix.getColumn() ; j ++) {
                if(getSentence(i , j)!= null) {
                    System.out.println(i + " " + j + " " + matrix.get(i , j).toString());
                }
            }
        }
    }
}
