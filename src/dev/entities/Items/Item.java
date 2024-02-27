package dev.entities.Items;

import dev.entities.Golem;
import dev.gfx.Camera;
import dev.states.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {

    public String name;
    public BufferedImage sprite;
    public int gridX, gridY;
    public int weight;
    public String description;
    public boolean consumable;

    public Item(int x, int y){
        this.gridX=x;
        this.gridY=y;
    }

    public abstract void tick();

    public abstract void use(Golem user);

    public abstract void drop(int x, int y);


    public void render(Graphics g){
        int xLoc=(gridX*30)- Camera.camX;
        int yLoc=(gridY*30)- Camera.camY;
        g.drawImage(sprite, xLoc, yLoc, null);
    }

}
