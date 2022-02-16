/**
 * This class creates a Lev0Pipe. The Lev0Pipe can only be a plastic pipe.
 * The starting coordinate of the pipe gap is either 100, 300, or 500.
 */
public class Lev0Pipe extends Pipe {
    private static final int HIGH_GAP_Y = 100;
    private static final int MID_GAP_Y = 300;
    private static final int LOW_GAP_Y = 500;

    /**
     * Initialize a newly created Lev0Pipe object
     */
    public Lev0Pipe(){
        super();
        setIsPlastic(false);
    }

    /**
     * Assign the y coordinates of the top and bottom pipes
     */
    @Override
    public void assignGap(){
        double random = (int)(Math.random()*3);
        if(random == 0){
            setTopY(HIGH_GAP_Y - PIPE_LENGTH/2.0);
        }
        else if(random == 1){
            setTopY(MID_GAP_Y - PIPE_LENGTH/2.0);
        }
        else{
            setTopY(LOW_GAP_Y - PIPE_LENGTH/2.0);
        }
        setBottomY(getTopY() + SPACE_BETWEEN_PIPES + PIPE_LENGTH);
    }
}
