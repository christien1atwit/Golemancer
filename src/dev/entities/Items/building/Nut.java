package dev.entities.Items.building;

import dev.entities.obstacles.Sapling;
import dev.gfx.Assets;

public class Nut extends Building {
    public Nut(int x, int y) {
        super(x, y);
        this.toBuild= new Sapling(this.gridX,this.gridY);
        this.sprite= Assets.nutSp;
        this.uses=1;
        this.consumable=true;
        this.name="Nut";
        this.description="This nut can be used on a patch of dirt to start growing a tree. (1 Use)";

    }

    @Override
    public void tick() {

    }

    @Override
    public void drop(int x, int y) {

    }
}
