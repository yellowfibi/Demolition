package demolition.drawables;

import demolition.App;
import demolition.Collideable;
import demolition.Drawable;
import processing.core.PImage;

public class Bomb implements Drawable , Collideable {
    ///*important, a bomb is collidable but for different reasons as rest
    ////here it will not prohibit movement however  it will cause death if bombguy or enemy(Livings) wch die.
        //ase whn is bomb guy then it wl update layer as dead
    // within range at detonation


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


    @Override
    public int getGridy() {
        return this.gridy;
    }

    @Override
    public int getGridx() {
        return this.gridx;
    }
}
