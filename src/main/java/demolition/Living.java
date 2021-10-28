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
        ///move with screen limits and ofset by the wall around*?? nono we may have open ends
        ///this is for the simple reason just incase open ended or not guarded at ends with no walls
        ////we still should nto go out of screen in no what ground

//        System.out.println("before "+state);
//        System.out.println(this);
        switch (state) {
            case UP:
                if(this.gridy>0){//+1
                    this.gridy--;
                    if(!this.level.moveValid(this)){
                        this.gridy++;///revert
//                        System.out.println(" move not valid, wall ahead ");
                        return false;
                    }
                }else System.out.println("upper screen limit reached");
                break;
            case RIGHT:
                if(this.gridx<App.gridw){//-1
                    this.gridx++;
                    if(!this.level.moveValid(this)){
                        this.gridx--;///revert
//                        System.out.println(" move not valid, wall ahead ");
                        return false;
                    }
                }else System.out.println("right screen limit reached");
                break;
            case LEFT:
                if(this.gridx>0){//+1
                    this.gridx--;
                    if(!this.level.moveValid(this)){
                        this.gridx++;///revert
//                        System.out.println(" move not valid, wall ahead ");
                        return false;
                    }
                }else System.out.println("left screen limit reached");
                break;
            case DOWN:
            default:
                if(this.gridy<App.gridh){//-1
                    this.gridy++;
                    if(!this.level.moveValid(this)){
                        this.gridy--;///revert
//                        System.out.println(" move not valid, wall ahead ");
                        return false;
                    }
                }else System.out.println("lower screen limit reached");
                break;
        }
//        System.out.println("after "+state);
//        System.out.println(this);
        return true;
    }
}
