// Class that creates the plan and arrangement of tables
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Planner {
    private int totalTables; // number of tables
    private Table[] tableList; // references to all tables
    
    private int totalSeatsPerTable; // number of seats per table
    private int companyLimit; // maximum amount of peolpe from the same company allowed at a table
    private int totalSeats;

    private Attendee[] allPeople; // references to everyone attending
    private int totalPeople = 0; // number of people attending starting from 0

    private String[] allCompanies; // names of companies
    private int[] companyFrequency; // counts the number of people from a company attending
    private int totalCompanies = 0; // number of companies that have number attending

    public Planner(int tableAmount, int SeatsPerTableAmount, int limit)  {
        totalTables = tableAmount;
        totalSeatsPerTable = SeatsPerTableAmount;
        companyLimit = limit;
        totalSeats = tableAmount * SeatsPerTableAmount;
        allPeople = new Attendee[totalSeats]; // one person to one seat
        allCompanies = new String[totalSeats]; // if every company sends one person, then # of companies = # of seats
        tableList= new Table[totalTables]; // total number of table need that amount of tables
        companyFrequency = new int[totalSeats]; // same size as allCompanies list

        // creates table objects
        for (int i = 0; i < totalTables; i++) {
            tableList[i] = new Table(totalSeatsPerTable);
        }

        // creates 0 entries for company frequency list
        for (int j = 0; j < totalSeats; j++) {
            companyFrequency[j] = 0;
        }

        // creates list of people attending who have already registered
        int temp = createPeopleList();
        if (temp == 0) {
            System.out.println("Can't find list of people, unable to make table arrangement");
            return;
        } else if (temp == 1) {
            System.out.println("Too many people, unable to make table arrangement");
            return;
        }

        // creates list of companies
        if (!createCompaniesList()) {
            System.out.println("Can't find list of companies, unable to make table arrangement");
            return;
        }
        
        navigation1(new Scanner(System.in));
    }

    // fills out allPeople array with all attendees in names.csv
    // returns 0 if can't find file, 1 if too many people, and 2 if successfully created list
    private int createPeopleList() {
        File list = new File("names.csv");
        try {
            Scanner scanName = new Scanner(list);
            Attendee a;

            while(scanName.hasNext()) {
                totalPeople++;
                if (totalPeople == totalSeats) {
                    return 1;
                }
                a = new Attendee(scanName.nextLine());
                allPeople[totalPeople - 1] = a;
                companyFrequency[a.getCompanyNum() - 1]++;
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

    // method that gets what user want to do in the beginning (before making table arrangement)
    private void navigation1(Scanner sc) {
        if (!getUserInput1(sc)) {
            addPerson(sc);
        } else {
            if (!generateTableArrangement()) {
                System.out.println("Unable to generate table arrangement");
                return;
            }
            navigation2(sc);
        }
        navigation1(sc);
    }

    // returns the user input with boolean (true if want to add name and false if want to see table arrangement), used in navigation1()
    private boolean getUserInput1(Scanner sc) {
        System.out.println("Do you want to see the table arrangement or add an attendee?");
        String input = sc.nextLine();

        if (input.contains("table") || input.contains("arrangement")) {
            return true;
        } else if (input.contains("add")) {
            return false;
        } else {
            return getUserInput1(sc);
        }
    }

    // method that gets what user want to do after making the table arrangement
    private void navigation2(Scanner sc) {
        int n = getUserInput2(sc);

        if (n == 0) {
            for (int i = 0; i < totalTables; i++) {
                System.out.print("Table " + (i + 1) + ":\n" + tableList[i].allPeople() + "\n");
            }
        } else if (n == 1) {
            int a = getUserInt(sc);
            System.out.print("Table " + (a + 1) + ":" + tableList[a].allPeople());
        } else if (n == 2) {
            System.out.print("Enter full name: ");
            String name = sc.nextLine();
            boolean found = false;
            
            for (int j = 0 ; j < totalTables && !found; j++) {
                if (tableList[j].hasPerson(name)) {
                    System.out.println(name + " is at table " + (j + 1));
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Unable to find the table where " + name + " is supposed to be");
            }
        }

        navigation2(sc);
    }

    private int getUserInput2(Scanner sc) {
        System.out.println("Do you want to print the table arrangement, see all guests at an indiviudal table, or find where a guest is sitting");
        String input = sc.nextLine();

        if (input.contains("print")) {
            return 0;
        } else if (input.contains("guests") || input.contains("individual table")) {
            return 1;
        } else if (input.contains("guest")) {
            return 2;
        }
        return getUserInput2(sc);
    }

    // returns the int user enters for table number, used in navigation2()
    private int getUserInt(Scanner sc) {
        System.out.print("Enter the table you want to see: ");
        String input;
        int n;
        while (true) {
            input = sc.nextLine();
            try {
                n = Integer.parseInt(input);
                if (n >= 1 && n <= totalTables) {
                    return n - 1;
                }
            } catch (NumberFormatException e) {}
        }
    }

    // Asks user for information about person and then adds them to list
    private void addPerson(Scanner sc) {
        System.out.print("First Name: ");
        String firstName = sc.nextLine();
        
        System.out.print("Last Name: ");
        String lastName = sc.nextLine();

        System.out.print("Company: ");
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
            System.out.print("Enter y or n: ");
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

    // returns whether a proper table arrangment can be created, if one can exist, then it make it
    private boolean generateTableArrangement() {
        // Checking that every company isn't sending to many people such that it would prevent the limit of x people from same company per table
        int maxCompanyAttendees = companyLimit * totalTables;
        for (int j = 0; j < totalCompanies; j++) {
            if (companyFrequency[j] > maxCompanyAttendees) {
                return false;
            }
        }
        // System.out.println("all companies satisfy the limit\n");
        return spreadAlgorithm();
        //return greedyAlgorithm() || spreadAlgorithm() || correctionAlgorithm();
    }

    /* Algorithm for creating a table arrangement
    Starts with first table, puts people there until either table full or too many people from same company at table is met, then moves to next table
    If table full, it ignores it and does the same approach to the next table
    Returns true if algorithm successful, and false if it fails
    */
   /* Unused, so commented
    private boolean greedyAlgorithm() {
        System.out.println("Greedy Algorithm -----------------------");
        int firstFreeTableIndex = 0; // index of the first table that has available seats
        int varyIndex;
        int companyNum;
        Table t;
        Attendee a;
        for (int i = 0; i < totalPeople; i++) {
            a = allPeople[i];
            System.out.println("Current Attendee = "  + a.getFullName());
            companyNum = a.getCompanyNum();
            System.out.println("Company Number of person = " + a.getCompanyNum());
            varyIndex = firstFreeTableIndex;
            System.out.println("varyIndex = " + varyIndex);
            t = tableList[varyIndex];
            System.out.println("Table " + (varyIndex + 1));
            System.out.println("People at the table of varyIndex = " + t.allPeople());
            System.out.println("Total tables = " + totalTables);
            
            // finds table that hasn't reached the limit of x people from same company at a table
            while(t.amountFromCompany(companyNum) == companyLimit) {
                varyIndex++;
                if (varyIndex == totalTables) { // only equals totalTables if has checked last table
                    System.out.println("\nNo more tables left b/c varyIndex = " + varyIndex);
                    return false;
                } else {
                    t = tableList[varyIndex];
                    System.out.println("Table " + (varyIndex + 1));
                    System.out.println("People at the table of varyIndex = " + t.allPeople());
                    System.out.println("Total tables = " + totalTables);
                }
            }

            t.addPerson(a); // adds person a to the table
            System.out.println();

            if (!t.seatAvail()) {
                firstFreeTableIndex++; // updates variable is table has become full
                if (firstFreeTableIndex == totalTables) { // if out of tables, checks whether put everyone at a table
                    if (i == totalPeople - 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    */

    /* Algorithm for creating table arrangement
    Assigns seats at table one by one (ex: at some instance, every table will have one person)
    If assigning a seat will break the limit of the amount of the same people at the company, put that person at the next available table
    Returns true if algorithm successful, and false if it fails 
    */
    private boolean spreadAlgorithm() {
        // System.out.println("\n\nSpread Algorithm -----------------------------------\n\n");
        int firstFreeTableIndex = 0; // base index
        int varyIndex;
        int companyNum;
        Table t;
        Attendee a;
        int[] size = new int[totalTables];
        for (int s = 0; s < totalTables; s++) {
            size[s] = 0;
            tableList[s] = new Table(totalSeatsPerTable); // resets tables from greedy algorithm
        }

        for (int i = 0; i < totalPeople; i++) {
            a = allPeople[i];
            companyNum = a.getCompanyNum();
            varyIndex = firstFreeTableIndex;
            t = tableList[varyIndex];
            /*
            System.out.println("Current Attendee = "  + a.getFullName());
            System.out.println("Company Number of person = " + a.getCompanyNum());
            System.out.println("varyIndex = " + varyIndex);
            System.out.println("Table " + (varyIndex + 1));
            System.out.println("People at the table of varyIndex = " + t.allPeople());
            System.out.println("Total tables = " + totalTables);
            */

            // Finds available table
            while(t.amountFromCompany(companyNum) == companyLimit) {
                do {
                    varyIndex = (varyIndex + 1) % (totalTables);
                    //System.out.println("New varyIndex = " + varyIndex);
                    if (varyIndex == firstFreeTableIndex) { // only equals if varyIndex has looped through all tables and non satisfactory
                        System.out.println("\nNo more tables left b/c varyIndex = " + varyIndex);
                        return false;
                    } else {
                    t = tableList[varyIndex];
                        /*
                        System.out.println("Table " + (varyIndex + 1));
                        System.out.println("People at the table of varyIndex = " + t.allPeople());
                        System.out.println("Total tables = " + totalTables); 
                        */
                    }
                } while (size[varyIndex] == totalSeatsPerTable); // skips tables that are already full
                
            }

            //System.out.println();
            t.addPerson(a);
            size[varyIndex]++;
            firstFreeTableIndex = (firstFreeTableIndex + 1) % (totalTables);
        }

        return true;
    }

    /* Algorithm for creating table arrangement
    Populates every table with people without checking for limit of amount of people at the same company at the same table
    Once everyone assigned, looks at each table and swaps people such that the limit is satisfied
    Returns true if algorithm successful, and false if it fails
    */
   /* Unused, so commented
   private boolean correctionAlgorithm() {
        System.out.println("TestS");

        return true;
   }
   */
}
