package demolition.drawables;

import demolition.App;
import demolition.Drawable;

public class Wall implements Drawable {
    private final App app;
    int gridx;
    int gridy;
    WallType type;


    public enum WallType{ SOLID, BROKEN}

    public Wall(App app, int gridx, int gridy, WallType type) {
        this.app = app;
        this.gridx = gridx;
        this.gridy = gridy;
        this.type = type;
//        System.out.println(" creating  "+this);

    }

    @Override
    public void draw(){
        app.image((this.type==WallType.SOLID?app.wall_solid :app.wall_broken),  gridx*tilew, App.top_offset +gridy*tileh, tileh,tilew);

    }

}
