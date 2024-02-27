package dev.entities.obstacles;

import dev.entities.Golem;
import dev.entities.Items.healing.Clay;
import dev.gfx.Assets;
import dev.states.GameState;
import dev.tiles.Tile;

public class ClayRock extends Obstacle{
    public ClayRock(int x, int y) {
        super(x, y);
        this.canPass=false;
        this.hardness=5;
        this.dropItem=new Clay(x,y);
        this.sprite= Assets.clayRockSp;
    }

    @Override
    public void shatter(int impact){
        if(impact>=hardness){
            boolean tCanPlace=false;
            boolean oCanPlace=false;
            int[] location=new int[2];
            for(int i=0;i<GameState.currentRoom.myTiles.size();i++){
                if(GameState.currentRoom.myTiles.get(i).tileX==gridX+1 && GameState.currentRoom.myTiles.get(i).tileY==gridY && GameState.currentRoom.myTiles.get(i).canPass){
                    tCanPlace=true;
                    location[0]=GameState.currentRoom.myTiles.get(i).tileX;
                    location[1]=GameState.currentRoom.myTiles.get(i).tileY;
                    i=GameState.currentRoom.myTiles.size();
                }
                 else if(GameState.currentRoom.myTiles.get(i).tileX==gridX-1 && GameState.currentRoom.myTiles.get(i).tileY==gridY && GameState.currentRoom.myTiles.get(i).canPass){
                    tCanPlace=true;
                    location[0]=GameState.currentRoom.myTiles.get(i).tileX;
                    location[1]=GameState.currentRoom.myTiles.get(i).tileY;
                    i=GameState.currentRoom.myTiles.size();
                }
                else if(GameState.currentRoom.myTiles.get(i).tileX==gridX && GameState.currentRoom.myTiles.get(i).tileY==gridY+1 && GameState.currentRoom.myTiles.get(i).canPass){
                    tCanPlace=true;
                    location[0]=GameState.currentRoom.myTiles.get(i).tileX;
                    location[1]=GameState.currentRoom.myTiles.get(i).tileY;
                    i=GameState.currentRoom.myTiles.size();
                }
                else if(GameState.currentRoom.myTiles.get(i).tileX==gridX && GameState.currentRoom.myTiles.get(i).tileY==gridY-1 && GameState.currentRoom.myTiles.get(i).canPass){
                    tCanPlace=true;
                    location[0]=GameState.currentRoom.myTiles.get(i).tileX;
                    location[1]=GameState.currentRoom.myTiles.get(i).tileY;
                    i=GameState.currentRoom.myTiles.size();
                }
            }
            if(tCanPlace){
                GameState.currentRoom.myItems.add(new Clay(location[0],location[1]));
            }
        }
    }

    @Override
    public Obstacle place() {
        return null;
    }

    @Override
    public void action(Golem gol) {

    }

    @Override
    public void tick() {

    }
}
