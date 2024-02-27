package dev.tiles;

import dev.entities.Golem;
import dev.entities.Items.Generic.Wood;
import dev.entities.Items.building.BlockKit;
import dev.entities.Items.healing.Clay;
import dev.gfx.Assets;
import dev.gfx.Camera;
import dev.sfx.SoundPlayer;
import dev.states.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Base extends Tile{
    //Base acts as a crafting spot
    //Clay -> new Golems
    //Wood -> Blockade Kits

    private boolean summoning;
    private int TIMER=30, actTime;
    private boolean lightningFrame;

    public Base(int tileLocX, int tileLocY, Camera cam) {
        super(tileLocX, tileLocY, cam);
        this.textures=new BufferedImage[]{Assets.baseSp};
        this.canPass=true;
        this.tileX=tileLocX;
        this.tileY=tileLocY;
        this.cam=cam;
        this.xLocation=tileX*30;
        this.yLocation=tileY*30;
        this.endX=this.xLocation+30;
        this.endY=this.yLocation+30;
        this.aniFrame=0;
        this.summoning=false;
        this.lightningFrame=true;
    }

    @Override
    public void logic() {
        //System.out.println("here");
        ArrayList<Integer> toRemove=new ArrayList<Integer>();
        for(int i=GameState.currentRoom.myItems.size()-1;i>=0;i--) {
            if (GameState.currentRoom.myItems.get(i).getClass() == Clay.class) {
                if (GameState.currentRoom.myItems.get(i).gridX == tileX && GameState.currentRoom.myItems.get(i).gridY == tileY) {
                    GameState.player.addGolem(new Golem(tileX, tileY));
                    toRemove.add(i);
                    new SoundPlayer().play(false, Assets.summon);
                    summoning=true;
                    actTime=TIMER;
                }
            } else if (GameState.currentRoom.myItems.get(i).getClass() == Wood.class) {
                if (GameState.currentRoom.myItems.get(i).gridX == tileX && GameState.currentRoom.myItems.get(i).gridY == tileY) {
                    GameState.currentRoom.myItems.add(new BlockKit(tileX, tileY));
                    toRemove.add(i);
                    new SoundPlayer().play(false, Assets.summon);
                    summoning=true;
                    actTime=TIMER;
                }
            }
        }
        for(int i:toRemove){
            GameState.currentRoom.myItems.remove(i);
        }
    }

    @Override
    public void animateSelf(Graphics g) {
        if(summoning){
            if(actTime%4==0){
                if(lightningFrame){
                    lightningFrame=false;

                }else{
                    lightningFrame=true;
                }
            }
            if(lightningFrame){

                g.drawImage(Assets.lightning0,xLocation-Camera.camX,yLocation-Camera.camY-actTime,null);
            }else{
                g.drawImage(Assets.lightning1,xLocation-Camera.camX,yLocation-Camera.camY-actTime,null);
            }
            actTime--;
            if(actTime<=0){
                summoning=false;
            }
        }
    }
}
