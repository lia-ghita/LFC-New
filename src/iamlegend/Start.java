package iamlegend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Start extends Application {

    private Stage primaryStage;
    private FXMLLoader firstLoader;
    private FXMLLoader secondLoader;
    private UiController uiController;
    private EditorController editorController;
    private Scene firstScene;
    private Scene secondScene;

    public Start(){
        try {
            firstLoader = new FXMLLoader(getClass().getResource("UI.fxml"));
            secondLoader = new FXMLLoader(getClass().getResource("Editor.fxml"));
            firstScene = new Scene(firstLoader.load());
            secondScene = new Scene(secondLoader.load());
            uiController = firstLoader.getController();
            editorController = secondLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        iniEvents();
    }

    private void iniEvents(){
        uiController.checkGrammarButton.setOnAction(event ->{
            String result = uiController.CheckGrammar();
            System.out.println(result);
            primaryStage.setScene(secondScene);
            editorController.TheLegendText.setText(result);
        });
        editorController.ApplyMarkov.setOnAction(event ->{
            editorController.ApplyMarkov();
        });

        uiController.uploadGrammarButton.setOnAction(event->{
            File f =  uiController.UploadGrammar(primaryStage);
            if (f!=null){
                loadGrammar(f);
            }
        });

        uiController.removeChars.setOnAction(e->{
            uiController.grammarRules.setText(uiController.removeChars(uiController.grammarRules.getText()));
        });

        editorController.backButton.setOnAction(event ->{
            primaryStage.setScene(firstScene);
            editorController.TheLegendText.clear();
        });

        uiController.grammarRules.setOnDragOver(dragEvent -> {
            if(dragEvent.getDragboard().hasFiles()){
                uiController.dragPopUp.toFront();
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
        });

        uiController.grammarRules.setOnDragDropped(event->{
            boolean success = false;
            Dragboard db = event.getDragboard();
            if(db.hasFiles()){
                File file = db.getFiles().get(0);
                loadGrammar(file);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
            uiController.dragPopUp.toBack();
        });
        firstScene.setOnDragEntered(dragEvent -> {
            if(dragEvent.getDragboard().hasFiles()){
                uiController.grammarRules.setEffect(new BoxBlur());
                uiController.dragPopUp.toFront();
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
        });
        firstScene.setOnDragExited(dragEvent ->{
            if(dragEvent.getDragboard().hasFiles()){
                uiController.grammarRules.setEffect(null);
                uiController.dragPopUp.toBack();
            }
        } );
    }

    public void loadGrammar(File f){
        uiController.uploadGrammar.setText(f.getName());
        Scanner sc = null;
        String str="";
        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(true){
            assert sc != null;
            if (!sc.hasNextLine()) break;
            str = sc.nextLine();
        }
        uiController.grammarRules.setText(str);

    }



    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("LFC");
        primaryStage.setScene(firstScene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}