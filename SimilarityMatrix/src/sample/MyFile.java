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

    String getString(){//gets the concat string
        String temp = "";
        for(String word: this.wordEntry){
            temp+=word;
        }
        return temp;
    }
}


