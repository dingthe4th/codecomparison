package sample;
//written by isaiah tupal

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

//  This is the class where I  play around ideas and see if they work
public class Test {

    static void hasher(String filePath)throws Exception{//hashes the code

        String fileString = "",fileLine;
        HashSet<Integer> listofGrams = new HashSet<>();

        File file = new File(filePath);
        FileInputStream fis= new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        while((fileLine=br.readLine()) != null){//reads the file

            fileLine = fileLine.replaceAll("^//(.*)","");//removes all the characters after // in a line

            fileString+=fileLine;


        }

        fileString = fileString.replaceAll("\\s+|/\\\\*.*?\\\\*/|\".*?\"","");//removes whitespace and special characters
        //first parameter of regex means remove all spaces, second parameter is remove comments while third is remove strings

        String temp ="";

        //make hashes of k grams
        for(int i = 0; i<fileString.length(); i++){
            if( (i+1) %10 == 0) {
                listofGrams.add(temp.hashCode());
                temp="";//empty temp
            }
            else
                temp = temp+fileString.charAt(i);

        }

        //
        for(int i = 0; i<listofGrams.size(); i++){
            if( (i+1) %10 == 0) {
                listofGrams.add(temp.hashCode());
                temp="";//empty temp
            }
            else
                temp = temp+fileString.charAt(i);

        }



    }

    public static void main(String args[]){
        try {
            hasher("Assets//Controller.java");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
