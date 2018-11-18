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
    public boolean containKey(String key) {
        for(String str : right) {
            if(right.equals(key)) {
                return true;
            }
        }
        return false;
    }
    public String toString() {
        return left + "->" + right;
    }

    public String toRight() {
        String str = "";
        for(String s : right) {
            str = str + s;
        }
        return str;
    }
}
