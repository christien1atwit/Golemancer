package dev.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    private boolean[] keys;
    public  boolean zero,up, down, left, right, run,attack,interact,sUp,sDown,sLeft,sRight, save, backspace, enter, tab,help, p;

    public boolean enableType=true;
    public String madeString="";

    public KeyManager(){
        keys= new boolean[256];
    }
    public void tick(){
        up=keys[KeyEvent.VK_UP];
        right=keys[KeyEvent.VK_RIGHT];
        down=keys[KeyEvent.VK_DOWN];
        left=keys[KeyEvent.VK_LEFT];
        run=keys[KeyEvent.VK_SHIFT];
        attack=keys[KeyEvent.VK_Z];
        interact=keys[KeyEvent.VK_X];
        sUp=keys[KeyEvent.VK_W];
        sDown=keys[KeyEvent.VK_S];
        sLeft=keys[KeyEvent.VK_A];
        sRight=keys[KeyEvent.VK_D];
        save=keys[KeyEvent.VK_SPACE];

        zero=keys[KeyEvent.VK_0];
        p=keys[KeyEvent.VK_P];
        help=keys[KeyEvent.VK_H];

        tab=keys[KeyEvent.VK_TAB];
        backspace=keys[KeyEvent.VK_BACK_SPACE];
        enter=keys[KeyEvent.VK_ENTER];

    }

    public void setMadeString(String str){
        madeString=str;
    }

    public void setEnableType(boolean enableType) {
        this.enableType = enableType;
        if(!enableType) {
            madeString = "";
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if(enableType){
            if(!backspace) {
                madeString = madeString.concat(String.valueOf((e.getKeyChar())));
                if (madeString.contains("\n")) {
                    madeString = madeString.substring(0, madeString.length() - 1);

                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()]=true;
        //System.out.println("hahahah!");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()]=false;
    }
}
