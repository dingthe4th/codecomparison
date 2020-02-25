package sample;
//written by isaiah tupal

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

//  This is the class where I  play around ideas and see if they work
public class Test {


    static final int k = 6;
    static final int t = 20 ;


    static void hasher(String filePath)throws Exception{    //hashes the code

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



    }// me playing around

    static String toString(String filePath){//reads the code and turns it into a string

        String fileString="";
        try {
            String fileLine;
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((fileLine = br.readLine()) != null) {//reads the file

                fileLine = fileLine.replaceAll("^//(.*)", "");//removes all the characters after // in a line

                fileString += fileLine;


            }

            fileString = fileString.replaceAll("\\s+|/\\\\*.*?\\\\*/|\".*?\"", "");//removes whitespace and special characters
            //first parameter of regex means remove all spaces, second parameter is remove comments while third is remove strings
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return fileString;

    }

    static ArrayList<Integer> toKGramsHash(String stringCode ){

        ArrayList<Integer> hashSet = new ArrayList<>();
        String temp =""; //concatenates the k grams
        for(int i = 0;i<stringCode.length();i++){
            temp = "";
            for(int j = i;j<i+k;j++){
                temp+=stringCode.charAt(j%stringCode.length());
            }
            hashSet.add( temp.hashCode() );
        }
        return hashSet;
    }

    static ArrayList<Integer> fingerPrint(ArrayList<Integer> h){
        int w = t-k+1;
        //iterate the entire hashsetint
        int min = Integer.MAX_VALUE,minIndex=-1;//records mins
        ArrayList<Integer> fingerPrints = new ArrayList<>();

        //initial window

        //got from github

        for (int i = 0; i<h.size();i++ ){ // i contain leftmost index of the window


           if(minIndex == i-1){// the min hash is not oustide of the window
               min = Integer.MAX_VALUE;
               for(int x=i; x<w+i; x++){
                   if( min> h.get( x % h.size()) ){
                       minIndex=x%h.size();
                       min = h.get( x % h.size());
                   }
                   fingerPrints.add(min);
               }
           }
           else{// if previous min hash is still in this window

               if(min>h.get( (i+w)%h.size()   )){// is the right hash smaller than min?
                   min = h.get((i+w)%h.size() );
                   minIndex=(i+w) %h.size();
                   fingerPrints.add(min);
               }
           }
        }




        /*
         WinnowFingerPrint fp = new WinnowFingerPrint();
        int n = input.length;
        int lastMin = -1;

        for (int i = 0; i < n - w + 1; i++) {
            int min = Integer.MAX_VALUE;
            int index = 0;
            for (int j = 0; j < w; j++) {
                if (input[i + j] < min) {
                    min = input[i + j];
                    index = i + j;
                }
            }

            if (lastMin != index) {
                fp.putHash(index, min);
                lastMin = index;
            }
        }
        return fp;
    }
        got from github end

         */

        return  fingerPrints;
        // i is left ipart of window
    }


    static float getSimilarity(ArrayList<Integer> fingerPrint1, ArrayList<Integer> fingerPrint2){

        ArrayList<Integer> fp1IndexSimilarSet = new ArrayList<>(),fp2IndexSimilarSet = new ArrayList<>();
        int occurences = 0;

        for(int i = 0;i<fingerPrint1.size(); i++){//iterates through the entirety of fingerprint1
            for(int j = 0;j<fingerPrint2.size();j++){
                if(fingerPrint1.get(i).equals(fingerPrint2.get(j))){
                    if(fp1IndexSimilarSet.contains(i)||fp2IndexSimilarSet.contains(j)){
                        //if  both indexes are not yet marked as similar to another element, add them to the set and increment occurences
                    }
                    else{

                        fp1IndexSimilarSet.add(i);
                        fp2IndexSimilarSet.add(j);
                        occurences++;
                    }
                }
            }
        }

        int union = fingerPrint1.size()+fingerPrint2.size()-occurences;
        System.out.print("Similarity checked");
        return (float)occurences/union;

    }

    static float getSimilarity(String code1, String code2){
        System.out.println(code1+" compared to "+code2);
        ArrayList<Integer> fp1 = fingerPrint(toKGramsHash(code1));
        ArrayList<Integer> fp2 = fingerPrint(toKGramsHash(code2));
        return getSimilarity(fp1,fp2);

    }


    public static void main(String args[]){
        try {
            String str = System.getProperty("user.dir");
            ArrayList<Integer> fp1 = fingerPrint(toKGramsHash(toString(str+"//SimilarityMatrix//Assets//Controller.java")));
            ArrayList<Integer> fp2 = fingerPrint(toKGramsHash(toString(str+"//SimilarityMatrix//Assets//Controller2.java")));
            System.out.println(fp1);
            System.out.println(fp2);
            System.out.println(getSimilarity(fp1,fp2));
            //System.out.println(toKGrams("THE BIG BROWN FOX JUMP OVER THE LAZY DOG",10));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
