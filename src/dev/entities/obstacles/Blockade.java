package dev.entities.obstacles;

import dev.entities.Golem;
import dev.gfx.Assets;

public class Blockade extends Obstacle{

    public Blockade(int x, int y) {
        super(x, y);
        this.sprite= Assets.blockadeSp;
        this.hardness=5;
        this.canPass=false;
    }

    @Override
    public Obstacle place() {
        return new Blockade(this.gridX, this.gridY);
    }

    @Override
    public void action(Golem gol) {

    }

    @Override
    public void tick() {

    }


}
