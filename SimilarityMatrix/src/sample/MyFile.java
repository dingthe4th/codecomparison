package sample;

import java.io.File;
import java.util.ArrayList;

public class MyFile {
    private File myFile;
    ArrayList<String> wordEntry;
    ArrayList<String> lineEntry;
    MyFile(File file) {
        this.myFile = file;
        this.wordEntry = new ArrayList<>();
        this.lineEntry = new ArrayList<>();
    }
    File getFile() {
        return myFile;
    }
}


