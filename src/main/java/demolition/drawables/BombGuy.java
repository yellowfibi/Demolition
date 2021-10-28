package demolition.drawables;

import demolition.*;
import processing.core.PImage;

public class BombGuy extends Living implements Drawable {
    private final App app;

    ///we dont maintain state from here bt in model in player


    public BombGuy(App app, int gridx, int gridy, Level level ) {
        super(gridx, gridy, level);
        this.app = app;
        ///player state is in motion only but positional state is maintained from within
    }

    private static PImage getImg(Direction state, App app){
        //noinspection EnhancedSwitchMigration
        switch (state) {
            case UP:
                return app.player_up;
            case RIGHT:
                return app.player_right;
            case LEFT:
                return app.player_left;
            case DOWN:
            default:
                return app.player_down;

        }
    }

    @Override
    public void draw() {
        if(this.app.player.isMoving()){
            //move
            this.move(this.app.player.getDirection());
            //animate
                ///to which on callback ie animate for 2 secs before allowing nxt movt or sth  or no need to disallow

            //stop
            this.app.player.setMoving(false);///movt signal recieved from evnt hndle

        }///else nothing
        app.image(getImg(this.app.player.getDirection(),app), gridx*tilew, App.top_offset +gridy*tileh, tileh, tilew);

    }




    @Override
    public String toString() {
        return "BombGuy{" +
                "app=" + app +
                ", gridx=" + gridx +
                ", gridy=" + gridy +
                '}';
    }
}
