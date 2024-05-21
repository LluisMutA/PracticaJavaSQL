import java.util.ArrayList;
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

    public String selectColumnsWithFilterAndOrder(String[] selectedColumns, String whereColumn, String whereValue, String orderByColumn, boolean isAscending) {
        // Filtrar filas según la cláusula WHERE
        List<Fila2> filteredRows = new ArrayList<>();
        for (Fila2 row : rows) {
            if (whereColumn.isEmpty() || row.getValue(whereColumn, this).equals(whereValue)) {
                filteredRows.add(row);
            }
        }

        // Calcular el ancho máximo de cada columna seleccionada
        int[] maxLengths = new int[selectedColumns.length];
        for (int i = 0; i < selectedColumns.length; i++) {
            int colIndex = getColumnIndex(selectedColumns[i]);
            if (colIndex == -1) {
                throw new IllegalArgumentException("Columna no encontrada: " + selectedColumns[i]);
            }
            maxLengths[i] = columns[colIndex].length();
        }

        for (Fila2 row : filteredRows) {
            for (int i = 0; i < selectedColumns.length; i++) {
                String value = row.getValue(selectedColumns[i], this);
                maxLengths[i] = Math.max(maxLengths[i], value.length());
            }
        }

        // Ordenar las filas si se ha especificado una columna para ordenar
        if (!orderByColumn.isEmpty()) {
            int orderColIndex = getColumnIndex(orderByColumn);
            filteredRows.sort((row1, row2) -> {
                String value1 = row1.getValue(orderByColumn, this);
                String value2 = row2.getValue(orderByColumn, this);

                if (isNumericColumn(orderColIndex)) {
                    Double num1 = Double.parseDouble(value1);
                    Double num2 = Double.parseDouble(value2);
                    return isAscending ? num1.compareTo(num2) : num2.compareTo(num1);
                } else {
                    return isAscending ? value1.compareTo(value2) : value2.compareTo(value1);
                }
            });
        }

        // Construir el resultado con formato
        StringBuilder result = new StringBuilder();

        // Añadir encabezados de columna
        for (int i = 0; i < selectedColumns.length; i++) {
            result.append(String.format("%-" + maxLengths[i] + "s | ", selectedColumns[i]));
        }
        result.delete(result.length() - 3, result.length()).append("\n");

        // Añadir separadores
        for (int i = 0; i < selectedColumns.length; i++) {
            result.append("-".repeat(maxLengths[i])).append(i < selectedColumns.length - 1 ? "-+-" : "-");
        }
        result.append("\n");

        // Añadir filas
        for (Fila2 row : filteredRows) {
            for (int i = 0; i < selectedColumns.length; i++) {
                String value = row.getValue(selectedColumns[i], this);
                result.append(String.format("%-" + maxLengths[i] + "s | ", value));
            }
            result.delete(result.length() - 3, result.length()).append("\n");
        }

        // Eliminar los espacios finales de las filas
        String formattedResult = result.toString();
        String[] lines = formattedResult.split("\n");
        StringBuilder finalResult = new StringBuilder();

        for (String line : lines) {
            finalResult.append(line.stripTrailing()).append("\n");
        }

        return finalResult.toString().trim();
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
        return -1; // Si no se encuentra la columna, retorna -1
    }
}