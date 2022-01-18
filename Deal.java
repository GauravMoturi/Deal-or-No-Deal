import java.util.*;
import javax.swing.*;

public class Deal {

    public static Case[] cases = new Case[26];
    public static int[] casesRemaining = new int[26];
    public static int personalCaseNumber = 0;
    public static String[] names = {"Ashley", "Brandi", "Catherine", "Diana", "Evelyn", "Francesca", "Gabrielle", "Henrietta", "Irene", "Julia", "Katie", "Lindsay", "Melanie", "Nicole", "Olivia", "Patricia", "Quentilda", "Rachel", "Sally", "Trisha", "Ursula", "Victoria", "Wendy", "Xena", "Yolanda", "Zoey"};
    public static ArrayList<Integer> values = new ArrayList<Integer>();
    public static int[] valuesArr = new int[26];
    public static Scoreboard scoreboard;
    public static int turns = 0;

    public static void main(String[] args) throws InterruptedException{
        newGame();
    }

    public static void newGame() throws InterruptedException{
        for (int i = 0; i < 26; i++)
            casesRemaining[i] = 1;
        setCases();
        playTheGame(6, 0);
    }

    public static void playTheGame(int casesToRemove, int countdown) throws InterruptedException{
        turns++;
        if (casesToRemove != 1 && casesToRemove != 2) {
            for (int i = casesToRemove; i > 0; i--)
                openYourCase();
            bankOffer(--casesToRemove, 0);
        } else if (casesToRemove == 2) {
            openYourCase();
            openYourCase();
            bankOffer(1, 3);
        } else {
            turns++;
            if (countdown > 0){
                openYourCase();
                bankOffer(1, --countdown);
            } else {
                openYourCase();
                lastRound();
            }
        }
    }

    public static void lastRound() throws InterruptedException{
        System.out.println("Two cases remain, one in your hand and one on the playing field. However, I am going to offer you three options:");
        int sum = 0;
        for (int i = 0; i < 26; i++)
            if (casesRemaining[i] == 1)
                sum += cases[i].value();
        int offer = ((int) (sum / 2.0));
        System.out.println("1) You take the bank's offer which I can tell you right now is $" + offer + ".");
        System.out.println("2) You walk away with whatever is in your personal case.");
        System.out.println("3) You walk away with whatever is in the other case you have not yet eliminated.");
        System.out.print("Which will it be? Enter the number that corresponds to your desired option above, 1, 2, or 3: ");
        Scanner reader = new Scanner(System.in);
        int choice = reader.nextInt();
        if (choice == 1) {
            System.out.println("Well folks, he decided to take the compromise and go with the bank offer, netting him $" + offer + "!");
            resetOrQuit();
        } else if (choice == 2) {
            System.out.println("I see, he wants to stick with his initial choice of case. Let's see if his faith pays off.");
            Thread.sleep(2000);
            System.out.print("Your personal case will land you: ");
            Thread.sleep(2000);
            System.out.println("$" + cases[personalCaseNumber-1].value() + "! That is what you chose from the start and will take with you.");
            resetOrQuit();
        } else if (choice == 3) {
            System.out.println("Wow. He distrusts his initial instinct and has decided to swap his case out for the one on the field.");
            Thread.sleep(2000);
            System.out.print("This act of self-suspicion will land you: ");
            Thread.sleep(2000);
            casesRemaining[personalCaseNumber-1] = 0;
            int result = 0;
            for (int i = 0; i < 26; i++)
                if (casesRemaining[i] != 0)
                    result = cases[i].value();
            System.out.println("$" + result + "! That is what you chose from the start and will take with you.");
            resetOrQuit();
        }
    }

    public static void bankOffer(int nextCasesToRemove, int nextCountdown) throws InterruptedException{
        int sum = 0, casesLeft = 0;
        for (int i = 0; i < 26; i++)
            if (casesRemaining[i] == 1) {
                sum += cases[i].value();
                casesLeft++;
            }
        int offer = ((int) (turns / 20.0 * ((double) sum)/((double) casesLeft)));
        System.out.print("[RING]...Hello?");
        Thread.sleep(1000);
        System.out.print("...");
        Thread.sleep(1000);
        System.out.print("yes");
        Thread.sleep(1000);
        System.out.print("...");
        Thread.sleep(1000);
        System.out.println("okay I will tell him. [HANGUP]");
        Thread.sleep(1000);
        System.out.print("The banker is offering you $" + offer + ". Deal, or no deal? Enter 1 for deal, 2 for no deal: ");
        if (dealOrNoDeal() == true) {
            System.out.println("Great! It's a deal, you will be walking out of here with $" + offer + ".");
            resetOrQuit();
        } else {
            System.out.println("He is taking the gamble and trying to put more pressure on the banker. No Deal!");
            playTheGame(nextCasesToRemove, nextCountdown);
        }
    }

    public static void resetOrQuit() throws InterruptedException{
        System.out.print("Do you want to play again? Enter 1 if you do or 2 if you don't: ");
        Scanner reader = new Scanner(System.in);
        int choice = reader.nextInt();
        if (choice != 1 && choice != 2) {
            System.out.println("C'mon, it's a yes or no question.");
            resetOrQuit();
        } else if (choice == 1)
            newGame();
        else
            return;
    }

    public static boolean dealOrNoDeal(){
        Scanner reader = new Scanner(System.in);
        int dornd = reader.nextInt();
        if (dornd != 1 && dornd != 2) {
            System.out.print("C'mon, enter 1 for deal, 2 for no deal: ");
            return dealOrNoDeal();
        } else if (dornd == 1)
            return true;
        else
            return false;
    }

    public static void openYourCase() throws InterruptedException{
        System.out.print("Please select the number of the case you wish to eliminate: ");
        Scanner reader = new Scanner(System.in);
        int number = reader.nextInt();
        if (number < 1 || number > 26) {
            System.out.println("C'mon, pick an actual case number.");
            openYourCase();
        } else if (casesRemaining[number-1] == 0) {
            System.out.println("You already eliminated that case earlier. Let's try this again.");
            openYourCase();
        } else {
            System.out.println("Alright case number " + number + " is " + names[number-1] + "'s case. " + names[number-1] + ", open your case.");
            Thread.sleep(2000);
            System.out.print("The case you have selected contains: ");
            Thread.sleep(2000);
            System.out.println("$" + cases[number-1].value() + "! That value is no more.");
            scoreboard.removeCase(number);
            scoreboard.removeValue(cases[number-1].value());
            scoreboard.setVisible(true);
            casesRemaining[number-1] = 0;
        }
    }

    public static void setCases(){
        setValues();
        Random rand = new Random();
        for (int i = 0; i < 26; i++)
            cases[i] = new Case(names[i], i, values.remove(rand.nextInt(values.size())));
        int personalCase = selectPersonalCase();
        personalCaseNumber = personalCase;
        scoreboard = new Scoreboard(cases, valuesArr, personalCase, personalCase);
        scoreboard.removeCase(personalCase);
        casesRemaining[personalCase-1] = 0;
        scoreboard.setVisible(true);
    }

    public static void setValues(){
        values.add(5000000);
        values.add(2000000);
        values.add(1000000);
        values.add(750000);
        values.add(500000);
        values.add(250000);
        values.add(100000);
        values.add(75000);
        values.add(50000);
        values.add(25000);
        values.add(10000);
        values.add(9000);
        values.add(8000);
        values.add(7000);
        values.add(6000);
        values.add(5000);
        values.add(4000);
        values.add(3000);
        values.add(2000);
        values.add(1000);
        values.add(500);
        values.add(400);
        values.add(300);
        values.add(200);
        values.add(100);
        values.add(1);
        for (int i = 0; i < 26; i++)
            valuesArr[25-i] = values.get(i);
    }

    public static int selectPersonalCase(){
        Scanner reader = new Scanner(System.in);
        System.out.println("There are twenty-six cases from which you will choose one to be your personal case.");
        System.out.print("Which one shall be your personal case? Please use an integer from 1 to 26: ");
        int caseNo = reader.nextInt();
        if (!(caseNo >= 1 && caseNo <= 26)) {
            System.out.println("Okay, let's try this again. Please follow the instructions this time.");
            return selectPersonalCase();
        } else {
            System.out.println("Alright, so case " + caseNo + " will be your personal case. We'll see if you stick with it.");
            //System.out.println(cases[personalCaseNumber].value());
            return caseNo;
        }
    }
}