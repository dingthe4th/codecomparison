package sample;
//written by isaiah tupal

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

//  This is the class where I  play around ideas and see if they work
public class Test {

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

    static ArrayList<Integer> toKGrams(String stringCode,int k ){
        ArrayList<Integer> hashSet = new ArrayList<>();
        String temp =""; //concatenates the k grams
        for(int i = 0;i<stringCode.length();i++){
            temp = "";
            for(int x = 0;x<k;x++){
                temp+=stringCode.charAt(i);
            }
            hashSet.add(temp.hashCode());

        }
        return hashSet;
    }

    static ArrayList<int[]> fingerPrint(ArrayList<Integer> h, int w){

        //iterate the entire hashsetint
        int min = 0;//records mins
        ArrayList<int[]> fingerPrints = new ArrayList<>();

        //initial window

        for(int x=w-1;x>0;x--){
            if(h.get(x)<h.get(min)){
                x=min;
            }

        }

        int[] fp = new int[2];
        fp[0] = h.get(min);
        fp[1]=min;
        fingerPrints.add(fp);

        for(int r = w;r<h.size();r++){
            if(min<r-w+1){// r is on the left side already
                min=r;
                for(int i = r-1;i>r-w+1;i--){
                    if(h.get(min)>h.get(i)){
                        min = i;
                    }
                }

                int[] temp = new int[2];
                temp[0] = h.get(min);
                temp[1]=min;
                fingerPrints.add(temp);

            }
            else{
                if(h.get(r)<=h.get(min)){
                    min = r;
                    int[] temp = new int[2];
                    temp[0] = h.get(min);
                    temp[1]=min;
                    fingerPrints.add(temp);
                }
            }


        }


        return  fingerPrints;
        // i is left ipart of window
    }


    static float getSimilarity(ArrayList<int[]> fingerPrint1, ArrayList<int[]> fingerPrint2){

        ArrayList<Integer> fp1IndexSimilarSet = new ArrayList<>(),fp2IndexSimilarSet = new ArrayList<>();
        int occurences = 0;

        for(int i = 0;i<fingerPrint1.size(); i++){//iterates through the entirety of fingerprint1
            for(int j = 0;j<fingerPrint2.size();j++){
                if(fingerPrint1.get(i)[0]==fingerPrint2.get(j)[0]){
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
        ArrayList<int[]> fp1 = fingerPrint(toKGrams(code1,3),100);
        ArrayList<int[]> fp2 = fingerPrint(toKGrams(code2,3),100);
        return getSimilarity(fp1,fp2);

    }


    public static void main(String args[]){
        try {
            ArrayList<int[]> fp1 = fingerPrint(toKGrams(toString("assets//Controller.java"),10),100);
            ArrayList<int[]> fp2 = fingerPrint(toKGrams(toString("assets//Controller2.java"),10),100);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
