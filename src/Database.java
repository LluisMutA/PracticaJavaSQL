import javax.print.attribute.HashPrintServiceAttributeSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

// clase table
// Llista de columnes
// llista de linees --Array List
// trobam CREATE TABLE - Comença la linea
// seguent valor - tableName
// començam detecció de "(" i indexOf ")"
// separam per paraule cream les columnes "name" (o lo que sigui)
// INSERT INTO (trobam es tableName, sha de detectar quin)
// VALUES començam detecció de "(" i indexOf ")" introduim valor
//
// TODO: un switch per CREATE, INSERT, VALUES i SELECT (*, FROM, ORDER BY, WHERE, DESC, OR, AND, =, >, < )
//
public class Database {

    static Table sqlTable;

    public static String exec(String data) {
        List<String> taula = new ArrayList<>();
        String[] instructions = data.split("\n");

        for (String instruction : instructions) {
            if(instruction.startsWith("SELECT") || (instructions[4] + 3 == "*")) {
                int start = instruction.indexOf("");
                int end = instruction.indexOf("");
                String select = instruction.substring(start, end);
            } else if (instruction.startsWith("CREATE")) {
                sqlTable = new Table(instruction);
            } else if (instruction.startsWith("INSERT INTO")) {
                // TODO: Hauriem de comprovar que estem fent un insert dins la taula correcta!!!!!

                int start = instruction.indexOf("(") + 1;
                int end = instruction.indexOf(")");
                String dadesString = instruction.substring(start, end);
                sqlTable.insertData(dadesString);

             /*
                int start = instruction.indexOf("VALUES") + "VALUES".length() +2;
                int end = instruction.indexOf(")", start);
                String name = instruction.substring(start, end);
                taula.add(name);*/
            }
            if(instruction.startsWith("SELECT")){
                String res = doSelect(instruction);
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Name\n-----\n");
        for (String name : taula) {
            result.append(name).append("\n");
        }

        return result.toString().trim();
    }

    private static String doSelect(String instruction) {
        System.out.println(instruction);
        String nomTaula = "";
        String colSelec = "";
        String selection = "";

        int start = instruction.indexOf("SELECT ") + 6;
        int end = instruction.indexOf("FROM");
        selection = instruction.substring(start, end).trim();
        if(selection.equals("*")){
            // Selecciona tots els camps
            return doSelect2(sqlTable.capcaleres);
        } else {
            // Només seleccionem algunes columnes
            String[] capSleleccionades = selection.split(", ");
            return doSelect2(capSleleccionades);
        }
    }

    private static String doSelect2(String[] capcaleres) {
        System.out.println("-------> " + Arrays.toString(capcaleres));
        
        return null;
    }
}
