import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LBYCP2D_Exer0 {
    ArrayList<String> list1 = new ArrayList<>();
    ArrayList<String> list2 = new ArrayList<>();
    int count;
    int totalWords;
    float percentage;

    public static void main(String[] args) throws IOException {
        LBYCP2D_Exer0 cf = new LBYCP2D_Exer0();
        System.out.println("Comparing two files...");
        cf.readFile("src\\file1.txt",cf.list1);
        cf.readFile("src\\file2.txt",cf.list2);
        cf.compareLists();
        System.out.println("Similarity Percentage: " + cf.getPercentage() + "%");
        System.out.println("Longest word in FILE 1: " + cf.getLongestWord(cf.list1));
        System.out.println("Longest word in FILE 2: " + cf.getLongestWord(cf.list2));
    }

    public void readFile(String filename, ArrayList<String> list) throws IOException {
        File file = new File(filename);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            line = line.replaceAll("[.(]"," ");
            String[] words = line.split(" ");
            for(String word : words) {
                word = word.replaceAll("\\W", "");
                list.add(word.trim());
            }
        }
        br.close();
    }

    public void compareLists() {
        System.out.println("LIST 1 Size: " + list1.size());
        System.out.println("LIST 2 Size: " + list2.size());
        if(list1.size() > list2.size()) {
            for(String word: list2)
                for(String word2 : list1)
                    if(word2.equalsIgnoreCase(word)) count++;
            totalWords = list1.size();
        }

        else {
            for(String word: list1)
                for(String word2: list2)
                    if(word2.equalsIgnoreCase(word)) count++;
            totalWords = list2.size();
        }

        System.out.println("WORD MATCH COUNT " + count);
        System.out.println("TOTAL WORDS " + totalWords);
        percentage = (float) count/totalWords * 100;
    }

    public float getPercentage() {
        if (percentage > 100) return 100;
        else return percentage;
    }

    public String getLongestWord(List<String> list) {
        String test = "";
        for(String word : list) {
            if(test.length() < word.length()) {
                test = word;
            }
        }
        return test;
    }

    /* Isaiah's Method
    static ArrayList<String> codeList1= new ArrayList<>(), codeList2 = new ArrayList<>();

    static void getFileToList(String pathName,ArrayList codeList){

         File file = new File(pathName);//open file
         BufferedReader br;
         try {

             br = new BufferedReader(new FileReader(file));
             String line;
             while ((line = br.readLine()) != null) {//iterates through the file until endfile
                 ArrayList<String> lineList = new ArrayList<>(Arrays.asList(line.split("[ \\t\\n]+")));
                 codeList.addAll(lineList);
             }

         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    static void getSimilarity(){
        int similarOccurence=0;
        int i;
        for(i = 0;i<codeList1.size()&&i<codeList2.size();i++){
            if(codeList1.get(i).equals(codeList2.get(i) )){
                similarOccurence++;
            }
        }
        System.out.println(" Similarity between two files are: "+(float)similarOccurence/i);
    }

     */
}
