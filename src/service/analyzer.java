package service;

import Tool.FileTool;
import model.CreatePPT;
import model.PPT;
import model.Production;
import model.error;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class analyzer {
    Stack<String> NonterminalNum;                                  //符号栈
    ArrayList<Production> output ;
    public void start(String CFGPath , String token_inputPath , String outputPath) throws IOException {
        CreatePPT create = new CreatePPT(CFGPath);
        PPT ppt = create.create();
        NonterminalNum = new Stack<String>();
        NonterminalNum.push("$");
        NonterminalNum.push(ppt.getNonterminalList().get(0));          //开始符
        output = new ArrayList<Production>();
        ArrayList<String> read = FileTool.read(token_inputPath);         //输入的词法单元
        ArrayList<String> input = new ArrayList<String>();
        String[] split = read.get(0).split(" ");
        for(int i = 0; i < split.length ; i ++) {
            input.add(split[i]);
        }
        input.add("$");               //输入结束符
        int index = 0;
        String X = ppt.getNonterminalList().get(0);
        boolean flag = true;
        String errorToken = "";
       // while(!input.get(index).equals("$") && !X.equals("$")) {
        while(!X.equals("$")) {                           //必须得给输入文法提供一个$结束符号,不然会在第一个判断语句那里报数据溢出的错误
            if(X.equals(input.get(index))) {
                NonterminalNum.pop();
                 index++;
            }
            else if(!ppt.isNonterminal(X)) {
                //X不是非终结符
                System.out.println("报错啦");
                flag = false;
                output = new ArrayList<Production>();
                errorToken = input.get(index);
                break;
            }
            else if(ppt.getColumn(input.get(index)) == -1 ||ppt.getSentence(ppt.getRow(X) , ppt.getColumn(input.get(index)))== null) {
                System.out.println("报错啦");
                flag = false;
                output = new ArrayList<Production>();
                errorToken = input.get(index);
                break;
            }
            else {
                NonterminalNum.pop();
                output.add(ppt.getSentence(ppt.getRow(X) , ppt.getColumn(input.get(index))));
                System.out.println(ppt.getSentence(ppt.getRow(X) , ppt.getColumn(input.get(index))));
                ArrayList<String> right = ppt.getSentence(ppt.getRow(X) , ppt.getColumn(input.get(index))).getRight();
                for(int i = right.size()-1 ; i >= 0 ; i--) {
                    if(!right.get(i).equals("ε")) {
                        NonterminalNum.push(right.get(i));
                    }

                }
            }
            X = NonterminalNum.pop();
            NonterminalNum.push(X);
        }
        if(flag) {
            FileTool.write(output , outputPath);
        }
        else {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, false));
            bw.write(new error("语法分析token序列出错" , errorToken).toString());
            bw.close();
        }
    }

    public static void main(String args[]) throws IOException {
        analyzer a = new analyzer();
        a.start("CFG_input" , "input_test2" , "output_result2");
    }
}
