// Separate class that has main method
import java.util.Scanner;
public class Runner {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        // Creates planner with User's given total tables amount and total seats per table amount
        Planner p = new Planner(getUserInt(sc, "Total Tables: "), getUserInt(sc, "Total Seats per Table: "), getUserInt(sc, "Limit of people from the same company at a table: "));
    }

    // retuns int from user, uses text as message for what number will be
    private static int getUserInt(Scanner sc, String text) {
        while (true) {
            System.out.print(text);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {}
        }
    }
}