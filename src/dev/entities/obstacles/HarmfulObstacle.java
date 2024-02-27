package dev.entities.obstacles;

import dev.entities.Golem;

public abstract class HarmfulObstacle extends Obstacle {

    public int damageAmount;


    public HarmfulObstacle(int x, int y) {
        super(x, y);
    }

    @Override
    public void action(Golem gol) {
        if(!broken){
            gol.health-=damageAmount;
        }

    }

}
