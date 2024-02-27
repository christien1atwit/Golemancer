package dev;

import dev.display.Display;
import dev.gfx.Assets;
import dev.gfx.Camera;
import dev.input.KeyManager;
import dev.states.GameState;
import dev.states.MenuState;
import dev.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class Game implements Runnable
{
    private Display display;
    public static int width, height;
    public String title;
    private boolean running = false;

    private Thread thread;
    
    private BufferStrategy bs;
    private Graphics g;


    //states
    public State gameState;
    public State menuState;

    //input
    private KeyManager keyManager;

    //Camera
    public static Camera camera=new Camera(0,0);

    public Game(String title, int width, int height)
    {
        this.width= width;
        this.height=height;
        this.title=title;
        keyManager = new KeyManager();

    }
    
    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();

        gameState= new GameState(this);
        menuState= new MenuState(this);
        State.setState(menuState);
    }



    private void tick()
    {
        keyManager.tick();
        if(State.getState() != null)
            State.getState().tick();
    }
    
    private void render()
    {
        bs= display.getCanvas().getBufferStrategy();
        if(bs==null)
        {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //clear screen
        g.clearRect(0,0,width,height);
        //Draw Here!

        if(State.getState() != null)
            State.getState().render(g);

        //End Drawing!
        bs.show();
        g.dispose();
    }
    
    public void run()
    {
        init();
        int fps=60;
        double timePerTick = 1000000000/fps;
        double delta = 0;
        long now;
        long lastTime=System.nanoTime();
        long timer = 0;
        int ticks=0;


        while(running){
            now= System.nanoTime();
            delta += (now-lastTime)/timePerTick;
            timer += now - lastTime;
            lastTime=now;

            if(delta >=1) {
                tick();
                render();
                ticks++;
                delta--;
            }
            if(timer >= 1000000000){
                //System.out.println(ticks);
                ticks=0;
                timer=0;
            }
        }
        
        stop();
    }

    public KeyManager getKeyManager(){
        return keyManager;
    }

    public synchronized void start()
    {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    public synchronized void stop()
    {
        if (!running)
            return;
        running=false;
        try{
        thread.join();
    } catch (InterruptedException e){
        e.printStackTrace();
    }
    }

    public void setDisplay(int wid, int hei){
       width=wid;
       height=hei;
       display.setCanSize(width,height);
    }
}