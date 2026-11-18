// Separate class that has main method
import java.util.Scanner;
public class Runner {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        // Creates planner with User's given total tables amount and total seats per table amount
        Planner p = new Planner(getUserInt(sc, "Total Tables: ", 1), getUserInt(sc, "Total Seats per Table: ", 1), getUserInt(sc, "Limit of people from the same company at a table: ", 0));
    }

    // retuns int from user, uses text as message for what number will be, limit is minimum acceptable value
    private static int getUserInt(Scanner sc, String text, int limit) {
        int n;
        while (true) {
            System.out.print(text);
            try {
                n = Integer.parseInt(sc.nextLine());
                if (n >= limit) {
                    return n;
                }
            } catch (NumberFormatException e) {}
        }
    }
}