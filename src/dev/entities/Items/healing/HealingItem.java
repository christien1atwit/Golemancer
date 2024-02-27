package dev.entities.Items.healing;

import dev.entities.Golem;
import dev.entities.Items.Item;

public abstract class HealingItem extends Item {

    public int healingValue;

    public HealingItem(int x, int y) {
        super(x, y);
        this.consumable=true;
    }

    @Override
    public void use(Golem user) {
        user.health+=healingValue;
        if(user.health>user.healthMax){
            user.health=user.healthMax;
        }
    }
}
