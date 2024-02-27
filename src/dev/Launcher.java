package dev;

import dev.Game;

/**
 *
 *
 * @author Nathan Christie
 * @version 3/3/2021
 */
public class Launcher
{
    public static void main(String[] args)
    {
        Game game=new Game("Golem: Open Test", 1280, 720);
        game.start();
        
    }
}
