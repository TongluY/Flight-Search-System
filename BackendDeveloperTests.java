import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Class containing methods that test the functionality of the backend of the BEAF application.
 */
public class BackendDeveloperTests {

    /**
     * Tests that the airport code checking method works as desired.
     */
    @Test
    public void testAirportCodeChecking() {
        IBEAFBackend backend = new BEAFBackend();
        // isStoredAirport() via invalidateAirport()
        TextUITester test0 = new TextUITester("");
        assertThrows(IllegalArgumentException.class, () -> backend.invalidateAirport("DFW"));
        assertDoesNotThrow(() -> backend.invalidateAirport("BUF"));
        assertDoesNotThrow(() -> backend.invalidateAirport("PDX"));
        assertThrows(IllegalArgumentException.class, () -> backend.invalidateAirport(" "));
        assertThrows(IllegalArgumentException.class, () -> backend.invalidateAirport("578"));
        assertDoesNotThrow(() -> backend.invalidateAirport("SAC"));
        assertThrows(IllegalArgumentException.class, () -> backend.invalidateAirport("LAX"));
        assertThrows(IllegalArgumentException.class, () -> backend.invalidateAirport("MSP"));
        assertThrows(IllegalArgumentException.class, () -> backend.invalidateAirport("BOS"));
        assertDoesNotThrow(() -> backend.invalidateAirport("JFK"));
        test0.checkOutput();
    }

    /**
     * Tests that the vertex/airport validating and invalidating results in correct calls to external (non-backend)
     * methods.
     */
    @Test
    public void testValidationCalls() {
        BEAFBackend backend = new BEAFBackend();
        // invalidateAirport()
        // use TextUITester to check for placeholder method prints which signify which methods were called.
        TextUITester test0 = new TextUITester("");
        backend.invalidateAirport("SAC");
        assertTrue(test0.checkOutput().trim().equals("hideVertex() called."));
        TextUITester test1 = new TextUITester("");
        backend.invalidateAirport("DEN");
        assertTrue(test1.checkOutput().trim().equals("hideVertex() called."));
        // validateAirport()
        TextUITester test2 = new TextUITester("");
        backend.validateAirport("SAC");
        assertTrue(test2.checkOutput().trim().equals("showVertex() called."));
        TextUITester test3 = new TextUITester("");
        backend.validateAirport("JFK");
        assertTrue(test3.checkOutput().trim().equals("showVertex() called."));

        // validateAllAirports()
        TextUITester test4 = new TextUITester("");
        backend.validateAllAirports();
        assertTrue(test4.checkOutput().contains("getHiddenVertices() called."));
        // getInvalidAirports()
        TextUITester test5 = new TextUITester("");
        List<String> invalidAirports = backend.getInvalidAirports();
        assertTrue(invalidAirports.isEmpty());
        assertTrue(test5.checkOutput().trim().equals("getHiddenVertices() called."));
        // isValid()
        TextUITester test6 = new TextUITester("");
        backend.isValid("SAC");
        assertTrue(test6.checkOutput().trim().equals("getHiddenVertices() called."));

    }

    /**
     * set portions & invalid input
     */
    @Test
    public void testPortionSetting() {
        BEAFBackend backend = new BEAFBackend();
        // setMileagePortion()
        assertDoesNotThrow(() -> backend.setMileagePortion(0.9));
        assertTrue(backend.getSavedMileagePortion() == 0.9);
        assertThrows(IllegalArgumentException.class, () -> backend.setMileagePortion(-1));
        assertThrows(IllegalArgumentException.class, () -> backend.setMileagePortion(1.3));
        assertDoesNotThrow(() -> backend.setMileagePortion(0));
        assertTrue(backend.getSavedMileagePortion() == 0);
        assertThrows(IllegalArgumentException.class, () -> backend.setMileagePortion(-0.5));
        assertDoesNotThrow(() -> backend.setMileagePortion(1));
        assertTrue(backend.getSavedMileagePortion() == 1);
        // setCostPortion()
        assertDoesNotThrow(() -> backend.setCostPortion(1));
        assertTrue(backend.getSavedMileagePortion() == 0);
        assertThrows(IllegalArgumentException.class, () -> backend.setCostPortion(3));
        assertDoesNotThrow(() -> backend.setCostPortion(0.3));
        assertTrue(backend.getSavedMileagePortion() == 0.7);
        assertThrows(IllegalArgumentException.class, () -> backend.setCostPortion(1.8));
        assertDoesNotThrow(() -> backend.setCostPortion(0));
        assertTrue(backend.getSavedMileagePortion() == 1);
        assertThrows(IllegalArgumentException.class, () -> backend.setCostPortion(-0.9));
        assertThrows(IllegalArgumentException.class, () -> backend.setCostPortion(100));
        // setPortions()
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(2, -1.5));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(1.5, -2));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(1, 1));
        assertDoesNotThrow(() -> backend.setPortions(0, 1));
        assertTrue(backend.getSavedMileagePortion() == 0);
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(0, 0.5));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(0.5, 1));
        assertDoesNotThrow(() -> backend.setPortions(1, 0));
        assertTrue(backend.getSavedMileagePortion() == 1);
        assertDoesNotThrow(() -> backend.setPortions(0.4, 0.6));
        assertTrue(backend.getSavedMileagePortion() == 0.4);
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(0.9, 1));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(0, 0));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(1, 0.9));
        // repeat in another order
        // setCostPortion()
        assertDoesNotThrow(() -> backend.setCostPortion(1));
        assertTrue(backend.getSavedMileagePortion() == 0);
        assertThrows(IllegalArgumentException.class, () -> backend.setCostPortion(3));
        assertDoesNotThrow(() -> backend.setCostPortion(0.3));
        assertTrue(backend.getSavedMileagePortion() == 0.7);
        assertThrows(IllegalArgumentException.class, () -> backend.setCostPortion(1.8));
        assertDoesNotThrow(() -> backend.setCostPortion(0));
        assertTrue(backend.getSavedMileagePortion() == 1);
        assertThrows(IllegalArgumentException.class, () -> backend.setCostPortion(-0.9));
        assertThrows(IllegalArgumentException.class, () -> backend.setCostPortion(100));
        // setMileagePortion()
        assertDoesNotThrow(() -> backend.setMileagePortion(0.9));
        assertTrue(backend.getSavedMileagePortion() == 0.9);
        assertThrows(IllegalArgumentException.class, () -> backend.setMileagePortion(-1));
        assertThrows(IllegalArgumentException.class, () -> backend.setMileagePortion(1.3));
        assertDoesNotThrow(() -> backend.setMileagePortion(0));
        assertTrue(backend.getSavedMileagePortion() == 0);
        assertThrows(IllegalArgumentException.class, () -> backend.setMileagePortion(-0.5));
        assertDoesNotThrow(() -> backend.setMileagePortion(1));
        assertTrue(backend.getSavedMileagePortion() == 1);
        // setPortions()
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(2, -1.5));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(1.5, -2));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(1, 1));
        assertDoesNotThrow(() -> backend.setPortions(0, 1));
        assertTrue(backend.getSavedMileagePortion() == 0);
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(0, 0.5));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(0.5, 1));
        assertDoesNotThrow(() -> backend.setPortions(1, 0));
        assertTrue(backend.getSavedMileagePortion() == 1);
        assertDoesNotThrow(() -> backend.setPortions(0.4, 0.6));
        assertTrue(backend.getSavedMileagePortion() == 0.4);
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(0.9, 1));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(0, 0));
        assertThrows(IllegalArgumentException.class, () -> backend.setPortions(1, 0.9));
    }

    /**
     * Checks that the different searches for itineraries do not adjust saved mileage portion.
     */
    @Test
    public void testGraphUpdates() {
        BEAFBackend backend = new BEAFBackend();
        // set mileage portion to initiate value
        backend.setMileagePortion(0.1);
        assertTrue(backend.getSavedMileagePortion() == 0.1);
        // change mileage portion
        backend.setMileagePortion(0.3);
        assertTrue(backend.getSavedMileagePortion() == 0.3);
        // call best
        // use TextUITester to check the right methods in placeholder classes, and thus in the future other developers'
        // classes, are called
        TextUITester test0 = new TextUITester("");
        backend.getBestItinerary("BUF", "PDX");
        assertTrue(backend.getSavedMileagePortion() == 0.3);
        assertTrue(test0.checkOutput().trim().equals("shortestPath() called."));
        // call shortest
        TextUITester test1 = new TextUITester("");
        backend.getShortestItinerary("PDX", "JFK");
        assertTrue(backend.getSavedMileagePortion() == 0.3);
        assertTrue(test1.checkOutput().trim().equals("shortestPath() called."));
        // call best
        TextUITester test2 = new TextUITester("");
        backend.getBestItinerary("SAC", "PDX");
        assertTrue(backend.getSavedMileagePortion() == 0.3);
        assertTrue(test2.checkOutput().trim().equals("shortestPath() called."));
        // call cheapest
        TextUITester test3 = new TextUITester("");
        backend.getCheapestItinerary("DEN", "PDX");
        assertTrue(backend.getSavedMileagePortion() == 0.3);
        assertTrue(test3.checkOutput().trim().equals("shortestPath() called."));
    }

    /**
     * Checks that airportCodesToFlights() works as desired.
     */
    @Test
    public void testAirportsToFlights() {
        BEAFBackend backend = new BEAFBackend();
        // 1. empty
        List<String> airportCodes0 = new ArrayList<>();
        List<IFlight> flights0 = backend.airportCodesToFlights(airportCodes0);
        assertTrue(flights0.isEmpty());

        // 2. single
        List<String> airportCodes1 = new ArrayList<>(Arrays.asList(new String[]{"BUF"}));
        List<IFlight> flights1 = backend.airportCodesToFlights(airportCodes1);
        assertTrue(flights1.isEmpty());

        // 3. non-existing
        List<String> airportCodes2 = new ArrayList<>(Arrays.asList(new String[]{"PDX", "DEN"}));
        assertThrows(IllegalArgumentException.class, () -> backend.airportCodesToFlights(airportCodes2));

        List<String> airportCodes3 = new ArrayList<>(Arrays.asList(new String[]{"BUF", "JFK", "SAC"}));
        assertThrows(IllegalArgumentException.class, () -> backend.airportCodesToFlights(airportCodes3));

        List<String> airportCodes4 = new ArrayList<>(Arrays.asList(new String[]{"PDX", "SAC", "BWI"}));
        assertThrows(IllegalArgumentException.class, () -> backend.airportCodesToFlights(airportCodes4));

        // 4. existing
        List<String> airportCodes5 = new ArrayList<>(Arrays.asList(new String[]{"BWI", "JFK"}));
        List<IFlight> flights5 = backend.airportCodesToFlights(airportCodes5);
        assertTrue(flights5.size() == 1);
        IFlight flight5 = flights5.get(0);
        assertTrue(flight5.getOriginCode().equals("BWI"));
        assertTrue(flight5.getDestinationCode().equals("JFK"));
        assertTrue(flight5.getFlightCost() == 175);
        assertTrue(flight5.getFlightDistance() == 156);

        List<String> airportCodes6 = new ArrayList<>(Arrays.asList(new String[]{"SAC", "JFK", "BWI"}));
        List<IFlight> flights6 = backend.airportCodesToFlights(airportCodes6);
        assertTrue(flights6.size() == 2);
        IFlight flight6a = flights6.get(0);
        assertTrue(flight6a.getOriginCode().equals("SAC"));
        assertTrue(flight6a.getDestinationCode().equals("JFK"));
        assertTrue(flight6a.getFlightCost() == 278);
        assertTrue(flight6a.getFlightDistance() == 401);
        IFlight flight6b = flights6.get(1);
        assertTrue(flight6b.getOriginCode().equals("JFK"));
        assertTrue(flight6b.getDestinationCode().equals("BWI"));
        assertTrue(flight6b.getFlightCost() == 180);
        assertTrue(flight6b.getFlightDistance() == 156);

    }
}
