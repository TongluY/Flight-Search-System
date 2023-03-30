/**
 * This will be the main frontend class, calling all of the different methods in
 * the backend. It will be able to be accessed through JavaFX in the main
 * program, and the command line in the test program.
 */
public interface IPlaneMapperFrontend {
    /**
     * Runs the frontend command loop.
     */
    public void runCommandLoop();

    /**
     * Runs the main menu of the program. Users may choose whether to use the GUI
     * version or the version in the console.
     * 
     * @return
     */
    public int mainMenu();
}
