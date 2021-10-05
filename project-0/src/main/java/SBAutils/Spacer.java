package SBAutils;

public class Spacer {

    //Constructor for Spacer
    public Spacer() {
    }

    //Prints a new line
    public void newL() {
        System.out.print("\n");
    }

    //Prints a border of "=="
    public void border() {
        for (int i = 0; i < 25; i++) {
            System.out.print("=");
        }

    }

    //Surround a String with borders above and below
    public void surround(String message){
        border();
        newL();
        System.out.println(message);
        border();
        newL();
    }

}
