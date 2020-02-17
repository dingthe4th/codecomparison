package sample;


import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;




public class Controller {

    static GridPane matrixBase;


    @FXML
    private AnchorPane scrollP;

    @FXML
    void chooseDirectoryE(MouseEvent event) {

    }

    @FXML
    private VBox mainLayout;




    @FXML
    void generateMatrixE(MouseEvent event) {
        Similarity.listStudents();

        matrixBase = new GridPane();
        scrollP.getChildren().add(matrixBase);


        matrixBase.setHgap(10); //horizontal gap in pixels => that's what you are asking for
         matrixBase.setVgap(10); //vertical gap in pixels
        matrixBase.setPadding(new Insets(10, 10, 10, 10));

        for ( int i = 0 ; i < Similarity.students.size() ; i++ ){

            Label name = new Label(Similarity.students.get(i).name);
            name.setWrapText(true);
            name.setRotate(90.0);
            name.setStyle(  "  -fx-pref-width:100; -fx-pref-height: 100");
            GridPane.setConstraints(name,i+1,1);

            matrixBase.getChildren().add(name);
        }


        for( int i = 0; i< Similarity.students.size(); i++ ){


            Label name = new Label(Similarity.students.get(i).name);
            name.setWrapText(true);
            name.setStyle(  "  -fx-pref-width:100; -fx-pref-height: 100");
            GridPane.setConstraints(name,0,i+3);

            matrixBase.getChildren().add(name);


            for(int j = 0 ; j < Similarity.students.size(); j++ ){

                StackPane cell = new StackPane();




                Label result = new Label(Similarity.getSimilarity( Similarity.students.get(i), Similarity.students.get(j) ) );

                Double dhue = Double.parseDouble( Similarity.getSimilarity( Similarity.students.get(i), Similarity.students.get(j)) );
                int hue = (int)(255-dhue*255);

                result.setStyle("-fx-font-size: 10;");
                cell.setStyle(  "  -fx-pref-width:50; -fx-pref-height: 50;-fx-background-color: rgb(255,"+hue+","+hue+");");
                cell.getChildren().add(result);
                GridPane.setConstraints(cell,j+1,i+3);
                matrixBase.getChildren().add(cell);
            }



        }



    }

}
