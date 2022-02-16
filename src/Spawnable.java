import bagel.Window;

public interface Spawnable {
    /**
     * The instance representing the x-coordinate of the right border of the window.
     */
    int RIGHT_BORDER_X = Window.getWidth();

    /**
     * The instance representing the step size.
     */
    //adjusted step size due to FPS
    int STEP_SIZE = 5;

    /**
     * Spawns the object to appear in the Window and set to initial values
     */
    void spawn();

    /**
     * Draw the object. The x-coordinate of the object decreases by the step size
     */
    void update();
}
