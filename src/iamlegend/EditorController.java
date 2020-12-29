package iamlegend;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditorController {
    public JFXTextArea inputText;
    public JFXTextArea outputText;
    public JFXTextArea TheLegendText;
    public JFXButton ApplyMarkov = new JFXButton();;
    public JFXButton infix2postfix;

    public void ApplyMarkov(){
       String text = inputText.getText();
       outputText.setText(GrammarHelper.markov(text));
   }


   public void Infix2Postfix(){
       ArrayList<Exp> l = new ArrayList<Exp>();
       Exp st=new Exp(inputText.getText());
       l.add(st);

       String str="";
       for(String x : inputText.getText().split("\n") )
       {
           Exp st1=new Exp(x);
           Infix2Postfix inf=new Infix2Postfix(st.exp);
           str+="infix : "+st.exp+"\n"+"postfix : "+inf.returnS+"\n---------------\n";
       }
       outputText.setText(str);
   }
}
