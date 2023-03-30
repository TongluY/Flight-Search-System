import java.util.LinkedList;
import java.util.Scanner;

public class PlaneMapperFrontend implements IPlaneMapperFrontend {

    private Scanner userInputScanner;
    private IBEAFBackend backend;
    private final int MAIN = 0;
    private int state;

    public PlaneMapperFrontend(Scanner userInputScanner, IBEAFBackend backend) {
        this.userInputScanner = userInputScanner;
        this.backend = backend;
        state = MAIN;
    }

    @Override
    public void runCommandLoop() {
        System.out.printf("\nWelcome to the Plane Route Application!\n" + "x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x+x\n");
        int status = 1; // variable that represents what status the entire application is in, such as
                        // exiting or running
        while (status != 0) { // 0 status represents exit
            if (status == 1) { // 1 status represents normal running
                if (state == MAIN) {
                    status = mainMenu();
                }
            } else {
                // this should not happen as the program never sets the state variable to
                // anything other the ones
                // that have been checked for
                System.out.println("Stop hacking, please.\n");
                status = 0;
            }
        }
    }

    @Override
    public int mainMenu() {
        boolean cont = true;
        System.out.println("Please select an option from the menu below:");
        System.out.println("1. Use the console version of the application");
        System.out.println("2. Exit the application");
        while (cont) {
            int choice = userInputScanner.nextInt();
            userInputScanner.nextLine();
            if (choice == 1) {
                consoleMenu();
                System.out.println("Thanks for using Bootleg Expedia!");
                cont = false;
            } else if (choice == 2) {
                System.out.println("Thanks for using Bootleg Expedia!");
                cont = false;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return 0;
    }

    private void consoleMenu() {
        System.out.println("Welcome to the console version of the application!");
        boolean keep = true;
        while (keep) {
            System.out.println("Search criteria:");
            System.out.println("\t" + "1) Best overall");
            System.out.println("\t" + "2) Cheapest");
            System.out.println("\t" + "3) Fastest");
            System.out.println("\t" + "4) Advanced options");
            int choice = userInputScanner.nextInt();
            userInputScanner.nextLine();
            String[] set = getOriginandDes();
            String origin = set[0];
            String destination = set[1];
            if (choice == 1) {
                LinkedList<IFlight> itinerary = backend.getBestItinerary(origin, destination);
                loopthruList(origin, destination, itinerary);
                keep = nextSearch();
            } else if (choice == 2) {
                LinkedList<IFlight> itinerary = backend.getCheapestItinerary(origin, destination);
                loopthruList(origin, destination, itinerary);
                keep = nextSearch();
            } else if (choice == 3) {
                LinkedList<IFlight> itinerary = backend.getShortestItinerary(origin, destination);
                loopthruList(origin, destination, itinerary);
                keep = nextSearch();
            } else if (choice == 4) {
                advanced(origin, destination);
                keep = nextSearch();
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private boolean nextSearch() {
        System.out.println("Would you like to make another search? (y)es or (n)o:");
        Boolean stop = true;
        Boolean r = null;
        while (stop) {
            String search = userInputScanner.nextLine();
            if (search.toLowerCase().equals("y")) {
                System.out.println();
                stop = false;
                r = true;
            } else if (search.toLowerCase().equals("n")) {
                System.out.println();
                stop = false;
                r = false;
            } else {
                System.out.println("The letter you entered is invalid, please enter again!");
                stop = true;
            }
        }
        return r;
    }

    private String throwExceptionUntilValid(String code) {
        boolean success = false;
        while (!success) {
            try {
                backend.validateAirport(code);
                success = true;
            } catch (IllegalArgumentException e) {
                if (code.trim().isEmpty()) {
                    success = true;
                } else {
                    System.out.println("The airport code you entered is invalid, please enter again!");
                    code = userInputScanner.nextLine();
                }
            }
        }
        return code;
    }

    private String[] getOriginandDes() {
        System.out.println("Hi! Let’s find the best flight for you!");
        System.out.println();
        System.out.print("Please enter the origin airport code:");
        String origin = userInputScanner.nextLine();
        origin = throwExceptionUntilValid(origin);
        System.out.println();
        System.out.print("Please enter the destination airport code:");
        String destination = userInputScanner.nextLine();
        boolean same = true;
        while (same) {
            if (!destination.equals(origin))
                same = false;
            else {
                System.out.println("\nThis is the same airport! Please enter a different destination.");
                destination = userInputScanner.nextLine();
            }
        }
        destination = throwExceptionUntilValid(destination);
        System.out.println();
        String[] result = new String[] { origin, destination };
        System.out.println("Please enter any airports you’d like to avoid (leave blank if none):");
        String airportAvoided = userInputScanner.nextLine();
        System.out.println();
        airportAvoided = throwExceptionUntilValid(airportAvoided);
        if (!airportAvoided.trim().isEmpty()) {
            backend.invalidateAirport(airportAvoided);
        }
        return result;
    }

    private void loopthruList(String origin, String destination, LinkedList<IFlight> itinerary) {
        String result = "The best way to fly from " + origin + " to " + destination + " is ";
        if (itinerary.size() == 1) {
            result += "on a nonstop flight, costing $" + itinerary.get(0).getFlightCost() + ".";
            System.out.println(result + "\n");
        } else {
            Double cost = 0.0;
            result += "to fly from ";
            for (int i = 0; i < itinerary.size(); i++) {
                if (i == 0) {
                    result += itinerary.get(i).getOriginCode() + " to " + itinerary.get(i).getDestinationCode();
                    cost += itinerary.get(i).getFlightCost();
                    continue;
                }
                result += ", and then " + itinerary.get(i).getOriginCode() + " to "
                        + itinerary.get(i).getDestinationCode();
                cost += itinerary.get(i).getFlightCost();
            }
            result += ".\nThis has an average total price of $" + cost + ".";
            System.out.println(result + "\n");
        }
    }

    private void advanced(String origin, String destination) {
        System.out.println("Advanced options:");
        boolean equals100 = false;
        while (!equals100) {
            System.out.println("Please enter the search weight on cost (percentage form):");
            System.out.println("For example if the expected weight on cost is 70%, then type 0.7");
            Double cost = userInputScanner.nextDouble();
            userInputScanner.nextLine();
            System.out.println();
            System.out.println("Please enter the search weight on time (percentage form):");
            Double time = userInputScanner.nextDouble();
            userInputScanner.nextLine();
            System.out.println();
            if (Math.abs(cost + time - 1.0) > 1e-6) {
                System.out.println("This does not add up to 100%, please try again.");
                System.out.println();
            } else {
                equals100 = true;
                backend.setPortions(time, cost);
                LinkedList<IFlight> itinerary = backend.getBestItinerary(origin, destination);
                loopthruList(origin, destination, itinerary);
            }
        }
    }
}
