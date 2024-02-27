package dev.gfx.menuscreens;

import dev.Game;
import dev.gfx.Assets;
import dev.states.MenuState;

import java.awt.*;

public class HowToPlayMenu extends Menu{


    public HowToPlayMenu(Game game) {
        super(game);
        bgGraphic= Assets.howToPlayBg;
        MAXoptions=0;
        menuName="HowToPlay";

    }

    @Override
    protected void drawText(Graphics g) {

    }

    @Override
    protected void menuDecline() {
        MenuState.currentMenu=new TitleMenu(game);
    }

    @Override
    protected void menuRun(int option) {

    }
}
