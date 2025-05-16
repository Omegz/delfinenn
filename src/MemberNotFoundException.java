// Raged
public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(int id) {
        super("Member with id " + id + " not found");
    }
}