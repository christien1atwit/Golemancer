package dev.course;

import dev.gfx.Assets;

import java.awt.image.BufferedImage;

public class MapWaypoint {

    public int x,y;
    public BufferedImage sprite;
    public int[][] area= new int[2][2];

    public MapWaypoint(int placeX,int placeY){
        x=placeX;
        y=placeY;
        sprite= Assets.testimg;
    }

    public void setArea(Rooms usedRoom){
        boolean scanningEdge=true;
        boolean onHorizontalEdge=false;
        boolean onVerticalEdge=false;
        int topEdgeLen=1;
        int sideEdgeLen=1;

        while(scanningEdge){
            int posXIn=1;
            int negXIn=1;
            int posYIn=1;
            int negYIn=1;
            while (!usedRoom.getTile(x+posXIn,y).canPass){
                topEdgeLen++;
                posXIn++;
                onHorizontalEdge=true;

            }
            while(!usedRoom.getTile(x-negXIn,y).canPass){
                topEdgeLen++;
                negXIn++;
                onHorizontalEdge=true;
            }
            if(onHorizontalEdge){
                while(!usedRoom.getTile(x+posXIn,y-negYIn).canPass){
                    sideEdgeLen++;
                    negYIn++;

                }
                area[0]=new int[]{x-negXIn+1, y+negXIn+1};
                area[1]=new int[]{x+posXIn-1, y+posYIn-1};
                System.out.println("["+area[0][0]+","+area[0][1]+"]"+" ["+area[1][0]+","+area[1][1]+"]");
            }else{
                while(!usedRoom.getTile(x,y+posYIn).canPass){
                    sideEdgeLen++;
                    posYIn++;
                    onVerticalEdge=true;
                }
                while(!usedRoom.getTile(x,y-negYIn).canPass){
                    sideEdgeLen++;
                    negYIn++;
                    onVerticalEdge=true;
                }
                while(!usedRoom.getTile(x+posXIn,y-negYIn).canPass){
                    topEdgeLen++;
                    posXIn++;
                }

                area[0]=new int[]{x-negXIn+1,y-negYIn+1};
                area[1]=new int[]{x-negXIn,y-negYIn};
                System.out.println("["+area[0][0]+","+area[0][1]+"]"+" ["+area[1][0]+","+area[1][1]+"]");
            }

            scanningEdge=false;
        }
    }
}
