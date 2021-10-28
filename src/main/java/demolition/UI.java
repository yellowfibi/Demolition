package demolition;

import demolition.drawables.Bomb;
import processing.core.PFont;

import static demolition.Drawable.tilew;

public class UI implements Drawable{

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
        PFont mono = app.createFont("PressStart2P-Regular.ttf", 28);
        app.textFont(mono);
        app.fill(0);

        if(this.type==UIWidget.LIFE){
            app.image(app.icon_lives, gridx*tilew, (float) (App.top_offset*.33+gridy*tileh), tileh, tilew);
            app.text(app.lives, gridx*tilew+tilew, (float) (App.top_offset*.33+gridy*tileh+tileh));
        }else{
            app.image(app.icon_timer, gridx*tilew, (float) (App.top_offset*.33+gridy*tileh), tileh, tilew);
            app.text(app.timer, gridx*tilew+tilew, (float) (App.top_offset*.33+gridy*tileh+tileh));
        }


    }
}
