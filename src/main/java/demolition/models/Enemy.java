package demolition.models;

import demolition.Living;
import demolition.drawables.BombGuy;
import demolition.drawables.EnemyGuy;

public class Enemy {

    Living.Direction direction;
    boolean moving;

    public Enemy() {
        moving=false;
        this.direction= Living.Direction.RIGHT;///DOWN is the default as specified
    }

    public Living.Direction getDirection() {
        return direction;
    }

    public void setDirection(BombGuy.Direction direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "direction=" + direction +
                ", moving=" + moving +
                '}';
    }
}
