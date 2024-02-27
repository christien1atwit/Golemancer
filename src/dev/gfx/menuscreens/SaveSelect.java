package dev.gfx.menuscreens;

import dev.Game;
import dev.course.maps.LoadableMap;
import dev.entities.Controller;
import dev.gfx.Assets;
import dev.states.GameState;
import dev.states.MenuState;
import dev.states.State;

import java.awt.*;
import java.io.File;

public class SaveSelect extends Menu{
    private String[] fileNames;
    public SaveSelect(Game game) {
        super(game);
        bgGraphic= Assets.menuBG;
        File f = new File("res/saves");
        fileNames=f.list();
        MAXoptions=fileNames.length-1;
    }

    @Override
    protected void drawText(Graphics g) {
        for(int i=0;i<fileNames.length;i++){
            if(i==selectedOption){
                g.setColor(Color.red);
            }else{
                g.setColor(Color.white);
            }
            g.drawString(fileNames[i],20,50+((i+1)*15));
        }
    }

    @Override
    protected void menuDecline() {
        MenuState.currentMenu=new TitleMenu(game);
    }

    @Override
    protected void menuRun(int option) {
        GameState.currentRoom= new LoadableMap(Game.camera, fileNames[option], true);
        GameState.player= new Controller(game);
        State.setState(game.gameState);
    }
}
