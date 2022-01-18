public class Case {

    private String nm;
    private int num;
    private int val;

    public Case(String name, int number, int value){
        nm = name;
        num = number;
        val = value;
    }

    public String name(){
        return nm;
    }

    public int number(){
        return num;
    }

    public int value(){
        return val;
    }
}