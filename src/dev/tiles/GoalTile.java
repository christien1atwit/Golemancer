package dev.tiles;

import dev.Game;
import dev.entities.Golem;
import dev.gfx.Assets;
import dev.gfx.Camera;
import dev.gfx.menuscreens.RoomSelect;
import dev.states.GameState;
import dev.states.MenuState;
import dev.states.State;

import java.awt.*;

public class GoalTile extends Tile{
    //NEED TO PUT INTO MAP MAKER!
    private final int TIMERTOT=600;
    private int timer;
    public GoalTile(int tileLocX, int tileLocY, Camera cam) {
        super(tileLocX, tileLocY, cam);
        textures= new java.awt.image.BufferedImage[]{Assets.goalSp};
        canPass=true;
        timer=0;

    }

    @Override
    public void logic() {

        for(Golem gol: GameState.player.golemTeam){
            if(gol.gridX==tileX && gol.gridY==tileY){
                GameState.currentRoom.completed=true;
                MenuState.currentMenu=new RoomSelect(State.getState().getGame());

            }
        }
    }

    @Override
    public void animateSelf(Graphics g) {



    }
}
