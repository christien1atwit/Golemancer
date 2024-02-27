package dev.gfx.menuscreens;

import dev.Game;
import dev.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Menu{
    protected BufferedImage bgGraphic;
    public String menuName;

    private final int ACTCOOL=5;
    protected int actActCool=0;

    protected int MAXoptions;
    protected int selectedOption;
    protected String menuState;

    public Game game;

    public Menu(Game game){

        this.game=game;
        MAXoptions=255;
        selectedOption=0;
        menuState="Root";
        bgGraphic= Assets.testBG;

    }

    private void menuUp(){
        if(selectedOption-1>=0){
            selectedOption--;
        }else{
            //AT TOP OF MENU PLAY INVALID SFX
        }
    }
    private void menuDown(){
        if(selectedOption+1<=MAXoptions){
            selectedOption++;
        }else{
            //AT BOTTOM OF MENU PLAY INVALID SFX
        }
    }

    private void menuAccept(){
        menuRun(selectedOption);
    }

    public void tick(){
        pollInput();
        cooldown();
    }

    public void render(Graphics g){
        g.drawImage(bgGraphic,0,0,null);
        drawText(g);
    }

    private void pollInput(){
        if(game.getKeyManager().up && actActCool==ACTCOOL){
            actActCool=0;
            menuUp();
        }else if(game.getKeyManager().down && actActCool==ACTCOOL){
            actActCool=0;
            menuDown();
        }
        if(game.getKeyManager().attack && actActCool==ACTCOOL){
            actActCool=0;
            menuAccept();
        }else if(game.getKeyManager().interact && actActCool==ACTCOOL){
            actActCool=0;
            menuDecline();
        }
    }
    private void cooldown(){
        if(actActCool<ACTCOOL){
            actActCool++;
        }
    }

    protected abstract void drawText(Graphics g);

    protected abstract void menuDecline();

    protected abstract void menuRun(int option);

}
