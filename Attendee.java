// Class that defines attendee objects, based on names.csv format
public class Attendee {
    private int rank;
    private String lastName;
    private String firstName;
    private int companyNum;
    private int tableNum; // numbered table attendee is at

    // line is intended to be a line from names.csv, assumes line isn't null
    public Attendee(String line) {
        String[] words = line.split(",");
        rank = Integer.parseInt(words[0]);
        lastName = words[1];
        firstName = words[2];
        companyNum = Integer.parseInt(words[3]);
        //System.out.println("Added " + firstName + " " + lastName);
    }

    public int getRank() {
        return rank;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getCompanyNum() {
        return companyNum;
    }

    public void setTableNum(int n) {
        tableNum = n;
    }

    public int getTableNum() {
        return tableNum;
    }
}