import bagel.*;

/*
    -Counting collision with same pipe after bird's centre x pass the right-x of pipe. <@542_f1 on piazza>
    -Did not include a level class <@524 on piazza>
    -Included javadocs for private and protected methods for personal use
*/

/**
 * SWEN20003 Project 2, Semester 2, 2021
 *
 * The ShadowFlap program implements a game application where a bird flies
 * when the space bar is pressed and falls when the space bar is not pressed.
 * The bird cannot leave the window nor collide with the pipes.
 * In level 1, the bird can pick up a weapon and shoot the weapon.
 *
 * @author: Christine Kim
 */
public class ShadowFlap extends AbstractGame {
    private final Font FONT =  new Font("cjkim-project-2/res/font/slkscr.ttf", 48);
    private final Image BACKGROUND_0 = new Image("cjkim-project-2/res/level-0/background.png");
    private final Image BACKGROUND_1 = new Image("cjkim-project-2/res/level-1/background.png");
    private final Bird bird;
    private final Pipe[] pipe;
    private final Weapon[] weapons;
    private final LifeBar[] lifeBar;
    private Image background;
    private int score;
    private int frameCount;
    private boolean gameStart;
    private boolean scoreAdded;
    private boolean win;
    private boolean levelUp;
    private boolean collidedPipe;
    private int levelUpFrameCount;
    private int level;
    private int timescale;
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 768;
    private static final int SCORE_COORDINATE = 100;
    private static final int SPAWN_INTERVAL = 200;
    private static final int SCORE_MESSAGE_LOCATION = 75;
    private static final int LEVEL1_INSTRUCTION_LOCATION = 68;
    private static final double TIMESCALE_RATE = 1.5;
    private static final int MAX_TIMESCALE = 5;
    private static final int MIN_TIMESCALE = 1;

    /**
     * Initialize a newly created ShadowFlap object
     */
    public ShadowFlap() {
        super(FRAME_WIDTH ,FRAME_HEIGHT, "Shadow Flap");
        bird = new Bird();
        pipe = new Pipe[3];
        for(int i = 0; i < pipe.length; i++){
            pipe[i] = new Lev0Pipe();
        }
        weapons = new Weapon[2];
        weapons[0] = new Rock();
        weapons[1] = new Bomb();
        lifeBar = new LifeBar[6];
        for(int i = 0; i < lifeBar.length; i++){
            lifeBar[i] = new LifeBar();
        }
        background = BACKGROUND_0;
        score = 0;
        frameCount = 100;
        gameStart = false;
        scoreAdded = false;
        win = false;
        levelUp = false;
        collidedPipe = false;
        //adjusted frame number due to FPS
        levelUpFrameCount = 150;
        level = 0;
        timescale = 1;
    }

    /**
     * The entry point for the program.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * Starts game when space key is pressed.
     * @param input access to input device
     */
    @Override
    public void update(Input input) {
        background.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if(input.wasPressed(Keys.SPACE)){
            gameStart = true;
        }
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        if(win){
            drawWinMessage();
        }
        else if(gameStart) {
            if(levelUp){
                levelUpScreen();
                if(levelUpFrameCount == 0){
                    levelUp();
                    gameStart = false;
                }
            }
            else if (LifeBar.getHeartCount() == 0) {
                drawLoseMessage();
            }
            else {
                startGame(input);
            }
        }
        else {
            FONT.drawString("PRESS SPACE TO START",
                            Window.getWidth() / 2.0 - FONT.getWidth("PRESS SPACE TO START") / 2.0,
                            Window.getHeight() / 2.0);
            if(level == 1){
                FONT.drawString("PRESS 'S' TO SHOOT",
                                Window.getWidth() / 2.0 - FONT.getWidth("PRESS 'S' TO SHOOT") / 2.0,
                                Window.getHeight() / 2.0 + LEVEL1_INSTRUCTION_LOCATION);
            }
        }
    }

    /**
     * Starts the game.
     * Determines when to spawn pipes.
     * Changes timescale when L or K is pressed.
     * Bird flies when Space key is pressed.
     * Detect out-of-bound and collision between bird and pipes.
     * @param input access to input device
     */
    private void startGame(Input input){
        if(canSpawnPipe()){
            setPipeIndex();
            pipe[Pipe.getPipeToSpawn()].spawn();
            frameCount = 0;
        }

        if(input.wasPressed(Keys.L)){
            increaseTimescale();
        }
        else if (input.wasPressed(Keys.K)){
            decreaseTimescale();
        }

        if(input.isDown(Keys.SPACE)){
            bird.fly();
        }
        else{
            bird.fall();
        }

        if(bird.outOfBound()){
            loseHeart();
            bird.spawn();
        }

        if(LifeBar.getHeartCount() != 0){
            FONT.drawString("Score: " + score, SCORE_COORDINATE,SCORE_COORDINATE);
            bird.update();
            updatePipes();
            updateLifeBar();
            if(level == 1){
                levelOne(input);
            }
        }

        if(birdCollidePipe()){
            collidedPipe = true;
            loseHeart();
            pipe[Pipe.getScorePipe()].remove();
        }

        if(birdPassedPipe()){
            if(!scoreAdded){
                if(!collidedPipe){
                    score++;
                    checkScore();
                }
            }
            scoreAdded = true;
        }
        else{
            scoreAdded = false;
        }
        collidedPipe = false;

        frameCount++;
    }

    /**
     * Change level 1 game settings
     */
    private void levelUp(){
        background = BACKGROUND_1;
        levelUp = false;
        level = 1;
        LifeBar.setHeartCount(6);
        LifeBar.setTotalHeartNum(6);
        score = 0;
        timescale = 1;
        frameCount = 100;
        Pipe.setPipeSpawnInterval(100);
        bird.levelUp();
        bird.spawn();
        for(int i = 0; i < LifeBar.getTotalHeartNum(); i++){
            lifeBar[i].setIsFullHeart(true);
        }
        for(int i = 0; i < pipe.length; i++){
            pipe[i] = new Lev1Pipe();

        }
    }

    /**
     * Spawn weapons,
     * check collision between weapon and bird or pipes,
     * check collision between bird and flames,
     * shoot weapon
     * @param input access to input device
     */
    private void levelOne(Input input){
        Weapon.decrementWeaponInterval();

        if(weapons[Weapon.getWeaponIndex()].getCollideBird()){
            if(!weapons[Weapon.getWeaponIndex()].getShoot()){
                weapons[Weapon.getWeaponIndex()].pickedUp(bird.getBirdRectangle().right(), bird.getY());
            }
            if(input.wasPressed(Keys.S)){
                weapons[Weapon.getWeaponIndex()].setShoot(true);
            }
        }

        if(weaponCollidePipe()){
            if(weapons[Weapon.getWeaponIndex()].canDestroy(pipe[Pipe.getScorePipe()].getIsPlastic())) {
                pipe[Pipe.getScorePipe()].remove();
                score++;
                checkScore();
            }
            weapons[Weapon.getWeaponIndex()].remove();
        }

        if(spawnWeapon()){
            Weapon.setRandomWeapon();
            weapons[Weapon.getWeaponIndex()].spawn();
            Weapon.resetWeaponInterval();
        }

        if(!weapons[Weapon.getWeaponIndex()].getRemoved()){
            weapons[Weapon.getWeaponIndex()].update();
        }
        if(weapons[Weapon.getWeaponIndex()].getShootingFrame() == 0){
            Weapon.setWeaponSpawnInterval(0);
        }
        if(bird.getBirdRectangle().intersects(weapons[Weapon.getWeaponIndex()].getWeaponRectangle())){
            weapons[Weapon.getWeaponIndex()].setCollideBird(true);
        }

        if(birdCollideFlames()){
            loseHeart();
            pipe[Pipe.getScorePipe()].remove();
            collidedPipe = true;
        }
    }

    /**
     * Checks conditions to spawn weapon
     */
    private boolean spawnWeapon(){
        return Weapon.getWeaponSpawnInterval() <= 0
                && pipe[Pipe.getPipeToSpawn()].getX() < Window.getWidth() - SPAWN_INTERVAL
                && pipe[Pipe.getPipeToSpawn()].getX() > Window.getWidth() - SPAWN_INTERVAL * 2
                && !weapons[Weapon.getWeaponIndex()].getCollideBird();
    }

    /**
     * Checks if passed the score thresholds for level 0 and level 1
     */
    private void checkScore(){
        if(level == 0 && score == 10){
            levelUp = true;
        }
        if(level == 1 && score == 30){
            win = true;
            drawWinMessage();
        }
    }

    /**
     * Checks if bird has passed a pipe without collision
     */
    private boolean birdPassedPipe(){
        return pipe[Pipe.getScorePipe()].getTopPipe().right() < bird.getX();
    }

    /**
     * Change the last full heart to empty heart
     */
    private void loseHeart(){
        lifeBar[LifeBar.getHeartCount()-1].setIsFullHeart(false);
        LifeBar.decrementHeartCount();
    }

    /**
     * Checks if pipe can be spawned
     * @return Whether pipe can be spawned
     */
    private boolean canSpawnPipe(){
        return frameCount >= Pipe.getPipeSpawnInterval();
    }

    /**
     *  Set pipeToSpawn to the index of the pipe to spawn
     *  and set scorePipe to the index of the pipe that is closest to the bird
     */
    private void setPipeIndex(){
        if(Pipe.getPipeToSpawn() == 0){
            Pipe.setPipeToSpawn(1);
            Pipe.setScorePipe(0);
        }
        else if(Pipe.getPipeToSpawn() == 1){
            Pipe.setPipeToSpawn(2);
            Pipe.setScorePipe(1);
        }
        else{
            Pipe.setPipeToSpawn(0);
            Pipe.setScorePipe(2);
        }
        pipe[Pipe.getPipeToSpawn()].setSpawned(true);
    }

    /**
     * Checks if bird collided with either the top pipe or the bottom pipe
     */
    private boolean birdCollidePipe(){
        return bird.getBirdRectangle().intersects(pipe[Pipe.getScorePipe()].getBottomPipe())
                || bird.getBirdRectangle().intersects(pipe[Pipe.getScorePipe()].getTopPipe());
    }

    /**
     * Checks if weapon is shot and collided with the pipes
     */
    private boolean weaponCollidePipe(){
        return !weapons[Weapon.getWeaponIndex()].getCollideBird()
                && weapons[Weapon.getWeaponIndex()].getShoot()
                && (weapons[Weapon.getWeaponIndex()].getWeaponRectangle().intersects(pipe[Pipe.getScorePipe()].getTopPipe())
                || weapons[Weapon.getWeaponIndex()].getWeaponRectangle().intersects(pipe[Pipe.getScorePipe()].getBottomPipe()));
    }

    /**
     * Checks if bird collided with the flames
     */
    private boolean birdCollideFlames(){
        return (bird.getBirdRectangle().intersects(pipe[Pipe.getScorePipe()].getFlamesTopRectangle())
                || bird.getBirdRectangle().intersects(pipe[Pipe.getScorePipe()].getFlamesBottomRectangle()))
                && bird.getX() < pipe[Pipe.getScorePipe()].getFlamesTopRectangle().right();
    }

    /**
     * Updates each of the pipes in the pipe array
     */
    private void updatePipes(){
        for (Pipe value : pipe) {
            value.update();
        }
    }

    /**
     * Update each heart in the life bar. 3 hearts for level 0 and 6 hearts for level 1
     */
    private void updateLifeBar(){
        for(int i = 0; i < LifeBar.getTotalHeartNum(); i++) {
            lifeBar[i].update();
        }
    }

    /**
     * Change weapon and pipe step sizes when timescale is increased
     */
    private void increaseTimescale(){
        if(timescale < MAX_TIMESCALE){
            timescale++;
            for (Pipe value : pipe) {
                value.setStepSize(value.getStepSize() * TIMESCALE_RATE);
            }
            if(level == 1) {
                for (Weapon weapon : weapons) {
                    weapon.setStepSize(weapon.getStepSize() * TIMESCALE_RATE);
                }
                Weapon.setWeaponSpawnInterval((int)(Weapon.getWeaponSpawnInterval() / TIMESCALE_RATE));
            }
            Pipe.setPipeSpawnInterval((int)(Pipe.getPipeSpawnInterval() / TIMESCALE_RATE));
        }
    }

    /**
     * Change weapon and pipe step sizes when timescale is decreased
     */
    private void decreaseTimescale(){
        if(timescale > MIN_TIMESCALE){
            timescale--;
            for (Pipe value : pipe) {
                value.setStepSize(value.getStepSize() / TIMESCALE_RATE);
                System.out.println(value.getStepSize());
            }
            if(level == 1) {
                for (Weapon weapon : weapons) {
                    weapon.setStepSize(weapon.getStepSize() / TIMESCALE_RATE);
                }
                Weapon.setWeaponSpawnInterval((int)(Weapon.getWeaponSpawnInterval() * TIMESCALE_RATE));
            }
            Pipe.setPipeSpawnInterval((int)(Pipe.getPipeSpawnInterval() * TIMESCALE_RATE));
        }
    }

    /**
     * Draws win message
     */
    private void drawWinMessage(){
        FONT.drawString("CONGRATULATIONS!",
                Window.getWidth()/2.0 - FONT.getWidth("CONGRATULATIONS!")/2.0,
                Window.getHeight()/2.0);
    }

    /**
     * Draws lose message
     */
    private void drawLoseMessage(){
        FONT.drawString("GAME OVER",
                Window.getWidth()/2.0 - FONT.getWidth("GAME OVER")/2.0,
                Window.getHeight()/2.0);
        FONT.drawString("FINAL SCORE: " + score ,
                Window.getWidth()/2.0 - FONT.getWidth("FINAL SCORE: " + score)/2.0,
                Window.getHeight()/2.0 + SCORE_MESSAGE_LOCATION);
    }

    /**
     * Draws level up message
     */
    private void drawLevelUpMessage(){
        FONT.drawString("LEVEL-UP!",
                Window.getWidth()/2.0 - FONT.getWidth("LEVEL-UP!")/2.0,
                Window.getHeight()/2.0);
    }

    /**
     * Show the level up message for 150 frames
     */
    private void levelUpScreen(){
        if(levelUpFrameCount != 0){
            drawLevelUpMessage();
            levelUpFrameCount--;
        }
    }
}
