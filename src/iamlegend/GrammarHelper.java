package iamlegend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isUpperCase;

public class GrammarHelper {
    public static String multimeaProductiilor="";
    public static List<Rule> rules = new ArrayList<>();
    public static char startSymbol;
    public static String productionSeparator;
    public static char emptyChar;
    public static  String multimeaNeterminalelor ="VN = {";
    public static  String multimeaTerminalelor="VT = {";

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

            return false;
        }

        Pattern pattern = Pattern.compile("^[a-zA-ZS@$&,+*\\-{}() \\[\\] ]+$");
        Matcher matcher = pattern.matcher(input);
        boolean matchFound = matcher.find();
        if (!matchFound){

            return false;

        }

            /* daca primul caracter din input nu este simbolul de start,
            atunci se va afisa un mesaj potrivit pentru rezultat*/
        if (input.charAt(0)!= 'S')

        {

            return false;
        }

        if (!input.contains("&"))

        {

            return false;
        }



        if(!input.contains("$"))
        {

            return false;
        }

        return true;

    }


    public static boolean CreateProductionsSet(String grammar, String indexProductii)
    {

        multimeaProductiilor = "P = { ";

        String regex ="\\$";
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

                multimeaNeterminalelor += p1 +", ";

                multimeaTerminalelor += p2 +", ";

                multimeaProductiilor += p1 + " → " + p2 + ", ";

                GrammarHelper.rules.add(r);

            }

        }
        if (rules!=null) {
            for (Rule r : rules) {
                System.out.println(r);
            }
        }
        int last = multimeaNeterminalelor.length() - 2;
        if (last > 0 && multimeaNeterminalelor.charAt(last) == ',') {
            multimeaNeterminalelor = multimeaNeterminalelor.substring(0, last);
        }
        multimeaNeterminalelor += "} - multimea neterminalelor\n";

        int last1 = multimeaTerminalelor.length() - 2;
        if (last1 > 0 && multimeaTerminalelor.charAt(last1) == ',') {
            multimeaTerminalelor = multimeaTerminalelor.substring(0, last1);
        }
        multimeaTerminalelor += "} - multimea terminalelor\n";

        int last2 = multimeaProductiilor.length() - 2;
        if (last2 > 0 && multimeaProductiilor.charAt(last2) == ',') {
            multimeaProductiilor = multimeaProductiilor.substring(0, last2);
        }
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


    public static String CreateResult(String input) {
        String result="";
        // daca mai exista caractere dupa "&", atunci luam numai primul subsir dinaintea lui "&"
        String[] grammarList = input.split("&");
        String grammar = grammarList[0].replace(emptyChar, 'λ');

        // pregatirea sirurilor de caractere car vor fi afisate drept rezultat
        String resultTitle = "G = ( VN, VT, S, P )\r\n\r\n";

        String indexProductii = "";
        String startSimbol = String.valueOf(startSymbol) + "- simbolul de start\r\n";

        try {
            if (!GrammarHelper.CreateProductionsSet(grammar, indexProductii)) {
                return "Production set invalid";
            }
            indexProductii += "\r\n";
            result=resultTitle + multimeaNeterminalelor + multimeaTerminalelor + startSimbol + indexProductii + multimeaProductiilor;

        }
        catch (Exception e){

            result="Separarea producțiilor nu s-a realizat corespunzător!\r\nVerificați poziția simbolului „$”!";

        }

        return result;
    }

    public static String markov(String input) {
        //https://stackoverflow.com/questions/32835469/how-to-implement-markovs-algorithm-in-java
        // https://en.wikipedia.org/wiki/Markov_algorithm
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

    public static String removeUselesscChars(String text) {

        String result = "";

        List<Character> kj = new ArrayList<>();
        List<Character> kj1 = new ArrayList<>();
        List<Character> temp = new ArrayList<>();
        List<Rule> temprules = new ArrayList<>();


//        Map
//                <String, String> finalRules;
//
//        finalRules.put("S", "are");
//        finalRules.put("M", "mere");
//
//        finalRules.get("M")

        for (Rule r : rules) {
            boolean hasLowerCase = r.getTo().equals(r.getTo().toLowerCase());
            if (hasLowerCase)
                if (!kj1.contains(r.getFrom()))
                    kj1.add(r.getFrom());
        }
        while (kj != kj1) {
            kj = kj1;
            for (Rule r : rules) {
                char target = r.getTo().charAt(0);
                for (Character c : kj1) {
                    if (target == c) {
                        if (!temp.contains(r.getFrom()) && !kj1.contains(r.getFrom()))
                            temp.add(r.getFrom());
                    }
                }

            }
            kj1.addAll(temp);
        }
        assert kj1 != null;
        temprules.addAll(rules) ;
        for (Rule r : rules) {
            if (!kj1.contains(r.getFrom()))
                temprules.remove(r);

        }
        rules = temprules;
            for (Rule r1 : rules) {
                result += r1.getFrom() + r1.getTo() + "$";
            }
            result += "&";
            for (Character item : kj1) {
                System.out.println(item + ", ");
            }
            return result;
        }




}
