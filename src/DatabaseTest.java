import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    static String db1 = "CREATE TABLE cat (Name, Breed, Owner, Weight)\n" +
            "INSERT INTO cat VALUES (Meowchacho, Persian, John, 3.65)\n" +
            "INSERT INTO cat VALUES (Meowzilla, Siamese, Jack, 4.01)\n" +
            "INSERT INTO cat VALUES (Fluffington, Balinese, Jack, 4.23)\n" +
            "INSERT INTO cat VALUES (Nibbles, Balinese, Mary, 3.75)\n" +
            "INSERT INTO cat VALUES (Purrlock Holmes, Persian, John, 3.96)\n";

    static String db2 = "CREATE TABLE country (Name, Population, Language)\n" +
            "INSERT INTO country VALUES (Germany, 83000000, German)\n" +
            "INSERT INTO country VALUES (United Kingdom, 67000000, English)\n" +
            "INSERT INTO country VALUES (Mexico, 129000000, Spanish)\n" +
            "INSERT INTO country VALUES (France, 68000000, French)\n" +
            "INSERT INTO country VALUES (United States, 331000000, English)\n" +
            "INSERT INTO country VALUES (Argentina, 45000000, Spanish)\n";

    static String db3 = "CREATE TABLE person (Name, Year, Country)\n" +
            "INSERT INTO person VALUES (Jack, 1980, France)\n" +
            "INSERT INTO person VALUES (Mary, 1990, Mexico)\n" +
            "INSERT INTO person VALUES (John, 1985, Argentina)\n" +
            "INSERT INTO person VALUES (Tom, 2000, Germany)\n" +
            "INSERT INTO person VALUES (Bill, 1990, Germany)\n" +
            "INSERT INTO person VALUES (Tina, 1977, Argentina)\n" +
            "INSERT INTO person VALUES (Magda, 2005, France)\n";

        static String db4 = "CREATE TABLE person (Name)\n" +
                "INSERT INTO person VALUES (Jack)\n" +
                "INSERT INTO person VALUES (Mary)\n" +
                "INSERT INTO person VALUES (John)\n";

        @Test
        public void test00() {
            String instructions = db4 +
                    "SELECT *    FROM person";

            String expected = "Name\n" +
                    "-----\n" +
                    "Jack\n" +
                    "Mary\n" +
                    "John";

            assertEquals(expected, Database2.exec(instructions));
        }

    @Test
    public void test01() {
        String instructions = db1 +
                "SELECT * FROM cat";

        String expected =
                "Name            | Breed    | Owner | Weight\n" +
                "----------------+----------+-------+--------\n" +
                "Meowchacho      | Persian  | John  | 3.65\n" +
                "Meowzilla       | Siamese  | Jack  | 4.01\n" +
                "Fluffington     | Balinese | Jack  | 4.23\n" +
                "Nibbles         | Balinese | Mary  | 3.75\n" +
                "Purrlock Holmes | Persian  | John  | 3.96";

        assertEquals(expected, Database2.exec(instructions));
    }

    @Test
    public void test02() {
        String instructions = db1 +
                "SELECT Name FROM cat";

        String expected =
                "Name\n" +
                "----------------\n" +
                "Meowchacho\n" +
                "Meowzilla\n" +
                "Fluffington\n" +
                "Nibbles\n" +
                "Purrlock Holmes";

        assertEquals(expected, Database2.exec(instructions));
    }

    @Test
    public void test03() {
        String instructions = db1 +
                "SELECT Name, Owner, Weight FROM cat ORDER BY Weight ASC";

        String expected =
                "Name            | Owner | Weight\n" +
                "----------------+-------+--------\n" +
                "Meowchacho      | John  | 3.65\n" +
                "Nibbles         | Mary  | 3.75\n" +
                "Purrlock Holmes | John  | 3.96\n" +
                "Meowzilla       | Jack  | 4.01\n" +
                "Fluffington     | Jack  | 4.23";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test04() {
        String instructions = db1 +
                "SELECT Name, Owner FROM cat ORDER BY Weight ASC";

        String expected =
                "Name            | Owner\n" +
                "----------------+-------\n" +
                "Meowchacho      | John\n" +
                "Nibbles         | Mary\n" +
                "Purrlock Holmes | John\n" +
                "Meowzilla       | Jack\n" +
                "Fluffington     | Jack";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test05() {
        String instructions = db2 +
                "SELECT * FROM country WHERE Language = English";

        String expected =
                "Name           | Population | Language\n" +
                "---------------+------------+----------\n" +
                "United Kingdom | 67000000   | English\n" +
                "United States  | 331000000  | English";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test06() {
        String instructions = db2 +
                "SELECT Name, Population FROM country WHERE Language = Spanish";

        String expected =
                "Name      | Population\n" +
                "----------+------------\n" +
                "Mexico    | 129000000\n" +
                "Argentina | 45000000";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test07() {
        String instructions = db2 +
                "SELECT Name, Population FROM country ORDER BY Population";

        String expected =
                "Name           | Population\n" +
                "---------------+------------\n" +
                "Argentina      | 45000000\n" +
                "United Kingdom | 67000000\n" +
                "France         | 68000000\n" +
                "Germany        | 83000000\n" +
                "Mexico         | 129000000\n" +
                "United States  | 331000000";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test08() {
        String instructions = db2 +
                "SELECT Population, Name FROM country ORDER BY Population DESC";

        String expected =
                "Population | Name\n" +
                "-----------+----------------\n" +
                "331000000  | United States\n" +
                "129000000  | Mexico\n" +
                "83000000   | Germany\n" +
                "68000000   | France\n" +
                "67000000   | United Kingdom\n" +
                "45000000   | Argentina";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test09() {
        String instructions = db2 +
                "SELECT * FROM country WHERE Population > 80000000 ORDER BY Population DESC";

        String expected =
                "Name          | Population | Language\n" +
                "--------------+------------+----------\n" +
                "United States | 331000000  | English\n" +
                "Mexico        | 129000000  | Spanish\n" +
                "Germany       | 83000000   | German";

        assertEquals(expected, Database.exec(instructions));
    }


    @Test
    public void test10() {
        String instructions = db3 +
                "SELECT * FROM person ORDER BY Country";

        String expected =
                "Name  | Year | Country\n" +
                "------+------+-----------\n" +
                "John  | 1985 | Argentina\n" +
                "Tina  | 1977 | Argentina\n" +
                "Jack  | 1980 | France\n" +
                "Magda | 2005 | France\n" +
                "Tom   | 2000 | Germany\n" +
                "Bill  | 1990 | Germany\n" +
                "Mary  | 1990 | Mexico";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test11() {
        String instructions = db3 +
                "SELECT * FROM person ORDER BY Name";

        String expected =
                "Name  | Year | Country\n" +
                "------+------+-----------\n" +
                "Bill  | 1990 | Germany\n" +
                "Jack  | 1980 | France\n" +
                "John  | 1985 | Argentina\n" +
                "Magda | 2005 | France\n" +
                "Mary  | 1990 | Mexico\n" +
                "Tina  | 1977 | Argentina\n" +
                "Tom   | 2000 | Germany";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test12() {
        String instructions = db3 +
                "SELECT * FROM person ORDER BY Year DESC";

        String expected =
                "Name  | Year | Country\n" +
                "------+------+-----------\n" +
                "Magda | 2005 | France\n" +
                "Tom   | 2000 | Germany\n" +
                "Mary  | 1990 | Mexico\n" +
                "Bill  | 1990 | Germany\n" +
                "John  | 1985 | Argentina\n" +
                "Jack  | 1980 | France\n" +
                "Tina  | 1977 | Argentina";

        assertEquals(expected, Database.exec(instructions));
    }


    @Test
    public void test13() {
        String instructions = db2 +
                "SELECT * FROM country WHERE Population < 50000000 OR Population > 100000000 ORDER BY Population DESC";

        String expected =
                "Name          | Population | Language\n" +
                "--------------+------------+----------\n" +
                "United States | 331000000  | English\n" +
                "Mexico        | 129000000  | Spanish\n" +
                "Argentina     | 45000000   | Spanish";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test14() {
        String instructions = db2 +
                "SELECT * FROM country WHERE Population < 100000000 AND Language = English";

        String expected =
                "Name           | Population | Language\n" +
                "---------------+------------+----------\n" +
                "United Kingdom | 67000000   | English";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test15() {
        String instructions = db1 +
                "SELECT * FROM cat WHERE Owner = John AND Breed = Persian AND Weight < 3.7";

        String expected =
                "Name       | Breed   | Owner | Weight\n" +
                "-----------+---------+-------+--------\n" +
                "Meowchacho | Persian | John  | 3.65";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test16() {
        String instructions = db3 +
                "SELECT * FROM person WHERE Year < 1979 OR Year > 2001 OR Country = France";

        String expected =
                "Name  | Year | Country\n" +
                "------+------+-----------\n" +
                "Jack  | 1980 | France\n" +
                "Tina  | 1977 | Argentina\n" +
                "Magda | 2005 | France";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test17() {
        String instructions = db1 + db3 +
                "SELECT person.Name, cat.Name, cat.Weight FROM person, cat " +
                "WHERE cat.Owner = person.Name";

        String expected =
                "person.Name | cat.Name        | cat.Weight\n" +
                "------------+-----------------+------------\n" +
                "Jack        | Meowzilla       | 4.01\n" +
                "Jack        | Fluffington     | 4.23\n" +
                "Mary        | Nibbles         | 3.75\n" +
                "John        | Meowchacho      | 3.65\n" +
                "John        | Purrlock Holmes | 3.96";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test18() {
        String instructions = db1 + db2 + db3 +
                "SELECT person.Name, cat.Name, cat.Weight FROM person, cat, country " +
                "WHERE person.Country = country.Name AND cat.Owner = person.Name " +
                "AND country.Population > 50000000";

        String expected =
                "person.Name | cat.Name    | cat.Weight\n" +
                "------------+-------------+------------\n" +
                "Jack        | Meowzilla   | 4.01\n" +
                "Jack        | Fluffington | 4.23\n" +
                "Mary        | Nibbles     | 3.75";

        assertEquals(expected, Database.exec(instructions));
    }

    @Test
    public void test19() {
        String instructions = db1 + db2 + db3 +
                "SELECT cat.*, person.* FROM person, cat, country " +
                "WHERE person.Country = country.Name AND cat.Owner = person.Name " +
                "AND country.Population > 50000000 ORDER BY cat.Weight DESC";

        String expected =
                "cat.Name    | cat.Breed | cat.Owner | cat.Weight | person.Name | person.Year | person.Country\n" +
                "------------+-----------+-----------+------------+-------------+-------------+----------------\n" +
                "Fluffington | Balinese  | Jack      | 4.23       | Jack        | 1980        | France\n" +
                "Meowzilla   | Siamese   | Jack      | 4.01       | Jack        | 1980        | France\n" +
                "Nibbles     | Balinese  | Mary      | 3.75       | Mary        | 1990        | Mexico";

        assertEquals(expected, Database.exec(instructions));
    }
}