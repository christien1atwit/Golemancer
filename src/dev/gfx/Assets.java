package dev.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int width= 30, height =30;

    public  static BufferedImage testimg;
    public static  BufferedImage testBG, titleBG, howToPlayBg, menuBG;
    public static  BufferedImage brickTileSp,brickFloorSp,grassSp,waterSp, facilFloor, facilWall, facilTable, facilAcid;
    public static BufferedImage golemN0,golemN1,golemDed;
    public static BufferedImage cursorSp;
    public static BufferedImage clubSp;
    public static BufferedImage blockadeSp;
    public static BufferedImage blockKitSp;
    public static BufferedImage baseSp;
    public static BufferedImage spikeSp, biterSp;
    public static BufferedImage lightning0,lightning1;
    public static BufferedImage keySp,treeSp, woodSp, dirtSp, saplingSp, clayRockSp, pathSp, nutSp, goalSp, CwcSp, lairSp, openDoorSp, closeDoorSp;
    public static String mainTheme,otherTheme, eldrich, plink,summon,save;

    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/test.png"));
        testimg=sheet.crop(0,0,width,height);
        testBG=ImageLoader.loadImage("/textures/forest.png");
        SpriteSheet sheet2= new SpriteSheet(ImageLoader.loadImage("/textures/wallFloorSheet.png"));
        brickFloorSp=sheet2.crop(0,0,width,height);
        brickTileSp=sheet2.crop(width,0,width, height);
        SpriteSheet golem= new SpriteSheet(ImageLoader.loadImage("/textures/golemSheet.png"));
        golemN0=golem.crop(0,0,width,height);
        golemN1=golem.crop(width,0,width,height);
        golemDed=golem.crop(width*2,0,width,height);
        cursorSp=ImageLoader.loadImage("/textures/cursor.png");
        clubSp=ImageLoader.loadImage("/textures/club.png");
        grassSp=ImageLoader.loadImage("/textures/grass.png");
        blockadeSp=ImageLoader.loadImage("/textures/blockade.png");
        blockKitSp=ImageLoader.loadImage("/textures/block_kit.png");
        baseSp= ImageLoader.loadImage("/textures/base.png");
        waterSp= ImageLoader.loadImage("/textures/water.png");
        spikeSp=ImageLoader.loadImage("/textures/spikes.png");
        treeSp=ImageLoader.loadImage("/textures/Tree.png");
        woodSp=ImageLoader.loadImage("/textures/wood.png");
        dirtSp=ImageLoader.loadImage("/textures/dirt.png");
        saplingSp=ImageLoader.loadImage("/textures/sapling.png");
        clayRockSp=ImageLoader.loadImage("/textures/clayRock.png");
        pathSp=ImageLoader.loadImage("/textures/path.png");
        nutSp=ImageLoader.loadImage("/textures/nut.png");
        lightning0=ImageLoader.loadImage("/textures/lightning0.png");
        lightning1=ImageLoader.loadImage("/textures/lightning1.png");
        titleBG=ImageLoader.loadImage("/textures/Title.png");
        goalSp=ImageLoader.loadImage("/textures/goal.png");
        CwcSp=ImageLoader.loadImage("/textures/CWC.png");
        lairSp=ImageLoader.loadImage("/textures/lair.png");
        biterSp=ImageLoader.loadImage("/textures/biter.png");
        howToPlayBg=ImageLoader.loadImage("/textures/HowToPlay.png");
        menuBG=ImageLoader.loadImage("/textures/MenuBG.png");
        closeDoorSp= ImageLoader.loadImage("/textures/lockedDoor.png");
        openDoorSp = ImageLoader.loadImage("/textures/openDoor.png");
        keySp = ImageLoader.loadImage("/textures/keycard.png");
        facilFloor= ImageLoader.loadImage("/textures/facilFloor.png");
        facilWall= ImageLoader.loadImage("/textures/facilWall.png");
        facilTable= ImageLoader.loadImage("/textures/facilTable.png");
        facilAcid= ImageLoader.loadImage("/textures/facilAcid.png");


        mainTheme="/audio/StreetFood2.wav";
        otherTheme="/audio/Sweaty.wav";
        eldrich="/audio/maro.wav";
        plink="/audio/plink.wav";
        summon="/audio/summon.wav";
        save="/audio/save.wav";


}
}
