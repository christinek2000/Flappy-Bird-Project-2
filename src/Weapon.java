import bagel.*;
import bagel.util.*;

/**
 * This class creates a Weapon. The weapon can be spawned, removed, shot, or picked up by a bird.
 */
public class Weapon implements Spawnable{
    private int shootingFrame;
    private double yCoord;
    private double xCoord;
    private final Image weaponImage;
    private Rectangle weaponRectangle;
    private boolean collideBird;
    private boolean shoot;
    private boolean removed;
    private double stepSize;
    private final int SHOOTING_FRAME;
    private static int weaponSpawnInterval = 0;
    private static int weaponIndex = (int)(Math.random() * 2);
    private static final int WEAPON_SPAWN_INTERVAL = 210;
    private static final int REMOVED_XCOORD = -100;

    /**
     * Initialize a newly created Weapon object
     */
    public Weapon(int shootingFrame, Image weaponImage){
        this.shootingFrame = shootingFrame;
        this.weaponImage = weaponImage;
        spawn();
        removed = true;
        stepSize = STEP_SIZE;
        SHOOTING_FRAME = shootingFrame;
    }

    /**
     * Spawns the object to appear in the Window and set to initial values
     */
    public void spawn(){
        yCoord = Math.random()*(500-100+1)+100;
        xCoord = RIGHT_BORDER_X;
        weaponRectangle = weaponImage.getBoundingBoxAt(new Point(xCoord, yCoord));
        removed = false;
        collideBird = false;
        shoot = false;
    }

    /**
     * Draw the object. The x-coordinate of the object decreases by the step size
     */
    public void update(){
        weaponImage.draw(xCoord, yCoord);
        weaponRectangle.moveTo(new Point(xCoord - weaponImage.getWidth() / 2.0,
                yCoord - weaponImage.getHeight() / 2.0));

        if(!collideBird && !shoot) {
            xCoord -= stepSize;
        }

        if(shoot){
            shootingFrame--;
            collideBird = false;
            if(shootingFrame == 0){
                remove();
            }
            xCoord += STEP_SIZE;
        }
    }

    /**
     * When weapon is picked up by the bird, attach weapon to the bird's beak
     * @param birdX x-coordinate of bird's beak
     * @param birdY y-coordinate of bird's beak
     */
    public void pickedUp(double birdX, double birdY){
        xCoord = birdX;
        yCoord = birdY;
    }

    /**
     * Checks whether the weapon can destroy the pipe
     * @param isPlastic the type of the pipe
     * @return whether the weapon can destroy the pipe
     */
    protected boolean canDestroy(Boolean isPlastic){
        return false;
    }

    /**
     * Remove the weapon from the Window
     */
    public void remove(){
        removed = true;
        shoot = false;
        xCoord = REMOVED_XCOORD;
        weaponRectangle.moveTo(new Point(xCoord, yCoord));
        shootingFrame = SHOOTING_FRAME;
    }

    /**
     * Return the remaining shooting frames
     * @return the remaining shooting frames
     */
    public int getShootingFrame(){
        return shootingFrame;
    }

    /**
     * Return whether the weapon was removed
     * @return whether the weapon was removed
     */
    public boolean getRemoved(){
        return removed;
    }

    /**
     * Return Whether weapon was shot
     * @return whether weapon was shot
     */
    public boolean getShoot(){
        return shoot;
    }

    /**
     * Return the weapon's Rectangle object
     * @return the weapon's Rectangle object
     */
    public Rectangle getWeaponRectangle(){
        return weaponRectangle;
    }

    /**
     * Return the step size
     * @return the step size
     */
    public double getStepSize(){
        return this.stepSize;
    }

    /**
     * Return whether the weapon has collided with the bird
     * @return whether the weapon has collided with the bird
     */
    public boolean getCollideBird(){
        return collideBird;
    }

    /**
     * Return weapon index
     * @return weapon index
     */
    public static int getWeaponIndex(){
        return weaponIndex;
    }

    /**
     * Return weapon's spawn interval
     * @return weapon's spawn interval
     */
    public static int getWeaponSpawnInterval(){
        return weaponSpawnInterval;
    }

    /**
     * Set whether bird collided with weapon
     * @param collide whether bird collided with weapon
     */
    public void setCollideBird(boolean collide){
        this.collideBird = collide;
    }

    /**
     * Set the step size
     * @param stepSize the step size
     */
    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    /**
     * Set whether the weapon is shot or not
     * @param shoot whether the weapon is shot or not
     */
    public void setShoot(boolean shoot){
        this.shoot = shoot;
    }

    /**
     * Chooses a random weapon. Index 0 is rock and index 1 is bomb
     */
    public static void setRandomWeapon(){
        weaponIndex = (int)(Math.random() * 2);
    }

    /**
     * Set weapon's spawn interval
     * @param weaponSpawnInterval weapon's spawn interval
     */
    public static void setWeaponSpawnInterval(int weaponSpawnInterval){
        Weapon.weaponSpawnInterval = weaponSpawnInterval;
    }


    /**
     * Set weaponSpawnInterval to its initial value
     */
    public static void resetWeaponInterval(){
        weaponSpawnInterval = WEAPON_SPAWN_INTERVAL;
    }

    /**
     * Decrease weapon's spawn interval by one
     */
    public static void decrementWeaponInterval(){
        weaponSpawnInterval--;
    }
}
