/**
 * An interface for the object to represent an edge.
 */
public interface IFlight {
    /**
     * A method to return airport code of the origin
     * 
     * @return Airport code of the origin, as a String.
     */
    public String getOriginCode();

    /**
     * A method to return airport code of the destination
     * 
     * @return Airport code of the destination, as a String.
     */
    public String getDestinationCode();

    /**
     * A method to get the flight cost between the origin and the destination.
     * 
     * @return Double (object) for flight cost, in US dollars
     */
    public Double getFlightCost();

    /**
     * A method to get the flight distance between the origin and the destination.
     * 
     * @return Double (object) for the flight distance, in miles.
     */
    public Double getFlightDistance();
}
