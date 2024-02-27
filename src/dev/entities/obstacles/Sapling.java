package dev.entities.obstacles;

import dev.entities.Golem;
import dev.entities.Items.building.Nut;
import dev.gfx.Assets;
import dev.states.GameState;

import java.util.Random;

public class Sapling extends Obstacle{

    private int timeToGrow;


    public Sapling(int x, int y) {
        super(x, y);
        Random rand=new Random();
        this.canPass=false;
        this.broken=false;
        this.dropItem=null;
        this.hardness=5;
        this.sprite= Assets.saplingSp;
        timeToGrow=rand.nextInt(5)+5;

    }

    @Override
    public Obstacle place() {
        return new Sapling(this.gridX,this.gridY);
    }

    @Override
    public void action(Golem gol) {

    }

    @Override
    public void tick() {
        timeToGrow--;
        if(timeToGrow<=0){
            this.broken=true;
            GameState.currentRoom.myObs.add(new Tree(gridX,gridY));
        }
    }
}
