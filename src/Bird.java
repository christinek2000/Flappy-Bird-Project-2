import bagel.*;
import bagel.util.*;

/**
 * This class creates a Bird. The bird can fly or fall and be drawn in corresponding coordinates.
 */
public class Bird implements Spawnable{
    private Image birdWingUp;
    private Image birdWingDown;
    private double y;
    private double yDirection;
    private double fallStepSize;
    private Rectangle birdRectangle;
    private boolean firstFallFrame;
    private int frameCount;
    private static final double FLY_STEP_SIZE = 6;
    private static final double GRAVITY = 0.4;
    private static final int BIRD_START_X = 200;
    private static final int BIRD_START_Y = 350;
    private static final Image Lev0BirdWingUp = new Image("cjkim-project-2/res/level-0/birdWingUp.png");
    private static final Image Lev0BirdWingDown = new Image("cjkim-project-2/res/level-0/birdWingDown.png");
    private static final Image Lev1BirdWingUp = new Image("cjkim-project-2/res/level-0/birdWingUp.png");
    private static final Image Lev1BirdWingDown = new Image("cjkim-project-2/res/level-0/birdWingDown.png");

    /**
     * Initialize a newly created Bird object
     */
    public Bird(){
        birdWingUp = Lev0BirdWingUp;
        birdWingDown = Lev0BirdWingDown;
        spawn();
        firstFallFrame = true;
        frameCount = 0;
    }

    /**
     * Spawns the object to appear in the Window and set to initial values
     */
    public void spawn(){
        y = BIRD_START_Y;
        yDirection = 0;
        fallStepSize = 0;
        birdRectangle = birdWingDown.getBoundingBoxAt(new Point(BIRD_START_X, y));
    }

    /**
     * Draw the object. The x-coordinate of the object decreases by the step size
     */
    public void update(){
        if(frameCount == 0){
            birdWingDown.draw(BIRD_START_X, y);
        }
        else if(frameCount % 10 == 0) {
            birdWingUp.draw(BIRD_START_X, y);
        }
        else {
            birdWingDown.draw(BIRD_START_X, y);
        }
        frameCount++;
    }

    /**
     * Bird's y-coordinate decreased by 6 pixels each frame
     */
    public void fly(){
        y -= FLY_STEP_SIZE;
        fallStepSize = 0;
        firstFallFrame = true;
        birdRectangle.moveTo(new Point(BIRD_START_X - birdWingDown.getWidth()/2,
                                       y - birdWingDown.getWidth()/2));
    }

    /**
     * Bird y coordinate increased.
     * accelerated by 0.4 per frame.
     */
    public void fall(){
        setBirdDirectionTo(new Point(BIRD_START_X, Window.getHeight()));
        if(firstFallFrame){
            fallStepSize = GRAVITY;
        }
        else{
            fallStepSize += GRAVITY;
        }
        if(fallStepSize > 10){
            fallStepSize = 10;
        }
        y += fallStepSize * yDirection;
        firstFallFrame = false;
        birdRectangle.moveTo(new Point(BIRD_START_X - birdWingDown.getWidth()/2,
                                       y - birdWingDown.getWidth()/2));
    }

    /**
     * Set the bird's direction to given Point
     * @param destination bird's destination point
     */
    public void setBirdDirectionTo(Point destination){
        double distance = destination.distanceTo(new Point(BIRD_START_X, y));
        yDirection = (destination.y - y) / distance;
    }

    /**
     * Checks if bird is out of the window
     * @return whether bird's coordinates is outside the range 0 to height of the frame
     */
    public boolean outOfBound(){
        return y < 0 || y >= Window.getHeight();
    }

    /**
     * Change images to level 1 bird
     */
    public void levelUp(){
        birdWingUp = Lev1BirdWingUp;
        birdWingDown = Lev1BirdWingDown;
    }

    /**
     * Returns the bird's Rectangle object
     * @return the bird's Rectangle object
     */
    public Rectangle getBirdRectangle(){
        return birdRectangle;
    }

    /**
     * Returns the bird's x-coordinate
     * @return the bird's x-coordinate
     */
    public double getX() {
        return BIRD_START_X;
    }

    /**
     * Returns the bird's y-coordinate
     * @return the bird's y-coordinate
     */
    public double getY() {
        return y;
    }
}
