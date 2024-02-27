package dev.entities.Items.Generic;

import dev.entities.Golem;
import dev.entities.Items.Item;
import dev.entities.Items.building.BlockKit;
import dev.gfx.Assets;
import dev.states.GameState;

public class Wood extends Item {
    public Wood(int x, int y) {
        super(x, y);
        this.consumable=false;
        this.name="Wood";
        this.description="A log from a felled tree. It can be used at a base to make a Blockade Kit.";
        this.weight=1;
        this.sprite= Assets.woodSp;
    }

    @Override
    public void tick() {

    }

    @Override
    public void use(Golem user) {

    }

    @Override
    public void drop(int x, int y) {
        GameState.currentRoom.myItems.add(new Wood(x,y));
    }
}
