package model;

import Tool.FileTool;

import java.io.IOException;
import java.util.ArrayList;

public class CreatePPT {
    ArrayList<String> input;
    ArrayList<String> nonterminalList;
    ArrayList<String> terminalList;

    public CreatePPT(ArrayList<String> input, ArrayList<String> nonterminalList, ArrayList<String> terminalList) {
        this.input = input;
        this.nonterminalList = nonterminalList;
        this.terminalList = terminalList;
    }

    public CreatePPT(String path) throws IOException {
        this.input = FileTool.read(path);
        this.nonterminalList = new ArrayList<String>();
        this.terminalList = new ArrayList<String>();
    }

    public PPT create() {
        eliminate();
        for(String str: input) {
            String[] split = str.split("->");
            String left = split[0];
             //非终结符
                 if(!isExistInNonterminal(left)) {
                     nonterminalList.add(left);
                 }
        }
        for(String str: input) {
            //终结符
            String[] split = str.split("->");
            String right = split[1];
            for(int i = 0 ; i < right.length() ; i ++) {
                if(i<right.length()-1 && right.substring(i , i+2).equals("id")) {
                    terminalList.add("id");
                    i++;
                }
                else if(!isExistInterminal(right.substring(i , i+1)) && !isExistInNonterminal(right.substring(i , i+1)) && !right.substring(i , i+1).equals("'")) {
                    terminalList.add(right.substring(i , i+1));
                }
            }
        }
                terminalList.remove("ε");
                terminalList.add("$");
        PPT ppt = new PPT(nonterminalList.size() , terminalList.size());
        for(int i = 0 ; i < input.size(); i++) {
            Production temp = InputToProduction(input.get(i));
            ArrayList<String> line = new ArrayList<String>();
            if(temp.getRight().get(0).equals("ε")) {
                follow(temp.getLeft() , line);
            }
            else {
                first(temp.getLeft() , line);
            }
            for(String s : line) {                                                             //对该语句构造符号表
                ppt.addProduction(getRow(temp.getLeft()) , getColumn(s) , temp);
            }
        }
        ppt.setNonterminalList(nonterminalList);
        ppt.setTerminalList(terminalList);

//        System.out.println(nonterminalList);
//        System.out.println(terminalList);
//        ppt.print();
        return ppt;
    }

    public void eliminate() {
        for(String str : input) {
            String[] split = str.split("->");
            String left = split[0];
            String right = split[1];
            if(left.equals(right.substring(0,1))) {
                ArrayList<String> sameLeft = new ArrayList<String>();
                for(String s : input) {
                    String[] spl = s.split("->");
                    String l = spl[0];
                    if(l.equals(left)) {
                        sameLeft.add(s);
                    }
                }
                input.remove(str);
                input.add(left + "->" +sameLeft.get(1).split("->")[1] + left + "'");
                input.add(left + "'" + "->" + sameLeft.get(0).split("->")[1].substring(1));
                input.add(left + "'" + "->" + "ε");
            }
        }
    }

    public ArrayList<String> first(String X ,ArrayList<String> line){
        ArrayList<Production> target = getLeft(X);
        for(Production p : target) {
            if(p.getRight().size()>1&&p.getRight().get(0).equals("ε")) {

            }
            else if(p.getRight().size()>=1&&!isExistInNonterminal(p.getRight().get(0)) && isExistInterminal(p.getRight().get(0))) {
                line.add(p.getRight().get(0));
                if(p.getRight().get(0).equals("i")) {
                    line.remove(p.getRight().get(0));
                    line.add(p.getRight().get(0)+p.getRight().get(1));
                }
            }
            else {
                if(p.getRight().size()>=2 && p.getRight().get(1).equals("'")) {
                     first(p.getRight().get(0)+p.getRight().get(1) , line);
                }
                else {
                    if(p.getRight().size()>=1) {
                        first(p.getRight().get(0) , line);
                    }
                }
            }
        }
        return line;
    }

    public ArrayList<String> follow(String X , ArrayList<String> line) {        //符号表
        ArrayList<Production> right = getRight(X);
        for(Production p : right) {
            if(X.equals(nonterminalList.get(0)))  {
                //开始符
                line.add("$");
                int index = getIndex(X,p.getRight());
                if(index == -1) {
                    if(!p.getLeft().equals(X)) {                    //防止死循环
                        follow(p.getLeft() , line);
                    }
                }
                else if (!isExistInNonterminal(p.getRight().get(index)) && isExistInterminal(p.getRight().get(index))) {
                    line.add(p.getRight().get(index));
                }
                else {
                    ArrayList<String> newLine = new ArrayList<String>();
                    for(String s : first(p.getRight().get(index) , newLine)) {
                        //会不会在first那里停下来
                        line.add(s);
                    }
                    if(hasNull(p.getRight().get(index))) {
                        follow(p.getLeft() , line);
                    }
                 }
                }
            else {
                int index = getIndex(X,p.getRight());
                if(index == -1) {
                    if(!p.getLeft().equals(X)) {                    //防止死循环
                        follow(p.getLeft() , line);
                    }
                }
                else if (!isExistInNonterminal(p.getRight().get(index)) && isExistInterminal(p.getRight().get(index))) {
                    line.add(p.getRight().get(index));
                }
                else {
                    ArrayList<String> newLine = new ArrayList<String>();
                    for(String s : first(p.getRight().get(index) , newLine)) {
                        //会不会在first那里停下来
                        line.add(s);
                    }
                    if(hasNull(p.getRight().get(index))) {
                        follow(p.getLeft() , line);
                    }
                }
            }
        }
        return line;
    }


    public boolean isExistInNonterminal(String str) {
        boolean flag = false;
        for(String temp : nonterminalList) {
            if(str.equals(temp)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private boolean isExistInterminal(String str) {
        boolean flag = false;
        for(String temp : terminalList) {
            if(str.equals(temp)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public boolean hasNull(String X) {
        boolean flag = false;
        ArrayList<Production> left = getLeft(X);
        for(Production p : left) {
            if(p.getRight().get(0).equals("ε")) {
                flag = true;
            }
        }
        return flag;
    }

    public ArrayList<Production> getLeft(String X) {
        ArrayList<Production> all = invertToProduction();
        ArrayList<Production> result = new ArrayList<Production>();
        for(Production pro : all) {
            if(pro.getLeft().equals(X)) {
                result.add(pro);
            }
        }
        return result;
    }


    public ArrayList<Production> getRight(String X) {
        ArrayList<Production> all = invertToProduction();
        ArrayList<Production> result = new ArrayList<Production>();
        for(Production pro : all) {
              if(isExistsInRight(pro , X)) {
                  result.add(pro);
              }
        }
        return result;
    }

    public int getIndex(String X , ArrayList<String> right) {
          int index = -1;
          for(int i = 0 ; i < right.size() ; i ++) {
              if(X.equals(right.get(i))&&i < right.size() - 1) {
                  index = i+1;
              }
          }
          return index;
    }

    public boolean isExistsInRight(Production pro , String X) {
        boolean flag = false;
        for(String s : pro.getRight()) {
            if(s.equals(X)) {
                 flag = true;
                 break;
            }
        }
        return flag;
    }

    public ArrayList<Production> invertToProduction() {
        ArrayList<Production> result = new ArrayList<Production>();
        for(String s : input) {
            String [] split = s.split("->");
            String left  = split[0];
            String right = split[1];
            ArrayList<String> realright = new ArrayList<String>();
            for(int i = 0; i < right.length() ; i ++) {
                if(i<right.length()-1 && right.substring(i , i+2).equals("id")) {
                    realright.add("id");
                    i++;
                }
                else if(i<right.length()-1&&right.substring(i+1 , i+2).equals("'")) {
                    realright.add(right.substring(i,i+2));
                    i++;
                }
                else {
                    realright.add(right.substring(i,i+1));
                }
            }
            result.add(new Production(left , realright));
        }
        return result;
    }

    public Production InputToProduction(String s) {
        String [] split = s.split("->");
        String left  = split[0];
        String right = split[1];
        ArrayList<String> realright = new ArrayList<String>();
        for(int i = 0; i < right.length() ; i ++) {
            if(i<right.length()-1 && right.substring(i , i+2).equals("id")) {
                realright.add("id");
                i++;
            }
            else if(i<right.length()-1&&right.substring(i+1 , i+2).equals("'")) {
                realright.add(right.substring(i,i+2));
                i++;
            }
            else {
                realright.add(right.substring(i,i+1));
            }
        }
            return  new Production(left , realright);
    }

    public int getRow(String X) {
        int index = -1;
        for(int i = 0 ; i < nonterminalList.size() ; i ++) {
            if(X.equals(nonterminalList.get(i))) {
                index = i;
            }
        }
        return index;
    }
    public int getColumn(String X) {
        int index = -1;
        for(int i = 0 ; i < terminalList.size() ; i ++) {
            if(X.equals(terminalList.get(i))) {
                index = i;
            }
        }
        return index;
    }
}
