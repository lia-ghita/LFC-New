package iamlegend;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

public class EditorController {
    public JFXTextArea inputText;
    public JFXTextArea outputText;
    public JFXTextArea TheLegendText;
    public JFXButton ApplyMarkov = new JFXButton();;

   public void ApplyMarkov(){
       String text = inputText.getText();
       outputText.setText(GrammarHelper.markov(text));
   }
}
