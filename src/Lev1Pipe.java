import bagel.*;
import bagel.util.*;

/**
 * This class creates a Lev1Pipe. The Lev1Pipe can be either a steel or plastic pipe.
 * Steel pipes can shoot flames. The starting coordinate of the pipe gap is random between
 * the range from 100 to 500.
 */
public class Lev1Pipe extends Pipe {
    private final Image flames = new Image("cjkim-project-2/res/level-1/flame.png");
    private final Rectangle flamesTopRectangle;
    private final Rectangle flamesBottomRectangle;
    private int flamesFrameCount;
    private boolean isPlastic;
    private static final int FLAME_INTERVAL = 20;
    private static final int FLAME_FRAME_COUNT = 3;

    /**
     * Initialize a newly created Lev1Pipe object
     */
    public Lev1Pipe(){
        super();
        flamesTopRectangle = flames.getBoundingBox();
        flamesBottomRectangle = flames.getBoundingBox();
        flamesFrameCount = FLAME_INTERVAL;
        isPlastic = true;
        setIsPlastic(false);
    }

    /**
     * Draw the object. The x-coordinate of the object decreases by the step size
     */
    @Override
    public void update() {
        super.update();
        if(getRemoved()){
            flamesTopRectangle.moveTo(new Point(Window.getWidth(),0));
            flamesBottomRectangle.moveTo(new Point(Window.getWidth(),0));
        }
        if(getSpawned() && !isPlastic && !getRemoved()) {
            if (flamesFrameCount == 0) {
                reinitializeFlameCount();
            }
            if (canDrawFlames()) {
                flames.draw(getTopPipe().left() + flames.getWidth() / 2.0, getTopPipe().bottom() + 10);
                flames.draw(getBottomPipe().left() + flames.getWidth() / 2.0, getBottomPipe().top() - 10, new DrawOptions().setRotation(Math.PI));
                flamesTopRectangle.moveTo(new Point(getTopPipe().left(), getTopPipe().bottom() + 10 - flames.getHeight()/2));
                flamesBottomRectangle.moveTo(new Point(getBottomPipe().left(), getBottomPipe().top() - 10 - flames.getHeight()/2.0));
            }
            flamesFrameCount--;
        }
    }

    /**
     * Assign the y coordinates of the top and bottom pipes
     */
    @Override
    public void assignGap(){
        double randomY = Math.random()*(500-100+1)+100;
        setTopY(randomY - PIPE_LENGTH/2.0);
        setBottomY(randomY + SPACE_BETWEEN_PIPES + PIPE_LENGTH/2.0);
    }

    /**
     * Choose whether to use a plastic pipe or a steel pipe
     * @param zeroOrOne zero makes the pipe a plastic pipe and one makes the pipe a steel pipe
     */
    @Override
    protected void choosePipe(int zeroOrOne) {
        if(zeroOrOne == 0){
            setImage(PLASTIC_PIPE);
            isPlastic = true;
            setIsPlastic(true);
        }
        else{
            setImage(STEEL_PIPE);
            isPlastic = false;
            setIsPlastic(false);
        }
    }

    /**
     * Return top flame's Rectangle object
     * @return top flame's Rectangle object
     */
    @Override
    public Rectangle getFlamesTopRectangle(){
        return flamesTopRectangle;
    }

    /**
     * Return the bottom flame's Rectangle object
     * @return the bottom flame's Rectangle object
     */
    @Override
    public Rectangle getFlamesBottomRectangle(){
        return flamesBottomRectangle;
    }

    /**
     * Reinitialize the frame count for the flames
     */
    private void reinitializeFlameCount(){
        flamesFrameCount = FLAME_INTERVAL;
    }

    /**
     * Checks if flames can be drawn
     * Can draw flames if current frame count is greater than 17
     * @return whether flames can be drawn
     */
    private boolean canDrawFlames(){
        return flamesFrameCount <= FLAME_FRAME_COUNT;
    }
}
