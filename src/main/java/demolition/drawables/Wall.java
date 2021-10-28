package demolition.drawables;

import demolition.*;

public class Wall implements Drawable , Collideable, Bombable {
    private final App app;
    int gridx;
    int gridy;
    WallType type;
    private Level level;

    @Override
    public void getBombed() {
        if ( this.type==WallType.BROKEN){
            this.level.breakWall(this);///special occasion to update drive direct without model's backing
            ////ahaa so this hapens too so dont limit my frmamework ahaa seen it now go tit
        }else System.out.println(" unbreakable ");

    }


    public enum WallType{ SOLID, BROKEN}

    public Wall(App app, int gridx, int gridy, WallType type, Level level) {
        this.app = app;
        this.gridx = gridx;
        this.gridy = gridy;
        this.type = type;
//        System.out.println(" creating  "+this);

        this.level = level;
    }

    @Override
    public void draw(){
        app.image((this.type==WallType.SOLID?app.wall_solid :app.wall_broken),  gridx*tilew, App.top_offset +gridy*tileh, tileh,tilew);

    }



    @Override
    public int getGridy() {
        return this.gridy;
    }

    @Override
    public int getGridx() {
        return this.gridx;
    }

    public WallType getType() {
        return this.type;
    }


    @Override
    public String toString() {
        return "Wall{" +
                "app=" + app +
                ", gridx=" + gridx +
                ", gridy=" + gridy +
                ", type=" + type +
                '}';
    }
}
