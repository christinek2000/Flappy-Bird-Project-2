import bagel.*;

/**
 * This class creates a Bomb. The bomb can destroy all pipe types.
 */
public class Bomb extends Weapon{
    private static final Image IMAGE = new Image("cjkim-project-2/res/level-1/bomb.png");
    private static final int SHOOTING_FRAME = 50;

    /**
     * Initialize a newly created Bomb object
     */
    public Bomb(){
        super(SHOOTING_FRAME, IMAGE);
    }

    /**
     * Checks if rock can destroy the pipe
     * @param isPlastic the type of the pipe
     * @return whether rock can destroy the pipe
     */
    @Override
    public boolean canDestroy(Boolean isPlastic){
        return true;
    }
}
