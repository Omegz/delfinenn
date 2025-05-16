import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Simple unit tests for the fee calculation logic.
 */
public class FeeCalculatorTest {
    
    // The calculator we'll use for all our tests
    private final FeeCalculator calculator = new StandardFeeCalculator();
    
    @Test
    public void testJuniorFee() {
        // Create a test member who is a junior
        Member junior = new Member(1, "Test Junior", 15, MembershipType.ACTIVE_JUNIOR);
        
        // Check if the calculated fee is 1000 as expected
        int fee = calculator.calculateFee(junior);
        assertEquals("Junior member should pay 1000", 1000, fee);
    }
    
    @Test
    public void testSeniorUnder60Fee() {
        // Create a test member who is a senior under 60
        Member seniorUnder60 = new Member(2, "Test Senior", 40, MembershipType.ACTIVE_SENIOR);
        
        // Check if the calculated fee is 1600 as expected
        int fee = calculator.calculateFee(seniorUnder60);
        assertEquals("Senior under 60 should pay 1600", 1600, fee);
    }
    
    @Test
    public void testSeniorOver60Fee() {
        // Create a test member who is a senior over 60
        Member seniorOver60 = new Member(3, "Test Senior", 65, MembershipType.ACTIVE_SENIOR);
        
        // Check if the calculated fee is 1200 as expected
        int fee = calculator.calculateFee(seniorOver60);
        assertEquals("Senior over 60 should pay 1200", 1200, fee);
    }
    
    @Test
    public void testPassiveFee() {
        // Create a test member who is passive
        Member passive = new Member(4, "Test Passive", 30, MembershipType.PASSIVE);
        
        // Check if the calculated fee is 500 as expected
        int fee = calculator.calculateFee(passive);
        assertEquals("Passive member should pay 500", 500, fee);
    }
}
