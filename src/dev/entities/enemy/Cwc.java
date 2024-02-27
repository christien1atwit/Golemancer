package dev.entities.enemy;

import dev.gfx.Assets;

import java.awt.image.BufferedImage;

public class Cwc extends Enemy{
    public Cwc(int x, int y) {
        super(x, y);
        sprites= new BufferedImage[]{Assets.CwcSp};
        aggroNoise=Assets.summon;
        dieNoise=Assets.eldrich;
        topHealth=3;
        currentHealth=topHealth;
        fight=1;
    }
}
