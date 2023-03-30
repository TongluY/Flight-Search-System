import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Placeholder implementation of IFlightDataLoader.java
 */
public class BEAFFlightDataLoaderBDTesting implements IFlightDataLoader {

    private List<String> nodeList;
    private List<IFlight> edgeList;
    private List<String> stateList;
    private List<String> airportList;
    private List<List<String>> airportData;

    public BEAFFlightDataLoaderBDTesting() {
        nodeList = new ArrayList<>(Arrays.asList(new String[]{"PDX", "SAC", "JFK", "DEN", "BUF", "BWI"}));
        edgeList = new ArrayList<>();
        edgeList.add(new FlightBDTesting("JFK","BUF",176,97));
        edgeList.add(new FlightBDTesting("BUF","JFK",199,97));
        edgeList.add(new FlightBDTesting("PDX","JFK",222,389));
        edgeList.add(new FlightBDTesting("DEN","BUF",256,287));
        edgeList.add(new FlightBDTesting("SAC","JFK",278,401));
        edgeList.add(new FlightBDTesting("DEN","SAC",158,180));
        edgeList.add(new FlightBDTesting("BUF","PDX",145,211));
        edgeList.add(new FlightBDTesting("BWI","JFK",175,156));
        edgeList.add(new FlightBDTesting("JFK","BWI",180,156));
        edgeList.add(new FlightBDTesting("BWI","SAC",243,278));
        stateList = new ArrayList<>(Arrays.asList(new String[]{"New Mexico", "Delaware", "Oklahoma",
                "Ohio", "Alaska", "Nebraska", "Louisiana"}));
        airportList = nodeList;
        airportData = new ArrayList<>(); // I don't really use this, so I won't do anything with it.
    }

    @Override
    public List<String> getNodeList() {
        return nodeList;
    }

    @Override
    public List<IFlight> getEdgeList() {
        return edgeList;
    }

    @Override
    public List<String> getStateList() {
        return stateList;
    }

    @Override
    public List<String> getAirportList() {
        return airportList;
    }

    @Override
    public List<List<String>> getAirportData() {
        return airportData;
    }
}
