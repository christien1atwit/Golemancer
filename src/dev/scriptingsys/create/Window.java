package dev.scriptingsys.create;

import dev.scriptingsys.Script;

import java.awt.*;
import java.util.ArrayList;
import dev.scriptingsys.Label;

public class Window {

    public String name;
    public String type;

    public Script outputSc;
    public Label createdLb;

    public ArrayList<String> intructions=new ArrayList<String>();
    public int instIndex=0;


    public int x,y;
    public int width, height;


    public static int numbWins=0;
    public static int currentWin=0;

    public int windowNumber;

    public Window(String name, String type){
        this.windowNumber=numbWins;
        this.type=type;
        numbWins++;
        this.width=620;
        this.height=540;
        this.x=100;
        this.y= 10;
        this.name=name;
    }

    public Window(String name, String type, int wid, int hei){
        this.windowNumber=numbWins;
        numbWins++;
        this.type=type;
        this.width=wid;
        this.height=hei;
        this.x=100;
        this.y= 10;
        this.name=name;
    }

    public void render(Graphics g){

        if(this.windowNumber==currentWin) {
            g.setColor(Color.black);
            g.drawRect(this.x, this.y, this.width, this.height);
            g.setColor(Color.GRAY);
            g.fillRect(this.x+1,this.y+1, this.width-1, this.height-1);
            g.setColor(Color.black);
            g.drawString(String.valueOf(this.windowNumber),this.x+5,this.height+10);
        }else if(this.windowNumber>currentWin){
            g.setColor(Color.black);
            g.drawRect(this.x+(10*(windowNumber-currentWin)), this.y+(10*(windowNumber-currentWin)), this.width, this.height);
            g.setColor(Color.GRAY);
            g.fillRect(this.x+(10*(windowNumber-currentWin))+1, this.y+(10*(windowNumber-currentWin))+1, this.width-1, this.height-1);
        }


    }

    public static void selectUp(){
        if(currentWin<numbWins)
            currentWin++;
    }

    public static void selectDown(){
        if(currentWin>0)
            currentWin--;
    }

    public String[] toStrArray(ArrayList<String> strList){
        String[] retArr= new String[strList.size()];
        for(int i =0;i<strList.size();i++){
            retArr[i]=strList.get(i);
        }
        return retArr;
    }

    public static int getCurrentWin(){
        return currentWin;
    }

    public static int getNumbWins(){
        return numbWins;
    }

}
