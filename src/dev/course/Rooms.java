package dev.course;


import dev.Game;
import dev.entities.Golem;
import dev.entities.Items.Generic.KeyCard;
import dev.entities.Items.Generic.Wood;
import dev.entities.Items.Item;
import dev.entities.Items.Weapon.Club;
import dev.entities.Items.building.BlockKit;
import dev.entities.Items.building.Nut;
import dev.entities.Items.healing.Clay;
import dev.entities.Path;
import dev.entities.enemy.Enemy;
import dev.entities.obstacles.*;
import dev.gfx.Assets;
import dev.gfx.Camera;
import dev.sfx.SoundPlayer;
import dev.states.GameState;
import dev.tiles.*;

import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public abstract class Rooms {
    public ArrayList<Tile> myTiles;
    public ArrayList<Item> myItems;
    public ArrayList<Obstacle> myObs;
    public ArrayList<Path> myPaths;
    public ArrayList<MapWaypoint> myWaypoints;
    public ArrayList<Enemy> myEnemys;
    public Camera cam;
    public String BGM;
    public SoundPlayer soundPlayer;
    public String music;
    public boolean drawPaths;
    public boolean completed;
    public int[] startingGol;
    public String name;
    public ArrayList<Golem> loadedGols;
    
    public Rooms(Camera cam){
        this.cam=cam;
        BGM= new String();
        myTiles= new ArrayList<Tile>();
        myItems= new ArrayList<Item>();
        myObs= new ArrayList<Obstacle>();
        myPaths=new ArrayList<Path>();
        myWaypoints=new ArrayList<MapWaypoint>();
        myEnemys=new ArrayList<Enemy>();
        soundPlayer=new SoundPlayer();
        drawPaths=true;
        completed=false;


    }
    public void tick(){
        ArrayList<Integer> toRemove=new ArrayList<Integer>();
        for(int i=myObs.size()-1;i>=0;i--){
            if(myObs.get(i).broken){
                toRemove.add(i);
            }
        }
        for(int i:toRemove){
            myObs.remove(i);
        }
        toRemove=new ArrayList<>();
        for(int i=myEnemys.size()-1;i>=0;i--){
            if(myEnemys.get(i).imDead){
                toRemove.add(i);
            }
        }
        for(int i:toRemove){
            myEnemys.remove(i);
        }
        for(int i=0; i<myTiles.size();i++){
            myTiles.get(i).tick();
        }
    }

    public void turnTick(){
        if(myObs!=null){
            for(int i=0;i<myObs.size();i++){
                myObs.get(i).tick();
            }
        }
        if(myEnemys!=null){
            for(int i=0;i<myEnemys.size();i++){
                myEnemys.get(i).action();
            }
        }
    }
    public void render(Graphics g){
        for(Tile i:myTiles){
            i.render(g);
        }
        for(Obstacle i: myObs){
            i.render(g);
        }
        for(Item i:myItems){
            i.render(g);
        }
        for(Enemy i:myEnemys){
            i.render(g);
        }
        if(drawPaths){
            for(Path i:myPaths){
                i.render(g);
            }
        }

    }

    public Tile getTile(int x, int y){
        if(myTiles!=null){
            for(Tile i: myTiles){
                if(i.tileX==x && i.tileY==y){
                    return i;
                }
            }
            System.out.println("Does not have tile at location");
            return  null;
        }
        System.out.println("myTiles null");
        return null;
    }

    public Obstacle getObs(int x, int y){
        if(myObs!=null){
            for(Obstacle i: myObs){
                if(i.gridX==x && i.gridY==y){
                    return i;
                }
            }
            System.out.println("Does not have obstacle at location");
            return  null;
        }
        System.out.println("myObs null");
        return null;
    }

    public Item getItem(int x, int y){
        if(myItems!=null){
            for(Item i: myItems){
                if(i.gridX==x && i.gridY==y){
                    return i;
                }
            }
            System.out.println("Does not have item at location");
            return  null;
        }
        System.out.println("myItems null");
        return null;
    }

    public void togglePaths(){
        if(drawPaths){
            drawPaths=false;
        }else{
            drawPaths=true;
        }
    }

    public void saveGame(String name){
        String saveName=name+".gsinf";
        ArrayList<String> lines = new ArrayList<>();
        try {
            Files.deleteIfExists(Paths.get("res/saves/"+saveName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.add("name="+name);
        lines.add("startingGol="+startingGol[0]+","+startingGol[1]);
        lines.add("BGM=otherTheme");
        lines.add("mapInfo:");
        for(Tile i:myTiles){
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


        }
        for(Obstacle i:myObs){
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
        }
        //ADD LOOP FOR ADDING ITEMS
        for(Item i:myItems){
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
        for(Golem gol : GameState.player.golemTeam){
            //uhh
            String itemStr;
            if(gol.heldItem!=null){
                itemStr=gol.heldItem.name;
            }else{
                itemStr="null";
            }

            lines.add(gol.name+","+itemStr+","+gol.strength+","+gol.fight+","+gol.health+","+gol.gridX+","+gol.gridY);
        }

        try {
            Files.write(Paths.get("res/saves/"+saveName),lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String fileName, boolean save){
        String path;
        if(save){
            path="res/saves/"+fileName;
        }else{
            path="res/maps/"+fileName;
        }


        try {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            String str;
            int index=-1;
            int instIndex=-1;
            boolean loadingMap=false;
            boolean loadingPlayer=false;
            while ((str = file.readLine()) != null) {
                //System.out.println(str);
                if(str.contains("name=")){
                    name=str.substring(str.indexOf("=")+1);
                    //System.out.println(name);
                }
                else if(str.contains("startingGol=")){
                    int commaInd=str.indexOf(",");
                    startingGol=new int[]{Integer.parseInt(str.substring(str.indexOf("=")+1,commaInd)),Integer.parseInt(str.substring(commaInd+1))};
                }
                else if(str.contains("mapInfo:")){
                    loadingMap=true;
                }
                else if(str.contains("playerInfo:")){
                    loadingMap=false;
                    loadingPlayer=true;
                    loadedGols=new ArrayList<Golem>();
                }
                else if(str.contains("BGM=")){
                    if(str.contains("otherTheme")){
                        BGM= Assets.otherTheme;
                    }
                    if(str.contains("mainTheme")){
                        BGM=Assets.mainTheme;
                    }
                }

                if(loadingMap) {
                    if(str.contains(",")){
                        int id=Integer.parseInt(str.substring(0,str.indexOf(",")));
                        str=str.substring(str.indexOf(",")+1);
                        int x=Integer.parseInt(str.substring(0,str.indexOf(",")));
                        str=str.substring(str.indexOf(",")+1);
                        int y=Integer.parseInt(str);
                        switch(id) {
                            case 0:
                                myTiles.add(new brickFloor(x,y, Game.camera));
                                break;
                            case 1:
                                myTiles.add(new StoneTile(x,y,Game.camera));
                                break;
                            case 2:
                                myTiles.add(new Grass(x,y,Game.camera));
                                break;
                            case 3:
                                myTiles.add(new Water(x,y,Game.camera));
                                break;
                            case 4:
                                myTiles.add(new Base(x,y,Game.camera));
                                break;
                            case 5:
                                myObs.add(new Blockade(x,y));
                                break;
                            case 6:
                                myObs.add(new Spikes(x,y));
                                break;
                            case 7:
                                myObs.add(new Tree(x,y));
                                break;
                            case 8:
                                myItems.add(new BlockKit(x,y));
                                break;
                            case 9:
                                myItems.add(new Wood(x,y));
                                break;
                            case 10:
                                myItems.add(new Clay(x,y));
                                break;
                            case 11:
                                myItems.add(new Club(x,y));
                                break;
                            case 12:
                                myTiles.add(new Dirt(x,y,Game.camera));
                                break;
                            case 13:
                                myTiles.add(new GoalTile(x,y,Game.camera));
                                break;
                            case 14:
                                myObs.add(new Lair(x,y));
                                break;
                            case 15:
                                myItems.add(new Nut(x,y));
                                break;
                            case 16:
                                myObs.add(new ClayRock(x,y));
                                break;
                            case 17:
                                myObs.add(new LockedDoor(x,y));
                                break;
                            case 18:
                                myItems.add(new KeyCard(x,y));
                                break;
                            case 19:
                                myTiles.add(new FacilityFloor(x,y,Game.camera));
                                break;
                            case 20:
                                myTiles.add(new FacilityWall(x,y,Game.camera));
                                break;
                            case 21:
                                myObs.add(new FacilityTable(x,y));
                                break;
                            case 22:
                                myObs.add(new FacilityAcid(x,y));
                                break;
                            default:
                                System.out.println("NO ID: "+id);
                                break;
                        }

                    }

                }else if(loadingPlayer){
                    if(str.contains(",")){
                        String golName=str.substring(0,str.indexOf(","));
                        str=str.substring(str.indexOf(",")+1);
                        String itemStr=str.substring(0, str.indexOf(","));
                        str=str.substring(str.indexOf(",")+1);
                        Item golItem;
                        if(itemStr.contains("Club")){
                            golItem=new Club(0,0);
                        }else if(itemStr.contains("Kit")){
                            golItem= new BlockKit(0,0);
                        }else if(itemStr.contains("Nut")){
                            golItem=new Nut(0,0);
                        }else if(itemStr.contains("Wood")){
                            golItem=new Wood(0,0);
                        }else if(itemStr.contains("Clay")){
                            golItem=new Clay(0,0);
                        }else if(itemStr.contains("Key Card")){
                            golItem=new KeyCard(0,0);
                        }else if(itemStr.contains("null")){
                            golItem=null;
                        }else{
                            golItem=null;
                        }
                        int golStren=Integer.parseInt(str.substring(0, str.indexOf(",")));
                        str=str.substring(str.indexOf(",")+1);
                        int golFig=Integer.parseInt(str.substring(0, str.indexOf(",")));
                        str=str.substring(str.indexOf(",")+1);
                        int golHel=Integer.parseInt(str.substring(0, str.indexOf(",")));
                        str=str.substring(str.indexOf(",")+1);
                        int golX=Integer.parseInt(str.substring(0, str.indexOf(",")));
                        str=str.substring(str.indexOf(",")+1);
                        int golY=Integer.parseInt(str);
                        Golem loadedGolem=new Golem(golX,golY,golName,golHel,golStren,golFig,golItem);
                        loadedGols.add(loadedGolem);
                    }

                }


            }

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

