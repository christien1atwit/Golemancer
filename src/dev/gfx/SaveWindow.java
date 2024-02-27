package dev.gfx;

import dev.Game;
import dev.sfx.SoundPlayer;
import dev.states.GameState;

import java.awt.*;

public class SaveWindow {
    private int[] size=new int[2];
    private Game game;
    //private boolean saveCom=false;
    private String fileName;
    private SoundPlayer sounds=new SoundPlayer();
    private int BKSPCOOL=2;
    private int actBKS=BKSPCOOL;

    public SaveWindow(int width, int height, Game game){
        size[0]=width;
        size[1]=height;
        this.game=game;
        GameState.saveWin=true;
        fileName="";

    }

    public void tick(){
        game.getKeyManager().setEnableType(true);
        if (game.getKeyManager().backspace && actBKS >= BKSPCOOL) {
            actBKS = 0;
            if (game.getKeyManager().madeString.length() > 0) {
                System.out.println("here");
                game.getKeyManager().madeString = game.getKeyManager().madeString.substring(0, game.getKeyManager().madeString.length() - 1);
                fileName = game.getKeyManager().madeString;
            }
        } else {
            if (!fileName.equals(game.getKeyManager().madeString)) {
                fileName = game.getKeyManager().madeString;
            }
        }
        if(game.getKeyManager().enter){
            GameState.currentRoom.saveGame(fileName);
            game.getKeyManager().setEnableType(false);
            sounds.play(false, Assets.save);
            GameState.saveWin=false;


        }
        if(actBKS<BKSPCOOL){
            actBKS++;
        }
    }

    public void render(Graphics g){
        int[] anchor={(Game.width/2)-(size[0]/2),(Game.height/2)-(size[1]/2)};
        g.setColor(Color.gray);
        g.fillRect(anchor[0],anchor[1],size[0],size[1]);
        g.setColor(Color.black);
        g.fillRect(anchor[0],anchor[1]+((anchor[1]/2)),size[0], 20);
        g.drawString("Save Name:",anchor[0]+10,anchor[1]+((anchor[1]/2)));
        g.setColor(Color.white);
        g.drawString(fileName,anchor[0]+10,anchor[1]+((anchor[1]/2))+15);

    }

}
