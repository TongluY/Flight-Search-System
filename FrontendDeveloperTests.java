import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.beans.beancontext.BeanContextChildSupport;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class FrontendDeveloperTests {
    /**
     * Helper method to produce output of BigMapper application as a String.
     *
     * @param input given input to BigMapper application as a String
     * @return output of BigMapper application as a String
     */
    private String fdOutput(String input) {
        TextUITester tester = new TextUITester(input);
        FDBackend backend = new FDBackend();
        PlaneMapperFrontend frontend = new PlaneMapperFrontend(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        return tester.checkOutput();
    }

    /**
     * Test the beginning, main menu, invalid input and ending
     */
    @Test
    public void test1() {
        String output = fdOutput("4\n2\n");
        // check beginning
        Assertions.assertTrue(output.trim().startsWith("Welcome to the Plane Route Application!"));
        Assertions.assertTrue(output.contains("x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x"));
        // check the menu of GUI, console, exit
        Assertions.assertTrue(output.contains("Please select an option from the menu below:"));
        Assertions.assertTrue(output.contains("1. Use the console version of the application"));
        Assertions.assertTrue(output.contains("2. Exit the application"));
        // check invalid input
        Assertions.assertTrue(output.contains("Invalid input. Please try again."));
        // check ending
        Assertions.assertTrue(output.trim().endsWith("Thanks for using Bootleg Expedia!"));
    }

    /**
     * Tests the console version criteria and best search
     */
    @Test
    public void test2() {
        String output = fdOutput("1\n1\nTYN\nori\ndes\n\nn\n");
        // check the menu
        Assertions.assertTrue(output.contains("Search criteria:"));
        Assertions.assertTrue(output.contains("1) Best overall"));
        Assertions.assertTrue(output.contains("2) Cheapest"));
        Assertions.assertTrue(output.contains("3) Fastest"));
        Assertions.assertTrue(output.contains("4) Advanced options"));
        // test the best case
        Assertions.assertTrue(output.contains("Hi! Let’s find the best flight for you!"));
        Assertions.assertTrue(output.contains("Please enter the origin airport code:"));
        Assertions.assertTrue(output.contains("Please enter the destination airport code:"));
        Assertions.assertTrue(output.contains("Please enter any airports you’d like to avoid (leave blank if none):"));
        Assertions.assertTrue(
                output.contains("The best way to fly from ori to des is on a nonstop flight, costing $200.0."));
        ;
        // test the validation
        Assertions.assertTrue(output.contains("The airport code you entered is invalid, please enter again!"));
        // test nextsearch
        Assertions.assertTrue(output.contains("Would you like to make another search? (y)es or (n)o:"));
    }

    /**
     * Tests the cheapest case and same input case
     */
    @Test
    public void test3() {
        String output = fdOutput("1\n2\noo\noo\ndd\n\nn\n");
        // check origin and destination inputs are the same
        Assertions.assertTrue(output.contains("This is the same airport! Please enter a different destination."));
        // check the cheapest case
        Assertions.assertTrue(output.contains(
                "The best way to fly from oo to dd is to fly from AMS to BOD, and then BOD to CDG, and then CDG to FCO.\nThis has an average total price of $600.0."));
    }

    /**
     * Tests the fastest case and airport to be avoided
     */
    @Test
    public void test4() {
        String output = fdOutput("1\n3\no\nd\na\nn\n");
        // check the airport to be avoided
        Assertions.assertTrue(output.contains("calling the method invalidateAirport()."));
        // check the fastest case
        Assertions
                .assertTrue(output.contains("The best way to fly from o to d is on a nonstop flight, costing $200.0."));
    }

    /**
     * Tests the advanced seach and invalid input of maing another search
     */
    @Test
    public void test5() {
        String output = fdOutput("1\n4\no\nd\n\n0.7\n0.2\n0.7\n0.3\nz\nn\n");
        // check the advanced search
        Assertions.assertTrue(output.contains("Please enter the search weight on cost (percentage form):"));
        Assertions.assertTrue(output.contains("For example if the expected weight on cost is 70%, then type 0.7"));
        Assertions.assertTrue(output.contains("Please enter the search weight on time (percentage form):"));
        // check if portions sum not equal to 100
        Assertions.assertTrue(output.contains("This does not add up to 100%, please try again."));
        Assertions.assertTrue(output.contains("calling the method setPortions()."));
        // check the invalid input of make another search
        Assertions.assertTrue(output.contains("The letter you entered is invalid, please enter again!"));
    }

    /**
     * Tests the Algorithm Engineer code about hideVertex() and showVertex()
     */
    @Test
    public void CodeReviewOfAlgorithmEngineer1() {
        FlightGraphADT<String, Integer> graph;
        graph = new FlightGraphADT<String, Integer>();
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertEdge("A", "B", 6);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("B", "C", 2);
        graph.insertEdge("C", "B", 3);
        assertEquals(graph.hideVertex("D"), false);
        assertEquals(graph.showVertex("B"), true);
    }

    /**
     * Tests the Algorithm Engineer code about getHiddenVertices().
     */
    @Test
    public void CodeReviewOfAlgorithmEngineer2() {
        FlightGraphADT<String, Integer> graph;
        graph = new FlightGraphADT<String, Integer>();
        graph.insertVertex("A");
        graph.insertVertex("C");
        graph.insertVertex("E");
        graph.insertEdge("A", "C", 4);
        graph.insertEdge("E", "C", 5);
        assertEquals(graph.getHiddenVertices().toString().contains("E"), false);
        assertEquals(graph.hideVertex("E"), true);
        assertEquals(graph.getHiddenVertices().toString().contains("E"), true);
        assertEquals(graph.showVertex("E"), true);
        assertEquals(graph.getHiddenVertices().toString().contains("E"), false);
    }

    /**
     * Tests integration of backend developer, data wrangler and algorithm engineer.
     */
    @Test
    public void Integration1() {
        try {
            IFlightDataLoader dw = new DWFlightDataLoader("./DWTestFiles/flight_data_truncated.dot");
            assertEquals(dw.getNodeList().size(), 92);
            assertEquals(dw.getEdgeList().size(), 289);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        BEAFBackend bd = new BEAFBackend();
        List<String> airports = new ArrayList<>(Arrays.asList(new String[] { "PDX" }));
        List<IFlight> flights = bd.airportCodesToFlights(airports);
        assertEquals(flights.isEmpty(), true);
    }

    /**
     * Tests the integration of backend developer and data wrangler.
     */
    @Test
    public void Integration2() {
        try {
            IFlightDataLoader dw = new DWFlightDataLoader("./DWTestFiles/flight_data_invalid_flight_format1.dot");
            assertEquals(dw.getNodeList().size(), 8);
            assertEquals(dw.getEdgeList().size(), 11);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        BEAFBackend bd = new BEAFBackend();
        List<String> airports = new ArrayList<>(Arrays.asList(new String[] { "ORD", "BOS", "IND" }));
        List<IFlight> flights = bd.airportCodesToFlights(airports);
        assertEquals(flights.size(), 2);
        IFlight f1 = flights.get(0);
        IFlight f2 = flights.get(1);
        assertEquals(f1.getOriginCode(), "ORD");
        assertEquals(f1.getDestinationCode(), "BOS");
        assertEquals(f1.getFlightCost(), 170.0895614);
        assertEquals(f1.getFlightDistance(), 867);
        assertEquals(f2.getOriginCode(), "BOS");
        assertEquals(f2.getDestinationCode(), "IND");
        assertEquals(f2.getFlightCost(), 200.03);
        assertEquals(f2.getFlightDistance(), 818);
    }
}