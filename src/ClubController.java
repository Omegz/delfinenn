import java.util.*;

// Controller-klassen styrer klubbens medlemmer og deres data
public class ClubController {
    // yasmine
    // Liste over alle medlemmer i klubben
    private final List<Member> members = new ArrayList<>();
    // Bruges til at give hvert medlem et unikt ID
    private int nextId = 1;

    // Raged
    // Reference til en ekstern beregner for kontingent
    private final FeeCalculator feeCalc;

    // Konstruktør: modtager en FeeCalculator og gemmer den
    public ClubController(FeeCalculator calc) {
        this.feeCalc = calc;
    }
    // Yasmine
    // Tilføjer et nyt medlem og tildeler automatisk et unikt ID
    public int addMember(String name, int age, MembershipType type) {
        Member m = new Member(nextId++, name, age, type);
        members.add(m);
        return m.getId(); // Returnerer det tildelte ID
    }

    // Raged
    // Beregner kontingent for et bestemt medlem ved hjælp af dets ID
    public int calcFee(int memberId) throws MemberNotFoundException {
        return feeCalc.calculateFee(get(memberId));
    }


    // Beregner det samlede forventede kontingentbeløb fra alle medlemmer
    public int expectedTotal() {
        return members.stream()
                // .stream() opretter en datastrøm over alle medlemmer i listen "members"
                // Det giver mulighed for at behandle dem én ad gangen med funktionelle operationer

                .mapToInt(feeCalc::calculateFee)
                // .mapToInt(...) anvender feeCalc.calculateFee på hvert medlem
                // Det betyder, at hvert medlem omdannes til et heltal, som er kontingentbeløbet
                // Resultatet er en "IntStream" – en strøm af heltal

                .sum();
        // .sum() lægger alle heltalene i strømmen sammen og returnerer summen
        // Dette svarer til at tilføje kontingentet for hvert enkelt medlem
    }


    // Ninah
    // Returnerer en liste over medlemmer, som endnu ikke har betalt kontingent
    public List<Member> arrears() {
        return members.stream() // Opretter en strøm af alle medlemmer
                .filter(m -> !m.isPaid()) // Filtrerer kun dem, hvor 'isPaid' er false (dvs. de har ikke betalt)
                .toList(); // Samler og returnerer resultatet som en ny liste
    }
    // Markerer et medlems kontingent som betalt
    public void markPaid(int id) throws MemberNotFoundException {
        get(id).setPaid(true);
    }


    // Omar
    // Tilføjer en præstationstid (i millisekunder) for et medlem i en bestemt disciplin
    public void addPerformance(int id, Discipline d, int ms) throws MemberNotFoundException {
        get(id).addResult(new TimeRecord(d, ms));
    }
    // Returnerer en liste over de fem hurtigste medlemmer i en given disciplin
    public List<Member> top5(Discipline d) {
        return members.stream()
                .filter(m -> !m.getResults().isEmpty())
                // Første filter: vi fjerner medlemmer, der slet ikke har nogen registrerede tider
                // getResults() returnerer en liste af tidstagninger – er den tom, er medlemmet udelukket

                .filter(m -> m.bestTime(d) < Integer.MAX_VALUE)
                // Andet filter: vi udelukker medlemmer, der ikke har nogen gyldig tid i den givne disciplin
                // Hvis bestTime(d) returnerer Integer.MAX_VALUE, betyder det, at der ikke findes nogen tid

                .sorted(Comparator.comparingInt(m -> m.bestTime(d)))
                // Sortér medlemmerne efter deres bedste tid i disciplinen (laveste tid er bedst)
                // Comparator.comparingInt(...) bruges til at sammenligne int-værdier

                .limit(5)
                // Vi tager kun de 5 første medlemmer efter sortering – dvs. de 5 hurtigste

                .toList();
        // Konverter resultatstrømmen til en liste og returnér
    }
    // Finder og returnerer det medlem, der har det angivne ID.
    // Hvis der ikke findes et medlem med det ID, kastes en MemberNotFoundException.
    private Member get(int id) throws MemberNotFoundException {
        return members.stream()
                .filter(m -> m.getId() == id)
                // .filter(...) bruges til at finde det medlem, hvis ID matcher det givne id
                // Vi beholder kun medlemmer hvor m.getId() == id

                .findFirst()
                // .findFirst() forsøger at finde det første medlem i strømmen, der opfylder filteret
                // Returnerer en Optional<Member> – dvs. et muligt resultat

                .orElseThrow(() -> new MemberNotFoundException(id));
        // Hvis Optional’en er tom (altså: intet medlem med det ID blev fundet),
        // så kaster vi en brugerdefineret fejl (MemberNotFoundException)
        // Det gør det muligt at håndtere fejlen i fx GUI eller logiklag
    }

}
