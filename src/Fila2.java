import java.util.ArrayList;
import java.util.List;

public class Fila2 {
    private List<String> values;

    public Fila2(String[] values) {
        this.values = new ArrayList<>();
        for (String value : values) {
            this.values.add(value);
        }
    }

    public List<String> getValues() {
        return values;
    }

    public String getValue(String columnName, Table2 table) {
        int columnIndex = table.getColumnIndex(columnName);
        if (columnIndex == -1) {
            throw new IllegalArgumentException("Columna no encontrada: " + columnName);
        }
        return values.get(columnIndex);
    }
}