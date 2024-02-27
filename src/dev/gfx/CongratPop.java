package dev.gfx;

import dev.Game;
import dev.states.State;

import java.awt.*;

public class CongratPop {
    public static final int TIMERTOT=300;
    public static int timer=0;
    public static boolean done=false;

    public static void start(Graphics g){
        timer++;
        if(timer>0 && timer<=TIMERTOT){
            //moveThis
            done=false;
            int centX= Game.width/2;
            int centY=Game.height/2;
            int width=200;
            int height=200;
            g.setColor(Color.black);
            g.fillRect(centX-width,centY-height/2,width,height);
            g.setColor(Color.white);
            g.drawString("YOU WON!", centX-width+20, centY-(height/2)+100);
        }else{
            done=true;
            timer=0;
        }
    }
}
