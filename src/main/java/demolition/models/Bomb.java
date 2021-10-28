package demolition.models;

public class Bomb {
    public final int gridx;
    public final int gridy;
    private boolean fired;

    public Bomb(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }


    public void setFire(boolean fire) {
        this.fired=fire;
    }

    public boolean getFire() {
        return this.fired;
    }

}
