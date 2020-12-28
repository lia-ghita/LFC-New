package iamlegend;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;

public class InputGrammar extends JFrame {
    private JTextField textField;
    public static JTextArea ResultField = new JTextArea();
    public static JTextArea InputGrammarField = new JTextArea();
    public static JTextArea Instructions;
    public InputGrammar() {
        getContentPane().setLayout(null);
       setBounds(10,10,1100,800);


        ResultField.setBounds(107, 230, 671, 310);
        getContentPane().add(ResultField);



        InputGrammarField.setBounds(107, 33, 671, 87);
        getContentPane().add(InputGrammarField);
        InputGrammarField.setToolTipText("Input your grammar here");
      //  InputGrammarField.setText("SAB$AaA$A@$Ba&");
        InputGrammarField.setText("S(string[] args){}$Ppublic $Tstatic $Vvoid $Mmain &");
        textField = new JTextField();
        textField.setBounds(107, 131, 553, 20);
        getContentPane().add(textField);
        textField.setColumns(10);

        JButton Upload = new JButton("Upload");
        Upload.setBounds(667, 131, 111, 22);
        getContentPane().add(Upload);

        JButton CheckGrammar = new JButton("Check Grammar");
        CheckGrammar.setBounds(163, 182, 145, 23);
        getContentPane().add(CheckGrammar);

        CheckGrammar.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 String text = InputGrammarField.getText();
             if (GrammarHelper.ReadGrammar(text));
               GrammarHelper.CreateResult(text);
            }
           }



        );

        JButton RemoveChars = new JButton("Remove useless chars");
        RemoveChars.setBounds(337, 182, 162, 23);
        getContentPane().add(RemoveChars);

        JButton ApplyMarkov= new JButton("Apply Markov");
        ApplyMarkov.setBounds(524, 182, 174, 23);
        getContentPane().add(ApplyMarkov);

        ApplyMarkov.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                     String text = InputGrammarField.getText();
           InputGrammar.ResultField.setText(GrammarHelper.markov(text));
                                           }
                                       }

        );

        JButton btnNewButton_4 = new JButton("New button");
        btnNewButton_4.setBounds(356, 593, 174, 23);
        getContentPane().add(btnNewButton_4);


        Instructions = new JTextArea();
        Instructions.setBounds(807, 35, 250, 505);
        getContentPane().add(Instructions);
        Instructions.setColumns(10);
        Instructions.setEditable(false);
        Instructions.setLineWrap(true);
        Instructions.setWrapStyleWord(true);
     /*   Instructions.setText("Regulile de citire sunt următoarele:\n" +
                "Primul simbol din prima producţie reprezintă axioma (simbolul de start);\n" +
                "Simbolul de separare dintre producţii este „$\";\n" +
                "Simbolurile neterminale sunt scrise cu litere mari;\n" +
                "Simbolurile terminale sunt scrise cu litere mici;\n" +
                "Secvenţa vidă va fi „@\";\n" +
                "Simbolul care marchează sfârşitul gramaticii este „&\".");
*/
        Instructions.setText("Intai apasati Check Grammar, Introduceti in fieldul de sus urmatoarea sintagma: PTVMS, apasati Apply Markov si observati rezultatul!");



    }
}
