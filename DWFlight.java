import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DWFlight implements IFlight {
    private static final Pattern FLIGHT_PARSER = Pattern.compile(   
        "(?<ORI>[A-Z]{3}) -> (?<DST>[A-Z]{3}) \\[label = \"[$](?<Price>[0-9.]+)\\/(?<Dist>[0-9.]+)mi\"\\]"
    );

    private String origin, destination;
    private Double cost, distance;

    /**
     * Additional constructor that parses through a line in dot file.
     * This method is specific to dot file in this project, in which each line
     * has the following format: <ORI> -> <DST> [label = "$<PRICE>/<DIST>mi"]
     * 
     * @param dotFileLine A line from dot file with specific format.
     * @throws IllegalArgumentException if the formatting is not correct.
     */
    public DWFlight(String dotFileLine) throws IllegalArgumentException {
        Matcher lineMatcher = FLIGHT_PARSER.matcher(dotFileLine);
        if (lineMatcher.find()) {
            this.origin = lineMatcher.group("ORI");
            this.destination = lineMatcher.group("DST");
            try {
                this.cost = Double.parseDouble(lineMatcher.group("Price"));
                this.distance = Double.parseDouble(lineMatcher.group("Dist"));
            }
            catch (NumberFormatException nfe) {
                // This should never happen unless the formatting is incorrect.
                throw new IllegalArgumentException("Error: Incorrect pattern");
            }
        }
        else {
            throw new IllegalArgumentException("Error: The format of line \"" + dotFileLine + "\" is incorrect.");
        }
    }

    public DWFlight(String originCode, String destinationCode, Double flightCost, Double flightDistance) {
        this(String.format("%s -> %s [label = \"$%f/%fmi\"]", 
            originCode.toUpperCase(), destinationCode.toUpperCase(), flightCost, flightDistance
        ));
    }

    /**
     * A method to return airport code of the origin
     * @return Airport code of the origin, as a String.
     */
    @Override
    public String getOriginCode() {
        return origin;
    }

    /**
     * A method to return airport code of the destination
     * @return Airport code of the destination, as a String.
     */
    @Override
    public String getDestinationCode() {
        return destination;
    }

    /**
     * A method to get the flight cost between the origin and the destination.
     * @return Double (object) for flight cost, in US dollars
     */
    @Override
    public Double getFlightCost() {
        return cost;
    }

    /**
     * A method to get the flight distance between the origin and the destination.
     * @return Double (object) for the flight distance, in miles.
     */
    @Override
    public Double getFlightDistance() {
        return distance;
    }
}
