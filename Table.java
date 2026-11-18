// Class that defines table objects
public class Table {
    private int totalSeats; // number of seats
    private Attendee[] people; // array of references to attendees
    private int totalPeople = 0; // number of people at table starting from 0

    public Table(int seats) {
        totalSeats = seats;
        people = new Attendee[seats];
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
            if (people[i].getCompanyNum == c) {
                output++;
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
        String names = "";
        for (int i = 0; i < totalPeople; i++) {
            names += people[i].getFullName();
            if (i != totalPeople - 1) {
                names += ", ";
            }
        }
        return names;
    }
}