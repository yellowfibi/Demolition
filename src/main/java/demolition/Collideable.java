package demolition;

public interface Collideable {

    int getGridy();
    int getGridx();

//    static boolean isInRange(int n, int low, int high) {
//        return low <= n && (n <=high); //to be completed
//    }///here is simple olusion check

    default boolean detectCollusion(Collideable item) {
         return item.getGridx()==this.getGridx() && item.getGridy()==this.getGridy();
    }
}
