package Tool;

import model.Production;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTool {
    public static ArrayList<String> read(String path) throws IOException {
        ArrayList<String> input = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String s = "";
        while ( (s = reader.readLine())!= null) {
            input.add(s);
        }
        return input;
    }

    public static void write(ArrayList<Production> output , String path) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path, false));
        for(Production p : output) {
            bw.write(p.toString());
            bw.newLine();
        }
        bw.close();
    }
}
