import java.util.Scanner;

public class PlaneMapper {
    public static void main(String[] args) {
        // Instantiate the backend
        IBEAFBackend backend = new BEAFBackend();
        // Instantiate the scanner for user input
        Scanner userInputScanner = new Scanner(System.in);
        // Instantiate the front end and pass references to the scanner, backend
        IPlaneMapperFrontend frontend = new PlaneMapperFrontend(userInputScanner, backend);

        // Start the input loop of the front end
        frontend.runCommandLoop();
    }
}
