// Class that creates the plan and arrangement of tables
import java.io.*;
import java.util.Scanner;
public class Planner {
    private int totalTables; // number of tables
    private int totalSeatsPerTable; // number of seats per table
    private int companyLimit; // maximum amount of peolpe from the same company allowed at a table

    private Attendee[] allPeople; // references to everyone attending
    private int totalPeople = 0; // number of people attending starting from 0

    public Planner(int tableAmount, int SeatsPerTableAmount, int limit) throws IOException {
        totalTables = tableAmount;
        totalSeatsPerTable = SeatsPerTableAmount;
        companyLimit = limit;
        allPeople = new Attendee[tableAmount * SeatsPerTableAmount];

        if (!createPeopleList()) {
            System.out.println("Too many people, unable to make table arrangement");
            return;
        }
    }

    // fills out allPeople array with all attendees in names.csv
    private boolean createPeopleList() {
        File list = new File("testSheet.csv");
        try {
            Scanner scanName = new Scanner(list);
            int totalSpots = totalTables * totalSeatsPerTable;

            while(scanName.hasNext()) {
                totalPeople++;
                if (totalPeople == totalSpots) {
                    return false;
                }
                allPeople[totalPeople - 1] = new Attendee(scanName.nextLine());
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}