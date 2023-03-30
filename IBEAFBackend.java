import java.util.LinkedList;
import java.util.List;

/**
 * Implementations of the class will work with the implementations of IBEAFDataWrangler and IBEAFGraphADT to search
 * stored flight data for optimal flight itineraries based on mileage, cost, or a balance of both. The class will
 * also contain methods to set the mileage/cost balance.
 */
public interface IBEAFBackend {

    /**
     * Marks an airport as invalid (default is valid), so that no created itineraries will go through that airport.
     * Will not do anything if airport is already marked as invalid.
     *
     * @param airportCode case-insensitive String containing the airport code that represents the airport to mark as
     *                    invalid
     * @throws IllegalArgumentException if given airport code does not match any airport in the stored data
     */
    public void invalidateAirport(String airportCode) throws IllegalArgumentException;

    /**
     * Marks an airport as valid (default is valid), so that created itineraries can go through that airport. Will not
     * do anything if airport is already marked as valid.
     *
     * @param airportCode case-insensitive String containing the airport code that represents the airport to mark as
     *                    valid
     * @throws IllegalArgumentException if given airport code does not match any airport in the stored data
     */
    public void validateAirport(String airportCode) throws IllegalArgumentException;

    /**
     * Validates all airports.
     *
     * @return true if any airports were invalid before this operation, false if no airports were invalid before this
     * operation
     */
    public boolean validateAllAirports();

    /**
     * Returns a List of Strings representing all currently invalid airports.
     *
     * @return a List of Strings representing all currently invalid airports
     */
    public List<String> getInvalidAirports();

    /**
     * Returns the validity of airport with given airport code.
     *
     * @param airportCode case-insensitive String containing the airport code that represents the airport to check
     *                    validity of
     * @return validity of airport with given airport code
     * @throws IllegalArgumentException if given airport code does not match any airport in the stored data
     */
    public boolean isValid(String airportCode) throws IllegalArgumentException;

    /**
     * Adjusts flight ratings (weight) based on a given mileage portion.
     *
     * @param mileagePortion a double between 0-1 inclusive that represents the portion of the flight rating (weight)
     *                       that comes from the mileage
     * @throws IllegalArgumentException if the given double for mileagePortion is not between 0-1 inclusive
     */
    public void setMileagePortion(double mileagePortion) throws IllegalArgumentException;

    /**
     * Adjusts flight ratings (weight) based on a given cost portion.
     *
     * @param costPortion a double between 0-1 inclusive that represents the portion of the flight rating (weight)
     *                    that comes from the cost
     * @throws IllegalArgumentException if the given double for costPortion is not between 0-1 inclusive
     */
    public void setCostPortion(double costPortion) throws IllegalArgumentException;

    /**
     * Adjusts flight ratings (weight) based on a given mileage and cost portions.
     *
     * @param mileagePortion a double between 0-1 inclusive that represents the portion of the flight rating (weight)
     *                       that comes from the mileage
     * @param costPortion    a double between 0-1 inclusive that represents the portion of the flight rating (weight)
     *                       that comes from the mileage
     * @throws IllegalArgumentException if one or both of the given doubles for mileagePortion and costPortion
     *                                  is not between 0-1 inclusive
     */
    public void setPortions(double mileagePortion, double costPortion) throws IllegalArgumentException;

    /**
     * Returns a LinkedList of IFlight objects representing the best itinerary, based on the current mileage and cost
     * balance, between the given origin airport and the given destination airport.
     *
     * @param originAirportCode the code of the airport where the trip starts
     * @param destAirportCode   the code of the airport where the trip ends
     * @return a LinkedList of IFlight objects representing the best itinerary based on current mileage and cost balance
     * @throws IllegalArgumentException if there are no possible itineraries between the given origin and destination
     *                                  airports, or if one or both of the airport codes does not correspond to an
     *                                  airport in the stored data
     */
    public LinkedList<IFlight> getBestItinerary(String originAirportCode, String destAirportCode) throws IllegalArgumentException;

    /**
     * Returns a LinkedList of IFlight objects representing the cheapest itinerary, based on cost, between the given
     * origin airport and the given destination airport.
     *
     * @param originAirportCode the code of the airport where the trip starts
     * @param destAirportCode   the code of the airport where the trip ends
     * @return a LinkedList of IFlight objects representing the best itinerary based on cost
     * @throws IllegalArgumentException if there are no possible itineraries between the given origin and destination
     *                                  airports, or if one or both of the airport codes does not correspond to an
     *                                  airport in the stored data
     */
    public LinkedList<IFlight> getCheapestItinerary(String originAirportCode, String destAirportCode) throws IllegalArgumentException;

    /**
     * Returns a LinkedList of IFlight objects representing the shortest itinerary, based on mileage, between the given
     * origin airport and the given destination airport.
     *
     * @param originAirportCode the code of the airport where the trip starts
     * @param destAirportCode   the code of the airport where the trip ends
     * @return a LinkedList of IFlight objects representing the best itinerary based on mileage
     * @throws IllegalArgumentException if there are no possible itineraries between the given origin and destination
     *                                  airports, or if one or both of the airport codes does not correspond to an
     *                                  airport in the stored data
     */
    public LinkedList<IFlight> getShortestItinerary(String originAirportCode, String destAirportCode) throws IllegalArgumentException;

}