package iamlegend;

import javax.xml.transform.Source;
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
    public static  String multimeaNeterminalelor = "";
    public static  String multimeaTerminalelor = "";
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
        List <Character> sources = new ArrayList<>();
        List <Character> targets = new ArrayList<>();
        multimeaProductiilor = "P = { ";
        String regex ="\\$";
        String[] productii = grammar.split(regex,0)  ;
        for (String s:productii) //generam multimea productiilor
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
                if (!sources.contains(p1))
                    sources.add(p1);
                for (int i=0; i<p2.length(); i++) {
                    if(!targets.contains(p2.charAt(i)) && Character.isLowerCase(p2.charAt(i))) {
                        targets.add(p2.charAt(i));

                    }
                }
                //   multimeaNeterminalelor += p1 +", ";
//                multimeaTerminalelor += p2 +", ";
                multimeaProductiilor += p1 + " → " + p2 + ", ";
                GrammarHelper.rules.add(r);
            }
        }
        multimeaNeterminalelor += "VN' = {";
        StringBuilder sb = new StringBuilder(multimeaNeterminalelor);
        sb.deleteCharAt(2);
//        System.out.println(sb.toString());
        multimeaNeterminalelor = sb.toString();
        for (Character c: sources) {
            multimeaNeterminalelor += c + ", "; //parcurgem si afisam neterminale
        }
        int last = multimeaNeterminalelor.length() - 2;
        if (last > 0 && multimeaNeterminalelor.charAt(last) == ',') { //pentru afisare in Legend
            multimeaNeterminalelor = multimeaNeterminalelor.substring(0, last);
        }
        multimeaNeterminalelor += "} - multimea neterminalelor\n";




        multimeaTerminalelor += "VT' = {";
        StringBuilder sb1 = new StringBuilder(multimeaTerminalelor);
        sb1.deleteCharAt(2);
//        System.out.println(sb.toString());
        multimeaTerminalelor = sb1.toString();
        for(Character ter : targets){

            multimeaTerminalelor += ter + ", "; //parcurgem si afisam terminale
        }
        int last1 = multimeaTerminalelor.length() - 2;
        if (last1 > 0 && multimeaTerminalelor.charAt(last1) == ',') { //pentru afisare in Legend
            multimeaTerminalelor = multimeaTerminalelor.substring(0, last1);
        }
        multimeaTerminalelor += "} - multimea terminalelor \n";
//        int last1 = multimeaTerminalelor.length() - 2;
//        if (last1 > 0 && multimeaTerminalelor.charAt(last1) == ',') {
//            multimeaTerminalelor = multimeaTerminalelor.substring(0, last1);
//        }
//        multimeaTerminalelor += "} - multimea terminalelor\n";

        int last2 = multimeaProductiilor.length() - 2;
        if (last2 > 0 && multimeaProductiilor.charAt(last2) == ',') {
            multimeaProductiilor = multimeaProductiilor.substring(0, last2);
        }
        multimeaProductiilor += " } - mulțimea producțiilor";
        int counter = 1;
        for(int i=0; i<multimeaProductiilor.length(); i++) //pentru afisare multimeaProductiilor in Legend
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
        String result = ""; //lista pentru output valide
        List<Character> kj = new ArrayList<>(); //lista in care salvam validele
        List<Character> listOfValidElements = new ArrayList<>(); //prima lista dupa parcurgere terminale

        List<Character> InvalidSources = new ArrayList<>(); //unde stocam toate simbolurile inutile
        List<Rule> temprules = new ArrayList<>();  //lista temporara
        List<Rule> tempFinalRules = new ArrayList<>(); //lista finala pe care o trecem in lista de rules
        for (Rule r : rules) {
            boolean hasLowerCase = r.getTo().equals(r.getTo().toLowerCase());
            if (hasLowerCase) //prima data le parcurgem pe cele terminale
                if (!listOfValidElements.contains(r.getFrom())) //apoi le salvam in lista, daca au terminale
                    listOfValidElements.add(r.getFrom());
        }

        boolean valid = true;
        while (kj != listOfValidElements) {
            kj = listOfValidElements; //salvam in lista kj productiile valide
            for (Rule r : rules) { //pentru regulile
                valid = true;
                char target = r.getTo().charAt(0);
                for(int i=0;i<r.getTo().length();i++){ //verificam regulile salvam in target
                    target = r.getTo().charAt(i);
                    if (!(listOfValidElements.contains(target))) {
                        valid = false; //daca nu corespunde, este invalid
                    }
                }
                if(valid){
                    if(!listOfValidElements.contains(r.getFrom())){
                        listOfValidElements.add(r.getFrom()); //adaugam elementele valide
                    }
                }
            }
        }



        assert listOfValidElements != null;
        temprules.addAll(rules) ;
        for (Rule r : rules) {
            if (!listOfValidElements.contains(r.getFrom())) {//parcurgem regulilele invalide
                System.out.println("regula stearsa 1");
                System.out.println(r);
                temprules.remove(r); //prin r le stergem din lista temporara
                if(!InvalidSources.contains(r.getFrom()))
                    InvalidSources.add(r.getFrom()); //adaugam in lista cele sterse
            }
        }
        tempFinalRules.addAll(temprules); //adaugam in lista rezultatele valide
        for (Rule r2 : temprules){
            for(Character c : InvalidSources) { //parcurgem lista invalida
                if (r2.getTo().contains(String.valueOf(c))) { //sa afisam regulile sterse
                    System.out.println("regula stearsa 2");
                    System.out.println(r2);
                    tempFinalRules.remove(r2);
                }
            }
        }
        System.out.println("Lista Ch. invalide: "); //afisam lista intreaga cu regulile sterse
        for (Character c : InvalidSources){
            System.out.println(c);
        }
        System.out.println("Sfarsit lista invalide");
        rules = tempFinalRules; //adaugam in lista rules rezultatele finale valide din lista temporara
//        temprules.addAll(rules);
//
//        rules = temprules;
        for (Rule r1 : rules) { //afisam rezultatul final fara simbolurile inutile
            result += r1.getFrom() + r1.getTo() + "$";
        }
        result += "&";
//        for (Character item : kj1) {
//            System.out.println(item + ", ");
//        }
        return result;
    }
}