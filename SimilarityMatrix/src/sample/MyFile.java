package sample;

import java.io.File;
import java.util.ArrayList;

public class MyFile {
    private File myFile;
    ArrayList<String> wordEntry;            // list of words per file
    ArrayList<String> lineEntry;            // list of lines per file
    ArrayList<String> operatorsList;        // list of operators
    ArrayList<String> operandsList;         // list of operands
    MetricsData metricsData;
    MyFile(File file) {
        this.myFile = file;
        this.wordEntry = new ArrayList<>();
        this.lineEntry = new ArrayList<>();
        this.operatorsList = new ArrayList<>();
        this.operandsList = new ArrayList<>();
        this.metricsData = new MetricsData();
    }

    File getFile() {
        return myFile;
    }

    String getString(){         //gets the concat string
        String temp = "";
        for(String word: this.wordEntry){
            temp+=word;
        }
        return temp;
    }

    void addOperator(String str) {
        this.operatorsList.add(str);
    }

    void addOperand(String str) {
        this.operandsList.add(str);
    }

}


