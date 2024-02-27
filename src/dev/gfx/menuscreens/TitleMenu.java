package dev.gfx.menuscreens;

import dev.Game;
import dev.gfx.Assets;
import dev.states.MenuState;

import java.awt.*;

public class TitleMenu extends Menu{

    public TitleMenu(Game game) {
        super(game);
        menuName="TitleMenu";
        MAXoptions=2;
        bgGraphic= Assets.titleBG;
    }

    @Override
    protected void drawText(Graphics g) {
            switch (selectedOption){
                case 0:
                    g.setColor(Color.red);
                    g.drawString(">New Game",100, 320);
                    g.setColor(Color.white);
                    g.drawString("Load Game", 100, 340);
                    g.drawString("How To Play", 100, 360);
                    break;
                case 1:
                    g.setColor(Color.white);
                    g.drawString("New Game",100, 320);
                    g.drawString("How To Play", 100, 360);
                    g.setColor(Color.red);
                    g.drawString(">Load Game", 100, 340);
                    break;
                case 2:
                    g.setColor(Color.white);
                    g.drawString("New Game",100, 320);
                    g.drawString("Load Game", 100, 340);
                    g.setColor(Color.red);
                    g.drawString(">How To Play", 100, 360);
            }
    }

    @Override
    protected void menuDecline() {

    }

    @Override
    protected void menuRun(int option) {
        switch(option){
            case 0:
                MenuState.currentMenu=new RoomSelect(game);
                break;
            case 1:
                MenuState.currentMenu=new SaveSelect(game);
                break;
            case 2:
                MenuState.currentMenu=new HowToPlayMenu(game);
                break;
            default:
                System.out.println("Menu Option Invalid????");
                break;
        }
    }
}
