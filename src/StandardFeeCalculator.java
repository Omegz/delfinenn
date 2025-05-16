// Raged
public class StandardFeeCalculator implements FeeCalculator {
    @Override
    public int calculateFee(Member m) {
        return switch (m.getType()) {
            case ACTIVE_JUNIOR -> 1000;
            case ACTIVE_SENIOR -> (m.getAge() >= 60 ? 1200 : 1600);
            case PASSIVE -> 500;
        };
    }
}