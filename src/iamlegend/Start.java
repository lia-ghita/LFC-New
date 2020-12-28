package iamlegend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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
                uiController.uploadGrammar.setText(f.getName());
            }
        });
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
