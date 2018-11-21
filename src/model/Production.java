package model;

import java.util.ArrayList;

public class Production {
    private String  left;               //左部
    private ArrayList<String> right;    //右部


    public Production(String left, ArrayList<String> right) {
        this.left = left;
        this.right = right;
    }

    public Production() {
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public ArrayList<String> getRight() {
        return right;
    }

    public void setRight(ArrayList<String> right) {
        this.right = right;
    }
    public String toString() {
        return left + "->" + toRight();
    }

    public String toRight() {
        String str = "";
        for(String s : right) {
            str = str + s;
        }
        return str;
    }
}
