package demolition.drawables;

import demolition.*;
import demolition.util.Direction;
import processing.core.PImage;

import java.util.List;

public class BombThing implements Drawable , Collideable {
    ///*important, a bomb is collidable but for different reasons as rest
    ////here it will not prohibit movement however  it will cause death if bombguy or enemy(Livings) wch die.
        //ase whn is bomb guy then it wl update layer as dead
    // within range at detonation


    private final App app;
    private final int centery;
    private final int centerx;
    private int gridx;
    private int gridy;
    private Level level;
    ///bombguy state
    private  BombState bombstate;

    public enum BombState{COUNTING, FIRING}
    public BombThing(App app, Level level) {
        this.app = app;
        this.gridx=app.bomb.gridx;
        this.gridy=app.bomb.gridy;
        this.centerx=this.gridx;
        this.centery=this.gridy;
        this.level = level;
        this.bombstate = BombState.COUNTING;
    }

    private static PImage getImg(Direction state, App app){
        //noinspection EnhancedSwitchMigration
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
        ////todo also realized should only set one bomb at aa time
        if(this.app.bomb.getFire()){
            this.bombstate=BombState.FIRING;
            ///draw the center
            app.image(this.app.bomb_explosion, centerx*tilew, App.top_offset +centery*tileh, tileh, tilew);

            ///do the movements in each direction while checking collusion
            int length = Direction.class.getEnumConstants().length;
            for (int i = 0; i < length; i++) {

                Direction direction = Direction.class.getEnumConstants()[i];
                this.gridx=centerx;
                this.gridy=centery;
                boolean bombedwall=false;
                int counter=0;
                while(this.spreadBomb(direction) && counter<2 && !bombedwall){///until we colide
                    app.image(getImg(direction,app), gridx*tilew, App.top_offset +gridy*tileh, tileh, tilew);
                    List<Bombable> bombables = this.level.checkBombable(this);
                    if(!bombables.isEmpty()){
                        for(Bombable bombable: bombables){
                            bombable.getBombed();
                            if(bombable instanceof  Wall) bombedwall=true;
                        }
                    }
                    counter++;
                }////and added constrains to 2 spaces and also stop when bombedwall a thing
            }

        }else{
            app.image(this.app.bomb_counting, centerx*tilew, App.top_offset +centery*tileh, tileh, tilew);
        }

    }



    protected boolean spreadBomb(Direction state){
        ///similar to move movement bt dont confuse here we wl check for colusion excempting brocken walls
        switch (state) {
            case UP:
                if(this.gridy>0){//+1
                    this.gridy--;
                    if(!this.level.bombAffects(this)){
                        this.gridy++;///revert
//                        System.out.println(" move not valid, wall ahead ");
                        return false;
                    }
                }else System.out.println("upper screen limit reached");
                break;
            case RIGHT:
                if(this.gridx<App.gridw){//-1
                    this.gridx++;
                    if(!this.level.bombAffects(this)){
                        this.gridx--;///revert
//                        System.out.println(" move not valid, wall ahead ");
                        return false;
                    }
                }else System.out.println("right screen limit reached");
                break;
            case LEFT:
                if(this.gridx>0){//+1
                    this.gridx--;
                    if(!this.level.bombAffects(this)){
                        this.gridx++;///revert
//                        System.out.println(" move not valid, wall ahead ");
                        return false;
                    }
                }else System.out.println("left screen limit reached");
                break;
            case DOWN:
            default:
                if(this.gridy<App.gridh){//-1
                    this.gridy++;
                    if(!this.level.bombAffects(this)){
                        this.gridy--;///revert
//                        System.out.println(" move not valid, wall ahead ");
                        return false;
                    }
                }else System.out.println("lower screen limit reached");
                break;
        }
//        System.out.println("after "+state);
//        System.out.println(this);
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
