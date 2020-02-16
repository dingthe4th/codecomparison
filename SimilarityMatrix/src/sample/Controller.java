package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Controller {;
    @FXML ScrollPane scrollPane = new ScrollPane();
    @FXML Label prompt;                                         // program status
    @FXML Label fileCountLabel;
    @FXML ListView<String> listView;
    GridPane gridPane;                      // for matrix display
    ArrayList<MyFile> filesDirectory;
    File folder;                                                // submission folder

    @FXML public void getFolder() throws IOException {
        filesDirectory = new ArrayList<>();
        DirectoryChooser folderChooser = new DirectoryChooser();
        folderChooser.setInitialDirectory(new File("Src\\sample"));
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
    }
    @FXML public void showMatrix() throws IOException {
        gridPane = new GridPane();
        if(folder == null) {
            prompt.setText("Please select submission folder first.");
            return;
        }
        scrollPane.setContent(gridPane);
        scrollPane.setLayoutX(0);
        scrollPane.setLayoutY(120);
        getMatrix(folder);
    }
    public void getMatrix(File folder) throws IOException {
        DataEntry[][] dataEntries = new DataEntry[filesDirectory.size()][filesDirectory.size()];
        for (MyFile myFile : filesDirectory) {
            constructList(myFile.getFile(),myFile.wordEntry,myFile.lineEntry);
        }
        for(int i = 0; i<filesDirectory.size(); i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            gridPane.add(sp,0,i+1);
        }
        for(int i = 0; i<filesDirectory.size(); i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            for (int j = 0; j < filesDirectory.size(); j++) {
                if (j == 0) {
                    gridPane.add(sp, i+1, 0);
                    float a = compare(filesDirectory.get(i).wordEntry, filesDirectory.get(j).wordEntry);
                    dataEntries[i][j] = new DataEntry((float) Math.round(a * 100) / 100);
                    gridPane.add(dataEntries[i][j].getStackPane(), i+1, j+1);
                }
                else {
                    float a = compare(filesDirectory.get(i).wordEntry, filesDirectory.get(j).wordEntry);
                    dataEntries[i][j] = new DataEntry((float) Math.round(a * 100) / 100);
                    gridPane.add(dataEntries[i][j].getStackPane(), i+1, j+1);
                }
            }
            gridPane.getRowConstraints().add(new RowConstraints(30));
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        }
        for(int i = 0; i<filesDirectory.size(); i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            gridPane.add(sp,i+1,filesDirectory.size()+1);
        }
        for(int i = 0; i<filesDirectory.size(); i++) {
            Rectangle rect = new Rectangle(50,30);
            rect.setFill(Color.TRANSPARENT);
            Label label = new Label(filesDirectory.get(i).getFile().getName());
            label.setAlignment(Pos.CENTER);
            StackPane sp = new StackPane(rect,label);
            gridPane.add(sp,filesDirectory.size()+1,i+1);
        }
    }
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
    }
}
