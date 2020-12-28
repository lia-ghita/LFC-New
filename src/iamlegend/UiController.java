package iamlegend;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class UiController {
    public JFXTextArea grammarRules;
    public JFXButton uploadGrammarButton;
    public JFXTextField uploadGrammar;
    public JFXButton checkGrammarButton;
    public TextField emptyCharacter;
    public TextField productionSeparator;
    public TextField startSymbol;



    public String CheckGrammar(){
        GrammarHelper.startSymbol=startSymbol.getText().charAt(0);
        GrammarHelper.emptyChar=emptyCharacter.getText().charAt(0);
        GrammarHelper.productionSeparator= productionSeparator.getText().charAt(0);
        String text = grammarRules.getText();
      if (text==""){
          if (uploadGrammar.getText()!= null)
          {

          }
        }
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
