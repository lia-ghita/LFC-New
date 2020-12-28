package iamlegend;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

public class UiController {
    public JFXTextArea grammarRules;
    public JFXButton uploadGrammarButton;
    public JFXTextField uploadGrammar;
    public JFXButton checkGrammarButton;

    public String CheckGrammar(){
        String text = grammarRules.getText();
        System.out.println(text);
        if (GrammarHelper.ReadGrammar(text))
            return GrammarHelper.CreateResult(text);
        return "";
    }


}
