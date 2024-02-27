package dev.gfx.menuscreens;

import dev.Game;
import dev.course.maps.LoadableMap;
import dev.entities.Controller;
import dev.gfx.Assets;
import dev.states.GameState;
import dev.states.MenuState;
import dev.states.State;

import java.awt.*;

public class RoomSelect extends Menu{
    private String[] strMaps;
    public RoomSelect(Game game) {
        super(game);
        menuName="Room Select";
        MAXoptions=5;
        strMaps=new String[MAXoptions+1];
        selectedOption=0;
        bgGraphic= Assets.menuBG;
        for(int i=0; i<=MAXoptions;i++){
            //add levels
            switch (i){
                case 0:
                    strMaps[i]="Water Base";
                    break;
                case 1:
                    strMaps[i]="Rivers";
                    break;
                case 2:
                    strMaps[i]="Mountain Valley";
                    break;
                case 3:
                    strMaps[i]="Key Test";
                    break;
                case 4:
                    strMaps[i]="Abandoned Laboratory";
                    break;
                default:
                    strMaps[i]="CREATE MAP";
                    //System.out.println("strMaps index out of bounds");
            }

        }
    }

    @Override
    protected void drawText(Graphics g) {
        int ySPACE=15;
        for(int i=0; i<=MAXoptions;i++){
            g.setColor(Color.white);
            if(i==selectedOption){
                g.setColor(Color.RED);
            }
            g.drawString(strMaps[i],20, 50+((i+1)*15));
        }

    }

    @Override
    protected void menuDecline() {
        MenuState.currentMenu= new TitleMenu(game);
    }

    @Override
    protected void menuRun(int option) {
        switch (option){
            case 0:
                //GameState.currentRoom=new WaterBase(Game.camera);
                GameState.currentRoom= new LoadableMap(Game.camera, "WaterBase.gsinf", false);
                GameState.player=new Controller(game);
                break;
            case 1:
                //GameState.currentRoom=new Rivers(Game.camera);
                GameState.currentRoom=new LoadableMap(Game.camera, "Rivers.gsinf", false);
                GameState.player=new Controller(game);
                break;
            case 2:
                GameState.currentRoom=new LoadableMap(Game.camera,"mountBiter.gsinf", false);
                GameState.player=new Controller(game);
                break;
            case 3:
                GameState.currentRoom=new LoadableMap(Game.camera,"keyTest.gsinf", false);
                GameState.player=new Controller(game);
                break;
            case 4:
                GameState.currentRoom= new LoadableMap(Game.camera, "Lab.gsinf", false);
                GameState.player=new Controller(game);
                break;
            default:
                GameState.player=new Controller(game,true);
                System.out.println("NO MAP "+option);
                break;
        }
        State.setState(game.gameState);
    }
}
