// Class that creates the plan and arrangement of tables
public class Planner {
    private int totalTables; // number of tables
    private int totalSeatsPerTable; // number of seats per table
    private int companyLimit; // maximum amount of peolpe from the same company allowed at a table

    public Planner(int tableAmount, int SeatsPerTableAmount, int limit) {
        totalTables = tableAmount;
        totalSeatsPerTable = SeatsPerTableAmount;
        companyLimit = limit;
    }
}