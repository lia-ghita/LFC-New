package iamlegend;

public class Rule {

    private char from;
    private String to = "";

    public Rule (char from, String to){
        this.from=from;
        this.to=to;
    }

    public char getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setFrom(char from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String toString(){

        return from + "->" +to;
    }

    public boolean matches(String input) {
        return (input.contains(String.valueOf(this.from)));

    }

    public String apply(String input) {

      String newimput=  input.replace(String.valueOf(this.from), this.to);
        return newimput;
    }


}
