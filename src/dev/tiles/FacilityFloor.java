package dev.tiles;

import dev.gfx.Assets;
import dev.gfx.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FacilityFloor extends  Tile{
    public FacilityFloor(int tileLocX, int tileLocY, Camera cam) {
        super(tileLocX, tileLocY, cam);
        canPass=true;
        textures=new BufferedImage[]{Assets.facilFloor};
    }

    @Override
    public void logic() {

    }

    @Override
    public void animateSelf(Graphics g) {

    }
}
