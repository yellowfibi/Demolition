package demolition.drawables;

import demolition.*;
import demolition.util.Direction;
import processing.core.PImage;

public class BombGuy extends Living implements Drawable ,Bombable{
    private final App app;

    


    public BombGuy(App app, int gridx, int gridy, Level level ) {
        super(gridx, gridy, level);
        this.app = app;
        
    }

    private static PImage getImg(Direction state, App app){
        
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
            
            this.move(this.app.player.getDirection());
            
                


            
            if(!this.level.checkEnemy(this) ){
                
                this.app.player.isHit(true);
            }


            


            
            this.app.player.setMoving(false);

        }
        if(this.app.player.placedBomb()){
            this.app.bomb=this.app.player.placeBomb(this.gridx, this.gridy);
            this.app.player.placeBomb(false);
        }
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

    @Override
    public void getBombed() {
        this.app.player.isHit(true);
    }
}
