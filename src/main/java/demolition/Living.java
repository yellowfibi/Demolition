package demolition;

import demolition.util.Direction;

public class Living implements Collideable{


    protected int gridx;
    protected int gridy;
    protected Level level;

    public Living(int gridx, int gridy, Level level) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.level = level;
    }


    @Override
    public int getGridy() {
        return this.gridy;
    }

    @Override
    public int getGridx() {
        return this.gridx;
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    protected boolean move(Direction state){
        
        
        



        switch (state) {
            case UP:
                if(this.gridy>0){
                    this.gridy--;
                    if(!this.level.moveValid(this)){
                        this.gridy++;

                        return false;
                    }
                }else System.out.println("upper screen limit reached");
                break;
            case RIGHT:
                if(this.gridx<App.gridw){
                    this.gridx++;
                    if(!this.level.moveValid(this)){
                        this.gridx--;

                        return false;
                    }
                }else System.out.println("right screen limit reached");
                break;
            case LEFT:
                if(this.gridx>0){
                    this.gridx--;
                    if(!this.level.moveValid(this)){
                        this.gridx++;

                        return false;
                    }
                }else System.out.println("left screen limit reached");
                break;
            case DOWN:
            default:
                if(this.gridy<App.gridh){
                    this.gridy++;
                    if(!this.level.moveValid(this)){
                        this.gridy--;

                        return false;
                    }
                }else System.out.println("lower screen limit reached");
                break;
        }


        return true;
    }
}
