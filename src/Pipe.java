import bagel.*;
import bagel.util.*;

/**
 * This class creates a Pipe. The pipe can be spawned or removed.
 */
public class Pipe implements Spawnable{
    private Image image;
    private double x;
    private double topY;
    private double bottomY;
    private Rectangle topPipe;
    private Rectangle bottomPipe;
    private double stepSize;
    private boolean spawned;
    private boolean isPlastic;
    private boolean removed;
    private static int scorePipe = 0;
    private static int pipeToSpawn = 0;
    private static int pipeSpawnInterval = 100;
    protected static final Image STEEL_PIPE = new Image("cjkim-project-2/res/level-1/steelPipe.png");
    protected static final Image PLASTIC_PIPE = new Image("cjkim-project-2/res/level/plasticPipe.png");
    protected static final int SPACE_BETWEEN_PIPES = 168;
    protected static final int PIPE_LENGTH = 768;

    /**
     * Initialize a newly created Pipe object
     */
    public Pipe(){
        this.image = PLASTIC_PIPE;
        spawn();
        stepSize = STEP_SIZE;
        spawned = false;
        isPlastic = true;
    }

    /**
     * Spawns the object to appear in the Window and set to initial values
     */
    public void spawn(){
        x = RIGHT_BORDER_X;
        assignGap();
        choosePipe((int)(Math.random()*2));
        topPipe = image.getBoundingBoxAt(new Point(x , topY));
        bottomPipe = image.getBoundingBoxAt(new Point(x, bottomY));
        spawned = true;
        removed = false;
    }

    /**
     * Draw the object. The x-coordinate of the object decreases by the step size
     */
    public void update(){
        if(!removed){
            if(spawned) {
                image.draw(x, topY);
                image.draw(x, bottomY, new DrawOptions().setRotation(Math.PI));
                topPipe.moveTo(new Point(x - image.getWidth() / 2, topY - image.getHeight() / 2));
                bottomPipe.moveTo(new Point(x - image.getWidth() / 2, bottomY - image.getHeight() / 2));
                x -= stepSize;
                if(x <= 0 - image.getWidth()){
                    spawned = false;
                }
            }
        }
    }

    /**
     * Choose whether to use a plastic pipe or a steel pipe
     * @param zeroOrOne zero makes the pipe a plastic pipe and one makes the pipe a steel pipe
     */
    protected void choosePipe(int zeroOrOne){
        image = PLASTIC_PIPE;
    }

    /**
     * Assign the y coordinates of the top and bottom pipes
     */
    protected void assignGap(){
        bottomY = image.getHeight()/2 + Window.getHeight()/2.0 + SPACE_BETWEEN_PIPES/2.0;
        topY = image.getHeight()/2 - Window.getHeight()/2.0 - SPACE_BETWEEN_PIPES/2.0;
    }

    /**
     * Removes the top and bottom pipes from the Window
     */
    public void remove(){
        removed = true;
        spawned = false;
        x = Window.getWidth();
        topPipe.moveTo(new Point(x, topY));
        bottomPipe.moveTo(new Point(x, bottomY));
    }

    /**
     * Return the value of removed
     * @return the value of removed
     */
    public boolean getRemoved(){
        return removed;
    }

    /**
     * Return the step size
     * @return the step size
     */
    public double getStepSize(){
        return stepSize;
    }

    /**
     * Return whether the pipe is plastic or not
     * @return whether the pipe is plastic or not
     */
    public boolean getIsPlastic(){
        return isPlastic;
    }

    /**
     * Return the top pipe Rectangle object
     * @return the top pipe Rectangle object
     */
    public Rectangle getTopPipe(){
        return topPipe;
    }

    /**
     * Return the bottom pipe Rectangle object
     * @return the bottom pipe Rectangle object
     */
    public Rectangle getBottomPipe(){
        return bottomPipe;
    }

    /**
     * Return the x-coordinate of the pipes
     * @return the x-coordinate of the pipes
     */
    public double getX(){
        return x;
    }

    /**
     * Return the y-coordinate of the top pipe
     * @return the y-coordinate of the top pipe
     */
    public double getTopY(){ return topY; }

    /**
     * Return whether pipe is spawned
     * @return whether pipe is spawned
     */
    public boolean getSpawned(){
        return spawned;
    }

    /**
     * Return top flame's Rectangle object
     * @return the top flame's Rectangle object
     */
    protected Rectangle getFlamesTopRectangle(){
        return null;
    }

    /**
     * Return bottom flame's Rectangle object
     * @return the bottom flame's rectangle object
     */
    protected Rectangle getFlamesBottomRectangle(){
        return null;
    }

    /**
     * Return index of pipe closest to the Bird object
     * @return index of pipe closest to the Bird object
     */
    public static int getScorePipe(){
        return scorePipe;
    }

    /**
     * Return index of pipe to be spawned
     * @return index of pipe to be spawned
     */
    public static int getPipeToSpawn(){
        return pipeToSpawn;
    }

    /**
     * Return pipe's spawn interval
     * @return pipe's spawn interval
     */
    public static int getPipeSpawnInterval(){
        return pipeSpawnInterval;
    }

    /**
     * Sets the value of the step size
     * @param stepSize the step size
     */
    public void setStepSize(double stepSize){
        this.stepSize = stepSize;
    }

    /**
     * Set value of isPlastic
     * @param isPlastic whether pipe is plastic or not
     */
    public void setIsPlastic(boolean isPlastic){
        this.isPlastic = isPlastic;
    }

    /**
     * Set value of top pipe's y-coordinate
     * @param topY top pipe's y-coordinate
     */
    public void setTopY(double topY){ this.topY = topY; }

    /**
     * Set value of bottom pipe's y-coordinate
     * @param bottomY bottom pipe's y-coordinate
     */
    public void setBottomY(double bottomY){ this.bottomY = bottomY; }

    /**
     * Set the image of the pipes
     * @param image the image of the pipes
     */
    public void setImage(Image image){
        this.image = image;
    }

    /**
     * Set the value of spawned
     * @param spawned whether pipes are spawned or now
     */
    public void setSpawned(boolean spawned){
        this.spawned = spawned;
    }

    /**
     * Set the pipe's spawn interval to the parameter value
     * @param pipeSpawnInterval pipe's spawn interval
     */
    public static void setPipeSpawnInterval(int pipeSpawnInterval){
        Pipe.pipeSpawnInterval = pipeSpawnInterval;
    }

    /**
     * Set the index of the pipe closest to the Bird object to the parameter value
     * @param scorePipe index of pipe closest to the Bird object
     */
    public static void setScorePipe(int scorePipe){
        Pipe.scorePipe = scorePipe;
    }

    /**
     * Set the index of the pipe to be spawned
     * @param pipeToSpawn index of the pipe to be spawned
     */
    public static void setPipeToSpawn(int pipeToSpawn){
        Pipe.pipeToSpawn = pipeToSpawn;
    }
}
