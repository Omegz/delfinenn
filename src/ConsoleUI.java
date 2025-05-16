import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {
    private final ClubController controller = new ClubController(new StandardFeeCalculator());
    private final Scanner sc = new Scanner(System.in);
    private boolean running = true;
    // Ninah
    public void start() {
        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 -> addMember();
                    case 2 -> calcFeeAndSendLink();
                    case 3 -> System.out.println("Expected total: " + controller.expectedTotal() + " kr\n");
                    case 4 -> controller.arrears().forEach(System.out::println);
                    case 5 -> addPerformance();
                    case 6 -> listTopPerformers();
                    case 0 -> running = false;
                    default -> System.out.println(" Invalid menu choice\n");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a number.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("================ Delfinen Menu ================");
        System.out.println("1) Add member");
        System.out.println("2) Calculate fee & send subscription link");
        System.out.println("3) Show expected total fee income");
        System.out.println("4) List members who haven't paid");
        System.out.println("5) Add performance result for swimmer");
        System.out.println("6) Show top 5 swimmers per discipline");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    // yasmine
    private void addMember() {
        try {
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Age: ");
            int age = Integer.parseInt(sc.nextLine());
            System.out.print("Active (a) or Passive (p): ");
            String ap = sc.nextLine().trim().toLowerCase();
            MembershipType type = ("p".equals(ap)) ? MembershipType.PASSIVE : (age < 18 ? MembershipType.ACTIVE_JUNIOR : MembershipType.ACTIVE_SENIOR);
            int id = controller.addMember(name, age, type);
            System.out.println("✔ Member added with id " + id + "\n");
        } catch (NumberFormatException e) {
            System.out.println(" Age must be a number.\n");
        }
    }

    // Raged
    private void calcFeeAndSendLink() {
        try {
            System.out.print("Member id: ");
            int id = Integer.parseInt(sc.nextLine());
            int fee = controller.calcFee(id);
            System.out.println("Fee: " + fee + " kr (dummy payment link emailed)\n");
            controller.markPaid(id);
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (NumberFormatException e) {
            System.out.println(" Id must be a number.\n");
        }
    }
    // omar
    private void addPerformance() {
        try {
            System.out.print("Member id: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Discipline (BUTTERFLY/CRAWL/BACKSTROKE/BREASTSTROKE): ");
            Discipline d = Discipline.valueOf(sc.nextLine().trim().toUpperCase());
            System.out.print("Time in milliseconds: ");
            int ms = Integer.parseInt(sc.nextLine());
            controller.addPerformance(id, d, ms);
            System.out.println(" Result saved\n");
        } catch (IllegalArgumentException e) {
            System.out.println(" Invalid discipline or number format.\n");
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }


    // Denne metode viser en liste over de 5 hurtigste medlemmer i en valgt disciplin
    private void listTopPerformers() {
        try {
            System.out.print("Discipline: ");
            // Brugeren bliver bedt om at indtaste en disciplin (fx BUTTERFLY, CRAWL osv.)

            Discipline d = Discipline.valueOf(sc.nextLine().trim().toUpperCase());
            // sc.nextLine() læser brugerens input som tekst
            // .trim() fjerner evt. mellemrum før og efter
            // .toUpperCase() konverterer inputtet til store bogstaver
            // Discipline.valueOf(...) forsøger at matche input til en værdi i enum-typen Discipline
            // Hvis inputtet ikke matcher en eksisterende disciplin, kastes en IllegalArgumentException

            controller.top5(d).forEach(System.out::println);
            // Hvis disciplinen er gyldig, kalder vi controllerens top5-metode
            // Den returnerer en liste over de 5 hurtigste medlemmer i den disciplin
            // forEach(System.out::println) udskriver hvert medlem én ad gangen

            System.out.println();
            // Ekstra linjeskift for pænere output

        } catch (IllegalArgumentException e) {
            // Hvis brugeren skrev en disciplin, der ikke findes i enum’en, fanges fejlen her
            System.out.println(" Invalid discipline.\n");
            // Udskriv en fejlbesked og spring resten over
        }
    }

}