import java.io.*;
import java.util.Scanner;
// purpose of this class is to test and see the format of cvs entries when using File classes
public class Test {
    public static void main(String[] args) throws IOException { // has throws IOException b/c not want code to just crash when encounter error
        File fc = new File("companies.csv");
        Scanner scanComp;
        try {
            scanComp = new Scanner(fc);
            //System.out.println("First number: " + scanComp.nextInt()); 
            // next int doesn't exist (InputMismatchException)
            System.out.println(scanComp.hasNext());
            for (int j = 0; j < 2; j++) {
                String s = scanComp.nextLine();
                int index = s.indexOf(",");
                int n = Integer.parseInt(s.substring(0,index));
                String name = s.substring(index + 1);
                System.out.println("First line of companies csv: " + s);
                System.out.println("First number = " + n);
                System.out.println("First name of company = " + name);
            }
            // System.out.println("Second lien of companies csv: " + scanComp.nextLine());
            scanComp.close();
        } catch (FileNotFoundException e) {
            System.out.println("Companies csv file not found");
        }
        
        System.out.println("\n");

        File fn = new File("names.csv");
        Scanner scanName;
        try {
            scanName = new Scanner(fn);
            String[] words = scanName.nextLine().split(",");
            for (int i = 0; i < words.length; i++) {
                System.out.println(words[i]);
                /*
                    Four sections
                    1. Total number of people at line
                    2. Last name
                    3. First Name
                    4. Numbered Company
                */
            }
            System.out.println("second line of names csv: " + scanName.nextLine());
            scanName.close();
        } catch (FileNotFoundException e) {
            System.out.println("Names csv file not found");
        }
        
    }
}