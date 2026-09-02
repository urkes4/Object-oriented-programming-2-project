package InputOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import Errors.*;

public class Reader {
	
    private BufferedReader reader;
    private String currentLine;

    public Reader(String filePath) throws IOException,FileNotFound {
    	File f = new File (filePath);
    	if(!f.exists())	throw new FileNotFound(filePath);
        reader = new BufferedReader(new FileReader(filePath));
        
    }

    public String readLine() throws IOException {
        currentLine = reader.readLine();
        return currentLine;
    }

    public boolean isEndOfFile() {
        return currentLine == null;
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}