package InputOutput;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	private FileWriter writer;

    public Writer(String filePath) throws IOException {
        writer = new FileWriter(filePath);
    }

    public void writeFile(String line) throws IOException{
    	writer.append(line+"\n");
    }
    
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
