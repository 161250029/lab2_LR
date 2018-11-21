package model;

import Tool.Matrix;

import java.util.ArrayList;

public class PPT {
    private Matrix matrix;       //PPT预测分析表
    private ArrayList<String> nonterminalList;
    private ArrayList<String> terminalList;

    public PPT(int row , int column) {
        this.matrix = new Matrix(row , column);
        this.nonterminalList = new ArrayList<String>();
        this.terminalList = new ArrayList<String>();
    }

    public void addProduction(int i , int j , Production pro) {
              this.matrix.add(i , j , pro);
        }
        public Production getSentence(int i , int j) {
              return this.matrix.get(i , j);
        }
        public Matrix getMatrix() {
        return this.matrix;
    }

        public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public ArrayList<String> getNonterminalList() {
        return nonterminalList;
    }

    public void setNonterminalList(ArrayList<String> nonterminalList) {
        this.nonterminalList = nonterminalList;
    }

    public ArrayList<String> getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(ArrayList<String> terminalList) {
        this.terminalList = terminalList;
    }
    public int getRow(String X) {
        int index = -1;
        for(int i = 0 ; i < nonterminalList.size() ; i ++) {
            if(X.equals(nonterminalList.get(i))) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int getColumn(String X) {
        int index = -1;
        for(int i = 0 ; i < terminalList.size() ; i ++) {
            if(X.equals(terminalList.get(i))) {
                index = i;
                break;
            }
        }
        return index;
    }

    public boolean isNonterminal(String X) {
        boolean flag= false;
        for(String str : nonterminalList) {
            if(X.equals(str)) {
                flag = true;
            }
        }
        return flag;
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
