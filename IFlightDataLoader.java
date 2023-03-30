import java.util.List;

/**
 * An interface for the Data Wrangler's loading. It can provide list of
 * nodes and edges and other data that other parts may find helpful
 */
public interface IFlightDataLoader {
    /**
     * Returns the list of nodes (all airports)
     * @return List of nodes (airport codes)
     */
    List<String> getNodeList();

    /**
     * Returns the list of edges (represented as IFlight objects)
     * @return List of available flights (IFlight objects)
     */
    List<IFlight> getEdgeList();

    /**
     * Returns the list of states available
     * @return List of states
     */
    List<String> getStateList();

    /**
     * Returns the list of nodes (all airports). Functionally identical to getNodeList(),
     * but added to make the naming more consistent with getStateList() method
     * @return List of nodes (airport codes)
     */
    List<String> getAirportList();

    /**
     * Returns a list of lists, with each sub-list containing data:
     *     [Airport code, State, City, Airport name]
     * Used to map airport code to other data or vice versa.
     * @return List of list with airports and its corresponding data.
     */
    List<List<String>> getAirportData();
}
