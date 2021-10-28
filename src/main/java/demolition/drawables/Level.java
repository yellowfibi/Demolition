package demolition.drawables;

import com.google.common.base.MoreObjects;
import demolition.App;
import demolition.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level  {
    String path;
    int time;
    private String map;
    private final App app;
    private List<Drawable> drawables;

    public Level(String path, int time, App app) {
        this.path = path;
        this.time = time;
        this.app = app;
        this.drawables=new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    public int getTime() {
        return time;
    }

    public void drawLevel(){
        for(Drawable item:drawables){
            item.draw();///nice adv of polymorph now
        }
    }
    public void initLevel(){///parse and draw
        Scanner sc;
        sc = new Scanner(this.map);
        int countery=0;
        while (sc.hasNextLine()){

            //System.out.println(sc.nextLine());
            String s = sc.nextLine();
            char[] chars = s.toCharArray();
            Drawable item;
            for (int i = 0; i < chars.length; i++) {
                char xter = chars[i];
                //noinspection EnhancedSwitchMigration
                switch (xter) {
                    ////level
                    case 'W':
                        item = new Wall(this.app, i, countery, Wall.WallType.SOLID);
                        break;
                    case 'B':
                        item = new Wall(this.app, i, countery, Wall.WallType.BROKEN);
                        break;
                    case ' ':
                        //noinspection DuplicateBranchesInSwitch
                        item = new Ground(this.app, i, countery, Ground.GroundType.PATH);
                        break;
                    case 'G':
                        item = new Ground(this.app, i, countery, Ground.GroundType.GOAL);
                        break;
                    ////bombguy
                    case 'P':
                        item = new BombGuy(this.app, i, countery );
                        break;
                    case 'R':
                        item = new Enemy(this.app, i, countery, Enemy.EnemyType.RED_ENEMY);
                        break;
                    case 'Y':
                        item = new Enemy(this.app, i, countery, Enemy.EnemyType.YELLOW_ENEMY);
                        break;



                    default:
                        //the default is the empty tile
                        item = new Ground(this.app, i, countery, Ground.GroundType.PATH);
                }
                this.drawables.add(item);
            }
            countery++;
        }


    }





    public void setMap(String levelmap) {
        this.map=levelmap;
    }













}