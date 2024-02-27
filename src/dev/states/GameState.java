package dev.states;

import dev.Game;
import dev.course.Rooms;
import dev.entities.Controller;
import dev.entities.Golem;
import dev.gfx.CongratPop;
import dev.scriptingsys.Script;
import dev.scriptingsys.create.Creator;
import dev.sfx.SoundPlayer;

import java.awt.*;

public class GameState extends State{

    private Game game;
    private SoundPlayer musicBox;
    private Script sc;
    private Golem gol;
    public static Controller player;
    private Creator create;
    public static Rooms currentRoom;

    public static boolean labelMake=false;
    public static boolean saveWin=false;

    public GameState(Game game){
        super(game);
        /*
        currentRoom=new Rivers(game.camera);
        gol=new Golem("Jeff",21,13);
        player=new Controller(game,gol);
        */
        create=new Creator(game);



    }

    @Override
    public void tick() {
      if(labelMake){
          create.tick();
      }else if(saveWin) {
          player.savWin.tick();
      }else {
          player.savWin=null;
          player.tick();
          if(currentRoom!=null)
          currentRoom.tick();
      }
      if(CongratPop.done){
          currentRoom.completed=false;
          CongratPop.done=false;
          State.setState(getState().getGame().menuState);
      }


    }
    public void setRoom(Rooms room){
        currentRoom=room;
    }

    @Override
    public void render(Graphics g) {
        if(labelMake){
            create.render(g);
        }else if(saveWin){
            if(currentRoom!=null)
                currentRoom.render(g);
            player.render(g);
            player.savWin.render(g);

        } else{
            if(currentRoom!=null){
                currentRoom.render(g);
            }
            player.render(g);

            if(currentRoom!=null&&currentRoom.completed){
                CongratPop.start(g);
            }
        }

    }
}
