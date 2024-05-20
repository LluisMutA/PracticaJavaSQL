import java.util.*;

public class Database2 {
    static Map<String, Table2> tables = new LinkedHashMap<>();

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
        String tableName = "";

        int startSelec = instruction.indexOf("SELECT") + 7;
        int endSelec = instruction.indexOf("FROM");
        selection = instruction.substring(startSelec, endSelec).trim();

        int start = instruction.indexOf("FROM") + 5;
        tableName = instruction.substring(start).trim();
        if (tableName.contains(" ")) {
            tableName = tableName.substring(0, tableName.indexOf(" "));
        }
        Table2 table = tables.get(tableName);

        if (selection.equals("*")) {
            return table.selectAll(); // SELECT * FROM
        } else {
            String[] selectedColumns = selection.split(",");
            for (int i = 0; i < selectedColumns.length; i++) {
                selectedColumns[i] = selectedColumns[i].trim();
            }
            return table.selectColumns(selectedColumns);
        }
    }

}