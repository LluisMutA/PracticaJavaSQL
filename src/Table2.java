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


    void insertData(String valuesString) {
        String[] values = valuesString.split(", ");
        Fila2 fila = new Fila2(values);
        rows.add(fila);
    }

    public String selectAll() {
        // amplada columna
        int[] maxLengths = new int[columns.length];
        for (int i = 0; i < columns.length; i++) {
            maxLengths[i] = columns[i].length();
        }

        for (Fila2 row : rows) {
            List<String> values = row.getValues();
            for (int i = 0; i < values.size(); i++) {
                maxLengths[i] = Math.max(maxLengths[i], values.get(i).length());
            }
        }
        StringBuilder result = new StringBuilder();

        // capçalera
        for (int i = 0; i < columns.length; i++) {
            result.append(String.format("%-" + maxLengths[i] + "s | ", columns[i]));
        }
        result.delete(result.length() - 3, result.length()).append("\n");


        for (int i = 0; i < columns.length; i++) {
            result.append("-".repeat(maxLengths[i])).append("-+-");
        }
        result.delete(result.length() - 2, result.length()).append("\n");

        // afegir files
        for (Fila2 row : rows) {
            List<String> values = row.getValues();
            for (int i = 0; i < values.size(); i++) {
                result.append(String.format("%-" + maxLengths[i] + "s | ", values.get(i)));
            }
            result.delete(result.length() - 3, result.length()).append("\n");
        }

        // trim de files
        String formattedResult = result.toString();
        String[] lines = formattedResult.split("\n");
        StringBuilder finalResult = new StringBuilder();

        for (String line : lines) {
            finalResult.append(line.stripTrailing()).append("\n");
        }

        return finalResult.toString().trim();
    }

    public String selectColumns(String[] selectedColumns) {
        // Calcular el ancho máximo de cada columna seleccionada
        int[] maxLengths = new int[selectedColumns.length];
        for (int i = 0; i < selectedColumns.length; i++) {
            for (int j = 0; j < columns.length; j++) {
                if (columns[j].equals(selectedColumns[i])) {
                    maxLengths[i] = columns[j].length();
                    break;
                }
            }
        }

        for (Fila2 row : rows) {
            for (int i = 0; i < selectedColumns.length; i++) {
                String value = row.getValue(selectedColumns[i], this);
                maxLengths[i] = Math.max(maxLengths[i], value.length());
            }
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
            result.append("-".repeat(maxLengths[i])).append("--+");
        }
        result.delete(result.length() - 2, result.length()).append("\n");

        // Añadir filas
        for (Fila2 row : rows) {
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



    public int getColumnIndex(String column) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals(column)) {
                return i;
            }
        }
        return -1; // No debería suceder si se usa correctamente
    }


    public String selectChoose(){
        // futur return
        StringBuilder result = new StringBuilder();

        // trim de files
        String formattedResult = result.toString();
        String[] lines = formattedResult.split("\n");
        StringBuilder finalResult = new StringBuilder();

        return finalResult.toString().trim();
    }
}
