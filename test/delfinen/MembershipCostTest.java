package delfinen;
import org.junit.Test;
import static org.junit.Assert.*;

public class MembershipCostTest {

    @Test
    public void testCostUnder18() throws CustomException {
        Member m = new Member("Test", 17, MembershipType.ACTIVE);
        assertEquals(1000.0, m.calculateFee(), 0.001);
    }

    @Test
    public void testCostOver18() throws CustomException {
        Member m = new Member("Test", 30, MembershipType.ACTIVE);
        assertEquals(1600.0, m.calculateFee(), 0.001);
    }

    @Test
    public void testCostSenior() throws CustomException {
        Member m = new Member("Test", 65, MembershipType.ACTIVE);
        assertEquals(1200.0, m.calculateFee(), 0.001); // 25% off 1600
    }

    @Test
    public void testPassive() throws CustomException {
        Member m = new Member("Test", 40, MembershipType.PASSIVE);
        assertEquals(500.0, m.calculateFee(), 0.001);
    }
}
