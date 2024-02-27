package dev.entities.obstacles;

import dev.entities.Golem;
import dev.gfx.Assets;

public class FacilityTable extends Obstacle{
    public FacilityTable(int x, int y) {
        super(x, y);
        canPass=false;
        hardness=10;
        sprite= Assets.facilTable;
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
