package demolition.models;

import demolition.drawables.EnemyGuy;
import demolition.util.Direction;

import java.util.Random;

public class Enemy {

    Direction direction;
    boolean moving;

    ///enemy type is only needed from rendeirng not in state so no nee dto make it a typ isntead pas sin mthd whn needed

    public Enemy() {
        moving=false;
        this.direction= Direction.RIGHT;///DOWN is the default as specified
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
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

    public void resolveCollusion(EnemyGuy.EnemyType type) {
        //noinspection EnhancedSwitchMigration
        switch (type) {
            case RED_ENEMY:
                this.direction=this.resolveYellow();
                break;
            case YELLOW_ENEMY:
            default:
                this.direction=resolveRed();
        }
    }

    private Direction resolveRed() {
        return randomEnum(Direction.class);
    }

    private static final Random random = new Random();
    ///small utilility
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }


    private Direction resolveYellow() {
        //noinspection EnhancedSwitchMigration
        switch (this.direction) {
            case LEFT:
                return Direction.UP;
            case UP:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.DOWN;
            case DOWN:
            default:
                return Direction.LEFT;
        }
    }
}
