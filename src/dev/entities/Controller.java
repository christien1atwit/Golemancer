package dev.entities;

import dev.Game;
import dev.entities.Items.Generic.KeyCard;
import dev.entities.Items.Generic.Wood;
import dev.entities.Items.Item;
import dev.entities.Items.Weapon.Club;
import dev.entities.Items.building.BlockKit;
import dev.entities.Items.building.Nut;
import dev.entities.Items.healing.Clay;
import dev.entities.obstacles.*;
import dev.gfx.Assets;
import dev.gfx.Camera;
import dev.gfx.SaveWindow;
import dev.scriptingsys.Script;
import dev.sfx.SoundPlayer;
import dev.states.GameState;
import dev.tiles.*;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Controller {
    private int COMMAND_COOL=10;
    private int activeComCool=0;
    private int CURSOR_COOL=10,actCursorCool=CURSOR_COOL;
    private int CAMERA_COOL=2,activeCamCool=CAMERA_COOL;

    private int CHANGE_COOL=20,actChangeCool=CHANGE_COOL;
    public Golem userGolem;
    public ArrayList<Golem> golemTeam= new ArrayList<Golem>();
    private Game game;
    private int cursorX,cursorY;
    private SoundPlayer soundPlayer=new SoundPlayer();
    //Maker mode Attributes
    private boolean makeMode=false;
    private Tile currentTile;
    private int ID_MAX=22;
    private int id=0;
    private ArrayList<Tile> placedTiles=new ArrayList<Tile>();
    private ArrayList<Obstacle> placedObs=new ArrayList<Obstacle>();
    private ArrayList<Item> placeItm=new ArrayList<Item>();
    private Obstacle currentObs;
    private Item currentItm;

    private boolean menuOpen=false;
    private boolean helpOpen=true;

    public SaveWindow savWin;

    private Golem selectGolem;
    private Item selectItem;
    private Tile selectedTile;

    public Controller(Game game, Golem golem, boolean makerMode){//this is used if a level is to be created.
        this.game=game;
        this.userGolem=golem;
        this.cursorX=0;
        this.cursorY=0;
        this.makeMode=makerMode;
        this.currentTile=new brickFloor(cursorX,cursorY, game.camera);
    }
    public Controller(Game game, Golem golem){
        this.game=game;
        this.golemTeam.add(golem);
        this.cursorX=golemTeam.get(0).gridX;
        this.cursorY=golemTeam.get(0).gridY;
    }
    public Controller(Game game){
        this.game=game;
        if(GameState.currentRoom.loadedGols.size()>0){
            this.golemTeam=GameState.currentRoom.loadedGols;
        }else{
            this.golemTeam.add(new Golem(GameState.currentRoom.startingGol[0],GameState.currentRoom.startingGol[1]));
        }
        this.cursorX=golemTeam.get(0).gridX;
        this.cursorY=golemTeam.get(0).gridY;
        game.camera.setCam( (cursorX * 30) - Game.width/2,  (cursorY * 30) - Game.height/2);
    }
    public Controller(Game game, boolean make){
        this.game=game;
        //this.golemTeam.add(new Golem(GameState.currentRoom.startingGol[0],GameState.currentRoom.startingGol[1]));
        this.cursorX=0;
        this.cursorY=0;
        this.makeMode=make;
        game.camera.setCam( (cursorX * 30) - Game.width/2,  (cursorY * 30) - Game.height/2);
    }

    public void tick(){
        //Makes the user's Golem advance through its Script.
        game.getKeyManager().setEnableType(false);
        if(!menuOpen) {
            if(!makeMode) {
                if ((game.getKeyManager().attack) && (activeComCool == COMMAND_COOL)) {
                    activeComCool = 0;
                    for(int i=0;i<golemTeam.size();i++){
                        golemTeam.get(i).doOrder();
                    }
                    GameState.currentRoom.turnTick();
                }
                for(int golNum=0;golNum<golemTeam.size();golNum++){
                    if((cursorX==golemTeam.get(golNum).gridX)&&(cursorY==golemTeam.get(golNum).gridY)){
                        selectGolem=golemTeam.get(golNum);
                        golNum=golemTeam.size();
                    }else{
                        selectGolem=null;
                    }
                }

                for(int i=0;i< GameState.currentRoom.myItems.size();i++){
                    if((cursorX==GameState.currentRoom.myItems.get(i).gridX)&&(cursorY==GameState.currentRoom.myItems.get(i).gridY)){
                        selectItem=GameState.currentRoom.myItems.get(i);
                        i=GameState.currentRoom.myItems.size();
                    }else{
                        selectItem=null;
                    }
                }

                for(int i=0;i<GameState.currentRoom.myTiles.size();i++){
                    if(GameState.currentRoom.myTiles.get(i).getClass()==Base.class){
                        if(GameState.currentRoom.myTiles.get(i).tileX==cursorX && GameState.currentRoom.myTiles.get(i).tileY==cursorY){
                            selectedTile=GameState.currentRoom.myTiles.get(i);
                            i=GameState.currentRoom.myTiles.size();
                        }else{
                            selectedTile=null;
                        }
                    }
                }
            }

                //Open Golem Menu
                if (selectGolem != null) {
                    if ((game.getKeyManager().interact) && (activeComCool == COMMAND_COOL)) {
                        activeComCool = 0;
                        menuOpen = true;
                        DropMenu.init(selectGolem);
                    }
                }else if(selectedTile != null){
                    if ((game.getKeyManager().interact) && (activeComCool == COMMAND_COOL)) {
                        activeComCool = 0;
                        menuOpen = true;
                        DropMenu.init(selectedTile);
                    }
                }

                if((game.getKeyManager().zero)&&(activeComCool == COMMAND_COOL)&&!makeMode){
                    activeComCool=0;
                    actChangeCool=0;
                    actCursorCool=0;
                    activeCamCool=0;
                    savWin=new SaveWindow(500,500,game);
                }
                //Moving the Cursor around
                if ((game.getKeyManager().up) && (actCursorCool == CURSOR_COOL)) {
                    actCursorCool = 0;
                    cursorY += -1;
                    soundPlayer.play(false, Assets.plink);
                    //System.out.println("Y: "+cursorY);
                }
                if ((game.getKeyManager().down) && (actCursorCool == CURSOR_COOL)) {
                    actCursorCool = 0;
                    cursorY += 1;
                    soundPlayer.play(false, Assets.plink);
                    //System.out.println("Y: "+cursorY);
                }
                if ((game.getKeyManager().left) && (actCursorCool == CURSOR_COOL)) {
                    actCursorCool = 0;
                    cursorX += -1;
                    soundPlayer.play(false, Assets.plink);
                    //System.out.println("X: "+cursorX);
                }
                if ((game.getKeyManager().right) && (actCursorCool == CURSOR_COOL)) {
                    actCursorCool = 0;
                    cursorX += 1;
                    soundPlayer.play(false, Assets.plink);
                    //System.out.println("X: "+cursorX);
                }
                if (makeMode) {
                    makerActions();
                }
        }else {
            DropMenu.tick(game);
            if(DropMenu.exiting){
                menuOpen=false;
                activeComCool=-3;
            }
        }
        //Controls for moving the camera.
        if ((game.getKeyManager().save) && (actCursorCool == CURSOR_COOL)) {
            actCursorCool = 0;
            game.camera.setCam( (cursorX * 30) - Game.width/2,  (cursorY * 30) - Game.height/2);
        }
        if ((game.getKeyManager().sUp) && (activeCamCool == CAMERA_COOL)) {
            activeCamCool = 0;
            game.camera.changeCam(0, -10);
        }
        if ((game.getKeyManager().sDown) && (activeCamCool == CAMERA_COOL)) {
            activeCamCool = 0;
            game.camera.changeCam(0, 10);
        }
        if ((game.getKeyManager().sLeft) && (activeCamCool == CAMERA_COOL)) {
            activeCamCool = 0;
            game.camera.changeCam(-10, 0);
        }
        if ((game.getKeyManager().sRight) && (activeCamCool == CAMERA_COOL)) {
            activeCamCool = 0;
            game.camera.changeCam(10, 0);
        }
        if((game.getKeyManager().enter)&&(actChangeCool==CHANGE_COOL)){
            actChangeCool=0;
            GameState.labelMake=true;
        }
        if((game.getKeyManager().help)&&(activeComCool==COMMAND_COOL)){
            activeComCool=0;
            if(helpOpen){
                helpOpen=false;
            }else{
                helpOpen=true;
            }
        }
        if((game.getKeyManager().p)&&(activeComCool==COMMAND_COOL)){
            activeComCool=0;
            GameState.currentRoom.togglePaths();
        }


        //Recharging Cooldowns
        if(activeComCool<COMMAND_COOL){
            activeComCool++;
        }
        if(activeCamCool<CAMERA_COOL){
            activeCamCool++;
        }
        if(actCursorCool<CURSOR_COOL){
            actCursorCool++;
        }
        if(actChangeCool<CHANGE_COOL){
            actChangeCool++;
        }
        ArrayList<Integer> removeIndex=new ArrayList<Integer>();
        for(int i=golemTeam.size()-1;i>=0;i--){
            if(!golemTeam.get(i).alive){
                removeIndex.add(i);
                if(golemTeam.get(i).equals(selectGolem)){
                    selectGolem=null;
                }
            }
        }
        for(Integer i:removeIndex){
            //System.out.println("HERE");
            golemTeam.remove((int)i);
        }

    }

    public void makerActions(){
        /*
        This method contains all of the logic regarding to Maker Mode.
         */
        boolean onTile=false;
        if(currentTile!=null){
            this.currentTile.tileX=cursorX;
            this.currentTile.tileY=cursorY;
            currentTile.tick();// used to update the render position of the tile
        }else if(currentObs!=null){
            this.currentObs.gridX=cursorX;
            this.currentObs.gridY=cursorY;

        }else if(currentItm!=null){
            this.currentItm.gridX=cursorX;
            this.currentItm.gridY=cursorY;
        }

        if(placedTiles!=null){
            //Checks if the cursor is on top of a placed Tile
            for(Tile i:placedTiles){
                //System.out.println("x: "+i.tileX+" y: "+i.tileY);
                if((cursorX==i.tileX)&&(cursorY==i.tileY)){
                    onTile=true;
                    //System.out.println("OnTile");
                }

            }
        }
        //Placing, Changing, Erasing
        if ((game.getKeyManager().attack) && (activeComCool == COMMAND_COOL)) {
            activeComCool = 0;
            if (!onTile) {
                switch (id){
                    case 0:
                        placedTiles.add(new brickFloor(currentTile.tileX, currentTile.tileY, game.camera));
                        break;
                    case 1:
                        placedTiles.add(new StoneTile(currentTile.tileX, currentTile.tileY, game.camera));
                        break;
                    case 2:
                        placedTiles.add(new Grass(currentTile.tileX, currentTile.tileY, game.camera));
                        break;
                    case 3:
                        placedTiles.add(new Water(currentTile.tileX, currentTile.tileY, game.camera));
                        break;
                    case 4:
                        placedTiles.add(new Base(currentTile.tileX, currentTile.tileY, game.camera));
                        break;
                    case 12:
                        placedTiles.add(new Dirt(currentTile.tileX, currentTile.tileY, game.camera));
                        break;
                    case 13:
                        placedTiles.add(new GoalTile(currentTile.tileX,currentTile.tileY,game.camera));
                        break;
                    case 19:
                        placedTiles.add(new FacilityFloor(currentTile.tileX,currentTile.tileY, game.camera));
                        break;
                    case 20:
                        placedTiles.add(new FacilityWall(currentTile.tileX,currentTile.tileY, game.camera));
                        break;
                    default:
                        System.out.println("Tried to place invalid ID");

                }

                //System.out.println("placed");
            }else{
                switch (id){
                    case 5:
                        placedObs.add(new Blockade(currentObs.gridX, currentObs.gridY));
                        break;
                    case 6:
                        placedObs.add(new Spikes(currentObs.gridX,currentObs.gridY));
                        break;
                    case 7:
                        placedObs.add(new Tree(currentObs.gridX,currentObs.gridY));
                        break;
                    case 8:
                        placeItm.add(new BlockKit(currentItm.gridX, currentItm.gridY));
                        break;
                    case 9:
                        placeItm.add(new Wood(currentItm.gridX, currentItm.gridY));
                        break;
                    case 10:
                        placeItm.add(new Clay(currentItm.gridX, currentItm.gridY));
                        break;
                    case 11:
                        placeItm.add(new Club(currentItm.gridX, currentItm.gridY));
                        break;
                    case 14:
                        placedObs.add(new Lair(currentObs.gridX,currentObs.gridY));
                        break;
                    case 15:
                        placeItm.add(new Nut(currentItm.gridX,currentItm.gridY));
                        break;
                    case 16:
                        placedObs.add(new ClayRock(currentObs.gridX,currentObs.gridY));
                        break;
                    case 17:
                        placedObs.add(new LockedDoor(currentObs.gridX,currentObs.gridY));
                        break;
                    case 18:
                        placeItm.add(new KeyCard(currentItm.gridX,currentItm.gridY));
                        break;
                    case 21:
                        placedObs.add(new FacilityTable(currentObs.gridX, currentObs.gridY));
                        break;
                    case 22:
                        placedObs.add(new FacilityAcid(currentObs.gridX, currentObs.gridY));
                        break;


                    default:
                        System.out.println("Tried to place Tile on Tile");

                }
            }
        }
        if ((game.getKeyManager().interact) && (activeComCool == COMMAND_COOL)) {
                activeComCool = 0;
                if(id<ID_MAX){
                    id++;
                }else{
                    id=0;
                }
                switch (id){
                    case 0:
                        currentTile=new brickFloor(cursorX,cursorY, game.camera);
                        currentObs=null;
                        currentItm=null;
                        break;
                    case 1:
                        currentTile=new StoneTile(cursorX,cursorY, game.camera);
                        currentObs=null;
                        currentItm=null;
                        break;
                    case 2:
                        currentTile=new Grass(cursorX,cursorY, game.camera);
                        currentObs=null;
                        currentItm=null;
                        break;
                    case 3:
                        currentTile=new Water(cursorX,cursorY,game.camera);
                        currentObs=null;
                        currentItm=null;
                        break;
                    case 4:
                        currentTile=new Base(cursorX,cursorY,game.camera);
                        currentObs=null;
                        currentItm=null;
                        break;
                    case 5:
                        currentObs=new Blockade(cursorX,cursorY);
                        currentTile=null;
                        currentItm=null;
                        break;
                    case 6:
                        currentObs=new Spikes(cursorX, cursorY);
                        currentTile=null;
                        currentItm=null;
                        break;
                    case 7:
                        currentObs=new Tree(cursorX,cursorY);
                        currentTile=null;
                        currentItm=null;
                        break;
                    case 8:
                        currentItm=new BlockKit(cursorX,cursorY);
                        currentTile=null;
                        currentObs=null;
                        break;
                    case 9:
                        currentItm=new Wood(cursorX, cursorY);
                        currentTile=null;
                        currentObs=null;
                        break;
                    case 10:
                        currentItm=new Clay(cursorX, cursorY);
                        currentTile=null;
                        currentObs=null;
                        break;
                    case 11:
                        currentItm=new Club(cursorX,cursorY);
                        currentTile=null;
                        currentObs=null;
                        break;
                    case 12:
                        currentTile=new Dirt(cursorX,cursorY,game.camera);
                        currentItm=null;
                        currentObs=null;
                        break;
                    case 13:
                        currentTile=new GoalTile(cursorX,cursorY,game.camera);
                        currentItm=null;
                        currentObs=null;
                        break;
                    case 14:
                        currentObs=new Lair(cursorX, cursorY);
                        currentItm=null;
                        currentTile=null;
                        break;
                    case 15:
                        currentItm=new Nut(cursorX, cursorY);
                        currentTile=null;
                        currentObs=null;
                        break;
                    case 16:
                        currentObs= new ClayRock(cursorX, cursorY);
                        currentTile=null;
                        currentItm=null;
                        break;
                    case 17:
                        currentObs= new LockedDoor(cursorX, cursorY);
                        currentTile=null;
                        currentItm=null;
                        break;
                    case 18:
                        currentItm= new KeyCard(cursorX, cursorY);
                        currentObs=null;
                        currentTile=null;
                        break;
                    case 19:
                        currentTile=new FacilityFloor(cursorX,cursorY,game.camera);
                        currentObs=null;
                        currentItm=null;
                        break;
                    case 20:
                        currentTile= new FacilityWall(cursorX, cursorY, game.camera);
                        currentObs=null;
                        currentItm=null;
                        break;
                    case 21:
                        currentObs= new FacilityTable(cursorX,cursorY);
                        currentTile=null;
                        currentItm=null;
                        break;
                    case 22:
                        currentObs= new FacilityAcid(cursorX, cursorY);
                        currentTile=null;
                        currentItm=null;
                        break;
                    default:
                        System.out.println("ID not valid");
                }

            }
        if((game.getKeyManager().run)&&(activeComCool==COMMAND_COOL)){
            /*
            This is used to erase placed tiles.
            The toRemove variable is used to store what tile is to be removed cannot be used in iterating loop due
            to modifying it while it is being accessed for iteration.
             */
            activeComCool=0;
            if(placedTiles.size()!=0){
                Tile toRemove = null;
                for (Tile i:placedTiles){
                    if((i.tileX==cursorX)&&(i.tileY==cursorY)){
                        toRemove=i;
                    }
                }
                if(toRemove!=null){
                    placedTiles.remove(toRemove);
                }
            }
            if(placedObs.size()!=0){
                Obstacle toRemove = null;
                for (Obstacle i:placedObs){
                    if((i.gridX==cursorX)&&(i.gridY==cursorY)){
                        toRemove=i;
                    }
                }
                if(toRemove!=null){
                    placedObs.remove(toRemove);
                }
            }
            if(placeItm.size()!=0){
                Item toRemove = null;
                for (Item i:placeItm){
                    if((i.gridX==cursorX)&&(i.gridY==cursorY)){
                        toRemove=i;
                    }
                }
                if(toRemove!=null){
                    placeItm.remove(toRemove);
                }
            }
        }
        //Saving
        if((game.getKeyManager().zero)&&(activeComCool==COMMAND_COOL)){
            activeComCool=0;
            ArrayList<String> lines = new ArrayList<>();
            try {
                Files.deleteIfExists(Paths.get("newMap.gsinf"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            lines.add("name=newMap");
            lines.add("startingGol=0,0");
            lines.add("BGM=otherTheme");
            lines.add("mapInfo:");
            for(Tile i:placedTiles){
                //add more conditionals for new tiles.
                if(i.getClass()==brickFloor.class){
                    //new brickFloor(xloc,yloc,cam)
                    lines.add("0,"+i.tileX+","+i.tileY);
                }
                if(i.getClass()==StoneTile.class){
                    //new brickFloor(xloc,yloc,cam)
                    lines.add("1,"+i.tileX+","+i.tileY);
                }
                if(i.getClass()==Grass.class){
                    lines.add("2,"+i.tileX+","+i.tileY);
                }
                if(i.getClass()==Water.class){
                    lines.add("3,"+i.tileX+","+i.tileY);
                }
                if(i.getClass()==Base.class){
                    lines.add("4,"+i.tileX+","+i.tileY);
                }
                if(i.getClass()==Dirt.class){
                    lines.add("12,"+i.tileX+","+i.tileY);
                }
                if(i.getClass()==GoalTile.class){
                    lines.add("13,"+i.tileX+","+i.tileY);
                }
                if(i.getClass()==FacilityFloor.class){
                    lines.add("19,"+i.tileX+","+i.tileY);
                }
                if(i.getClass()==FacilityWall.class){
                    lines.add("20,"+i.tileX+","+i.tileY);
                }


            }
            for(Obstacle i:placedObs){
                if(i.getClass()==Blockade.class){
                    lines.add("5,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==Spikes.class){
                    lines.add("6,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==Tree.class){
                    lines.add("7,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==Lair.class){
                    lines.add("14,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==ClayRock.class){
                    lines.add("16,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==LockedDoor.class){
                    lines.add("17,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==FacilityTable.class){
                    lines.add("21,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==FacilityAcid.class){
                    lines.add("22,"+i.gridX+","+i.gridY);
                }
            }
            //ADD LOOP FOR ADDING ITEMS
            for(Item i:placeItm){
                if(i.getClass()==BlockKit.class){
                    lines.add("8,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==Wood.class){
                    lines.add("9,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==Clay.class){
                    lines.add("10,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==Club.class){
                    lines.add("11,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()==Nut.class){
                    lines.add("15,"+i.gridX+","+i.gridY);
                }
                if(i.getClass()== KeyCard.class){
                    lines.add("18,"+i.gridX+","+i.gridY);
                }
            }
            lines.add("playerInfo:");

            try {
                Files.write(Paths.get("newMap.gsinf"),lines, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void render(Graphics g){
        if(makeMode) {
            if (placedTiles != null) {
                for (Tile i : placedTiles) {
                    i.render(g);
                }
            }
            if(placedObs!=null){
                for(Obstacle i: placedObs){
                    i.render(g);
                }
            }
            if(placeItm!=null){
                for(Item i: placeItm){
                    i.render(g);
                }
            }
            //Check if null before rendering currentTile/item/obs
            if(currentTile!=null){
                currentTile.render(g);
            }else if(currentItm!=null){
                currentItm.render(g);
            }else if(currentObs!=null){
                currentObs.render(g);
            }

        }else{
            for(int i=0;i<golemTeam.size();i++){
                golemTeam.get(i).render(g);
            }
        }

        g.drawImage(Assets.cursorSp,(int)((cursorX*30)- Camera.camX),(int)((cursorY*30)- Camera.camY),null);
        g.setColor(Color.black);
        g.fillRect(0,0,100,10);
        g.setColor(Color.white);
        g.drawString("X: "+cursorX+" Y: "+cursorY,0,10);
        if(menuOpen){
            DropMenu.render(g);
        }
        if(selectItem!=null){
            //DRAW ITEM INFO WINDOW
            g.setColor(Color.BLACK);
            int[] infoCorner={game.width-200,0};
            g.fillRect(infoCorner[0],infoCorner[1],200,200);
            g.setColor(Color.white);
            g.drawRect(infoCorner[0]+5,infoCorner[1]+5,32,32);
            g.drawImage(selectItem.sprite, infoCorner[0]+5,infoCorner[1]+5,null);
            g.drawString("Item: "+selectItem.name, infoCorner[0]+40,infoCorner[1]+15);
            ArrayList<String> descChunks=new ArrayList<String>();
            g.drawString("Weight: "+selectItem.weight, infoCorner[0]+40,infoCorner[1]+25);
            g.drawString("Description: ",infoCorner[0]+40,infoCorner[1]+35);
            for(int i=0;i<(selectItem.description.length()/30)+1;i++){
                if((i+1)*30>selectItem.description.length()){
                    g.drawString(selectItem.description.substring(i*30),infoCorner[0]+5, infoCorner[1]+50+(i*10));
                }else{
                    g.drawString(selectItem.description.substring(i*30,(i+1)*30),infoCorner[0]+5, infoCorner[1]+50+(i*10));
                }

            }
            //g.drawString("Description: "+selectItem.description, infoCorner[0]+5,infoCorner[1]+50);
        }
        if(selectGolem!=null){
            //draw golem INFO
            g.setColor(Color.BLACK);
            int[] infoCorner={Game.width-200,0};
            g.fillRect(infoCorner[0],infoCorner[1],200,game.height);
            Script infoScript= selectGolem.useOrders;
            g.setColor(Color.white);
            int entry=1;
            g.drawString("Name: "+selectGolem.name,infoCorner[0]+10,infoCorner[1]+(entry*10));
            entry++;
            g.drawString("Health: "+selectGolem.health+"/"+selectGolem.healthMax,infoCorner[0]+10,infoCorner[1]+(entry*10));
            entry++;
            g.drawString("Strength: "+selectGolem.strength,infoCorner[0]+10,infoCorner[1]+(entry*10));
            entry++;
            g.drawString("Fight: "+selectGolem.fight,infoCorner[0]+10,infoCorner[1]+(entry*10));
            entry++;
            String facingDir;
            switch (selectGolem.targetDir){
                case 0:
                    facingDir="North";
                    break;
                case 1:
                    facingDir="East";
                    break;
                case 2:
                    facingDir="South";
                    break;
                case 3:
                    facingDir="West";
                    break;
                default:
                    facingDir="Not Defined???";
                    break;
            }
            g.drawString("Facing: "+facingDir, infoCorner[0]+10,infoCorner[1]+(entry*10));
            entry++;
            if(selectGolem.heldItem!=null){
                g.drawString("Item: "+selectGolem.heldItem.name, infoCorner[0]+10,infoCorner[1]+(entry*10));
            }else{
                g.drawString("Item: ", infoCorner[0]+10,infoCorner[1]+(entry*10));
            }
            entry++;
            if(infoScript!=null){
                g.drawString("Script Name: "+infoScript.name, infoCorner[0]+10,infoCorner[1]+(entry*10));
                entry++;
                g.drawString("Current Label: "+ infoScript.currentLabel.name, infoCorner[0]+10,infoCorner[1]+(entry*10));
                entry++;
                for(int i=0; i<infoScript.currentLabel.instructions.length;i++){
                    int y = infoCorner[1] + (entry * 10) + (i * 10);
                    if (infoScript.currentLabel.pointer == i) {
                        g.drawString(i + " >" + infoScript.currentLabel.instructions[i], infoCorner[0] + 15, y);
                    } else {
                        g.drawString(i + " " + infoScript.currentLabel.instructions[i], infoCorner[0] + 15, y);
                    }

                }
            }

        }
        if(helpOpen){
            g.setColor(Color.black);
            int YSPACE=20;
            int ENTRY=0;
            g.fillRect(0,Game.height-100,Game.width-200,100);
            g.setColor(Color.WHITE);
            g.drawString("Controls         (Press H to open/close this menu)",10,(Game.height-100+15)+(YSPACE*ENTRY));
            ENTRY++;
            g.drawString("Z: Select/Advance Turn | X: Cancel/Open Drop-down Menu | P: Toggle Path Visibility", 10, (Game.height-100+15)+(YSPACE*ENTRY));
            ENTRY++;
            g.drawString("WASD: Move Camera | Arrow Keys: Move Cursor",10 , (Game.height-100+15)+(YSPACE*ENTRY));
            ENTRY++;
            g.drawString("Space: Center Camera | Enter: Switch Modes", 10, (Game.height-100+15)+(YSPACE*ENTRY));
            ENTRY++;
        }


    }

    public void addGolem(Golem newGol){
        golemTeam.add(newGol);
    }
}
