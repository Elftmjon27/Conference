// Class that creates the plan and arrangement of tables
import java.io.*;
import java.util.Scanner;
public class Planner {
    private int totalTables; // number of tables
    private int totalSeatsPerTable; // number of seats per table
    private int companyLimit; // maximum amount of peolpe from the same company allowed at a table
    private int totalSeats;

    private Attendee[] allPeople; // references to everyone attending
    private int totalPeople = 0; // number of people attending starting from 0

    private String[] allCompanies; // names of companies
    private int totalCompanies = 0; // number of companies that have number attending

    public Planner(int tableAmount, int SeatsPerTableAmount, int limit)  {
        totalTables = tableAmount;
        totalSeatsPerTable = SeatsPerTableAmount;
        companyLimit = limit;
        totalSeats = tableAmount * SeatsPerTableAmount;
        allPeople = new Attendee[totalSeats]; // one person to one seat
        allCompanies = new String[totalSeats]; // if every company sends one person, then # of companies = # of seats

        int temp = createPeopleList();
        if (temp == 0) {
            System.out.println("Can't find list of people, unable to make table arrangement");
            return;
        } else if (temp == 1) {
            System.out.println("Too many people, unable to make table arrangement");
            return;
        }

        if (!createCompaniesList()) {
            System.out.println("Can't find list of companies, unable to make table arrangement");
            return;
        }

        navigation(new Scanner(System.in));
    }

    // fills out allPeople array with all attendees in names.csv
    // returns 0 if can't find file, 1 if too many people, and 2 if successfully created list
    private int createPeopleList() {
        File list = new File("names.csv");
        try {
            Scanner scanName = new Scanner(list);

            while(scanName.hasNext()) {
                totalPeople++;
                if (totalPeople == totalSeats) {
                    return 1;
                }
                allPeople[totalPeople - 1] = new Attendee(scanName.nextLine());
            }
            scanName.close();
            return 2;
        } catch (FileNotFoundException e) {
            return 0;
        }
    }

    // fills out allCompanies array with all companies with people attending, companies in companies.csv
    // returns true if found companies.csv and successfully created list, false if couldn't find it
    private boolean createCompaniesList() {
        File list = new File("companies.csv");
        try {
            Scanner scanComp = new Scanner(list);
            
            String[] text;
            while(scanComp.hasNext()) {
                text = scanComp.nextLine().split(",");
                allCompanies[totalCompanies] = text[1];
                totalCompanies++;
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }


    private void navigation(Scanner sc) {
        if (getUserInput(sc)) {
            addPerson(sc);
        } else {
            if (!generateTableArrangement()) {
                System.out.println("Unable to generate table arrangement");
            }
            return;
        }
        navigation(sc);
    }

    // returns the user input with boolean (true if want to add name and false if want to see table arrangement)
    private boolean getUserInput(Scanner sc) {
        System.out.println("Do you want to see the table arrangement or add an attendee?");
        String input = sc.nextLine();

        if (input.contains("table") || input.contains("arrangement")) {
            return true;
        } else if (input.contains("add")) {
            return false;
        } else {
            return getUserInput(sc);
        }
    }

    // Asks user for information about person and then adds them to list
    private void addPerson(Scanner sc) {
        System.out.print("First Name: ");
        String firstName = sc.nextLine();
        
        System.out.print("Last Name: ");
        String lastName = sc.nextLine();

        System.out.println("Company: ");
        String company = sc.nextLine();

        if (confirmAddPerson(sc, firstName + " " + lastName, company)) {

        } else {
            addPerson(sc);
            return;
        }
        
        newCompanyCheck(company);
        newPersonCheck(firstName, lastName, company);
    }

    // returns whether user has confirmed that want to add person with name name and from company company
    private boolean confirmAddPerson(Scanner sc, String name, String company) {
        String input;
        while (true) {
            System.out.println("Confirm you want to add the following person:");
            System.out.println("Name: " + name + "\nCompany: " + company);
            System.out.print("Enter y or n");
            input = sc.nextLine();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
        }
    }

    // check whether company c already has someone them from attending, adds company to list if not already
    private void newCompanyCheck(String c) {
        for (int i = 0; i < totalCompanies; i++) {
            if (allCompanies[i].equals(c)) {
                return;
            }
        }
        allCompanies[totalCompanies] = c;
        totalCompanies++;
    }

    // returns whether someone is already attending, adds them to attending list if not already attending
    private boolean newPersonCheck(String firstName, String lastName, String company) {
        for (int i = 0; i < totalPeople; i++) {
            if (allPeople[i].getFullName().equals(firstName + " " + lastName) && allCompanies[allPeople[i].getCompanyNum() - 1].equals(company)) {
                return false;
            }
        }

        // gets the company number
        int companyNum = 0; // 0 is placeholder to avoid compiler complaints
        for (int i = 0; i < totalCompanies; i++) {
            if (allCompanies[i].equals(company)) {
                companyNum = i + 1;
            }
        }

        allPeople[totalPeople] = new Attendee("" + (totalPeople + 1) + "," + lastName + "," + firstName + "," + companyNum);
        totalPeople++;
        return true;
    }

    private boolean generateTableArrangement() {

    }
}