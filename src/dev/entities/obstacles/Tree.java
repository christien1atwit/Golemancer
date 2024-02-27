package dev.entities.obstacles;

import dev.entities.Golem;
import dev.entities.Items.Generic.Wood;
import dev.entities.Items.building.BlockKit;
import dev.gfx.Assets;
import dev.states.GameState;

public class Tree extends Obstacle{

    public Tree(int x, int y) {
        super(x, y);
        this.broken=false;
        this.canPass=false;
        this.hardness=10;
        this.sprite= Assets.treeSp;
        this.dropItem= new Wood(gridX,gridY);//change this later to a raw material
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
