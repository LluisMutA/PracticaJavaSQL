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
        String orderByColumn = "";
        String whereColumn = "";
        String whereValue = "";
        boolean isAscending = true;

        int startSelec = instruction.indexOf("SELECT") + 7;
        int endSelec = instruction.indexOf("FROM");
        selection = instruction.substring(startSelec, endSelec).trim();

        int start = instruction.indexOf("FROM") + 5;
        int end = instruction.indexOf("ORDER BY");
        if (end == -1) {
            end = instruction.indexOf("WHERE");
        }
        if (end == -1) {
            tableName = instruction.substring(start).trim();
        } else {
            tableName = instruction.substring(start, end).trim();
        }

        if (tableName.contains(" ")) {
            tableName = tableName.substring(0, tableName.indexOf(" "));
        }

        Table2 table = tables.get(tableName);

        if (instruction.contains("WHERE")) {
            start = instruction.indexOf("WHERE") + 6;
            end = instruction.indexOf("ORDER BY");
            if (end == -1) {
                end = instruction.length();
            }
            String whereClause = instruction.substring(start, end).trim();
            String[] whereParts = whereClause.split(" = ");
            whereColumn = whereParts[0].trim();
            whereValue = whereParts[1].trim();
        }

        if (instruction.contains("ORDER BY")) {
            start = instruction.indexOf("ORDER BY") + 9;
            String orderClause = instruction.substring(start).trim();
            if (orderClause.endsWith("DESC")) {
                isAscending = false;
                orderByColumn = orderClause.substring(0, orderClause.length() - 4).trim();
            } else if (orderClause.endsWith("ASC")) {
                orderByColumn = orderClause.substring(0, orderClause.length() - 3).trim();
            } else {
                orderByColumn = orderClause.trim();
            }
        }

        String[] selectedColumns;
        if (selection.equals("*")) {
            selectedColumns = table.getColumns();
        } else {
            selectedColumns = selection.split(", ");
        }

        return table.selectColumnsWithFilterAndOrder(selectedColumns, whereColumn, whereValue, orderByColumn, isAscending);
    }
}