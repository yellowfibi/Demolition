package demolition.drawables;

import demolition.*;
import demolition.util.Direction;
import processing.core.PImage;

import java.util.List;

public class BombThing implements Drawable , Collideable {
     


    private final App app;
    private final int centery;
    private final int centerx;
    private int gridx;
    private int gridy;
    private Level level;
    
    private  BombState bombstate;

    public enum BombState{COUNTING, FIRING}
    public BombThing(App app, demolition.Level level2) {
        this.app = app;
        this.gridx=app.bomb.gridx;
        this.gridy=app.bomb.gridy;
        this.centerx=this.gridx;
        this.centery=this.gridy;
        this.level = level2;
        this.bombstate = BombState.COUNTING;
    }

    private static PImage getImg(Direction state, App app){
        
        switch (state) {
            case RIGHT:
            case LEFT:
                return app.bomb_explosion_hori;
            case UP:
            case DOWN:
            default:
                return app.bomb_explosion_vert;
        }
    }
    @Override
    public void draw() {
        
        if(this.app.bomb.getFire()){
            this.bombstate=BombState.FIRING;
            
            app.image(this.app.bomb_explosion, centerx*tilew, App.top_offset +centery*tileh, tileh, tilew);

            
            int length = Direction.class.getEnumConstants().length;
            for (int i = 0; i < length; i++) {

                Direction direction = Direction.class.getEnumConstants()[i];
                this.gridx=centerx;
                this.gridy=centery;
                boolean bombedwall=false;
                int counter=0;
                while(this.spreadBomb(direction) && counter<2 && !bombedwall){
                    app.image(getImg(direction,app), gridx*tilew, App.top_offset +gridy*tileh, tileh, tilew);
                    List<Bombable> bombables = this.level.checkBombable(this);
                    if(!bombables.isEmpty()){
                        for(Bombable bombable: bombables){
                            bombable.getBombed();
                            if(bombable instanceof  Wall) bombedwall=true;
                        }
                    }
                    counter++;
                }
            }

        }else{
            app.image(this.app.bomb_counting, centerx*tilew, App.top_offset +centery*tileh, tileh, tilew);
        }

    }



    protected boolean spreadBomb(Direction state){
        
        switch (state) {
            case UP:
                if(this.gridy>0){
                    this.gridy--;
                    if(!this.level.bombAffects(this)){
                        this.gridy++;

                        return false;
                    }
                }else System.out.println("upper screen limit reached");
                break;
            case RIGHT:
                if(this.gridx<App.gridw){
                    this.gridx++;
                    if(!this.level.bombAffects(this)){
                        this.gridx--;

                        return false;
                    }
                }else System.out.println("right screen limit reached");
                break;
            case LEFT:
                if(this.gridx>0){
                    this.gridx--;
                    if(!this.level.bombAffects(this)){
                        this.gridx++;

                        return false;
                    }
                }else System.out.println("left screen limit reached");
                break;
            case DOWN:
            default:
                if(this.gridy<App.gridh){
                    this.gridy++;
                    if(!this.level.bombAffects(this)){
                        this.gridy--;

                        return false;
                    }
                }else System.out.println("lower screen limit reached");
                break;
        }


        return true;
    }



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
        return "BombThing{" +
                "app=" + app +
                ", centery=" + centery +
                ", centerx=" + centerx +
                ", gridx=" + gridx +
                ", gridy=" + gridy +
                ", level=" + level +
                ", bombstate=" + bombstate +
                '}';
    }
}
