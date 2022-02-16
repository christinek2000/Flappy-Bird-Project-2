import bagel.*;

/**
 * This class creates a Rock. The Rock can destroy plastic pipes only.
 */
public class Rock extends Weapon{
    private static final Image IMAGE = new Image("cjkim-project-2/res/level-1/rock.png");
    private static final int SHOOTING_FRAME = 25;

    /**
     * Initialize a newly created Rock object
     */
    public Rock(){
        super(SHOOTING_FRAME, IMAGE);
    }

    /**
     * Checks if rock can destroy the pipe
     * @param isPlastic the type of the pipe
     * @return whether rock can destroy the pipe
     */
    @Override
    public boolean canDestroy(Boolean isPlastic){
        if(isPlastic) {
            return true;
        }
        else{
            return false;
        }
    }
}
