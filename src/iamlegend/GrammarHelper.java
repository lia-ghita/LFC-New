package iamlegend;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isUpperCase;

public class GrammarHelper {
public static String multimeaProductiilor="";
public static List<Rule> rules = new ArrayList<>();
public static char startSymbol;
public static char productionSeparator;
public static char emptyChar;


     public static boolean ReadGrammar(String input){
         /* daca in intreg continutul introdus de la tastatura sau citit din fisier
          * exista si alte caractere in afara de
          * majusculele alfabetului latin,
          * de literele mici ale alfabetului latin,
          * de "@", "$" si "&",
          * atunci continutul nu este corespunzator si
          * se afiseaza un mesaj potrivit pentru rezultat*/

         if(input.length()==0)
         {
           //  InputGrammar.ResultField.setText("Fișierul este gol!") ;
             return false;
         }


         Pattern pattern = Pattern.compile("^[a-zA-ZS@$&,+*\\-{}() ]+$");
         Matcher matcher = pattern.matcher(input);
         boolean matchFound = matcher.find();
         if (!matchFound){
           //  InputGrammar.ResultField.setText("În șirul dat există simboluri necorespunzătoare!");
             return false;
         }


            /* daca primul caracter din input nu este simbolul de start,
            atunci se va afisa un mesaj potrivit pentru rezultat*/
         if (input.charAt(0)!= 'S')

         {
           //  InputGrammar.ResultField.setText("În șirul dat, primul caracter nu este simbolul de start „S”!");
             return false;
         }

            /* daca sirul dat nu contine simbolul care indica sfarsitul gramaticii,
             atunci se va afisa un mesaj corespunzator la rezultat*/
         if (!input.contains("&"))

         {
         //    InputGrammar.ResultField.setText("Șirul de caractere nu conține simbolul „&”, care marchează sfârșitul gramaticii.");
             return false;
         }

         /* daca sirul de caractere introdus sau citit nu contine secventa vida,
          * atunci se va afisa un mesaj corespunzator la rezultat */
         if (!input.contains("@"))
         {

           //  InputGrammar.ResultField.setText("Șirul de caractere nu conține simbolul „@”, care simbolizează secvența vidă!");
             return false;
         }

         if(!input.contains("$"))
         {
            // InputGrammar.ResultField.setText("Șirul de caractere nu conține „$”, simbolul pentru separarea producțiilor!");
             return false;
         }

         return true;

    }


    private static boolean CreateProductionsSet(String grammar, String indexProductii)
    {
        multimeaProductiilor = "P = { ";

String regex ="\\"+productionSeparator;
        String[] productii = grammar.split(regex,0)  ;

        for (String s:productii)
        {

            String productie = s;

         /*   if (!char.IsUpper(productie[0]))
            {
                resultTextBox.Text = "Partea stângă a producției " + (i + 1).ToString() + " nu conține neterminal!";
                return false;
            }*/
            if (productie.substring(1).length() != 0)
            {
                char p1 = productie.charAt(0);
                String p2= productie.substring(1);
                 Rule r = new Rule(p1, p2);
                multimeaProductiilor += p1 + " → " + p2 + "; ";

              GrammarHelper.rules.add(r);

            }
            /*else
            {
                resultTextBox.Text = "Partea dreaptă a producției " + (i + 1).ToString() + " este incompletă!";
                return false;
            }*/


        }
            if (rules!=null) {
                for (Rule r : rules) {
                    System.out.println(r);
                }
            }
      /*  if (!char.IsUpper(productii[productii.Length - 1][0]))
        {
            resultTextBox.Text = "Partea stânga a producției " + (productii.Length).ToString() + " nu conține neterminal!";
            return false;
        }*/
      /*  if (productii[productii.length - 1].substring(1).length() != 0)
        {
            multimeaProductiilor += productii[productii.length - 1].charAt(0) + " → " + productii[productii.length- 1].substring(1);
        }*/
     /*   else
        {
            resultTextBox.Text = "Partea dreaptă a producției " + (productii.Length).ToString() + " este incompletă!";
            return false;
        }*/





        multimeaProductiilor += " } - mulțimea producțiilor";

        int counter = 1;
        for(int i=0; i<multimeaProductiilor.length(); i++)
        {
            if (multimeaProductiilor.charAt(i) == '→')
            {
                indexProductii +=  String.valueOf(counter)+" ";
                counter++;
            }
            else
            {
                if (multimeaProductiilor.charAt(i) == ' ')
                    indexProductii += " ";
                else
                    indexProductii += "  ";
            }
        }

        return true;
    }
    public static String CreateMultimeaNetereminalelor(String grammar)
    {
        String multimeaNeterminalelor = "VN = { ";
        String neterminale = "";// new String(grammar.Where(Char.IsUpper).ToArray()); // neterminalele sunt toate literele mare
        String neterminaleObisnuite = neterminale.replace(String.valueOf(startSymbol), "");

        // multimea neterminalelor va cuprinde neterminalele diferite de "S" si distincte intre ele, in ordine alfabetica
        String neterminaleSortate = "";//String.Concat(neterminaleObisnuite.OrderBy(c => c).Distinct());
        if (neterminaleSortate.length() != 0)
        {
            // daca mai exista si alte neterminale in afara de "S", atunci punem virgula dupa "S"
            multimeaNeterminalelor += String.valueOf(startSymbol)+","; // primul simbol afisat este simbolul de start
            for (int i = 0; i < neterminaleSortate.length() - 1; i++)
            {
                if (neterminaleSortate.charAt(i)!= startSymbol)
                    multimeaNeterminalelor += " " + neterminaleSortate.charAt(i) + ",";
            }
            multimeaNeterminalelor += " " + neterminaleSortate.charAt(neterminaleSortate.length()- 1);
        }
        else
        {
            // daca nu mai exista si alte neterminale in afara de "S", atunci nu mai punem virgula dupa "S"
            multimeaNeterminalelor += String.valueOf(startSymbol);
        }

        multimeaNeterminalelor += " } - mulțimea neterminalelor\r\n";

        return multimeaNeterminalelor;
    }

    public static String CreateMultimeaTerminalelor(String grammar)
    {
        String multimeaTerminalelor = "VT = { ";
        String litereMici = "";//new String(grammar.Where(char.IsLower).ToArray()); // neterminalele sunt toate literele mici

        String terminaleObisnuite = litereMici.replace("λ", ""); // literele mici fara lamda

        // terminalele distincte intre ele si distincte de lamda
        String terminaleSortateAlfabetic = terminaleObisnuite;
                //String.Concat(terminaleObisnuite.OrderBy(c => c).Distinct());

        for (int i = 0; i < terminaleSortateAlfabetic.length(); i++)
        {
            if (terminaleSortateAlfabetic.charAt(i) != 'λ')
                multimeaTerminalelor += terminaleSortateAlfabetic.charAt(i) + ", ";
        }

        // multimeaTerminalelor += "λ } - mulțimea terminalelor\r\n";
        multimeaTerminalelor += String.valueOf(emptyChar) + "} - mulțimea terminalelor\r\n";

        return multimeaTerminalelor;
    }

    public static String CreateResult(String input) {
         String result="";
        // daca mai exista caractere dupa "&", atunci luam numai primul subsir dinaintea lui "&"
        String[] grammarList = input.split("&");
        String grammar = grammarList[0].replace(emptyChar, 'λ');

        // pregatirea sirurilor de caractere car vor fi afisate drept rezultat
        String resultTitle = "G = ( VN, VT, S, P )\r\n\r\n";
        String multimeaNeterminalelor = CreateMultimeaNetereminalelor(grammar);
        String multimeaTerminalelor = CreateMultimeaTerminalelor(grammar);

        String indexProductii = "";
        String startSimbol = String.valueOf(startSymbol) + "- simbolul de start\r\n";

        try {
            if (!GrammarHelper.CreateProductionsSet(grammar, indexProductii)) {
                return "Production set invalid";
            }
            indexProductii += "\r\n";
            result=resultTitle + multimeaNeterminalelor + multimeaTerminalelor + startSimbol + indexProductii + multimeaProductiilor;
            // CustomizeResult(resultTitle, multimeaNeterminalelor,indexProductii);



        }
        catch (Exception e){

                result="Separarea producțiilor nu s-a realizat corespunzător!\r\nVerificați poziția simbolului „$”!";

        }

        return result;
    }

    public static String markov(String input) {
    //https://stackoverflow.com/questions/32835469/how-to-implement-markovs-algorithm-in-java
        // find the first matching rule, apply it and recurse
        for (Rule rule : rules) {
            if (rule.matches(input)) {
              String temp = rule.apply(input);
                return markov (temp);
            }

        }
        return input;
        // no rule matched so just return the input text
        // - this is the terminating case for the recursion
      // return input;
    }





}
