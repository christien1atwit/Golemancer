package dev.entities.enemy;

import dev.Game;
import dev.entities.Golem;
import dev.entities.obstacles.Obstacle;
import dev.gfx.Camera;
import dev.sfx.SoundPlayer;
import dev.states.GameState;
import dev.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy {
    public int gridX,gridY;
    protected BufferedImage[] sprites;
    protected int topHealth, currentHealth;
    protected String aggroNoise, dieNoise;
    protected SoundPlayer sounds= new SoundPlayer();
    protected int fight;

    public boolean imDead;
    protected boolean isAggro;

    public Enemy(int x, int y){
        gridX=x;
        gridY=y;
        imDead=false;
        isAggro=false;

    }

    public void action(){
        if(currentHealth<=0){
             death();
        }
        int [] aggroInf=checkSurround();
        if((Math.abs(aggroInf[1]-gridX)==1 && Math.abs(aggroInf[2]-gridY)<2)||(Math.abs(aggroInf[1]-gridX)<2 && Math.abs(aggroInf[2]-gridY)==1)){
            attack();
        }else if(isAggro){
            pursue(aggroInf);
        }

        }

    private void attack(){
        for(int i=0; i<GameState.player.golemTeam.size();i++){
            if(Math.abs(GameState.player.golemTeam.get(i).gridX-gridX)<2 && Math.abs(GameState.player.golemTeam.get(i).gridY-gridY)<2){
                GameState.player.golemTeam.get(i).health-=fight;
            }
        }
        System.out.println("attack: "+fight);
    }

    public void damage(int incoming){
        currentHealth-=incoming;
        if(currentHealth<=0){
            death();
        }
    }

    private void pursue(int[] info){
        if(gridX>info[1]){
            if(checkCollision(gridX-1,gridY)){
                gridX--;
            }

        }else if(gridX<info[1]){
            if(checkCollision(gridX+1,gridY)){
                gridX++;
            }
        }else{
            if(gridY>info[2]){
                if(checkCollision(gridX,gridY-1)){
                    gridY--;
                }
            }else if(gridY<info[2]){
                if(checkCollision(gridX,gridY+1)){
                    gridY++;
                }
            }
        }
    }

    private boolean checkCollision(int targX, int targY){
        for(Tile til : GameState.currentRoom.myTiles){
            if(til.tileX==targX && til.tileY==targY){
                if(!til.canPass){
                    return false;
                }
            }
        }
        for(Obstacle obs : GameState.currentRoom.myObs){
            if(obs.gridX==targX && obs.gridY==targY){
                if(!obs.canPass){
                    return false;
                }
            }
        }
        return true;
    }

    private int[] checkSurround(){
        for(Golem gol : GameState.player.golemTeam){
            if(Math.abs(gridX-gol.gridX)<4 && Math.abs(gridY-gol.gridY)<4){
                if(!isAggro){
                    sounds.play(false, aggroNoise);
                }
                isAggro=true;
                return new int[]{1,gol.gridX,gol.gridY};
            }
        }
        isAggro=false;
        return new int[]{0,gridX,gridY};
    }


    public void render(Graphics g){
        int xLoc=(gridX*30)- Camera.camX;
        int yLoc=(gridY*30)- Camera.camY;
        g.drawImage(sprites[0], xLoc, yLoc, null);
    }
    public void death(){
        imDead=true;
        sounds.play(false, dieNoise);
    }
}
