package dev.entities.Items.Weapon;

import dev.entities.Items.building.BlockKit;
import dev.gfx.Assets;
import dev.states.GameState;

public class Club extends Melee {


    public Club(int x, int y) {
        super(x, y);
        this.fightIncrease=10;
        this.sprite= Assets.clubSp;
        this.weight=1;
        this.description="A simple wooden club. (Fight +10)";
        this.name="Club";
    }

    @Override
    public void tick() {

    }

    @Override
    public void drop(int x, int y) {
        GameState.currentRoom.myItems.add(new Club(x,y));
    }
}
