import java.util.*;

public class Database2 {
    static Map<String, Table2> tables = new HashMap<>();

    public static String exec(String data) {
        tables.clear();
        String[] instructions = data.split("\n");

        StringBuilder result = new StringBuilder();
        for (String instruction : instructions) {
            instruction = instruction.trim();
            if (instruction.startsWith("SELECT")) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(doSelect(instruction));
            } else if (instruction.startsWith("CREATE")) {
                createTable(instruction);
            } else if (instruction.startsWith("INSERT INTO")) {
                insertIntoTable(instruction);
            }
        }
        return result.toString().trim();
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
        List<String> whereConditions = new ArrayList<>();
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
            whereConditions = Arrays.asList(whereClause.split(" AND "));
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

        return table.selectColFilter(selectedColumns, whereConditions, orderByColumn, isAscending);
    }
}
