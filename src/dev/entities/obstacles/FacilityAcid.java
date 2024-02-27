package dev.entities.obstacles;

import dev.gfx.Assets;

public class FacilityAcid extends HarmfulObstacle{
    public FacilityAcid(int x, int y) {
        super(x, y);
        damageAmount=5;
        canPass=true;
        sprite= Assets.facilAcid;
        broken=false;
        hardness=9999;
    }

    @Override
    public Obstacle place() {
        return null;
    }

    @Override
    public void tick() {

    }
}
