import javax.print.attribute.HashPrintServiceAttributeSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Table {
    String name;
    String[]  capcaleres;
    List<Fila> files;

    Table(String sentenciaCreate) {
        System.out.println("CREATE: " + sentenciaCreate);
        int start = sentenciaCreate.indexOf("(")+1;
        int end = sentenciaCreate.indexOf(")");
        String cols = sentenciaCreate.substring(start, end);
        this.name = sentenciaCreate.split(" ")[2];
        this.capcaleres = cols.split(", ");
        System.out.println(Arrays.toString(capcaleres));

        this.files = new ArrayList<>();
    }

    void insertData(String valorsSeparatsPerComa) {
        System.out.println(valorsSeparatsPerComa);
        // Separar per ", "
        String[] valors = valorsSeparatsPerComa.split(", ");
        // Generar una nova fila amb aquests valors
        Fila f = new Fila(valors);
        // Afegir la fila a la llista "files"
        files.add(f);
    }

}
