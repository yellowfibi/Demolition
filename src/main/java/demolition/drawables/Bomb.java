package demolition.drawables;

import demolition.App;
import demolition.Drawable;
import processing.core.PImage;

public class Bomb implements Drawable {
    private final App app;
    private final int gridx;
    private final int gridy;
    ///bombguy state
    private  BombState bombstate;

    public enum BombState{COUNTING, FIRING}
    public Bomb(App app, int gridx, int gridy ) {
        this.app = app;
        this.gridx = gridx;
        this.gridy = gridy;
        this.bombstate = BombState.COUNTING;
    }

    private static PImage getImg(BombState state, App app){
        //noinspection EnhancedSwitchMigration
        switch (state) {
            case COUNTING:
            default:
                return app.bomb_counting;
            case FIRING:
                return app.bomb_explosion;

        }
    }
    @Override
    public void draw() {
        app.image(getImg(this.bombstate,app), gridx*tilew, App.top_offset +gridy*tileh, tileh, tilew);

    }
}
