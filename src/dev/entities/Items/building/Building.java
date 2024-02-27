package dev.entities.Items.building;

import dev.Game;
import dev.entities.Golem;
import dev.entities.Items.Item;
import dev.entities.obstacles.Obstacle;
import dev.states.GameState;
import dev.tiles.Tile;

public abstract class Building extends Item {
    public Obstacle toBuild;
    public int uses;



    public Building(int x, int y) {
        super(x, y);
        this.consumable=false;
    }

    @Override
    public void use(Golem user) {
        boolean canPlace = false;
        switch(user.targetDir){
            case 0:
                //place North
                for(Tile i: GameState.currentRoom.myTiles){
                    if(i.tileX==user.gridX && i.tileY==user.gridY-1){
                        canPlace=i.canPass;
                    }
                }
                for(Obstacle i: GameState.currentRoom.myObs){
                    if(i.gridX==user.gridX && i.gridY==user.gridY-1){
                        canPlace=i.canPass;
                    }
                }
                if(canPlace){
                    toBuild.gridX=user.gridX;
                    toBuild.gridY=user.gridY-1;
                    GameState.currentRoom.myObs.add(toBuild.place());
                    uses--;

                }
                break;
            case 2:
                //place South
                for(Tile i: GameState.currentRoom.myTiles){
                    if(i.tileX==user.gridX && i.tileY==user.gridY+1){
                        canPlace=i.canPass;
                    }
                }
                for(Obstacle i: GameState.currentRoom.myObs){
                    if(i.gridX==user.gridX && i.gridY==user.gridY+1){
                        canPlace=i.canPass;
                    }
                }
                if(canPlace){
                    toBuild.gridX=user.gridX;
                    toBuild.gridY=user.gridY+1;
                    GameState.currentRoom.myObs.add(toBuild.place());
                    uses--;
                }
                break;
            case 1:
                //place East
                for(Tile i: GameState.currentRoom.myTiles){
                    if(i.tileX==user.gridX+1 && i.tileY==user.gridY){
                        canPlace=i.canPass;
                    }
                }
                for(Obstacle i: GameState.currentRoom.myObs){
                    if(i.gridX==user.gridX+1 && i.gridY==user.gridY){
                        canPlace=i.canPass;
                    }
                }
                if(canPlace){
                    toBuild.gridX=user.gridX+1;
                    toBuild.gridY=user.gridY;
                    GameState.currentRoom.myObs.add(toBuild.place());
                    uses--;
                }
                break;
            case 3:
                //place West
                for(Tile i: GameState.currentRoom.myTiles){
                    if(i.tileX==user.gridX-1 && i.tileY==user.gridY){
                        canPlace=i.canPass;
                    }
                }
                for(Obstacle i: GameState.currentRoom.myObs){
                    if(i.gridX==user.gridX-1 && i.gridY==user.gridY){
                        canPlace=i.canPass;
                    }
                }
                if(canPlace){
                    toBuild.gridX=user.gridX-1;
                    toBuild.gridY=user.gridY;
                    GameState.currentRoom.myObs.add(toBuild.place());
                    uses--;
                }
                break;
            default:
                System.out.println("Placed in nonexistent direction");
        }
        if(uses<1){
            this.consumable=true;
        }
    }
}
