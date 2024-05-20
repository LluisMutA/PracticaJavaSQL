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
