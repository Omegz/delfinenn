import java.io.*;
import java.util.List;

public class DataStore {
    // Omar
    // Gemmer en liste til en fil ved hjælp af objektserialisering
    public static <T> void save(String file, List<T> list) throws IOException {
        // Opretter en ObjectOutputStream, som skriver til filen
        // try-with-resources sørger for, at filen automatisk bliver lukket bagefter
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(list); // Skriver hele listen som ét objekt til filen
        }
    }

    // Indlæser en liste fra en fil og returnerer den
    @SuppressWarnings("unchecked") // Vi ved, at castet er sikkert, så vi skjuler advarslen
    public static <T> List<T> load(String file) throws IOException, ClassNotFoundException {
        // Opretter en ObjectInputStream, som læser fra filen
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) in.readObject(); // Læser objektet og caster det til en liste
        }
    }
}
