import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database2 {
    static Map<String, Table2> tables = new HashMap<>();

    public static String exec(String data) {
        String[] instructions = data.split("\n");

        for (String instruction : instructions) {
            if (instruction.startsWith("SELECT")) {
                return doSelect(instruction);
            } else if (instruction.startsWith("CREATE")) {
                createTable(instruction);
            } else if (instruction.startsWith("INSERT INTO")) {
                insertIntoTable(instruction);
            }
        }
        return "";
    }

    private static void createTable(String instruction) {
        Table2 table = new Table2(instruction);
        tables.put(table.getName(), table);
    }

    private static void insertIntoTable(String instruction) {
        String tableName = instruction.split(" ")[2];
        Table2 table = tables.get(tableName);

        int start = instruction.indexOf("(") + 1;
        int end = instruction.indexOf(")");
        String valuesString = instruction.substring(start, end);
        table.insertData(valuesString);
    }

    private static String doSelect(String instruction) {
        String selection = "";

        int start = instruction.indexOf("FROM") + 5;
        //int stop = intruction.indexOf(" ");    ------ Per fer
        String tableName = instruction.substring(start).trim();
        Table2 table = tables.get(tableName);

        // falta if
       // if ((instruction.indexOf("SELECT") + 7) == '*') {
            return table.selectAll(); // SELECT * FROM
        //}
        //return "";
    }
}