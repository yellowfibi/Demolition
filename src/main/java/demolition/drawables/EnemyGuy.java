package demolition.drawables;

import demolition.*;
import processing.core.PImage;

public class EnemyGuy extends Living implements Drawable {
    private final App app;
    private final EnemyType type;
    ///bombguy state

    public enum EnemyType {RED_ENEMY, YELLOW_ENEMY}
    public EnemyGuy(App app, int gridx, int gridy, EnemyType type, Level level) {
        super(gridx,gridy,level);
        this.app = app;
        this.type = type;
    }

    private static PImage getImg(Direction state, EnemyType playertype, App app){
        //noinspection EnhancedSwitchMigration
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
        ///all enemies move determinstic to initial position and not their individual states
        ///hence we cn jst contorl them by their type and not actual instances
        if(this.app.red_enemy.isMoving() && this.type==EnemyType.RED_ENEMY){
            //move
            this.move(this.app.red_enemy.getDirection());
            //animate
            ///to which on callback ie animate for 2 secs before allowing nxt movt or sth  or no need to disallow

            //stop
            this.app.red_enemy.setMoving(false);///movt signal recieved from evnt hndle

        }///else nothing
        if(this.app.yellow_enemy.isMoving() && this.type==EnemyType.YELLOW_ENEMY){
            //move
            this.move(this.app.yellow_enemy.getDirection());
            //animate
            ///to which on callback ie animate for 2 secs before allowing nxt movt or sth  or no need to disallow

            //stop
            this.app.yellow_enemy.setMoving(false);///movt signal recieved from evnt hndle

        }///else nothing
        app.image(getImg((this.type==EnemyType.RED_ENEMY? this.app.red_enemy:this.app.yellow_enemy).getDirection(),this.type,app), gridx*tilew, App.top_offset +gridy*tileh, tileh, tilew);

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
