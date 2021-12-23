# Local Code Similarity Checker with Winnowing Algorithm in JavaFX

String toString(String filepath) 

        turns file into a string without whitespace or comments 


ArrayList toKGrams(String stringcode, k) 

        k is the k grams of a letter (the bigger the k, the bigger the chunk of the file will be used)
        just use k=4 for now
        kgrams is a hash of k grams
        
ArrayList fingerPrint(ArrayList<Integer> h, int w) 
        
        h is what you get from toKGrams
        w=10 for now
        returns fingerprint

float getSimilarity(fingerprint1, fingerprint2)
      use the fingerprint() to get fingerprint and use it here to find the similarity of the two documents
    
private static final String initialDirectory = "D:\\LBYCP2D_Collab\\SimilarityMatrix\\src";
    set as the initial directory of the program || change if you are using different PC
 

      
  

