package dev.tiles;

import dev.gfx.Assets;
import dev.gfx.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FacilityWall extends Tile{
    public FacilityWall(int tileLocX, int tileLocY, Camera cam) {
        super(tileLocX, tileLocY, cam);
        canPass=false;
        textures=new BufferedImage[]{Assets.facilWall};
    }

    @Override
    public void logic() {

    }

    @Override
    public void animateSelf(Graphics g) {

    }
}
