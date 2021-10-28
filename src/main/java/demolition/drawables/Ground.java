package demolition.drawables;

import demolition.App;
import demolition.Collideable;
import demolition.Drawable;
import demolition.Level;

public class Ground implements Drawable, Collideable {

    private final App app;
    private final int gridx;
    private final int gridy;
    private final GroundType type;
    private Level level;

    public enum GroundType{ PATH, GOAL}

    public Ground(App app, int gridx, int gridy, GroundType type, Level level) {

        this.app = app;
        this.gridx = gridx;
        this.gridy = gridy;
        this.type = type;
//        System.out.println(" creating  "+this);

        this.level = level;
    }
    @Override
    public void draw() {
        app.image((this.type== GroundType.PATH?app.ground_path :app.ground_goal), gridx*tilew, App.top_offset +gridy*tileh, tileh,tilew);

        if(this.type==GroundType.GOAL && !this.level.checkPlayer(this) )
            this.app.finish(App.GameState.GOAL_REACHED);///donno what pattern is this now bt continue

    }


    ////TODO COLIDABLE IS NEEDED FOR THE GOAL STATE

    @Override
    public int getGridy() {
        return this.gridy;
    }

    @Override
    public int getGridx() {
        return this.gridx;
    }

    @Override
    public String toString() {
        return "Ground{" +
                "app=" + app +
                ", gridx=" + gridx +
                ", gridy=" + gridy +
                ", type=" + type +
                ", level=" + level +
                '}';
    }
}
