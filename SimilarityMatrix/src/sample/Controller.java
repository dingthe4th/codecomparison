package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable {;
    /* initial directory of the program || change if you are using different PC */
    private static final String initialDirectory = "D:\\LBYCP2D_Collab\\SimilarityMatrix\\src";
    public TableView<Top10> tableView;
    @FXML private TableColumn<Top10, String> table_col1;
    @FXML private TableColumn<Top10, String> table_col2;
    @FXML private TableColumn<Top10, Float> table_col3;
    @FXML ScrollPane scrollPane = new ScrollPane();             // parent of grid pane
    @FXML Label prompt;                                         // program status
    @FXML Label fileCountLabel;                                 // display file count in submission folder
    @FXML ListView<String> listView;                            // list view for GUI
    GridPane gridPane;                                          // for matrix display
    ArrayList<MyFile> filesDirectory;                           // list for files
    ArrayList<String> operatorsMainList;                        // list for operators from a text file
    ArrayList<Top10> top10Score;                                // list the top 10 similarity scorer
    File folder;                                                // submission folder
    ObservableList<Top10> top10ObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        top10ObservableList = FXCollections.observableArrayList();
        tableView.setItems(top10ObservableList);
        table_col1.setCellValueFactory(new PropertyValueFactory<>("file_1"));
        table_col2.setCellValueFactory(new PropertyValueFactory<>("file_2"));
        table_col3.setCellValueFactory(new PropertyValueFactory<>("score"));
    }

    @FXML public void getFolder() throws IOException {
        filesDirectory = new ArrayList<>();
        DirectoryChooser folderChooser = new DirectoryChooser();
        folderChooser.setInitialDirectory(new File(initialDirectory));
        folder = folderChooser.showDialog(null);
        prompt.setText("Folder selected: " + folder.getName());
        listView.getItems().clear();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            MyFile temp = new MyFile(file);
            System.out.println(file.getName());
            filesDirectory.add(temp);
            listView.getItems().add(temp.getFile().getName());
        }
        fileCountLabel.setText(filesDirectory.size() + " files found.");

        for (MyFile myFile : filesDirectory) {
            constructList(myFile.getFile(),myFile.wordEntry,myFile.lineEntry);
        }
        getOperatorsMainList();
        getMetrics();

        top10Score = new ArrayList<>();
        // initialize top10Scores for tableViewing
        for(int i=0; i<10; i++) {
            Top10 temp = new Top10("DEFAULT1", "DEFAULT2",0);
            top10Score.add(temp);
        }
        top10ObservableList.clear();
        // ends init
    }       // gets the submission folder
    @FXML public void showMatrix() throws IOException {
        gridPane = new GridPane();
        if(folder == null) {
            prompt.setText("Please select submission folder first.");
            return;
        }
        scrollPane.setContent(gridPane);
        scrollPane.setLayoutX(13);
        scrollPane.setLayoutY(226);
        getMatrix();
    }       // showMatrix
    public void getMatrix() {
        DataEntry[][] dataEntries = new DataEntry[filesDirectory.size()][filesDirectory.size()];

        // DISPLAYS NAME : LEFT -- verified
        for(int i = 0; i<filesDirectory.size(); i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setMaxSize(50,30);
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            gridPane.add(sp,0,i+1);
        }

        // DISPLAYS DATA -- verified
        for(int i = 0; i<filesDirectory.size(); i++) {

            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setMaxSize(50,30);
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            for (int j = 0; j < filesDirectory.size(); j++) {
                if (j == 0) {
                    gridPane.add(sp, i+1, 0);
                    float a =  Test.getSimilarity(filesDirectory.get(i).getString(),filesDirectory.get(j).getString());
                    dataEntries[i][j] = new DataEntry((float) Math.round(a * 100) / 100);
                    gridPane.add(dataEntries[i][j].getStackPane(), i+1, j+1);
                    Top10 entry1 = new Top10(filesDirectory.get(i).getFile().getName(),filesDirectory.get(j).getFile().getName(),a);
                    if(a!=1 && !isAlreadyTop10(entry1)) updateTop10List(entry1);
                }
                else {
                    float a =  Test.getSimilarity(filesDirectory.get(i).getString(),filesDirectory.get(j).getString());
                    dataEntries[i][j] = new DataEntry((float) Math.round(a * 100) / 100);
                    gridPane.add(dataEntries[i][j].getStackPane(), i+1, j+1);
                    Top10 entry1 = new Top10(filesDirectory.get(i).getFile().getName(),filesDirectory.get(j).getFile().getName(),a);
                    if(a!=1 && !isAlreadyTop10(entry1)) updateTop10List(entry1);
                }
            }
            gridPane.getRowConstraints().add(new RowConstraints(30));
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        }

        // update observable Top 10 list in TableView
        for(Top10 entry : top10Score) {
            top10ObservableList.add(entry);
        }
        // End of update

        // DISPLAYS NAME : BOTTOM -- verified
        for(int i = 0; i<filesDirectory.size(); i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setMaxSize(50,30);
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            gridPane.add(sp,i+1,filesDirectory.size()+1);
        }

        // DISPLAYS NAME : RIGHT -- verified
        for(int i = 0; i<filesDirectory.size(); i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);

            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setMaxSize(50,30);
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            gridPane.add(sp,filesDirectory.size()+1,i+1);
        }
    }                                   // get matrix values

    // update Top 10 values
    public void updateTop10List(Top10 entry1) {
        for(int k=0; k<top10Score.size(); k++) {
            if((entry1.getScore() > top10Score.get(k).getScore()) && !top10Score.contains(entry1)) {
                System.out.println("I GOT HERE BOY");
                top10Score.remove(top10Score.size()-1);
                top10Score.add(k,entry1);
            }
        }
    }

    /*
    * Checks if same entry is in the list of Top 10
    * This is to avoid the existence of a similar entry within the top 10 list.
    * =====================
    * Example:
    * FILE A, FILE B, SCORE
    * a.java, b.java, 1.0
    * b.java, a.java, 1.0
    * =====================
    */
    public boolean isAlreadyTop10(Top10 entry) {
        boolean similar = false;
        for(Top10 entries : top10Score) {
            boolean flag1 = entry.getFile_1().equals(entries.getFile_2());
            boolean flag2 = entry.getFile_2().equals(entries.getFile_1());
            if(flag1 && flag2) {
                similar = true;
                break;
            }
        }
        return similar;
    }

    /* generate list per submission entry
    *   PER LINE
    *   PER WORD
    */
    public void constructList(File file, ArrayList<String> wordList, ArrayList<String> lineList) throws IOException {
        if(file.isDirectory()) {
            for(File entry : Objects.requireNonNull(file.listFiles())) {
                constructList(entry,wordList,lineList);
            }
        }
        else if (file.getName().toLowerCase().endsWith(".cpp") || file.getName().toLowerCase().endsWith(".java")) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                lineList.add(line);
                line = line.replaceAll("[.(]"," ");
                String[] words = line.split(" ");
                for(String word : words) {
                    word = word.replaceAll("\\W", "");
                    wordList.add(word.trim());
                }
                for(int i=0 ; i < wordList.size() ; i++) {
                    if(wordList.get(i).equals("")) wordList.remove(i);
                }
            }
            br.close();
        }
    }
    /*
    * OLD FUNCTION FOR COMPARING 2 FILES
    */
    public float compare(ArrayList<String> list1, ArrayList<String> list2) {
        int totalWords, count;
        ArrayList<String> temp1 = new ArrayList<>(list1);
        ArrayList<String> temp2 = new ArrayList<>(list2);

        System.out.println(temp1);
        System.out.println(temp2);

        if(temp1.size() > temp2.size()) {
            System.out.println("TEMP 1 BIGGER");
            System.out.println("TEMP1 " + temp1.size());
            System.out.println("TEMP2 " + temp2.size() + "\n");
            totalWords = temp1.size();
            temp2.retainAll(temp1);
            count = temp2.size();
        }

        else {
            System.out.println("TEMP 2 BIGGER");
            System.out.println("TEMP1 " + temp1.size());
            System.out.println("TEMP2 " + temp2.size() + "\n");
            totalWords = temp2.size();
            temp1.retainAll(temp2);
            count = temp1.size();
        }

        return (float) count/totalWords;
    }   // used in Algorithm per word
    @FXML public void showMetrics() {
        gridPane = new GridPane();
        if(folder == null) {
            prompt.setText("Please select submission folder first.");
            return;
        }
        scrollPane.setContent(gridPane);
        updateMetrics();

        // DISPLAY DATA
        for(int i=0; i< filesDirectory.size(); i++) {
            gridPane.add(filesDirectory.get(i).metricsData.getDifficultyPane(),1,i+2);
            gridPane.add(filesDirectory.get(i).metricsData.getEffortPane(),2,i+2);
            gridPane.add(filesDirectory.get(i).metricsData.getLengthPane(),3,i+2);
            gridPane.add(filesDirectory.get(i).metricsData.getLevelPane(),4,i+2);
            gridPane.add(filesDirectory.get(i).metricsData.getVocabolaryPane(),5,i+2);
            gridPane.add(filesDirectory.get(i).metricsData.getVolumePane(),6,i+2);
        }


        // DISPLAY NAME : LEFT
        for(int i = 0; i<filesDirectory.size(); i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            gridPane.add(sp,0,i+2);
        }

        for(int i = 1; i<7; i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label();
            StackPane sp = new StackPane(rect,label);
            switch(i) {
                case 1:
                    label.setText("Difficulty");
                    break;
                case 2:
                    label.setText("Effort");
                    break;
                case 3:
                    label.setText("Length");
                    break;
                case 4:
                    label.setText("Level");
                    break;
                case 5:
                    label.setText("Vocabulary");
                    break;
                case 6:
                    label.setText("Volume");
                    break;
            }
            gridPane.add(sp,i,0);
        }

    }                       // show the metrics matrix
    public void updateMetrics() {
        for(int i = 0; i<filesDirectory.size(); i++) {
            double uniqueOperators = 0;                                // gets the number of unique operators
            double uniqueOperands = 0;                                 // gets the number of unique operands
            double totalOperators = 0;                                 // gets the total number of operators
            double totalOperands = 0;                                  // gets the total number of operands
            ArrayList<String> foundOperators = new ArrayList<>();      // store the operators that is already found per file
            ArrayList<String> foundOperands = new ArrayList<>();       // store the operands that is already found per file

            for(String operator: filesDirectory.get(i).operatorsList) {
                if(!foundOperators.contains(operator)) {
                    uniqueOperators++;
                    totalOperators++;
                    foundOperators.add(operator);
                }
                else if(foundOperators.contains(operator)) totalOperators++;
            }
            for(String operand: filesDirectory.get(i).operandsList) {
                if(!foundOperands.contains(operand)) {
                    uniqueOperands++;
                    totalOperands++;
                    foundOperands.add(operand);
                }
                else if(foundOperands.contains(operand)) totalOperands++;
            }

            System.out.println("Unique Operands: " +uniqueOperands);
            System.out.println("Unique Operators: " +uniqueOperators);
            System.out.println("Total Operands: " +totalOperands);
            System.out.println("Total Operators: " +totalOperators);
            System.out.println(foundOperands);
            System.out.println(foundOperators);

            filesDirectory.get(i).metricsData.setProgramVocabolary(Math.round(uniqueOperands + uniqueOperators));
            filesDirectory.get(i).metricsData.setProgramLength(Math.round(totalOperands + totalOperators));
            filesDirectory.get(i).metricsData.setProgramVolume(Math.round(filesDirectory.get(i).metricsData.programLength *
                    Math.log(filesDirectory.get(i).metricsData.programVocabolary)));
            double temp = (2*uniqueOperands) / (uniqueOperators*totalOperands);
            filesDirectory.get(i).metricsData.setProgramLevel((double)Math.round(temp*1000)/1000);
            filesDirectory.get(i).metricsData.setProgramDifficulty(Math.round((uniqueOperators*totalOperands) / (2*uniqueOperands)));
            filesDirectory.get(i).metricsData.setProgramEffort(Math.round(filesDirectory.get(i).metricsData.programVolume *
                    filesDirectory.get(i).metricsData.programDifficulty));
        }
    }                           // get metrics score
    public void getMetrics() {
        boolean commentDetected = false;
        for(int i = 0; i<filesDirectory.size(); i++) {
            for (String line : filesDirectory.get(i).lineEntry) {
//                System.out.println("LINE " + line);
                // detects if line contains a // comment
                if(matchCount(line,"//") != 0) {
                    try {
                        line = line.substring(0,line.indexOf('/')-1);
                    }
                    catch (StringIndexOutOfBoundsException e) {
                        line = line.substring(0,line.indexOf('/'));
                    }
                }
                // detects if line contains a /* */ comment
                if(matchCount(line,"/*") != 0 && matchCount(line, "*/") != 0) continue;
                // detects if line contains a /* comment
                if(matchCount(line,"/*") != 0 && !commentDetected) {
                    commentDetected = true;
                    continue;
                }
                // detects if line contains a */ comment
                if((commentDetected) && (matchCount(line,"*/") == 0)) continue;
                else commentDetected = false;
                for (String pattern : operatorsMainList) {
                    int a = matchCount(line, pattern);
                    if(pattern.equals(".")) {
                        line = line.replaceAll("[.]"," ");
                    }
                    if(pattern.equals("(")) {
                        line = line.replaceAll("[(]"," ");
                    }
                    if (a!=0) {
                        for(int j=0; j<a; j++)
                            filesDirectory.get(i).addOperator(pattern);
                    }
                }
                String[] operandsCandidate = line.split(" ");
                for(String words : operandsCandidate) {
                    words = words.replaceAll("\\W", " ");
                    words = words.trim();
                    if(!words.equals(" ") && !words.equals(""))
//                        System.out.println("WORDS: " + words.trim());
                        filesDirectory.get(i).addOperand(words.trim());
                }
            }
        }
    }
    // get list of operators and operands
    int matchCount(String str, String target) {
        Pattern p = Pattern.compile(target, Pattern.LITERAL);
        Matcher m = p.matcher(str);
        int count = 0;
        while(m.find()) count++;
        return  count;
    }               // returns the frequency of a substring in a string
    void getOperatorsMainList() throws IOException {
        File file = new File("D:\\LBYCP2D_Collab\\SimilarityMatrix\\src\\sample\\list_operators.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        operatorsMainList = new ArrayList<>();
        while((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            for(String word : words) {
                operatorsMainList.add(word.trim());
            }
        }
        System.out.println(operatorsMainList);
        System.out.println("MAINLISTOPERATORS" + operatorsMainList.size());
    }           // get the list of operators from a text file
    public void exitProgram() { System.exit(0); }
}
