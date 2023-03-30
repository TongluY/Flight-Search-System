import java.util.LinkedList;
import java.util.List;

public class FDBackend implements IBEAFBackend {
    public String[] airports = { "Atlanta", "Boston", "Chicago", "Denver", "Los Angeles", "New York", "San Francisco",
            "Seattle" };

    @Override
    public void invalidateAirport(String airportCode) throws IllegalArgumentException {
        System.out.println("calling the method invalidateAirport().");
    }

    @Override
    public void validateAirport(String airportCode) throws IllegalArgumentException {
        if (airportCode.equals("TYN"))
            throw new IllegalArgumentException();
    }

    @Override
    public void setMileagePortion(double mileagePortion) throws IllegalArgumentException {
    }

    @Override
    public void setCostPortion(double costPortion) throws IllegalArgumentException {
    }

    @Override
    public void setPortions(double mileagePortion, double costPortion) throws IllegalArgumentException {
        System.out.println("calling the method setPortions().\n\n");
    }

    @Override
    public LinkedList<IFlight> getBestItinerary(String originAirportCode, String destAirportCode)
            throws IllegalArgumentException {
        LinkedList<IFlight> list = new LinkedList<>();
        FDFlight f1 = new FDFlight("AMS", "BOD", 200.0, 5000.0);
        list.add(f1);
        return list;
    }

    @Override
    public LinkedList<IFlight> getCheapestItinerary(String originAirportCode, String destAirportCode)
            throws IllegalArgumentException {
        LinkedList<IFlight> list = new LinkedList<>();
        FDFlight f1 = new FDFlight("AMS", "BOD", 200.0, 5000.0);
        FDFlight f2 = new FDFlight("BOD", "CDG", 300.0, 500.0);
        FDFlight f3 = new FDFlight("CDG", "FCO", 100.0, 1000.0);
        list.add(f1);
        list.add(f2);
        list.add(f3);
        return list;
    }

    @Override
    public LinkedList<IFlight> getShortestItinerary(String originAirportCode, String destAirportCode)
            throws IllegalArgumentException {
        LinkedList<IFlight> list = new LinkedList<>();
        FDFlight f1 = new FDFlight("AMS", "BOD", 200.0, 5000.0);
        list.add(f1);
        return list;
    }

    @Override
    public boolean validateAllAirports() {
        // This is a placeholder class - ignore
        return false;
    }

    @Override
    public List<String> getInvalidAirports() {
        // This is a placeholder class - ignore
        return null;
    }

    @Override
    public boolean isValid(String airportCode) throws IllegalArgumentException {
        // This is a placeholder class - ignore
        return false;
    }

}
