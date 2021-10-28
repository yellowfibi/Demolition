package demolition;

import demolition.drawables.BombGuy;
import demolition.models.Enemy;
import demolition.models.Player;
import processing.core.PApplet;
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


    ////game state
    Level[] levels;
    private int currentlevel;
    public int lives;
    public int timer;
    public Player player;
    public Enemy red_enemy;
    public Enemy yellow_enemy;
    private int savedTime;
    int enemy_movt_interval = 1000;
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

            icon_lives= loadImage(new URI(Objects.requireNonNull(c.getResource("/icons/player.png")).toString()).getPath() );
            icon_timer = loadImage(new URI(Objects.requireNonNull(c.getResource("/icons/clock.png")).toString()).getPath() );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ///initialize game state
        this.player=new Player();
        this.yellow_enemy=new Enemy();
        this.red_enemy=new Enemy();
        this.currentlevel=0;

        ///initialize game
        this.levels[currentlevel].initLevel();


        savedTime= millis();//current time


    }

    public void draw() {
        //draw bg
        background(unhex("fddd11"));

        //draw ui
        UI life = new UI(this, 4, 0, UI.UIWidget.LIFE);
        life.draw();
        UI time = new UI(this, 8, 0, UI.UIWidget.TIMER);
        time.draw();

        ///perform movements
        autoMovements();

        //draw levels
        this.levels[currentlevel].drawLevel();




    }

    private void autoMovements(){//analogous of keypressed
        int passedTime = millis() - savedTime;
        if (passedTime > enemy_movt_interval) {
//            System.out.println("moving "+savedTime);
            this.red_enemy.setMoving(true);
            this.red_enemy.setDirection(red_enemy.getDirection());
//            this.yellow_enemy.setMoving(true);
//            this.yellow_enemy.setDirection(yellow_enemy.getDirection());
            savedTime = millis();
        }
    }


    //interations
    public void  keyPressed() {
        if (key == CODED) {
            if(keyCode== UP){
                this.player.setMoving(true);
                this.player.setDirection(BombGuy.Direction.UP);
            }else if(keyCode== LEFT){
                this.player.setMoving(true);
                this.player.setDirection(BombGuy.Direction.LEFT);
            }else if(keyCode== DOWN){
                this.player.setMoving(true);
                this.player.setDirection(BombGuy.Direction.DOWN);
            }else if(keyCode== RIGHT){
                this.player.setMoving(true);
                this.player.setDirection(BombGuy.Direction.RIGHT);
            }///else nothing

        } else if (key == 's') {

        } else if (key == 32 || key == ' ') { ////space


        } else if (keyCode == ENTER || keyCode== RETURN) { ////space
//            if(restartable)restartGame();else println("cannot restart game at this state");
        }else println("use w and s to move ship, and space to shoot");

        this.levels[currentlevel].drawLevel();


    }


    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
