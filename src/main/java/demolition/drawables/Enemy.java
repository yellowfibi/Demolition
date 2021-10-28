package demolition.drawables;

import demolition.App;
import demolition.Drawable;
import processing.core.PImage;

public class Enemy implements Drawable {
    private final App app;
    private final int gridx;
    private final int gridy;
    private final EnemyType type;
    ///bombguy state
    private  Direction direction;

    public enum EnemyType {RED_ENEMY, YELLOW_ENEMY}
    public enum Direction{UP, RIGHT,DOWN, LEFT}
    public Enemy(App app, int gridx, int gridy, EnemyType type) {
        this.app = app;
        this.gridx = gridx;
        this.gridy = gridy;
        this.type = type;
        this.direction= Direction.DOWN;///DOWN is the default as specified
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
        app.image(getImg(this.direction,this.type,app), gridx*tilew, App.top_offset +gridy*tileh, tileh, tilew);

    }



}
