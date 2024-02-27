package dev.tiles;

import dev.gfx.Camera;
import dev.states.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile {
    public BufferedImage[] textures;
    public int aniFrame=0;
    public int xLocation, yLocation,tileX,tileY;
    public boolean canPass;
    public Camera cam;
    public int endX,endY;
    public Tile(int tileLocX, int tileLocY, Camera cam){
        this.tileX=tileLocX;
        this.tileY=tileLocY;
        this.cam=cam;
        this.xLocation=tileLocX*30;
        this.yLocation=tileLocY*30;
        this.endX=xLocation+30;
        this.endY=yLocation+30;
    }
    public void tick(){
        this.xLocation=tileX*30;
        this.yLocation=tileY*30;
        this.endX=xLocation+30;
        this.endY=yLocation+30;
        if(GameState.currentRoom!=null)
        logic();
    }

    public abstract void logic();

    public void render(Graphics g){

        //System.out.println(textures.length);
        g.drawImage(textures[aniFrame],(int)(xLocation- Camera.camX),(int)(yLocation- Camera.camY),null);
        animateSelf(g);
    }
    public abstract void animateSelf(Graphics g);
}
