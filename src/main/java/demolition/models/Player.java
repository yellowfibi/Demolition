package demolition.models;

import demolition.util.Direction;

public class Player {

    Direction direction;
    boolean moving;
    private boolean ishit;
    private boolean place;

    public Player() {
        moving=false;
        ishit=false;
        this.direction= Direction.DOWN;///DOWN is the default as specified
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Bomb placeBomb( int gridx, int gridy){
        return new Bomb(gridx,gridy);

    }


    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public String toString() {
        return "Player{" +
                "direction=" + direction +
                ", moving=" + moving +
                '}';
    }

    public void isHit(boolean b) {
        this.ishit=b;
    }

    public boolean isHit() {
        return this.ishit;
    }

    public void placeBomb(boolean b) {
        this.place=b;
    }
    public boolean placedBomb(){
        return this.place;
    }
}
