
/**
 * Placeholder implementation of IFlight interface for backend testing.
 */
public class FlightBDTesting implements IFlight {

    private String originCode;
    private String destinationCode;
    private double cost;
    private double distance;

    public FlightBDTesting(String originCode, String destinationCode, double cost, double distance) {
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.cost = cost;
        this.distance = distance;
    }

    @Override
    public String getOriginCode() {
        return originCode;
    }

    @Override
    public String getDestinationCode() {
        return destinationCode;
    }

    @Override
    public Double getFlightCost() {
        return cost;
    }

    @Override
    public Double getFlightDistance() {
        return distance;
    }
}
