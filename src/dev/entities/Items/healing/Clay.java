package dev.entities.Items.healing;

import dev.entities.Items.building.BlockKit;
import dev.gfx.Assets;
import dev.states.GameState;

import java.awt.image.BufferedImage;

public class Clay extends HealingItem{



    public Clay(int x, int y) {
        super(x, y);
        this.sprite=Assets.golemDed;
        this.name="Clay";
        this.description="A normal hunk of clay. It can be used to repair minor damages in Golems. (5 HP)";
        this.weight=1;
        this.healingValue=5;
    }

    @Override
    public void tick() {

    }

    @Override
    public void drop(int x, int y) {
        GameState.currentRoom.myItems.add(new Clay(x,y));
    }
}
