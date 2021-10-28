package demolition;

import demolition.drawables.UI;
import demolition.models.Bomb;
import demolition.models.Enemy;
import demolition.models.Player;
import demolition.util.Direction;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;
    public static final int top_offset=64;

    public static int gridw=15;
    public static int gridh=13;


    public static final int FPS = 60;

    public PImage wall_solid;
    public PImage wall_broken;
    public PImage ground_path;
    public PImage ground_goal;

    public PImage player_up;
    public PImage player_right;
    public PImage player_down;
    public PImage player_left;

    public PImage red_enemy_up;
    public PImage red_enemy_left;
    public PImage red_enemy_down;
    public PImage red_enemy_right;

    public PImage yellow_enemy_up;
    public PImage yellow_enemy_left;
    public PImage yellow_enemy_down;
    public PImage yellow_enemy_right;

    public PImage bomb_counting;
    public PImage bomb_explosion;

    public PImage icon_lives;
    public PImage icon_timer;


    public PImage bomb_explosion_hori;
    public PImage bomb_explosion_vert;

    public PFont font;

    ////game state
    Level[] levels;
    private int currentlevel;
    public int lives=3;
    public Bomb bomb;
    public Player player;
    public Enemy red_enemy;
    public Enemy yellow_enemy;
    private int uiSavedTime;
    private int savedTime;
    private int bombTime;
    private int fireTime;
    int enemy_movt_interval = 1000;
    private int maxTime=180;
    public int timer;
    private int detonation_time=2000;
    private int decomposition_time=1000;
    private boolean restartable=false;
    private GameState state;
    ////enemies creted by their attribute/type  and not individual so incase of multiple occurence they will be
    ///controlled same way their states only differentiated by their initial conditions and not individual state control


    public App() {
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS);


        //load config
        JSONObject json= loadJSONObject("config.json");
        //        System.out.println(json.toString());
        GameConfig config;
        config=GameConfig.fromJSON(json, this);


        // load level maps
        levels = config.getLevels();
        for (int i = 0; i < levels.length; i++) {
            try {
                levels[i].setMap(new String(Files.readAllBytes(Paths.get(levels[i].getPath()))) );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // load font
        font = this.createFont("PressStart2P-Regular.ttf", 28);

        // load assets
        try {
            Class<? extends App> c =this.getClass();
            wall_solid = loadImage(new URI(Objects.requireNonNull(c.getResource("/wall/solid.png")).toString()).getPath() );
            wall_broken = loadImage(new URI(Objects.requireNonNull(c.getResource("/broken/broken.png")).toString()).getPath() );
            ground_path = loadImage(new URI(Objects.requireNonNull(c.getResource("/empty/empty.png")).toString()).getPath() );
            ground_goal = loadImage(new URI(Objects.requireNonNull(c.getResource("/goal/goal.png")).toString()).getPath() );
            player_up = loadImage(new URI(Objects.requireNonNull(c.getResource("/player/player_up1.png")).toString()).getPath() );
            player_left = loadImage(new URI(Objects.requireNonNull(c.getResource("/player/player_left1.png")).toString()).getPath() );
            player_down = loadImage(new URI(Objects.requireNonNull(c.getResource("/player/player1.png")).toString()).getPath() );
            player_right = loadImage(new URI(Objects.requireNonNull(c.getResource("/player/player_right1.png")).toString()).getPath() );

            red_enemy_up = loadImage(new URI(Objects.requireNonNull(c.getResource("/red_enemy/red_up1.png")).toString()).getPath() );
            red_enemy_left = loadImage(new URI(Objects.requireNonNull(c.getResource("/red_enemy/red_left1.png")).toString()).getPath() );
            red_enemy_down = loadImage(new URI(Objects.requireNonNull(c.getResource("/red_enemy/red_down1.png")).toString()).getPath() );
            red_enemy_right = loadImage(new URI(Objects.requireNonNull(c.getResource("/red_enemy/red_right1.png")).toString()).getPath() );

            yellow_enemy_up = loadImage(new URI(Objects.requireNonNull(c.getResource("/yellow_enemy/yellow_up1.png")).toString()).getPath() );
            yellow_enemy_left = loadImage(new URI(Objects.requireNonNull(c.getResource("/yellow_enemy/yellow_left1.png")).toString()).getPath() );
            yellow_enemy_down = loadImage(new URI(Objects.requireNonNull(c.getResource("/yellow_enemy/yellow_down1.png")).toString()).getPath() );
            yellow_enemy_right = loadImage(new URI(Objects.requireNonNull(c.getResource("/yellow_enemy/yellow_right1.png")).toString()).getPath() );

            bomb_counting= loadImage(new URI(Objects.requireNonNull(c.getResource("/bomb/bomb1.png")).toString()).getPath() );
            bomb_explosion = loadImage(new URI(Objects.requireNonNull(c.getResource("/explosion/centre.png")).toString()).getPath() );
            bomb_explosion_hori = loadImage(new URI(Objects.requireNonNull(c.getResource("/explosion/horizontal.png")).toString()).getPath() );
            bomb_explosion_vert = loadImage(new URI(Objects.requireNonNull(c.getResource("/explosion/vertical.png")).toString()).getPath() );

            icon_lives= loadImage(new URI(Objects.requireNonNull(c.getResource("/icons/player.png")).toString()).getPath() );
            icon_timer = loadImage(new URI(Objects.requireNonNull(c.getResource("/icons/clock.png")).toString()).getPath() );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.init();
    }

    private void init(){
        ///initialize game state
        this.state=GameState.RUNNING;
        this.player=new Player();
        this.yellow_enemy=new Enemy();
        this.red_enemy=new Enemy();
        this.currentlevel=0;
        this.maxTime=this.levels[currentlevel].getTime();

        ////init game time
        this.timer =this.maxTime;

        ///initialize game
        this.levels[currentlevel].initLevel();


        savedTime= millis();//current time
        uiSavedTime= millis();//current time
    }

    public void draw() {
        //draw bg
        background(unhex("fddd11"));

        if(this.state== GameState.RUNNING){
            ///perform ui update
            autoState();

            //draw ui
            UI life = new UI(this, 4, 0, UI.UIWidget.LIFE);
            life.draw();
            UI time = new UI(this, 8, 0, UI.UIWidget.TIMER);
            time.draw();
            ///ui is a eg of drive  done from hardcode and not soft


            ///perform movements
            boolean moved = autoMovements();
            boolean fired = autoDetonations();

            //draw levels
            this.levels[currentlevel].drawLevel();


            //check game state
            //////best to check after auto mvts not direct so not reupdate hit multipel tiems
            if(moved){
                if(this.player.isHit() ){
                    System.out.println("player hit");
                    if(this.lives>=0){
                        lives--;
                        this.restartLevel();
                    }else{
                        this.finish(GameState.OUT_OF_LIFE);
                    }
                }

            }
            if (timer ==0) {
                this.finish(GameState.OUT_OF_TIME);
            }

        }else if(this.state== GameState.OUT_OF_LIFE || this.state== GameState.OUT_OF_TIME){
            ///now restarting game i supose press enter to play again
            textSize(30);
            this.fill(0);
            textAlign(CENTER);
            text("GAME OVER", (float)WIDTH/2, (float)HEIGHT/2);
            restartable=true;
        }else{
            assert (this.state== GameState.GOAL_REACHED);
            if(currentlevel<this.levels.length-1){
                currentlevel++;
                System.out.println(this.levels.length+" level set to "+this.currentlevel);
                this.state=GameState.RUNNING;
                restartLevel();
            }else{
                textSize(30);
                this.fill(0);
                textAlign(CENTER);
                text("YOU WIN", (float)WIDTH/2, (float)HEIGHT/2);
                restartable=true;
            }
        }



    }

    private void autoState() {
        int passedTime = millis() - uiSavedTime;
        if (passedTime > 1000) {///move every second
            this.timer--;
            uiSavedTime = millis();
        }
    }

    private void restartLevel() {
        player.isHit(false);
        this.levels[currentlevel].initLevel();

    }

    public void finish(GameState state) {
        this.state=state;
        System.out.println(" game state "+state);
    }


    private boolean autoMovements(){//analogous of keypressed
        int passedTime = millis() - savedTime;
        if (passedTime > enemy_movt_interval) {
//            System.out.println("moving "+savedTime);
            if(this.red_enemy!=null){
                this.red_enemy.setMoving(true);
                this.red_enemy.setDirection(red_enemy.getDirection());
            }
            if(this.yellow_enemy!=null){
                this.yellow_enemy.setMoving(true);
                this.yellow_enemy.setDirection(yellow_enemy.getDirection());
            }

            savedTime = millis();
            return true;
        }else return false;
    }

    private boolean autoDetonations(){//analogous of keypressed
        if(this.bomb!=null && bombTime==0)bombTime=millis();

//        System.out.println(millis()+" bt "+this.bombTime);
        if(this.bomb!=null){
            int passedTime = millis() -  bombTime;
            if (passedTime > detonation_time) {
                ///firing in progress
                if(!this.bomb.getFire()){
                    System.out.println(" triggering bomb");
                    this.bomb.setFire(true);///using ths pattern evn though one time no param woudl still have done
                    if(this.bomb!=null && fireTime==0)this.fireTime= millis();
                }else{///if already fired  check decomposition time
                    int elapsed = millis() -  fireTime;
                    if (elapsed > decomposition_time) {
                        this.bomb=null;
                        bombTime=0;
                        fireTime=0;
                    }
                }
                return true;//firing done or in progress
            }else return false;
        }else return false;
    }




    //interations
    @SuppressWarnings("ConstantConditions")
    public void  keyPressed() {
        if (key == CODED) {
            if(keyCode== UP){
                this.player.setMoving(true);
                this.player.setDirection(Direction.UP);
            }else if(keyCode== LEFT){
                this.player.setMoving(true);
                this.player.setDirection(Direction.LEFT);
            }else if(keyCode== DOWN){
                this.player.setMoving(true);
                this.player.setDirection(Direction.DOWN);
            }else if(keyCode== RIGHT){
                this.player.setMoving(true);
                this.player.setDirection(Direction.RIGHT);
            }///else nothing

        } else if (key == 32 || key == ' ') { ////space
            if(this.bomb==null) this.player.placeBomb(true); else System.out.println(" there is still bomb existent ");
        } else if (keyCode == ENTER || keyCode== RETURN) { //space
            if(restartable)this.restartGame();else println("cannot restart game at this state");
        }else println("use w and s to move ship, and space to shoot");

        this.levels[currentlevel].drawLevel();
    }

    private void restartGame() {
        this.init();
    }


    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }

    public enum GameState {OUT_OF_TIME, OUT_OF_LIFE, RUNNING, GOAL_REACHED}
}
