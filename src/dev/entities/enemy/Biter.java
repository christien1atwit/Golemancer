package dev.entities.enemy;

import dev.gfx.Assets;

import java.awt.image.BufferedImage;

public class Biter extends Enemy{
    public Biter(int x, int y) {
        super(x, y);
        sprites= new BufferedImage[]{Assets.biterSp};
        fight=2;
        aggroNoise=Assets.save;
        dieNoise=Assets.eldrich;
        topHealth=5;
        currentHealth=topHealth;

    }
}
