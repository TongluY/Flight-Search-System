import java.util.ArrayList;
import java.util.List;

/**
 * Placeholder implementation of IFlightGraphADT.java.
 */
public class BEAFFlightGraphBDTesting<NodeType, EdgeType extends Number>  implements IFlightGraphADT<NodeType, EdgeType> {

    @Override
    public boolean insertVertex(NodeType data) {
        return false;
    }

    @Override
    public boolean removeVertex(NodeType data) {
        return false;
    }

    @Override
    public boolean insertEdge(NodeType source, NodeType target, EdgeType weight) {
        return false;
    }

    @Override
    public boolean removeEdge(NodeType source, NodeType target) {
        return false;
    }

    @Override
    public boolean containsVertex(NodeType data) {
        return false;
    }

    @Override
    public boolean containsEdge(NodeType source, NodeType target) {
        return false;
    }

    @Override
    public EdgeType getWeight(NodeType source, NodeType target) {
        return null;
    }

    @Override
    public List<NodeType> shortestPath(NodeType start, NodeType end) {
        System.out.println("shortestPath() called.");
        return new ArrayList<>();
    }

    @Override
    public double getPathCost(NodeType start, NodeType end) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getEdgeCount() {
        return 0;
    }

    @Override
    public int getVertexCount() {
        return 0;
    }

    @Override
    public ArrayList<NodeType> getHiddenVertices() {
        System.out.println("getHiddenVertices() called.");
        return new ArrayList<>();
    }

    @Override
    public boolean showVertex(NodeType A) throws IllegalArgumentException {
        System.out.println("showVertex() called.");
        return false;
    }

    @Override
    public boolean hideVertex(NodeType A) throws IllegalArgumentException {
        System.out.println("hideVertex() called.");
        return false;
    }
}
