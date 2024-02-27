package dev.entities.obstacles;

import dev.entities.Golem;
import dev.entities.Items.Item;
import dev.gfx.Camera;
import dev.states.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Obstacle {
    public int gridX,gridY;
    public BufferedImage sprite;
    public int hardness;
    public boolean broken;
    public boolean canPass;
    public Item dropItem;

    public Obstacle(int x, int y){
        this.gridX=x;
        this.gridY=y;
        this.broken=false;
    }

    public void shatter(int impact){
        if(impact>=hardness){
            broken=true;
        }
        if(dropItem!=null){
            dropItem.drop(gridX,gridY);
        }
    }
    public abstract Obstacle place();

    public abstract void action(Golem gol);

    public abstract void tick();

    public void render(Graphics g){
        int xLoc=(gridX*30)- Camera.camX;
        int yLoc=(gridY*30)- Camera.camY;
        g.drawImage(sprite, xLoc, yLoc, null);
    }


}
