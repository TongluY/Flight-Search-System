import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlgorithmEngineerTests {

    private FlightGraphADT<String, Integer> graph;

    @BeforeEach
    public void createGraph() {
        graph = new FlightGraphADT<String, Integer>();
        // insert vertices A-F
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        // insert edges
        graph.insertEdge("A", "B", 6);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "D", 5);
        graph.insertEdge("B", "E", 1);
        graph.insertEdge("B", "C", 2);
        graph.insertEdge("C", "B", 3);
        graph.insertEdge("C", "F", 1);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("E", "A", 4);
        graph.insertEdge("F", "A", 1);
        graph.insertEdge("F", "D", 1);
    }

    // public static void main(String[] args) throws Exception {

    // }

    /**
     * test if hideVertex() or showVertex() show the error message if the parameter
     * is null
     */
    @Test
    public void Test1() {
        try {
            graph.hideVertex("");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Cannot hide a null vertex!");
        }

        try {
            graph.showVertex("");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Cannot hide a null vertex!");
        }

    }

    /**
     * test if hideVertex() and showVertex() returns false if I hide or show a
     * vertex that does not exist
     */
    @Test
    public void Test2() {
        boolean hide_g = graph.hideVertex("G");
        boolean show_g = graph.showVertex("G");
        assertEquals(hide_g, false);
        assertEquals(show_g, false);
    }

    /**
     * test if hideVertex() works well test both cases 1) there should not contain
     * the element if I do not hide that 2) it should contain this element if I hide
     * that
     */
    @Test
    public void Test3() {
        assertFalse(graph.hidden.toString().contains("A"));
        assertFalse(graph.hidden.toString().contains("B"));
        boolean hide_a = graph.hideVertex("A");
        boolean hide_b = graph.hideVertex("B");
        assertEquals(hide_a, true);
        assertEquals(hide_b, true);
        assertTrue(graph.hidden.toString().contains("A"));
        assertTrue(graph.hidden.toString().contains("B"));
    }

    /**
     * test if GetHiddenNodes() works well test cases: 1) show the nodes if before
     * we hide them 2) do not show the nodes after we hide them
     */
    @Test
    public void Test4() {
        assertFalse(graph.getHiddenVertices().toString().contains("A"));
        assertFalse(graph.getHiddenVertices().toString().contains("B"));
        boolean hide_a = graph.hideVertex("A");
        boolean hide_b = graph.hideVertex("B");
        assertEquals(hide_a, true);
        assertEquals(hide_b, true);
        assertTrue(graph.getHiddenVertices().toString().contains("A"));
        assertTrue(graph.getHiddenVertices().toString().contains("B"));
    }

    /**
     * test if showVertex() works well given GetHiddenNodes() has already been
     * tested true, we can do the following tests test both cases 1) it can be
     * return true if the parameter contains a valid node 2) given the hashtable
     * contains this node before we show it, the hashtable should not contain this
     * node after we show it
     * 
     */
    @Test
    public void Test5() {
        assertFalse(graph.getHiddenVertices().toString().contains("A"));
        assertFalse(graph.getHiddenVertices().toString().contains("B"));
        boolean hide_a = graph.hideVertex("A");
        boolean hide_b = graph.hideVertex("B");
        assertEquals(hide_a, true);
        assertEquals(hide_b, true);
        assertTrue(graph.getHiddenVertices().toString().contains("A"));
        assertTrue(graph.getHiddenVertices().toString().contains("B"));
        boolean show_a = graph.showVertex("A");
        boolean show_b = graph.showVertex("B");
        assertEquals(show_a, true);
        assertEquals(show_b, true);
        assertFalse(graph.getHiddenVertices().toString().contains("A"));
        assertFalse(graph.getHiddenVertices().toString().contains("B"));
    }

}
