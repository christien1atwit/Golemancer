package dev.states;



import dev.Game;
import dev.gfx.menuscreens.Menu;
import dev.gfx.menuscreens.TitleMenu;

import java.awt.*;


public class MenuState extends State {

    public static Menu currentMenu;
    public String command;

    public  MenuState(Game game){
        super(game);
        currentMenu=new TitleMenu(game);

    }
    @Override
    public void tick() {

        currentMenu.tick();

    }


    @Override
    public void render(Graphics g) {
        currentMenu.render(g);
    }
}
