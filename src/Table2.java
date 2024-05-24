import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Table2 {
    private String name;
    private String[] columns;
    private List<Fila2> rows;

    Table2(String createInstruction) {
        this.rows = new ArrayList<>();
        int start = createInstruction.indexOf("(") + 1;
        int end = createInstruction.indexOf(")");
        String cols = createInstruction.substring(start, end);
        this.columns = cols.split(", ");
        this.name = createInstruction.split(" ")[2];
    }

    public String getName() {
        return name;
    }

    public String[] getColumns() {
        return columns;
    }

    void insertData(String valuesString) {
        String[] values = valuesString.split(", ");
        Fila2 fila = new Fila2(values);
        rows.add(fila);
    }

    public String selectColFilter(String[] selectedColumns, List<String> whereConditions, String orderByColumn, boolean isAscending) {
        List<Fila2> filteredRows = new ArrayList<>();
        for (Fila2 row : rows) {
            boolean match = evaluateWhereConditions(row, whereConditions);
            if (match) {
                filteredRows.add(row);
            }
        }

        int[] maxLengths = new int[selectedColumns.length];
        for (int i = 0; i < selectedColumns.length; i++) {
            int colIndex = getColumnIndex(selectedColumns[i]);
            maxLengths[i] = columns[colIndex].length();
        }

        for (Fila2 row : filteredRows) {
            for (int i = 0; i < selectedColumns.length; i++) {
                String value = row.getValue(selectedColumns[i], this);
                maxLengths[i] = Math.max(maxLengths[i], value.length());
            }
        }

        if (!orderByColumn.isEmpty()) {
            int orderColIndex = getColumnIndex(orderByColumn);
            Comparator<Fila2> comparator = new Comparator<>() {
                @Override
                public int compare(Fila2 row1, Fila2 row2) {
                    String value1 = row1.getValue(orderByColumn, Table2.this);
                    String value2 = row2.getValue(orderByColumn, Table2.this);

                    if (isNumericColumn(orderColIndex)) {
                        Double num1 = Double.parseDouble(value1);
                        Double num2 = Double.parseDouble(value2);
                        return isAscending ? num1.compareTo(num2) : num2.compareTo(num1);
                    } else {
                        return isAscending ? value1.compareTo(value2) : value2.compareTo(value1);
                    }
                }
            };

            filteredRows.sort(comparator);
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < selectedColumns.length; i++) {
            result.append(String.format("%-" + maxLengths[i] + "s | ", selectedColumns[i]));
        }
        result.delete(result.length() - 3, result.length()).append("\n");

        for (int i = 0; i < selectedColumns.length; i++) {
            result.append("-".repeat(maxLengths[i])).append(i < selectedColumns.length - 1 ? "-+-" : "-");
        }
        result.append("\n");

        for (Fila2 row : filteredRows) {
            for (int i = 0; i < selectedColumns.length; i++) {
                String value = row.getValue(selectedColumns[i], this);
                result.append(String.format("%-" + maxLengths[i] + "s | ", value));
            }
            result.delete(result.length() - 3, result.length()).append("\n");
        }

        String[] lines = result.toString().split("\n");
        StringBuilder finalResult = new StringBuilder();

        for (String line : lines) {
            finalResult.append(line.trim()).append("\n");
        }

        return finalResult.toString().trim();
    }

    private boolean evaluateWhereConditions(Fila2 row, List<String> whereConditions) {
        for (String condition : whereConditions) {
            String[] orConditions = condition.split("\\s+OR\\s+");
            boolean orMatch = false;
            for (String orCondition : orConditions) {
                String[] andConditions = orCondition.split("\\s+AND\\s+");
                boolean andMatch = true;
                for (String andCondition : andConditions) {
                    if (!evaluateCondition(row, andCondition.trim())) {
                        andMatch = false;
                        break;
                    }
                }
                if (andMatch) {
                    orMatch = true;
                    break;
                }
            }
            if (!orMatch) {
                return false;
            }
        }
        return true;
    }


    private boolean evaluateCondition(Fila2 row, String condition) {
        String[] parts = condition.split(" ");
        String column = parts[0].trim();
        String operator = parts[1].trim();
        String value = parts[2].trim();

        String columnValue = row.getValue(column, this);

        if (isNumeric(columnValue) && isNumeric(value)) {
            double columnNum = Double.parseDouble(columnValue);
            double valueNum = Double.parseDouble(value);

            switch (operator) {
                case "=":
                    return columnNum == valueNum;
                case ">":
                    return columnNum > valueNum;
                case "<":
                    return columnNum < valueNum;
                case ">=":
                    return columnNum >= valueNum;
                case "<=":
                    return columnNum <= valueNum;
                default:
                    return false;
            }
        } else {
            switch (operator) {
                case "=":
                    return columnValue.equals(value);
                case ">":
                    return columnValue.compareTo(value) > 0;
                case "<":
                    return columnValue.compareTo(value) < 0;
                case ">=":
                    return columnValue.compareTo(value) >= 0;
                case "<=":
                    return columnValue.compareTo(value) <= 0;
                default:
                    return false;
            }
        }
    }

    private boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private boolean isNumericColumn(int colIndex) {
        for (Fila2 row : rows) {
            String value = row.getValues().get(colIndex);
            if (!value.matches("\\d+(\\.\\d+)?")) {
                return false;
            }
        }
        return true;
    }

    public int getColumnIndex(String columnName) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals(columnName)) {
                return i;
            }
        }
        return 0;
    }
}