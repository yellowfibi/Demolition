package demolition;

import com.google.common.base.MoreObjects;
import demolition.drawables.*;
import demolition.models.Bomb;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Level  {
    String path;
    private int time;
    private String map;
    private final App app;
    private List<Drawable> drawables;
    private List<Drawable> toremove;

    public Level(String path, int time, App app) {
        this.path = path;
        this.time = time;
        this.app = app;
        this.drawables=new ArrayList<>();
        this.toremove=new ArrayList<>();
    }

    public String getPath() {
        return path;
    }

    public int getTime() {
        return time;
    }

    private void updateLevel(){
        

        if(this.app.bomb!=null){
            this.drawables.add(new BombThing( app , this));
        }else{
            this.drawables.removeIf(new Predicate<Drawable>() {
                @Override
                public boolean test(Drawable drawable) {
                    return drawable instanceof BombThing;
                }
            });
        }

        if (this.app.red_enemy == null) {
            this.drawables.removeIf(new Predicate<Drawable>() {
                @Override
                public boolean test(Drawable drawable) {
                    return drawable instanceof EnemyGuy && ((EnemyGuy)drawable).getType()== EnemyGuy.EnemyType.RED_ENEMY;
                }
            });
        }

        if (this.app.yellow_enemy == null) {
            this.drawables.removeIf(new Predicate<Drawable>() {
                @Override
                public boolean test(Drawable drawable) {
                    return drawable instanceof EnemyGuy && ((EnemyGuy)drawable).getType()== EnemyGuy.EnemyType.YELLOW_ENEMY;
                }
            });
        }
        this.drawables.sort(new DrawableComparator());
    }

    public void drawLevel(){
        updateLevel();
        for(Drawable item:drawables){
            item.draw();
        }
        postupdateLevel();
        
    }

    private void postupdateLevel() {
        this.drawables.removeAll(this.toremove);
        
        
        
        toremove.clear();
    }

    public void initLevel(){
        this.drawables=new ArrayList<>();
        Scanner sc;
        sc = new Scanner(this.map);
        int countery=0;
        while (sc.hasNextLine()){

            
            String s = sc.nextLine();
            char[] chars = s.toCharArray();
            Drawable item;
            for (int i = 0; i < chars.length; i++) {
                
                this.drawables.add(new Ground(this.app, i, countery, Ground.GroundType.PATH, this));
                
                char xter = chars[i];
                
                switch (xter) {
                    
                    case 'W':
                        item = new Wall(this.app, i, countery, Wall.WallType.SOLID, this);
                        break;
                    case 'B':
                        item = new Wall(this.app, i, countery, Wall.WallType.BROKEN, this);
                        break;
                    case ' ':
                        
                        item = new Ground(this.app, i, countery, Ground.GroundType.PATH, this);
                        break;
                    case 'G':
                        item = new Ground(this.app, i, countery, Ground.GroundType.GOAL, this);
                        break;
                    
                    case 'P':
                        item = new BombGuy(this.app, i, countery, this );
                        break;
                    case 'R':
                        item = new EnemyGuy(this.app, i, countery, EnemyGuy.EnemyType.RED_ENEMY, this);
                        break;
                    case 'Y':
                        item = new EnemyGuy(this.app, i, countery, EnemyGuy.EnemyType.YELLOW_ENEMY, this);
                        break;
                    
                    default:
                        item = new Ground(this.app, i, countery, Ground.GroundType.PATH, this);
                }
                this.drawables.add(item);
                

            }
            countery++;
        }
        
        
        this.drawables.sort(new DrawableComparator());


    }





    public void setMap(String levelmap) {
        this.map=levelmap;
    }


    public boolean moveValid(Collideable mover) {
        
        
        for(Drawable item:drawables){
            if(item instanceof  Wall) {
                
                if(((Collideable) item).detectCollusion(mover)) {

                    return false;
                }
            }
        }
        return true;
    }

    public boolean bombAffects(Collideable mover) {
        
        
        for(Drawable item:drawables){
            if(item instanceof  Wall && ((Wall)item).getType()== Wall.WallType.SOLID) {
                
                if(((Collideable) item).detectCollusion(mover)) {

                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkEnemy(Living living) {
        if(living instanceof BombGuy){
            for(Drawable item:drawables){
                if(item instanceof  EnemyGuy) {
                    
                    if(((Collideable) item).detectCollusion(living)) {
                        System.out.println(">>colliding with "+item);
                        return false;
                    }
                }
            }
        }else if(living instanceof EnemyGuy){
            for(Drawable item:drawables){
                if(item instanceof  BombGuy) {
                    
                    if(((Collideable) item).detectCollusion(living)) {
                        System.out.println(">>colliding with "+item);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<Bombable> checkBombable(BombThing bomb) {
        List<Bombable> items=new ArrayList<>();
        for(Drawable item:drawables){
            if(
                    item instanceof  EnemyGuy ||
                    item instanceof  BombGuy ||
                    (item instanceof  Wall  ) 
            ) {
                
                if(((Collideable) item).detectCollusion(bomb)) {
                    System.out.println(">>colliding with "+item);
                    items.add((Bombable) item);
                }
            }
        }
        return items;
    }

    public void breakWall(Wall wall) {
        this.toremove.add(wall);
    }

    public boolean checkPlayer(Ground goal) {

        for(Drawable item:drawables){
            if(item instanceof  BombGuy) {
                
                if(((Collideable) item).detectCollusion(goal)) {
                    System.out.println(">>goal reached "+item);
                    return false;
                }
            }
        }
        return true;
    }

    private static class DrawableComparator implements Comparator<Drawable> {
        private int classify(Drawable d){
            if (BombThing.class.equals(d.getClass())) {
                return 3;
            } else if (BombGuy.class.equals(d.getClass())) {
                return 4;
            } else if (EnemyGuy.class.equals(d.getClass())) {
                return 2;
            } else if (Ground.class.equals(d.getClass())) {
                return 0;
            } else if (Wall.class.equals(d.getClass())) {
                return 1;
            } else throw new IllegalStateException();
        }

        @Override
        public int compare(Drawable o1, Drawable o2) {
            int z1=classify(o1);
            int z2=classify(o2);
            return z1-z2;
        }
    }
}










