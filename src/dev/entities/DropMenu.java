package dev.entities;




import com.sun.xml.internal.fastinfoset.util.StringArray;
import dev.Game;
import dev.gfx.Assets;
import dev.gfx.Camera;
import dev.scriptingsys.Script;
import dev.sfx.SoundPlayer;
import dev.states.GameState;
import dev.tiles.Base;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class DropMenu {

    private static ArrayList<String> menuCategories=new ArrayList<String>();
    private static String[][] menuOptions;
    private static int[] selection={0,0}; //selection[0]=category selection[1]=option
    private static String category="top";

    private static int maxStringLen=0;



    private static SoundPlayer soundPlayer=new SoundPlayer();

    private static int ACTION_COOL=5,actCool=0;
    private static Object actingObject;
    public static boolean exiting=false;

    public static void init(Object obj){
        actingObject=obj;
        exiting=false;
        actCool=-3;
        if(actingObject.getClass()==Golem.class){
            menuCategories.add("Main");
            menuCategories.add("Script");
            menuCategories.add("Rename");
            menuOptions=new String[menuCategories.size()][];
            menuOptions[0]=new String[]{"Give Script", "Random Name","Destroy"};
            File f = new File("res/scripts");
            // Populates the array with names of files and directories
            String[] pathnames = f.list();
            menuOptions[1]=pathnames;
            menuOptions[2]=new String[]{""};

        }
        if(actingObject.getClass()== Base.class){
            menuCategories.add("Main");
            menuOptions=new String[menuCategories.size()][];
            menuOptions[0]= new String[]{"Craft"};
        }
    }

    public static void tick(Game game){
        for(int i=0;i<menuOptions[selection[0]].length;i++){
            if(menuOptions[selection[0]][i].length()>maxStringLen){
                maxStringLen=menuOptions[selection[0]][i].length();
            }
        }

        //Menu UP/DOWN
        if(game.getKeyManager().up && actCool==ACTION_COOL){
            actCool=0;
            if(selection[1]>0){
                selection[1]--;
                soundPlayer.play(false, Assets.plink);
            }
        }
        if(game.getKeyManager().down && actCool==ACTION_COOL){
            actCool=0;
            if(selection[1]<menuOptions[selection[0]].length-1){
                selection[1]++;
                soundPlayer.play(false, Assets.plink);
            }
        }



        if(actingObject.getClass()==Golem.class){
            //Drop Down menu for a Golem
            if(game.getKeyManager().attack && actCool==ACTION_COOL){
                actCool=0;
                if(selection[0]==0){
                    switch (selection[1]){
                        case 0:
                            if(menuOptions[1].length>0)
                            selection[0]=1;
                            selection[1]=0;
                            break;
                        case 1:
                            //Golem Rename
                            Random rand = new Random();
                            int randomIndex= rand.nextInt(Golem.maxNames);
                            int numberInTotal=0;
                            ((Golem)actingObject).name= Golem.RandomNames[randomIndex];
                                for(int i=0;i<GameState.player.golemTeam.size();i++){
                                    if(GameState.player.golemTeam.get(i).name.contains(((Golem)actingObject).name)){
                                        numberInTotal++;
                                    }
                                }
                                if(numberInTotal>1){
                                    ((Golem)actingObject).name=((Golem)actingObject).name+String.valueOf(numberInTotal);
                                }


                            selection[0]=0;
                            selection[1]=0;
                            exiting=true;
                            break;
                        case 2:
                            //Golem Destroy
                            ((Golem) actingObject).death();
                            selection[0]=0;
                            selection[1]=0;
                            exiting=true;
                            break;
                        default:
                            System.out.println("Selection[1] out of Range");
                            break;
                    }
                }else if(selection[0]==1){
                    ((Golem) actingObject).useOrders=new Script(menuOptions[selection[0]][selection[1]]);
                    if(((Golem) actingObject).defaultOrders==null){
                        ((Golem) actingObject).defaultOrders=((Golem) actingObject).useOrders;
                    }
                    selection[0]=0;
                    selection[1]=0;
                    exiting=true;
                }
            }

        }

        if(actingObject.getClass()==Base.class){
            //Drop Down menu for Base
            if(game.getKeyManager().attack && actCool==ACTION_COOL) {
                actCool = 0;
                ((Base) actingObject).logic();
                selection[0]=0;
                selection[1]=0;
                exiting=true;
            }
        }

        //Cooldowns
        if(actCool<ACTION_COOL){
            actCool++;
        }
        //Backing Out of Menu
        if(game.getKeyManager().interact && actCool==ACTION_COOL){
            actCool=0;
            if(selection[0]>0){
                selection[0]--;
                selection[1]=0;
            }else{
                exiting=true;
                actCool=0;
            }
        }

    }
    public static void render(Graphics g){
        //if(actingObject.getClass()==Golem.class){
            //Golem Menu draw code
        int xLoc = 0, yLoc=0;
        if(actingObject.getClass()==Golem.class) {
            xLoc = (((Golem) actingObject).gridX * 30 + 30) - Camera.camX;
            yLoc = (((Golem) actingObject).gridY * 30 + 30) - Camera.camY;
        }else if(actingObject.getClass()==Base.class){
            xLoc=((Base)actingObject).endX+30-Camera.camX;
            yLoc=((Base)actingObject).endY+30-Camera.camY;
        }

            int width=(maxStringLen+2)*7;
            int height=(menuOptions[selection[0]].length*10)+5;
            g.setColor(Color.black);
            g.fillRect(xLoc,yLoc,width,height);
            g.setColor(Color.WHITE);
            for(int i=0;i<menuOptions[selection[0]].length;i++){
                if(menuOptions[selection[0]][i].equals(menuOptions[selection[0]][selection[1]])){
                    g.setColor(Color.red);
                    g.fillRect(xLoc,yLoc + (10 * (i)),width,10);
                    g.setColor(Color.white);
                    g.drawString(">"+menuOptions[selection[0]][i], xLoc, yLoc + (10 * (i+1)));
                }else {
                    g.drawString(menuOptions[selection[0]][i], xLoc, yLoc + (10 * (i+1)));
                }
            }


        //}

    }
}
