// Class that defines table objects
public class Table {
    private int tableNum; // Number assigned to table
    private int totalSeats; // number of seats
    private Attendee[] people; // array of references to attendees
    private int totalPeople = 0; // number of people at table starting from 0
    private String[] companies; // array that converts company number to company name

    public Table(int num, int seats, String[] companies) {
        tableNum = num;
        totalSeats = seats;
        people = new Attendee[seats];
        this.companies = companies;
    }
    
    // returns whether there is a seat available at table
    public boolean seatAvail() {
       return totalPeople < totalSeats - 1;
    }

    // adds someone to the table
    public void addPerson(Attendee a) {
        people[totalPeople] = a;
        totalPeople++;
    }

    // returns the amount of people from company c at the table
    public int amountFromCompany(int c) {
        int output = 0;
        for (int i = 0; i < totalPeople; i++) {
            if (people[i].getCompanyNum() == c) {
                output++;
            }
        }
        return output;
    }

    // returns a string of people from a specific company at the table
    public String peopleFromCompany(String name) {
        String output = "";
        for (int i = 0; i < totalPeople; i++) {
            if (companies[people[i].getCompanyNum() - 1].equals(name)) {
                output += people[i].getFullName() + " - Table " + tableNum + "\n";
            }
        }
        return output;
    }

    // returns whether the table has someone called name
    public boolean hasPerson(String name) {
        for (int i = 0; i < totalPeople; i++) {
            if (people[i].getFullName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // returns who is sitting at the table
    public String allPeople() {
        String names = "Table " + tableNum + ":\n";
        for (int i = 0; i < totalPeople; i++) {
            names += people[i].getFullName() + " - " + companies[people[i].getCompanyNum() - 1] + "\n";
        }
        return names;
    }
}