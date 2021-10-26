package demolition.drawables;

import demolition.App;
import demolition.Drawable;

import java.util.Scanner;

public class Level  {
    String path;
    int time;
    private String map;
    private final App app;

    public Level(String path, int time, App app) {
        this.path = path;
        this.time = time;
        this.app = app;
    }

    public String getPath() {
        return path;
    }

    public int getTime() {
        return time;
    }

    public void drawLevel(){///parse and draw
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
                    default:
                        //the default is the empty tile
                        item = new Ground(this.app, i, countery, Ground.GroundType.PATH);
                }
                item.draw();
            }

            countery++;

        }


    }





    public void setMap(String levelmap) {
        this.map=levelmap;
    }
}