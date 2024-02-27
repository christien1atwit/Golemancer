package dev.entities.Items.building;

import dev.entities.obstacles.Blockade;
import dev.gfx.Assets;
import dev.states.GameState;

public class BlockKit extends Building{
    public BlockKit(int x, int y) {
        super(x, y);
        this.gridX=x;
        this.gridY=y;
        this.sprite= Assets.blockKitSp;
        this.name="Blockade Kit";
        this.description="This kit allows the construction of a blockade (3 Uses)";
        this.uses=3;
        this.toBuild=new Blockade(gridX,gridY);
    }

    @Override
    public void tick() {

    }

    @Override
    public void drop(int x, int y) {
        GameState.currentRoom.myItems.add(new BlockKit(x,y));
    }
}
