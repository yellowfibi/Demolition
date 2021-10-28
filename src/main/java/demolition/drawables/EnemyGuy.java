package demolition.drawables;

import demolition.*;
import demolition.util.Direction;
import processing.core.PImage;

public class EnemyGuy extends Living implements Drawable,Bombable {
    private final App app;
    private final EnemyType type;

    @Override
    public void getBombed() {
        
        if( this.type==EnemyType.RED_ENEMY){
            this.app.red_enemy=null;
        }else {
            assert type==EnemyType.YELLOW_ENEMY;
            this.app.yellow_enemy=null;
        }
    }

    

    public enum EnemyType {RED_ENEMY, YELLOW_ENEMY}
    public EnemyGuy(App app, int gridx, int gridy, EnemyType type, Level level) {
        super(gridx,gridy,level);
        this.app = app;
        this.type = type;
    }

    private static PImage getImg(Direction state, EnemyType playertype, App app){
        
        switch (state) {
            case UP:
                return playertype== EnemyType.RED_ENEMY ?app.red_enemy_up:app.yellow_enemy_up;
            case RIGHT:
                return playertype== EnemyType.RED_ENEMY ?app.red_enemy_right:app.yellow_enemy_right;
            case LEFT:
                return playertype== EnemyType.RED_ENEMY ?app.red_enemy_left:app.yellow_enemy_left;
            case DOWN:
            default:
                return playertype== EnemyType.RED_ENEMY ?app.red_enemy_down:app.yellow_enemy_down;

        }
    }
    @Override
    public void draw() {
        
        
        if(this.app.red_enemy!=null)
            if(this.app.red_enemy.isMoving() && this.type==EnemyType.RED_ENEMY){
                
                boolean move = this.move(this.app.red_enemy.getDirection());
                while(!move){
                    this.app.red_enemy.resolveCollusion(this.type);
                    move = this.move(this.app.red_enemy.getDirection());
                }

                
                

                
                this.app.red_enemy.setMoving(false);



                if(!this.level.checkEnemy(this) ){
                    
                    this.app.player.isHit(true);
                }

            }
        if(this.app.yellow_enemy!=null)
            if(this.app.yellow_enemy.isMoving() && this.type==EnemyType.YELLOW_ENEMY){
                
                boolean move = this.move(this.app.yellow_enemy.getDirection());
                while(!move){
                    this.app.yellow_enemy.resolveCollusion(this.type);
                    move = this.move(this.app.yellow_enemy.getDirection());
                }

                
                

                
                this.app.yellow_enemy.setMoving(false);



                if(!this.level.checkEnemy(this) ){
                    
                    this.app.player.isHit(true);
                }
            }





        app.image(getImg((this.type==EnemyType.RED_ENEMY? this.app.red_enemy:this.app.yellow_enemy).getDirection(),this.type,app), gridx*tilew, App.top_offset +gridy*tileh, tileh, tilew);

    }


    public EnemyType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "EnemyGuy{" +
                "app=" + app +
                ", gridx=" + gridx +
                ", gridy=" + gridy +
                ", type=" + type +
                ", enemy =" + (this.type==EnemyType.RED_ENEMY? this.app.red_enemy:this.app.yellow_enemy) +
                '}';
    }
}
