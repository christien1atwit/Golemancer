package dev.entities;

import dev.Game;
import dev.entities.Items.Item;
import dev.entities.Items.healing.Clay;
import dev.entities.obstacles.Obstacle;
import dev.gfx.Assets;
import dev.gfx.Camera;
import dev.scriptingsys.Script;
import dev.sfx.SoundPlayer;
import dev.states.GameState;
import dev.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Golem {
    /*
    The Golem class is the class of a Golem unit. The name, stats, and orders given to it will be decided by the user.

     */



    public String name;

    public int healthMax=20;
    public int health=10;
    public int initiative;
    public int strength=2;
    public int fight=strength*5;

    private boolean makingPath=false;
    private int pathNum=0;
    private int pathId=Path.numPaths;
    private int onPathID=-1;
    private boolean atEndPath=false;

    public Item heldItem;
    private SoundPlayer soundPlayer;

    public int targetDir=0;//0-north 1-east 2-south 3-west
    public boolean alive=true;
    public int aniFrame=0;//Determines which sprite to use when being rendered
    public int gridX,gridY=0;//The X/Y position of the Golem on the grid of tiles
    public Script defaultOrders;//The default Script the Golem will revert back to once any special commands are finished.
    public Script useOrders;//The Script the Golem is currently using.
    public int aniTimer=30;//Used to time the animation of the Golem.
    BufferedImage[] sprites=new BufferedImage[]{Assets.golemN0,Assets.golemN1,Assets.golemDed};//Stores all of the sprites of the Golem.

    public static final String hStr="Health", thStr="Mhealth", initStr="Initiative";

    public static final String[] RandomNames={"Golo","Jimmy","Mario","Rango","R-7038","Golio","Paul","George","John","Taro","Yoko","Ringo",
    "Jonathan","Spike","Issac","Boop","Eustace","Patrick","Clunk","Sniffy","Ingred","Joanna","Marsha","Diane","NullPointerException",
    "Z80","Robit","Jenny","Ivan","Cossack","Admiral","DJ","Erik","Ethan","Grant", "Dom", "Dawson", "Nick", "Ryan","Erik", "Noah", "Luigi",
            "Benito", "Idi", "Kim", "Clair", "Shandon", "Costner", "Micheal", "Bruno","Elias","Jayden","Sammy","Nate","Sadie","Sarah","The Boss",
    "Commander", "Caliph", "Hamood", "Mohammad", "Carl", "Sheen"};
    public static final int maxNames=RandomNames.length;

    public Golem(Script script, String name) {
        /*
        This constructor is used when the golem is given a script from some other object.
         */
        this.defaultOrders=script;
        this.useOrders=this.defaultOrders;
        this.name=name;
        this.soundPlayer=new SoundPlayer();

    }
    public Golem(String scName,String name){
        /*
        This constructor is used when the golem is given a script from a file.
         */
        this.defaultOrders=new Script(scName);
        this.useOrders=this.defaultOrders;
        this.name=name;
        this.soundPlayer=new SoundPlayer();

    }
    public Golem(int x, int y){
        /*
        This constructor is used when the golem is given a script from a file.
         */
        name();
        this.gridX=x;
        this.gridY=y;
        this.soundPlayer=new SoundPlayer();
    }
    public Golem(int x, int y,String name,int health, int strength, int fight, Item givItem){
        this.gridX=x;
        this.gridY=y;
        this.name=name;
        this.health=health;
        this.strength=strength;
        this.fight=fight;
        this.heldItem=givItem;
    }


    public void doOrder(){//The Golem reads from its current Script and then executes that order.
        boolean conditional=false;
        for(Obstacle obs: GameState.currentRoom.myObs){
            if(obs.gridX==gridX && obs.gridY==gridY){
                obs.action(this);
            }
        }

        if(health<=0){
            death();
        }else if(useOrders!=null) {
            String useCom = useOrders.read();
            if (useCom.equals("moveUp")) {
                this.moveUp();
            }
            else if (useCom.equals("moveDown")) {
                this.moveDown();
            }
            else if (useCom.equals("moveLeft")) {
                this.moveLeft();
            }
            else if (useCom.equals("moveRight")) {
                this.moveRight();
            }
            else if (useCom.contains("assess")) {
                this.assess(useCom);
                //doOrder();
                conditional=true;

            }
            else if(useCom.equals("pickUp")){
                this.pickUp();
            }
            else if(useCom.equals("useItem")){
                this.useItem();
            }
            else if(useCom.equals("dropItem")){
                this.dropItem();
            }
            else if(useCom.equals("selfDestruct")){
                this.death();
            }
            else if(useCom.equals("attack")){
                this.attack();
            }
            else if(useCom.contains("jumpTo")){
                this.jumpTo(useCom);
                conditional=true;
            }
            else if(useCom.equals("startLayPath")){
                this.startLayPath();
            }
            else if(useCom.equals("endLayPath")){
                this.endLayPath();
            }
            else if(useCom.equals("follow")){
                this.follow();
            }
            else if(useCom.equals("backFollow")){
                this.backFollow();
            }
            else if(useCom.contains("onPath")){
                onPath(useCom);
                conditional=true;
            }
            else if(useCom.equals("faceClk")){
                this.faceClk();
            }
            else if(useCom.equals("faceCclk")){
                this.faceCclk();
            }
            else if(useCom.equals("faceN")){
                this.faceN();
            }
            else if(useCom.equals("faceE")){
                this.faceE();
            }
            else if(useCom.equals("faceS")){
                this.faceS();
            }
            else if(useCom.equals("faceW")){
                this.faceW();
            }
            else if(useCom.equals("orientToPath")){
                this.orientToPath();
            }
            else if(useCom.equals("moveForward")){
                this.moveForward();
            }
            else if(useCom.equals("moveBackward")){
                this.moveBackward();
            }
            else if(useCom.equals("senseItem")){
                this.senseItem();
            }

        }else{
            System.out.println(name+": I dont have script");
        }
        if(conditional){
            doOrder();
        }


        //System.out.println(useCom);//Testing Print statement

    }
    public void death(){
        health=0;
        alive=false;
        if(emptySpace()){
            GameState.currentRoom.myItems.add(new Clay(gridX,gridY));
            soundPlayer.play(false, Assets.eldrich);
        }

    }

    public void attack(){//Implemented, Documented
        //add attack method
        for(int i=0;i<GameState.currentRoom.myObs.size();i++){
            switch(targetDir){
                case 0:
                    if(gridX==GameState.currentRoom.myObs.get(i).gridX && gridY-1==GameState.currentRoom.myObs.get(i).gridY){
                        GameState.currentRoom.myObs.get(i).shatter(fight);
                    }
                    break;
                case 1:
                    if(gridX+1==GameState.currentRoom.myObs.get(i).gridX && gridY==GameState.currentRoom.myObs.get(i).gridY){
                        GameState.currentRoom.myObs.get(i).shatter(fight);
                    }

                    break;
                case 2:
                    if(gridX==GameState.currentRoom.myObs.get(i).gridX && gridY+1==GameState.currentRoom.myObs.get(i).gridY){
                        GameState.currentRoom.myObs.get(i).shatter(fight);
                    }
                    break;
                case 3:
                    if(gridX-1==GameState.currentRoom.myObs.get(i).gridX && gridY==GameState.currentRoom.myObs.get(i).gridY){
                        GameState.currentRoom.myObs.get(i).shatter(fight);
                    }
                    break;
            }

        }
        for(int i=0;i<GameState.currentRoom.myEnemys.size();i++){
            switch(targetDir){
                case 0:
                    if(gridX==GameState.currentRoom.myEnemys.get(i).gridX && gridY-1==GameState.currentRoom.myEnemys.get(i).gridY){
                        GameState.currentRoom.myEnemys.get(i).damage(fight);
                    }
                    break;
                case 1:
                    if(gridX+1==GameState.currentRoom.myEnemys.get(i).gridX && gridY==GameState.currentRoom.myEnemys.get(i).gridY){
                        GameState.currentRoom.myEnemys.get(i).damage(fight);
                    }

                    break;
                case 2:
                    if(gridX==GameState.currentRoom.myEnemys.get(i).gridX && gridY+1==GameState.currentRoom.myEnemys.get(i).gridY){
                        GameState.currentRoom.myEnemys.get(i).damage(fight);
                    }
                    break;
                case 3:
                    if(gridX-1==GameState.currentRoom.myEnemys.get(i).gridX && gridY==GameState.currentRoom.myEnemys.get(i).gridY){
                        GameState.currentRoom.myEnemys.get(i).damage(fight);
                    }
                    break;
            }

        }
    }

    public void moveRight(){ //Implemented, Documented
        boolean canMove=false;
        for(Tile i: GameState.currentRoom.myTiles){
            if((i.tileX==gridX+1)&&(i.tileY==gridY)){
                canMove=i.canPass;
            }
        }
        for(Obstacle i: GameState.currentRoom.myObs){
            if((i.gridX==gridX+1)&&(i.gridY==gridY)){
                canMove=i.canPass;
            }
        }
        if(canMove){
            gridX+=1;
            if(makingPath){
                GameState.currentRoom.myPaths.add(new Path(gridX,gridY,pathNum,pathId));
                pathNum++;
            }
        }
    }

    public void moveLeft(){//Implemented, Documented
        boolean canMove=false;
        for(Tile i: GameState.currentRoom.myTiles){
            if((i.tileX==gridX-1)&&(i.tileY==gridY)){
                canMove=i.canPass;
            }
        }
        for(Obstacle i: GameState.currentRoom.myObs){
            if((i.gridX==gridX-1)&&(i.gridY==gridY)){
                canMove=i.canPass;
            }
        }
        if(canMove){
            gridX-=1;
            if(makingPath){
                GameState.currentRoom.myPaths.add(new Path(gridX,gridY,pathNum,pathId));
                pathNum++;
            }
        }
    }

    public void moveUp(){//Implemented, Documented
        boolean canMove=false;
        for(Tile i: GameState.currentRoom.myTiles){
            if((i.tileX==gridX)&&(i.tileY==gridY-1)){
                canMove=i.canPass;
            }
        }
        for(Obstacle i: GameState.currentRoom.myObs){
            if((i.gridX==gridX)&&(i.gridY==gridY-1)){
                canMove=i.canPass;
            }
        }
        if(canMove){
            gridY-=1;

            if(makingPath){
                GameState.currentRoom.myPaths.add(new Path(gridX,gridY,pathNum,pathId));
                pathNum++;
            }
        }
    }

    public void moveDown(){//Implemented, Documented
        boolean canMove=false;
        for(Tile i: GameState.currentRoom.myTiles){
            if((i.tileX==gridX)&&(i.tileY==gridY+1)){
                canMove=i.canPass;
            }
        }
        for(Obstacle i: GameState.currentRoom.myObs){
            if((i.gridX==gridX)&&(i.gridY==gridY+1)){
                canMove=i.canPass;
            }
        }
        if(canMove){
            gridY+=1;
            if(makingPath){
                GameState.currentRoom.myPaths.add(new Path(gridX,gridY,pathNum,pathId));
                pathNum++;
            }
        }
    }

    public void assess(String useCom){//The Golem checks one of its statistics and if a certain condition is met, the Golem goes to a new Label. //Implemented, Documented
        useCom=useCom.substring(6);//Cuts the 'assess' off of the command.
        if(useCom.contains("Health")){
            useCom=useCom.substring(6);//Cuts 'Health' off of the command.
            if((float)(health/healthMax)<0.9){//Checks if it is below 90% of its maximum health.
                //System.out.println(useCom);
                useOrders.goToLabel(useCom);//Changes the current Label of the Golem's current Script.
                doOrder();
            }
        }


    }

    

    public void pickUp(){//Implemented, Documented
        for(int i=0;i<GameState.currentRoom.myItems.size();i++){
            if(GameState.currentRoom.myItems.get(i).gridX==gridX && GameState.currentRoom.myItems.get(i).gridY==gridY){
                if(strength>=GameState.currentRoom.myItems.get(i).weight){
                    heldItem=GameState.currentRoom.myItems.get(i);
                    GameState.currentRoom.myItems.remove(heldItem);
                    i=GameState.currentRoom.myItems.size();
                    //System.out.println(heldItem.name);
                }
            }
        }

    }

    public void useItem(){//Implemented, Documented
        if(heldItem!=null){
            heldItem.use(this);
            if(heldItem.consumable){
                heldItem=null;
            }

        }
    }

    public void dropItem(){//Implemented, Documented
        if(heldItem!=null){
            boolean emptySpace=true;
            for(int i=0;i<GameState.currentRoom.myItems.size();i++){
                if(GameState.currentRoom.myItems.get(i).gridX==gridX && GameState.currentRoom.myItems.get(i).gridY==gridY){
                    emptySpace=false;
                    i=GameState.currentRoom.myItems.size();
                }
            }
            if(emptySpace) {
                heldItem.gridX = gridX;
                heldItem.gridY = gridY;
                GameState.currentRoom.myItems.add(heldItem);
                heldItem = null;
            }
        }
    }

    public void startLayPath(){//Implemented, Documented
        //starts laying a path where it walks
        makingPath=true;
        pathNum=0;
        pathId=Path.numPaths;
        GameState.currentRoom.myPaths.add(new Path(gridX,gridY,pathNum,pathId));
        pathNum++;

    }
    public void endLayPath(){//Implemented, Documented
        //ends laying a path
        makingPath=false;
        pathId=Path.numPaths;

    }
    public void follow(){//Implemented, Documented
        //follows path it is on Positively
        Path currentPath;
        boolean foundPath=false;
        for(int i=0;i<GameState.currentRoom.myPaths.size();i++){
            currentPath=GameState.currentRoom.myPaths.get(i);
            if(currentPath.x==gridX && currentPath.y==gridY){
                if(onPathID==-1){
                    onPathID= currentPath.pathID;
                    atEndPath=false;
                }
                for(int x=0;x<GameState.currentRoom.myPaths.size();x++){
                    if(currentPath.pathID==GameState.currentRoom.myPaths.get(x).pathID && currentPath.order+1==GameState.currentRoom.myPaths.get(x).order){
                        gridX=GameState.currentRoom.myPaths.get(x).x;
                        gridY=GameState.currentRoom.myPaths.get(x).y;
                        foundPath=true;
                        atEndPath=false;
                        x=GameState.currentRoom.myPaths.size()+1;
                        i=GameState.currentRoom.myPaths.size()+1;
                    }else if(x==GameState.currentRoom.myPaths.size()-1){
                        atEndPath=true;
                    }
                }
            }


        }
        if(!foundPath){
            onPathID=-1;
        }

    }
    public void backFollow(){//Implemented, Documented
        //follows path it is on Negatively
        Path currentPath;
        boolean foundPath=false;
        for(int i=0;i<GameState.currentRoom.myPaths.size();i++){
            currentPath=GameState.currentRoom.myPaths.get(i);
            if(currentPath.x==gridX && currentPath.y==gridY){
                if(onPathID==-1){
                    onPathID= currentPath.pathID;
                    atEndPath=false;
                }
                for(int x=0;x<GameState.currentRoom.myPaths.size();x++){
                    if(currentPath.pathID==GameState.currentRoom.myPaths.get(x).pathID && currentPath.order-1==GameState.currentRoom.myPaths.get(x).order){
                        gridX=GameState.currentRoom.myPaths.get(x).x;
                        gridY=GameState.currentRoom.myPaths.get(x).y;
                        foundPath=true;
                        atEndPath=false;
                        x=GameState.currentRoom.myPaths.size()+1;
                        i=GameState.currentRoom.myPaths.size()+1;
                    }else if(x==GameState.currentRoom.myPaths.size()-1){
                        atEndPath=true;

                    }
                }
            }


        }
        if(!foundPath){
            onPathID=-1;
        }
        //System.out.println(atEndPath);
    }

    public void jumpTo(String useCom){//Implemented, Documented
        //jumps to label
        useOrders.goToLabel(useCom);


    }
    public void onPath(String useCom) {//Implemented, Documented
        //Jumps if is on a path
        useCom = useCom.substring(6);
        Path currentPath;
        if (!atEndPath){
            for (int i = 0; i < GameState.currentRoom.myPaths.size(); i++) {
                currentPath = GameState.currentRoom.myPaths.get(i);
                if (currentPath.x == gridX && currentPath.y == gridY) {
                    useOrders.goToLabel(useCom);

                }
            }
    }else{
            atEndPath=false;
        }
        //System.out.println(atEndPath);
    }

    public void faceClk(){//Implemented, Documented
        switch(targetDir){
            case 3:
                targetDir=0;
                break;
            default:
                targetDir++;
        }
    }
    public void faceCclk(){//Implemented, Documented
        switch (targetDir){
            case 0:
                targetDir=3;
                break;
            default:
                targetDir--;
        }
    }
    public void faceN(){//Implemented, Documented
        targetDir=0;
    }
    public void faceS(){//Implemented, Documented
        targetDir=2;
    }
    public void faceE(){//Implemented, Documented
        targetDir=1;
    }
    public void faceW(){//Implemented, Documented
        targetDir=3;
    }

    public void orientToPath(){//doc'd impl'd
        int posX=1,posY=1,negX=1,negY=1;
        boolean endPoint=false;
        ArrayList<Tile> searchTiles=GameState.currentRoom.myTiles;
        ArrayList<Path> searchPaths=GameState.currentRoom.myPaths;
        if(searchPaths!=null){
            while(!endPoint) {
                for (int i = 0; i < searchTiles.size(); i++) {//posX
                    if (searchTiles.get(i).tileX == gridX + posX && searchTiles.get(i).tileY == gridY) {
                        if (searchTiles.get(i).canPass) {
                            for (int p = 0; p < searchPaths.size(); p++) {
                                if (searchPaths.get(p).x == gridX + posX && searchPaths.get(p).y == gridY) {
                                    endPoint = true;
                                }
                            }
                            if (!endPoint) {
                                posX++;
                            }
                        } else {
                            endPoint = true;
                            posX = searchTiles.size();
                        }
                    }
                }
            }
            endPoint=false;
            while(!endPoint) {
                for (int i = 0; i < searchTiles.size(); i++) {//negX
                    if (searchTiles.get(i).tileX == gridX - negX && searchTiles.get(i).tileY == gridY) {
                        if (searchTiles.get(i).canPass) {
                            for (int p = 0; p < searchPaths.size(); p++) {
                                if (searchPaths.get(p).x == gridX + negX && searchPaths.get(p).y == gridY) {
                                    endPoint = true;
                                }
                            }
                            if (!endPoint) {
                                negX++;
                            }
                        } else {
                            endPoint = true;
                            negX = searchTiles.size();
                        }
                    }
                }
            }
            endPoint=false;
            while (!endPoint) {
                for (int i = 0; i < searchTiles.size(); i++) {//posY
                    if (searchTiles.get(i).tileX == gridX && searchTiles.get(i).tileY == gridY + posY) {
                        if (searchTiles.get(i).canPass) {
                            for (int p = 0; p < searchPaths.size(); p++) {
                                if (searchPaths.get(p).x == gridX && searchPaths.get(p).y == gridY + posY) {
                                    endPoint = true;
                                }
                            }
                            if (!endPoint) {
                                posY++;
                            }
                        } else {
                            endPoint = true;
                            posY = searchTiles.size();
                        }
                    }
                }
            }
            endPoint=false;
            while(!endPoint){
                for(int i=0;i<searchTiles.size();i++){//negY
                    if(searchTiles.get(i).tileX==gridX && searchTiles.get(i).tileY==gridY-negY){
                        if(searchTiles.get(i).canPass){
                            for(int p=0;p<searchPaths.size();p++){
                                if(searchPaths.get(p).x==gridX && searchPaths.get(p).y==gridY-negY){
                                    endPoint=true;
                                }
                            }
                            if(!endPoint){
                                negY++;
                            }
                        }else{
                            endPoint=true;
                            negY=searchTiles.size();
                        }
                    }
                }
            }
            if(posX<posY && posX<negX && posX<negY){//put a break point here
                targetDir=1;
            }else if(posY<posX && posY<negX && posY<negY){
                targetDir=2;
            }else if(negY<posY && negY<negX && negY<posX){
                targetDir=0;
            }else if(negX<posY && negX<negY && negX<posX){
                targetDir=3;
            }else{
                targetDir=0;
            }
        }

    }

    public void moveForward(){
        switch (targetDir){
            case 0:
                moveUp();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveLeft();
                break;
            default:
                System.out.println("targetDir is "+targetDir);
                break;
        }
    }
    public void moveBackward(){
        switch (targetDir){
            case 0:
                moveDown();
                break;
            case 1:
                moveLeft();
                break;
            case 2:
                moveUp();
                break;
            case 3:
                moveRight();
                break;
            default:
                System.out.println("targetDir is "+targetDir);
                break;
        }
    }

    public void render(Graphics g){
        int xLocation=gridX*30;//calculates where the Golem's exact pixel-X position is.
        int yLocation=gridY*30;//calculates where the Golem's exact pixel-Y position is.
        aniTimer--;
        if(aniTimer==0){//Changes the sprite to be used when the animation timer reaches 0.
            aniTimer=30;
            if(aniFrame==0){
                aniFrame=1;
            }else if(aniFrame==1){
                aniFrame=0;
            }
        }
        if(((xLocation- Camera.camX)<Game.width && (xLocation- Camera.camX)>-30)&&((yLocation- Camera.camY)<Game.height && (yLocation- Camera.camY)>-30)) {
            g.drawImage(sprites[aniFrame], (xLocation - Camera.camX), (yLocation - Camera.camY), null);
        }
    }

    public void pathTo(){
        //make it take object and set goal x y;
        int goalX=0, goalY=0;
        ArrayList<int[]> pathX=new ArrayList<int[]>(), pathY=new ArrayList<int[]>();
        int currentX=gridX, currentY=gridY;
        while(currentX!=goalX &&currentY!=goalY){
            //pathX
            if(goalX>currentX){
                //Try to increase currentX
                if(GameState.currentRoom.getTile(currentX+1,currentY).canPass &&(GameState.currentRoom.getObs(currentX+1, currentY).canPass ||GameState.currentRoom.getObs(currentX+1, currentY)==null )){
                    //If it can move towards add it to the path
                    currentX++;
                    pathX.add(new int[]{currentX, currentY});
                }else {

                }
            }
        }

    }

    public void senseItem(){
        for(int i=0;i<8;i++){
            switch (i){
                case 0:
                    if(GameState.currentRoom.getItem(gridX,gridY+1)!=null){
                        gridY+=1;
                        i=9;
                    }
                    break;
                case 1:
                    if(GameState.currentRoom.getItem(gridX,gridY-1)!=null){
                        gridY-=1;
                        i=9;
                    }
                    break;
                case 2:
                    if(GameState.currentRoom.getItem(gridX+1,gridY)!=null){
                        gridX+=1;
                        i=9;
                    }
                    break;
                case 3:
                    if(GameState.currentRoom.getItem(gridX-1,gridY)!=null){
                        gridX-=1;
                        i=9;
                    }
                    break;
                case 4:
                    if(GameState.currentRoom.getItem(gridX+1,gridY+1)!=null){
                        gridX+=1;
                        gridY+=1;
                        i=9;
                    }
                    break;
                case 5:
                    if(GameState.currentRoom.getItem(gridX+1,gridY-1)!=null){
                        gridX+=1;
                        gridY-=1;
                        i=9;
                    }
                    break;
                case 6:
                    if(GameState.currentRoom.getItem(gridX-1,gridY-1)!=null){
                        gridX-=1;
                        gridY-=1;
                        i=9;
                    }
                    break;
                case 7:
                    if(GameState.currentRoom.getItem(gridX-1,gridY+1)!=null){
                        gridX-=1;
                        gridY+=1;
                        i=9;
                    }
                    break;
                default:
                    System.out.println("?");
            }

        }
    }

    private boolean emptySpace(){
        boolean emptySpace=true;
        for(int i=0;i<GameState.currentRoom.myItems.size();i++){
            if(GameState.currentRoom.myItems.get(i).gridX==gridX && GameState.currentRoom.myItems.get(i).gridY==gridY){
                emptySpace=false;
                i=GameState.currentRoom.myItems.size();
            }

        }
        return emptySpace;
    }

    public void name(){
        Random rand = new Random();
        int randomIndex= rand.nextInt(Golem.maxNames);
        int numberInTotal=0;
        name= Golem.RandomNames[randomIndex];
        if(GameState.player!=null) {
            for (int i = 0; i < GameState.player.golemTeam.size(); i++) {
                if (GameState.player.golemTeam.get(i).name.contains(name)) {
                    numberInTotal++;
                }
            }

            if (numberInTotal > 1) {
                name = name + String.valueOf(numberInTotal);
            }
        }
    }
}
