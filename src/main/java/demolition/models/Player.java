package demolition.models;

import demolition.drawables.BombGuy;

public class Player {

    BombGuy.Direction direction;
    boolean moving;

    public Player() {
        moving=false;
        this.direction= BombGuy.Direction.DOWN;///DOWN is the default as specified
    }

    public BombGuy.Direction getDirection() {
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
}
