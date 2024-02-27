package dev.tiles;

import dev.gfx.Assets;
import dev.gfx.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Water extends Tile{
    public Water(int tileLocX, int tileLocY, Camera cam) {
        super(tileLocX, tileLocY, cam);
        this.aniFrame=0;
        this.textures= new BufferedImage[]{Assets.waterSp};
        this.tileX=tileLocX;
        this.tileY=tileLocY;
        this.cam=cam;
        this.xLocation=tileX*30;
        this.yLocation=tileY*30;
        this.endX=this.xLocation+30;
        this.endY=this.yLocation+30;
        this.canPass=false;
    }

    @Override
    public void logic() {

    }

    @Override
    public void animateSelf(Graphics g) {

    }
}
