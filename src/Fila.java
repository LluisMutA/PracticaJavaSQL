import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Fila {
    List<String> dades;

    public  Fila(String[] valors) {
    //    this.dades = valors;
        dades = new LinkedList<>(); // cream la llista d
        for (String contFiles : valors){
            dades.add(contFiles);
            System.out.println(dades);
        }
    }
}
