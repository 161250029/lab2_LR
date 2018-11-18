package service;

import model.PPT;

import java.util.ArrayList;
import java.util.Stack;

public class analyzer {
    Stack<String> NonterminalNum;
    public void start() {
        PPT ppt = new PPT();
        NonterminalNum = new Stack<String>();
        NonterminalNum.push("$");
        NonterminalNum.push("E");          //默认是E
        ArrayList<String> input = new ArrayList<String>();         //输入的词法单元
        input.add("id"); input.add("+");  input.add("id"); input.add("*"); input.add("id"); input.add("$");
        int index = 0;
        String X = "E";
       // while(!input.get(index).equals("$") && !X.equals("$")) {
        while( !X.equals("$")) {
            if(X.equals(input.get(index))) {
                NonterminalNum.pop();
                 index++;
            }
            else if(!X.equals("E")&&!X.equals("E'")&&!X.equals("T")&&!X.equals("F")&&!X.equals("T'")) {
                //X不是非终结符
                System.out.println("报错啦");
                break;
            }
            else if(getcolumn(input.get(index)) == -1 ||ppt.getSentence(ppt.getSentenceRow(X) , getcolumn(input.get(index)))== null) {
                System.out.println("报错啦");
                break;
            }
            else {
                NonterminalNum.pop();
                System.out.println(ppt.getSentence(ppt.getSentenceRow(X) , getcolumn(input.get(index))));
                ArrayList<String> right = ppt.getSentence(ppt.getSentenceRow(X) , getcolumn(input.get(index))).getRight();
                for(int i = right.size()-1 ; i >= 0 ; i--) {
                    if(!right.get(i).equals("ε")) {
                        NonterminalNum.push(right.get(i));
                    }

                }
            }
            X = NonterminalNum.pop();
            NonterminalNum.push(X);
        }
    }

    public int getcolumn(String input) {
             if (input.equals("id")) {
                 return 0;
             }
             else if (input.equals("+")) {
                 return 1;
             }
             else if (input.equals("*")) {
                 return 2;
             }
             else  if (input.equals("(")) {
                 return 3;
             }
             else if (input.equals(")")) {
                 return 4;
             }
             else if (input.equals("$")) {
                 return 5;
             }
             else {
                 return -1;                  //错误
             }
    }
    public static void main(String args[]) {
        analyzer a = new analyzer();
        a.start();
    }
}
