package demolition;

import demolition.drawables.Level;
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


    public static final int FPS = 60;

    public PImage wall_solid;
    public PImage wall_broken;
    public PImage ground_path;
    public PImage ground_goal;


    Level[] levels;
    private int currentlevel;



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
//            wall_solid = loadImage(new URI(Objects.requireNonNull(c.getResource("/wall/solid.png")).toString()).getPath() );
//            wall_broken = loadImage(new URI(Objects.requireNonNull(c.getResource("/broken/broken.png")).toString()).getPath() );
//            wall_solid = loadImage(new URI(Objects.requireNonNull(c.getResource("/wall/solid.png")).toString()).getPath() );
//            wall_broken = loadImage(new URI(Objects.requireNonNull(c.getResource("/broken/broken.png")).toString()).getPath() );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ///initialize game state
        this.currentlevel=0;



    }

    public void draw() {
        background(0, 0, 0);
        //draw bg

        //draw levels
        this.levels[currentlevel].drawLevel();



    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
