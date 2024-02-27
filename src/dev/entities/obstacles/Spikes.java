package dev.entities.obstacles;


import dev.entities.Golem;
import dev.gfx.Assets;

public class Spikes extends HarmfulObstacle {

    public Spikes(int x, int y) {
        super(x, y);
        this.broken=false;
        this.canPass=true;
        this.damageAmount=1;
        this.hardness=15;
        this.sprite= Assets.spikeSp;
    }

    @Override
    public Obstacle place() {
        return null;
    }

    @Override
    public void tick() {

    }


}
