package dev.gfx;

import dev.Game;

import java.awt.*;

public abstract class GameMenu {
    public int MENUOPTION;
    public Game game;
    public int menuSelection;
    private int INTERCOOL=5;
    private int actInterCool=INTERCOOL;
        public GameMenu(Game game){
            menuSelection=0;
            this.game=game;
    }
    public void tick(){
            if(game.getKeyManager().up){
                if(actInterCool>=INTERCOOL){
                    actInterCool=0;
                    if(menuSelection>0){
                        menuSelection-=1;
                    }

                }
            }
        if(game.getKeyManager().down){
            if(actInterCool>=INTERCOOL){
                actInterCool=0;
                if(menuSelection<MENUOPTION){
                    menuSelection+=1;
                }

            }
        }
        if(actInterCool<INTERCOOL){
            actInterCool+=1;
        }
        specificLogic();

    }
    public abstract void specificLogic();

    public abstract void  render(Graphics g);
}
