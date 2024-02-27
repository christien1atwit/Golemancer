package dev.entities;

import dev.gfx.Assets;
import dev.gfx.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Path {
    public static int numPaths=0;
    public int pathID;
    public int x,y;
    public int order;
    private BufferedImage sprite;

    public Path(int x, int y, int ord, int pID){
        this.x=x;
        this.y=y;
        if(ord==0){
            numPaths++;
        }
        this.order=ord;
        this.pathID=pID;
        sprite=Assets.pathSp;
        //System.out.print("----\n x:"+this.x+"\ny:"+this.y+"\npathID="+this.pathID+"\n----\n");
    }

    public void render(Graphics g){
        int drawX=x*30;
        int drawY=y*30;
        g.drawImage(sprite, drawX-Camera.camX,drawY-Camera.camY,null);
        g.setColor(Color.red);
        g.drawString(String.valueOf(pathID), drawX-Camera.camX, drawY-Camera.camY+10);
        g.setColor(Color.black);

    }

}
