package iamlegend;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class UiController {
    public JFXTextArea grammarRules;
    public JFXButton uploadGrammarButton;
    public JFXTextField uploadGrammar;
    public JFXButton checkGrammarButton;
    public JFXButton removeChars;
    public JFXTextField startSymbol;
    public JFXTextField productionSeparator;
    public JFXTextField emptyCharacter;
    public Label dragPopUp;


    public String CheckGrammar(){
        GrammarHelper.startSymbol=startSymbol.getText().charAt(0);
        GrammarHelper.emptyChar=emptyCharacter.getText().charAt(0);
        GrammarHelper.productionSeparator= productionSeparator.getText();
        String text = grammarRules.getText();
        if (text==""){
            if (uploadGrammar.getText()!= null)
            {

            }
        }
        if (GrammarHelper.ReadGrammar(text)){

            String s = GrammarHelper.CreateResult(text);
            GrammarHelper.removeUselesscChars(text);
            return s;
        }

        return "Gramatica este invalida";
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

    public  String removeChars(String s){
        String result="";
        GrammarHelper.CreateResult(s);
        result = GrammarHelper.removeUselesscChars(s);
        return result;
    }
}