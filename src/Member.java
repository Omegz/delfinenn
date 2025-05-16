// Yasmine
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member {
    // tilfojet vores variabler
    private final int id;
    private final String name;
    private final int age;
    private final MembershipType type;
    private boolean paid = false;
    private final List<TimeRecord> results = new ArrayList<>();

    // tilfojet vores konstruktor

    public Member(int id, String name, int age, MembershipType type) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.type = type;
    }
    // tilfojet vores setters
    public int getId() { return id; }
    public int getAge() { return age; }
    public MembershipType getType() { return type; }

    // tilfojet vores betalings status
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }

    // performance
    public void addResult(TimeRecord r) { results.add(r); }
    public List<TimeRecord> getResults() { return Collections.unmodifiableList(results); }

    // Returnerer den bedste (laveste) tid i en bestemt disciplin
    public int bestTime(Discipline d) {
        return results.stream()
                .filter(r -> r.discipline() == d)         // Beholder kun resultater fra den ønskede disciplin
                .mapToInt(TimeRecord::timeMillis)         // Ekstraherer tiden i millisekunder
                .min()                                    // Finder den laveste tid
                .orElse(Integer.MAX_VALUE);               // Hvis der ikke er nogen tid: returnér "uendelig"
    }


    @Override
    public String toString() {
        return id + ": " + name + " (" + age + ", " + type + ")" + (paid ? " [Paid]" : " [Unpaid]");
    }
}