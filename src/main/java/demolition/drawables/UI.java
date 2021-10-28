package demolition.drawables;

import demolition.App;
import demolition.Drawable;
import processing.core.PConstants;

public class UI implements Drawable {

    private final App app;
    private final int gridx;
    private final int gridy;
    private UIWidget type;

    public enum UIWidget{LIFE, TIMER}


    public UI(App app, int gridx, int gridy, UIWidget type) {
        this.app = app;
        this.gridx = gridx;
        this.gridy = gridy;
        this.type = type;
    }

    @Override
    public void draw() {
        app.textFont(app.font);
        app.fill(0);
        app.textSize(25);
        app.textAlign(PConstants.LEFT);


        if(this.type==UIWidget.LIFE){
            app.image(app.icon_lives, gridx*tilew, (float) (App.top_offset*.33+gridy*tileh), tileh, tilew);
            app.text(app.lives, gridx*tilew+tilew, (float) (App.top_offset*.33+gridy*tileh+tileh));
        }else{
            app.image(app.icon_timer, gridx*tilew, (float) (App.top_offset*.33+gridy*tileh), tileh, tilew);
            app.text(app.timer, gridx*tilew+tilew, (float) (App.top_offset*.33+gridy*tileh+tileh));
        }


    }
}
