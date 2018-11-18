package Tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTool {
    //public static List<Production>
    public static ArrayList<String> read(String path) throws IOException {
        ArrayList<String> input = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String s = "";
        while ( (s = reader.readLine())!= null) {
            input.add(s);
        }
        return input;
    }
}
