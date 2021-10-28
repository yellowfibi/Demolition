package demolition.drawables;

import demolition.App;
import demolition.Drawable;

public class Ground implements Drawable {

    private final App app;
    private final int gridx;
    private final int gridy;
    private final GroundType type;

    public enum GroundType{ PATH, GOAL}

    public Ground(App app, int gridx, int gridy, GroundType type) {

        this.app = app;
        this.gridx = gridx;
        this.gridy = gridy;
        this.type = type;
//        System.out.println(" creating  "+this);

    }
    @Override
    public void draw() {
        app.image((this.type== GroundType.PATH?app.ground_path :app.ground_goal), gridx*tilew, App.top_offset +gridy*tileh, tileh,tilew);

    }

}
