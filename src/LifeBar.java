import bagel.*;

/**
 * This class creates a life bar. The life bar can either be a full heart or an empty heart
 */
public class LifeBar{
    private final Image fullLife = new Image("cjkim-project-2/res/level/fullLife.png");
    private final Image noLife = new Image("cjkim-project-2/res/level/noLife.png");
    private final int thisXCoord;
    private boolean isFullHeart;
    private static int xCoord = 50;
    private static final int Y_COORD = 15;
    private static final int GAP = 50;
    private static int totalHeartNum = 3;
    private static int heartCount = 3;

    /**
     * Initialize a newly created LifeBar object
     */
    public LifeBar(){
        thisXCoord = xCoord + GAP;
        xCoord = thisXCoord;
        isFullHeart = true;
    }

    /**
     * Draw life bar
     */
    public void update(){
        if(isFullHeart){
            fullLife.drawFromTopLeft(thisXCoord, Y_COORD);
        }
        else{
            noLife.drawFromTopLeft(thisXCoord, Y_COORD);
        }
    }

    /**
     * Returns number of filled hearts
     * @return number of filled hearts
     */
    public static int getHeartCount(){
        return heartCount;
    }

    /**
     * Returns current total number of hearts in the life bar
     * @return total number of hearts
     */
    public static int getTotalHeartNum(){
        return totalHeartNum;
    }

    /**
     * Set the total number of hearts to parameter value
     * @param totalHeartNum total number of hearts
     */
    public static void setTotalHeartNum(int totalHeartNum){
        LifeBar.totalHeartNum = totalHeartNum;
    }

    /**
     * Set the number of filled hearts to parameter value
     * @param heartCount number of filled hearts
     */
    public static void setHeartCount(int heartCount){
        LifeBar.heartCount = heartCount;
    }

    /**
     * Set whether the heart is a full heart or an empty heart
     * @param isFullHeart whether the heart is a full heart or an empty heart
     */
    public void setIsFullHeart(boolean isFullHeart){
        this.isFullHeart = isFullHeart;
    }


    /**
     * Decrease the value of heartCount by one
     */
    public static void decrementHeartCount(){
        heartCount--;
    }

}
