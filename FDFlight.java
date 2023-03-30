public class FDFlight implements IFlight {
    private String origin;
    private String destination;
    private Double cost;
    private Double distance;

    /**
     * Constructs a BigMac object, given data about the BigMac object.
     * 
     * @param year       year of BigMac
     * @param country    country of BigMac
     * @param priceUSD   price in USD of BigMac
     * @param localPrice local prince of BigMac
     */
    public FDFlight(String origin, String destination, Double cost, Double distance) {
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.distance = distance;
    }

    @Override
    public String getOriginCode() {
        return this.origin;
    }

    @Override
    public String getDestinationCode() {
        return this.destination;
    }

    @Override
    public Double getFlightCost() {
        return this.cost;
    }

    @Override
    public Double getFlightDistance() {
        return this.distance;
    }

    @Override
    public String toString() {
        return "From " + getOriginCode() + " to " + getDestinationCode() + " costing $" + getFlightCost().toString()
                + " (distance: " + getFlightDistance() + ").";
    }

}
