package dev.entities.Items.Generic;

import dev.entities.Golem;
import dev.entities.Items.Item;
import dev.entities.obstacles.LockedDoor;
import dev.entities.obstacles.Obstacle;
import dev.gfx.Assets;
import dev.states.GameState;

public class KeyCard extends Item {
    public KeyCard(int x, int y) {
        super(x, y);
        this.consumable=false;
        this.description="This Key Card can open one locked door before it expires.";
        this.name="Key Card";
        this.weight=1;
        this.sprite= Assets.keySp;

    }

    @Override
    public void tick() {

    }

    @Override
    public void use(Golem user) {
        boolean onDoor=false;
        Obstacle checkedObs;
        switch (user.targetDir){
            case 0://Up
                checkedObs=GameState.currentRoom.getObs(user.gridX,user.gridY-1);
                if(checkedObs!=null){
                    if(checkedObs.getClass()==LockedDoor.class){
                        if(!checkedObs.canPass) {
                            consumable = true;
                            ((LockedDoor) checkedObs).keyUsed();
                        }
                    }
                }
                break;
            case 1: //Right
                checkedObs=GameState.currentRoom.getObs(user.gridX+1,user.gridY);
                if(checkedObs!=null){
                    if(checkedObs.getClass()==LockedDoor.class){
                        if(!checkedObs.canPass) {
                            consumable = true;
                            ((LockedDoor) checkedObs).keyUsed();
                        }
                    }
                }
                break;
            case 2: //Down
                checkedObs=GameState.currentRoom.getObs(user.gridX,user.gridY+1);
                if(checkedObs!=null){
                    if(checkedObs.getClass()==LockedDoor.class){
                        if(!checkedObs.canPass) {
                            consumable = true;
                            ((LockedDoor) checkedObs).keyUsed();
                        }
                    }
                }
                break;
            case 3: //Left
                checkedObs=GameState.currentRoom.getObs(user.gridX-1,user.gridY);
                if(checkedObs!=null){
                    if(checkedObs.getClass()==LockedDoor.class){
                        if(!checkedObs.canPass) {
                            consumable = true;
                            ((LockedDoor) checkedObs).keyUsed();
                        }

                    }
                }
                break;

            default:
                System.out.println("Use Key in non-existant direction");


        }
    }

    @Override
    public void drop(int x, int y) {
        GameState.currentRoom.myItems.add(new KeyCard(x,y));
    }
}
