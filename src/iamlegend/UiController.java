package iamlegend;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class UiController {
    public JFXTextArea grammarRules;
    public JFXButton uploadGrammarButton;
    public JFXTextField uploadGrammar;
    public JFXButton checkGrammarButton;

    public String CheckGrammar(){
        String text = grammarRules.getText();
        System.out.println(text);
      //  if (GrammarHelper.ReadGrammar(text))
            return GrammarHelper.CreateResult(text);

    }

    public File UploadGrammar(Stage s){
        String grammar="";
        FileChooser fc = new FileChooser();

        fc.setTitle("Choose the file containing the defined grammar");
        File file = fc.showOpenDialog(s);
        if (file!= null){
            return file;
        }

        return null;
    }


}
