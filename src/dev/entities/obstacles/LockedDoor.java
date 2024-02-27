package dev.entities.obstacles;

import dev.entities.Golem;
import dev.gfx.Assets;

import java.awt.image.BufferedImage;

public class LockedDoor extends Obstacle{
    private BufferedImage open= Assets.openDoorSp;
    private BufferedImage close= Assets.closeDoorSp;
    public LockedDoor(int x, int y) {
        super(x, y);
        this.sprite=close;
        this.canPass=false;
        this.broken=false;
        this.hardness=9999;
    }

    @Override
    public Obstacle place() {
        return null;
    }

    @Override
    public void action(Golem gol) {
        //Open Door
    }

    public void keyUsed(){
        canPass=true;
        sprite=Assets.openDoorSp;
    }

    @Override
    public void tick() {

    }
}
