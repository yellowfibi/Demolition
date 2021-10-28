package demolition;

import demolition.drawables.*;

import java.util.ArrayList;
import java.util.Comparator;
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
                    ////livings
                    case 'P':
                        item = new BombGuy(this.app, i, countery, this );
                        break;
                    case 'R':
                        item = new EnemyGuy(this.app, i, countery, EnemyGuy.EnemyType.RED_ENEMY, this);
                        break;
                    case 'Y':
                        item = new EnemyGuy(this.app, i, countery, EnemyGuy.EnemyType.YELLOW_ENEMY, this);
                        break;
                    //the default is the empty tile
                    default:
                        item = new Ground(this.app, i, countery, Ground.GroundType.PATH);
                }
                this.drawables.add(item);
            }
            countery++;
        }
        ///however this leaves the rendering in order of reading data while we may want to control the layering instead
        /////hence we need to sort the list after initialization
        this.drawables.sort(new Comparator<Drawable>() {
            private int classify(Drawable d){
                if (Bomb.class.equals(d.getClass())) {
                    return 3;
                } else if (BombGuy.class.equals(d.getClass())) {
                    return 4;
                } else if (EnemyGuy.class.equals(d.getClass())) {
                    return 2;
                } else if (Ground.class.equals(d.getClass())) {
                    return 1;
                } else if (Wall.class.equals(d.getClass())) {
                    return 0;
                } else throw new IllegalStateException();
            }
            @Override
            public int compare(Drawable o1, Drawable o2) {
                int z1=classify(o1);
                int z2=classify(o2);
                return z1-z2;
            }
        });


    }





    public void setMap(String levelmap) {
        this.map=levelmap;
    }


    public boolean moveValid(Collideable mover) {
        ///only cares about walls though for now nd coliding with livings as th only movers
        ////we still use the polymorph of colidable though for reusablity
        for(Drawable item:drawables){
            if(item instanceof  Wall) {
                //System.out.println("checking colusion "+item);
                if(((Collideable) item).detectCollusion(mover)) {
                    System.out.println(">>colliding with "+item);
                    return false;
                }///nice adv of polymorph now
            }
        }
        return true;
    }
}










