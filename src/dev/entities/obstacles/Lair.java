package dev.entities.obstacles;

import dev.entities.Golem;
import dev.entities.enemy.Biter;
import dev.entities.enemy.Cwc;
import dev.entities.enemy.Enemy;
import dev.gfx.Assets;
import dev.states.GameState;

public class Lair extends Obstacle{
    private int spawnTurn;

    public Lair(int x, int y) {
        super(x, y);
        sprite= Assets.lairSp;
        hardness=10;
        canPass=true;
        broken=false;
        spawnTurn=20;

    }

    @Override
    public Obstacle place() {
        return null;
    }

    @Override
    public void action(Golem gol) {

    }

    @Override
    public void tick() {
        spawnTurn--;
        if(spawnTurn==0){
            boolean noEnemy=true;
            for(Enemy ene : GameState.currentRoom.myEnemys){
                if(ene.gridX==gridX&&ene.gridY==gridY){
                    noEnemy=false;
                }
            }
            if(noEnemy){
                GameState.currentRoom.myEnemys.add(new Biter(gridX,gridY));
            }
            spawnTurn=20;
        }
    }
}
