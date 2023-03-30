import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing the backend of the BEAF application.
 */
public class BEAFBackend implements IBEAFBackend {

    // single graph to save memory, otherwise would use 3 or 4 separate for best,
    // cheapest, shortest, and custom
    private IFlightGraphADT<String, Double> flightGraph; // graph object used for calculating best itineraries
    private IFlightDataLoader flightDataLoader; // object containing methods that provide useful information
    private double previousMileagePortion; // mileagePortion at the last time flightGraph was updated
    private double savedMileagePortion; // mileagePortion + costPortion = 1.00; default mileagePortion = 0, costPortion
                                        // = 1
    // private boolean testing;

    /**
     * Creates a backend object for the BEAF application.
     */
    public BEAFBackend() {
        previousMileagePortion = -1; // default is -1 so it will be different from savedMileagePortion from
        // the beginning
        try {
            flightDataLoader = new DWFlightDataLoader("flight_data_processed.dot");
            flightGraph = new FlightGraphADT<>();
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println("Application could not start due to an unknown error");
            throw new IllegalArgumentException("Application could not start due to an unknown error");
        }
    }

    /**
     * Marks an airport as invalid (default is valid), so that no created
     * itineraries will go through that airport. Will not do anything if airport is
     * already marked as invalid.
     *
     * @param airportCode case-insensitive String containing the airport code that
     *                    represents the airport to mark as invalid
     * @throws IllegalArgumentException if given airport code does not match any
     *                                  airport in the stored data
     */
    @Override
    public void invalidateAirport(String airportCode) throws IllegalArgumentException {
        if (!isStoredAirport(airportCode)) {
            throw new IllegalArgumentException("Given airport code does not correspond to an airport stored in data.");
        }
        flightGraph.hideVertex(airportCode.toUpperCase().trim());
    }

    /**
     * Marks an airport as valid (default is valid), so that created itineraries can
     * go through that airport. Will not do anything if airport is already marked as
     * valid.
     *
     * @param airportCode case-insensitive String containing the airport code that
     *                    represents the airport to mark as valid
     * @throws IllegalArgumentException if given airport code does not match any
     *                                  airport in the stored data
     */
    @Override
    public void validateAirport(String airportCode) throws IllegalArgumentException {
        if (!isStoredAirport(airportCode)) {
            throw new IllegalArgumentException("Given airport code does not correspond to an airport stored in data.");
        }
        flightGraph.showVertex(airportCode.toUpperCase().trim());
    }

    /**
     * Validates all airports.
     *
     * @return true if any airports were invalid before this operation, false if no
     *         airports were invalid before this operation
     */
    @Override
    public boolean validateAllAirports() {
        List<String> hiddenVertices = flightGraph.getHiddenVertices();
        for (String hiddenVertex : hiddenVertices) {
            try {
                flightGraph.showVertex(hiddenVertex);
            } catch (IllegalArgumentException e) {
                // just let it go, it is not important to the user, this is not going to happen
                // anyway
            }
        }
        return false;
    }

    /**
     * Returns a List of Strings representing all currently invalid airports.
     *
     * @return a List of Strings representing all currently invalid airports
     */
    @Override
    public List<String> getInvalidAirports() {
        return flightGraph.getHiddenVertices();
    }

    /**
     * Returns the validity of airport with given airport code.
     *
     * @param airportCode case-insensitive String containing the airport code that
     *                    represents the airport to check validity of
     * @return validity of airport with given airport code
     * @throws IllegalArgumentException if given airport code does not match any
     *                                  airport in the stored data
     */
    @Override
    public boolean isValid(String airportCode) throws IllegalArgumentException {
        if (!isStoredAirport(airportCode)) {
            throw new IllegalArgumentException("Given airport code does not correspond to an airport stored in data.");
        }
        return flightGraph.getHiddenVertices().contains(airportCode);
    }

    /**
     * Adjusts flight ratings (weight) based on a given mileage portion.
     *
     * @param mileagePortion a double between 0-1 inclusive that represents the
     *                       portion of the flight rating (weight) that comes from
     *                       the mileage
     * @throws IllegalArgumentException if the given double for mileagePortion is
     *                                  not between 0-1 inclusive
     */
    @Override
    public void setMileagePortion(double mileagePortion) throws IllegalArgumentException {
        if (mileagePortion < 0 || mileagePortion > 1) {
            throw new IllegalArgumentException("Mileage portion of flight weight must be between 0 and 1, inclusive.");
        }
        savedMileagePortion = mileagePortion;
    }

    /**
     * Adjusts flight ratings (weight) based on a given cost portion.
     *
     * @param costPortion a double between 0-1 inclusive that represents the portion
     *                    of the flight rating (weight) that comes from the cost
     * @throws IllegalArgumentException if the given double for costPortion is not
     *                                  between 0-1 inclusive
     */
    @Override
    public void setCostPortion(double costPortion) throws IllegalArgumentException {
        if (costPortion < 0 || costPortion > 1) {
            throw new IllegalArgumentException("Cost portion of flight weight must be between 0 and 1, inclusive.");
        }
        savedMileagePortion = 1 - costPortion;
    }

    /**
     * Adjusts flight ratings (weight) based on a given mileage and cost portions.
     *
     * @param mileagePortion a double between 0-1 inclusive that represents the
     *                       portion of the flight rating (weight) that comes from
     *                       the mileage
     * @param costPortion    a double between 0-1 inclusive that represents the
     *                       portion of the flight rating (weight) that comes from
     *                       the mileage
     * @throws IllegalArgumentException if one or both of the given doubles for
     *                                  mileagePortion and costPortion is not
     *                                  between 0-1 inclusive
     */
    @Override
    public void setPortions(double mileagePortion, double costPortion) throws IllegalArgumentException {
        if (mileagePortion < 0 || mileagePortion > 1 || costPortion < 0 || costPortion > 1) {
            throw new IllegalArgumentException(
                    "Mileage portion and cost portion of flight weight both must be between 0 and 1, inclusive.");
        }
        if (Math.abs(mileagePortion + costPortion - 1.0) > 1e-6) {
            throw new IllegalArgumentException("Mileage portion and cost portion of flight weight must add up to 1.00");
        }
        savedMileagePortion = mileagePortion;
    }

    /**
     * Helper method for checking saved mileage portion.
     */
    protected double getSavedMileagePortion() {
        return savedMileagePortion;
    }

    /**
     * Returns a LinkedList of IFlight objects representing the best itinerary,
     * based on the current mileage and cost balance, between the given origin
     * airport and the given destination airport.
     *
     * @param originAirportCode the code of the airport where the trip starts
     * @param destAirportCode   the code of the airport where the trip ends
     * @return a LinkedList of IFlight objects representing the best itinerary based
     *         on current mileage and cost balance
     * @throws IllegalArgumentException if there are no possible itineraries between
     *                                  the given origin and destination airports,
     *                                  or if one or both of the airport codes does
     *                                  not correspond to an airport in the stored
     *                                  data
     */
    @Override
    public LinkedList<IFlight> getBestItinerary(String originAirportCode, String destAirportCode)
            throws IllegalArgumentException {
        checkFlightAirportCodes(originAirportCode, destAirportCode);
        return getBestItinerary(originAirportCode, destAirportCode, savedMileagePortion);
    }

    /**
     * Private helper method that returns a LinkedList of IFlight objects
     * representing the best itinerary, based on a given mileage and cost balance,
     * between the given origin airport and the given destination airport.
     *
     * @param originAirportCode the code of the airport where the trip starts
     * @param destAirportCode   the code of the airport where the trip ends
     * @param mileagePortion    the portion of flight ratings (weight) to be
     *                          accounted for by mileage
     * @return a LinkedList of IFlight objects representing the best itinerary based
     *         on current mileage and cost balance
     * @throws IllegalArgumentException if there are no possible itineraries between
     *                                  the given origin and destination airports,
     *                                  or if one or both of the airport codes does
     *                                  not correspond to an airport in the stored
     *                                  data
     */
    private LinkedList<IFlight> getBestItinerary(String originAirportCode, String destAirportCode,
            double mileagePortion) throws IllegalArgumentException {
        updateflightGraph(mileagePortion); // this is only place updateGraph is called currently because the way it is
        // set up is that the graph is only updated if the user requests an itinerary,
        // rather than every time the mileagePortion changes
        List<String> airports;
        try {
            airports = flightGraph.shortestPath(originAirportCode.toUpperCase().trim(),
                    destAirportCode.toUpperCase().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "No itinerary found between " + originAirportCode + " and " + destAirportCode + ".");
        }
        LinkedList<IFlight> itinerary;
        try {
            itinerary = airportCodesToFlights(airports);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error when converting ordered airport list to itinerary.");
        }
        return itinerary;
    }

    /**
     * Returns a LinkedList of IFlight objects representing the cheapest itinerary,
     * based on cost, between the given origin airport and the given destination
     * airport.
     *
     * @param originAirportCode the code of the airport where the trip starts
     * @param destAirportCode   the code of the airport where the trip ends
     * @return a LinkedList of IFlight objects representing the best itinerary based
     *         on cost
     * @throws IllegalArgumentException if there are no possible itineraries between
     *                                  the given origin and destination airports,
     *                                  or if one or both of the airport codes does
     *                                  not correspond to an airport in the stored
     *                                  data
     */
    @Override
    public LinkedList<IFlight> getCheapestItinerary(String originAirportCode, String destAirportCode)
            throws IllegalArgumentException {
        return getBestItinerary(originAirportCode, destAirportCode, 0);
    }

    /**
     * Returns a LinkedList of IFlight objects representing the shortest itinerary,
     * based on mileage, between the given origin airport and the given destination
     * airport.
     *
     * @param originAirportCode the code of the airport where the trip starts
     * @param destAirportCode   the code of the airport where the trip ends
     * @return a LinkedList of IFlight objects representing the best itinerary based
     *         on mileage
     * @throws IllegalArgumentException if there are no possible itineraries between
     *                                  the given origin and destination airports,
     *                                  or if one or both of the airport codes does
     *                                  not correspond to an airport in the stored
     *                                  data
     */
    @Override
    public LinkedList<IFlight> getShortestItinerary(String originAirportCode, String destAirportCode)
            throws IllegalArgumentException {
        return getBestItinerary(originAirportCode, destAirportCode, 1);
    }

    /**
     * Converts ordered List of airports to a corresponding LinkedList of IFlight
     * objects.
     *
     * @param airportCodes ordered List of airports to generate a corresponding
     *                     LinkedList of IFlight objects for
     * @return LinkedList of IFlight objects corresponding to given ordered List of
     *         airports, empty if zero or one airport in given List of airport codes
     * @throws IllegalArgumentException if a flight between two consecutive airports
     *                                  in the given List cannot be found.
     */
    protected LinkedList<IFlight> airportCodesToFlights(List<String> airportCodes) throws IllegalArgumentException {
        LinkedList<IFlight> flights = new LinkedList<>();
        List<IFlight> allFlights = flightDataLoader.getEdgeList(); // all the flights stored
        // traverse through every leg of the itinerary
        for (int i = 0; i < airportCodes.size() - 1; i++) {
            String airportCodeFrom = airportCodes.get(i);
            String airportCodeTo = airportCodes.get(i + 1);
            boolean found = false;
            for (IFlight f : allFlights) {
                if (f.getOriginCode().toUpperCase().trim().equals(airportCodeFrom)
                        && f.getDestinationCode().toUpperCase().trim().equals(airportCodeTo)) {
                    flights.add(f);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("No flight found on leg of itinerary between " + airportCodeFrom
                        + " and " + airportCodeTo + ".");
            }
        }
        return flights;
    }

    /**
     * Updates the graph if it must be changed.
     *
     * @param mileagePortion mileage portion of edge weights to update the graph of
     *                       flights with
     * @return true if graph had to be changed and the changes were made, false if
     *         graph did not have to be changed and changes were not made.
     */
    private boolean updateflightGraph(double mileagePortion) {
        // only update if current mileagePortion is different from previous
        // mileagePortion at last update
        if (Math.abs(previousMileagePortion - mileagePortion) < 1e-6) {
            return false;
        } else {
            // all old data is invalid at this point because of changed weight portions, so
            // because there is no easy way
            // to clear a graph (no method as of now), create a new graph
            flightGraph = new FlightGraphADT<>();

            // add all vertices
            for (String airportCode : flightDataLoader.getAirportList()) {
                flightGraph.insertVertex(airportCode);
            }
            // add all edges with proper weights
            for (IFlight flight : flightDataLoader.getEdgeList()) {
                // weight formula: cost * costPortion + mileage/2 * mileagePortion
                double weight = flight.getFlightCost() * (1 - mileagePortion)
                        + flight.getFlightDistance() * mileagePortion / 2;
                // this should not happen, but just in case
                if (weight < 0) {
                    weight = 0;
                }
                flightGraph.insertEdge(flight.getOriginCode().toUpperCase().trim(),
                        flight.getDestinationCode().toUpperCase().trim(), weight);
            }
            previousMileagePortion = mileagePortion; // previous mileagePortion is only updated here
            return true;
        }
    }

    /**
     * Verifies that 2 given airport codes correspond to airports stored in data.
     * Commented as if the first code is the code for the origin airport and the
     * second code is code of the destination airport, and same for the produced
     * exceptions, though this method can be used for any two airport codes.
     *
     * @param originAirportCode the code of the airport where the trip starts
     * @param destAirportCode   the code of the airport where the trip ends
     * @throws IllegalArgumentException if one or both of the given airport codes
     *                                  does not correspond to an airport stored in
     *                                  data
     */
    private void checkFlightAirportCodes(String originAirportCode, String destAirportCode)
            throws IllegalArgumentException {
        if (!isStoredAirport(originAirportCode)) {
            throw new IllegalArgumentException(
                    "Given origin airport code does not correspond to an airport stored in data.");
        }
        if (!isStoredAirport(destAirportCode)) {
            throw new IllegalArgumentException(
                    "Given destination airport code does not correspond to an airport stored in data.");
        }
    }

    /**
     * Checks to see if given airport code corresponds to an airport stored in data.
     *
     * @param airportCode airport code to check if there is corresponding airport
     *                    stored in data for
     * @return true if given airport code corresponds to an airport stored in data,
     *         false if given airport code does not correspond to an airport stored
     *         in data.
     */
    private boolean isStoredAirport(String airportCode) {
        return flightDataLoader.getAirportList().contains(airportCode.toUpperCase().trim());
    }

}
