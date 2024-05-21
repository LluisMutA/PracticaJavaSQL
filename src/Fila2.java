import java.util.Arrays;
import java.util.List;

public class Fila2 {
    private List<String> values;

    Fila2(String[] values) {
        this.values = Arrays.asList(values);
    }

    public List<String> getValues() {
        return values;
    }

    public String getValue(String column, Table2 table) {
        int columnIndex = table.getColumnIndex(column);
        return values.get(columnIndex);
    }
}