package dev.entities.Items.Weapon;

import dev.entities.Golem;
import dev.entities.Items.Item;

public abstract class Melee extends Item {
    public int fightIncrease;
    public boolean equipped;

    public Melee(int x, int y){
        super(x,y);
        this.consumable=false;
        this.equipped=false;
    }

    @Override
    public void use(Golem user) {
        if(!equipped){
            user.fight+=fightIncrease;
            equipped=true;
        }
    }
}
