import java.util.ArrayList;

/*
 * Extension interface of the GraphADT interface: adds methods to accommodate
 * for the utilities desired by the Project 3 BEAF application
 */
public interface IFlightGraphADT<NodeType, EdgeType extends Number> extends GraphADT<NodeType, EdgeType> {
    /*
     * @return a list containing the data to all of the vertices that are hidden
     * away from the dijkstrasShortestPath method
     */
    public ArrayList<NodeType> getHiddenVertices();

    /*
     * Removes the vertex corresponding to the pass data from the hashtable
     * containing the vertices that the dijkstrasShortestPath method will avoid
     * exploring
     * 
     * @param A the data corresponding to a vertex that will be removed when hidden
     * vertices list
     * 
     * @throws IllegalArgumentException if the data does not correspond to any
     * vertex within the graph
     * 
     * @return true if the vertex is successfully removed from the hidden vertices
     * list
     */
    public boolean showVertex(NodeType A) throws IllegalArgumentException;

    /*
     * Hides a vertex from the dijkstrasShortestPath method. Adds the passed vertex
     * data to a hashtable containing vertices that the method will avoid exploring
     * 
     * @param A the data corresponding to a vertex that will be avoided when finding
     * the shortest path
     * 
     * @throws IllegalArgumentException if the data does not correspond to any
     * vertex within the graph
     * 
     * @return true if the vertex is successfully hidden away
     */
    public boolean hideVertex(NodeType A) throws IllegalArgumentException;
}
