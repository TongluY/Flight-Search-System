import java.util.ArrayList;
import java.util.Hashtable;
//import java.util.NoSuchElementException;

public class FlightGraphADT<NodeType, EdgeType extends Number> extends Graph<NodeType, EdgeType> implements IFlightGraphADT<NodeType, EdgeType> {

    public Hashtable<NodeType, Vertex> hidden;

    public FlightGraphADT() {
        this.hidden = new Hashtable<>();
        this.vertices = new Hashtable<>();
    }

    /*
     * makes the travel costs surrounding a given connection infinitely
     * large, attempting to remove the location from being a potential
     * connection in the least costly path
     * 
     * @return true if the vertex is successfully made "hidden", false
     * otherwise
     */
    /**
     * @param A
     * @return
     */
    @Override
    public boolean hideVertex(NodeType A) {
        if (A == null) {
            throw new NullPointerException("Cannot hide a null vertex");
        }
        Vertex targetVertex = vertices.get(A);
        if (targetVertex == null) {
            return false;
        }
        hidden.put(A, targetVertex);
        return true;
    }

    @Override
    public boolean showVertex(NodeType A) {
        if (A == null) {
            throw new NullPointerException("Cannot hide a null vertex");
        }
        Vertex targetVertex = vertices.get(A);
        if (targetVertex == null) {
            return false;
        }
        hidden.remove(A, targetVertex);
        return true;
    }

    @Override
    public ArrayList<NodeType> getHiddenVertices() {
        ArrayList<NodeType> hiddenNodes = new ArrayList<>();
        for (NodeType data : hidden.keySet()) {
            hiddenNodes.add(data);
        }
        return hiddenNodes;
    }
}