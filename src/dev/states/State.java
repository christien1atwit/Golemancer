package dev.states;



import dev.Game;

import java.awt.*;

public abstract class State {

    private static  State currentState = null;

    public static void setState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }
    //CLASs

    protected Game game;

    public Game getGame(){
        return game;
    }

    public State(Game game){
        this.game=game;

    }


    public abstract void tick();

    public abstract void  render(Graphics g);


}
