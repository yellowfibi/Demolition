package demolition.drawables;

import demolition.App;
import demolition.Drawable;
import processing.core.PImage;

public class BombGuy implements Drawable {
    private final App app;
    private int gridx;
    private int gridy;

    ///we dont maintain state from here bt in model in player


    public enum Direction{UP, RIGHT,DOWN, LEFT}
    public BombGuy(App app, int gridx, int gridy ) {
        this.app = app;
        this.gridx = gridx;
        this.gridy = gridy;
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

    private void move(Direction state){
        System.out.println("before "+state);
        System.out.println(this);
        //noinspection EnhancedSwitchMigration
        switch (state) {
            case UP:
                this.gridy--;
                break;
            case RIGHT:
                this.gridx++;
                break;
            case LEFT:
                this.gridx--;
                break;
            case DOWN:
            default:
                this.gridy++;
        }
        System.out.println("after "+state);
        System.out.println(this);
    }
    @Override
    public void draw() {
        if(this.app.player.isMoving()){
            //move
            this.move(this.app.player.getDirection());
            //amimate
                ///to which on callback ie animate for 2 secs before alowign nxt movt or sth  or no need to disallow

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
