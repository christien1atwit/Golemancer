package dev.scriptingsys.create;

import dev.Game;
import dev.entities.Controller;
import dev.entities.Golem;
import dev.gfx.Textbox;

import dev.scriptingsys.Label;
import dev.scriptingsys.Script;
import dev.sfx.SoundPlayer;
import dev.states.GameState;
import javafx.animation.ScaleTransition;
import sun.awt.windows.WingDings;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Creator {

    public String scName="Untitled";
    public ArrayList<Window> myWins =new ArrayList<Window>();
    private int selectedOpt=0;
    public Controller user;
    public Game game;

    private int maxOptions=0;

    public String selectedInstrucion="";
    public int instID=0;

    private int CHANGECOOL=20,actChangeCool=CHANGECOOL;
    private int SELCOOL=10, actSel=SELCOOL;
    private int BKSPCOOL=4,actBKS=BKSPCOOL;
    private int length;
    private boolean statCheck=false, labelJump=false;

    private boolean popInfoWin;
    private String infoWinText;
    private int INFO_TIME=120, infoTimer=INFO_TIME;

    public Creator(Game game){
        this.game=game;
        setup();

    }

    public void setup(){
        myWins.add(new Window(scName, "Create"));
        myWins.add(new Window("Main","LabelM"));
        game.getKeyManager().setMadeString(myWins.get(Window.getCurrentWin()).name);
    }

    public void tick() {
        Window curWin=myWins.get(Window.getCurrentWin());
        { //Menuing up down left and right
        if ((game.getKeyManager().down) && (actSel == SELCOOL)) {
            actSel = 0;
            if (curWin.type != "Create") {
                if (selectedOpt == 2) {
                    if (curWin.instIndex < curWin.intructions.size()+1) {
                        curWin.instIndex++;
                    }
                } else if (selectedOpt < maxOptions) {
                    selectedOpt++;
                }
            } else if (selectedOpt < maxOptions) {
                selectedOpt++;
            }
            //System.out.println(selectedOpt);
        }

        if ((game.getKeyManager().up) && (actSel == SELCOOL)) {
            actSel = 0;
            if (curWin.type != "Create") {
                if (selectedOpt == 2) {
                    if (curWin.instIndex > 0) {
                        curWin.instIndex--;
                    } else {
                        selectedOpt--;
                    }
                } else if (selectedOpt > 0) {
                    selectedOpt--;
                }
            } else if (selectedOpt > 0) {
                selectedOpt--;
            }
            //System.out.println(selectedOpt);
        }

        if ((game.getKeyManager().right) && (actSel == SELCOOL)) {
            actSel = 0;
            if (curWin.type != "Create"){
                if(selectedOpt==2){
                    instID++;
                }else if (Window.getCurrentWin() < Window.getNumbWins() - 1) {
                    //System.out.println(selectedOpt);
                    Window.currentWin++;
                    curWin=myWins.get(Window.getCurrentWin());
                    selectedOpt = 0;
                    game.getKeyManager().setMadeString(curWin.name);

                }
            }else if (Window.getCurrentWin() < Window.getNumbWins() - 1) {
                Window.currentWin++;
                curWin=myWins.get(Window.getCurrentWin());
                selectedOpt = 0;
                game.getKeyManager().setMadeString(curWin.name);
            }
        }
        if ((game.getKeyManager().left) && (actSel == SELCOOL)) {
            actSel = 0;
            if (curWin.type != "Create") {
                if (selectedOpt == 2) {
                    if(instID>0) {
                        instID--;
                    }
                }else if (Window.getCurrentWin() > 0) {
                    Window.currentWin--;
                    curWin=myWins.get(Window.getCurrentWin());
                    selectedOpt = 0;
                    game.getKeyManager().setMadeString(curWin.name);
                }
            }else if (Window.getCurrentWin() > 0) {
                Window.currentWin--;
                curWin=myWins.get(Window.getCurrentWin());
                selectedOpt = 0;
                game.getKeyManager().setMadeString(curWin.name);
            }
        }
    }

        if(curWin.type.equals("Create")){
            scTopInfoTick(curWin);
        }
        if(curWin.type.equals("LabelM")||curWin.type.equals("Label")){
            labelLogic(curWin);
        }
        if(game.getKeyManager().enter && actChangeCool==CHANGECOOL){
            actChangeCool=0;
            GameState.labelMake=false;
        }

        {//cooldowns
            if (actBKS < BKSPCOOL) {
                actBKS++;
            }
            if (actSel < SELCOOL) {
                actSel++;
            }
            if(actChangeCool<CHANGECOOL){
                actChangeCool++;
            }
        }
    }

    private void labelLogic(Window curWin){
        maxOptions=2;
        //System.out.println(curWin.name);
        if(selectedOpt==0 && !curWin.type.equals("LabelM")){
            //Label Naming
            game.getKeyManager().setEnableType(true);

            if (game.getKeyManager().backspace && actBKS >= BKSPCOOL) {
                actBKS = 0;
                if (game.getKeyManager().madeString.length() > 0) {
                    game.getKeyManager().madeString = game.getKeyManager().madeString.substring(0, game.getKeyManager().madeString.length() - 1);
                    curWin.name = game.getKeyManager().madeString.toUpperCase();
                }
            } else {
                if (!curWin.name.equals(game.getKeyManager().madeString)) {
                    curWin.name = game.getKeyManager().madeString.toUpperCase();
                }
            }
        }
        if(selectedOpt==1){
            //Save Label
            game.getKeyManager().setEnableType(false);
            if(game.getKeyManager().attack && actSel==SELCOOL){
                actSel=0;
                if(!curWin.type.equals("LabelM")) {
                    curWin.createdLb = new Label(curWin.name.toUpperCase(), curWin.toStrArray(curWin.intructions));
                } else{
                    curWin.createdLb = new Label(curWin.name, curWin.toStrArray(curWin.intructions));
                }
                System.out.println(curWin.createdLb.name);
            }
        }
        if(selectedOpt==2){
            //Editing Label
            game.getKeyManager().setEnableType(false);
            if(labelJump){
                if(Window.getNumbWins()>2){
                    int labelIndex=instID+1;
                    if(labelIndex<myWins.size()){
                        String targetLabel=myWins.get(labelIndex).name;
                        if(!targetLabel.equals("Main")){
                            targetLabel=targetLabel.toUpperCase();
                        }
                        if(!selectedInstrucion.contains(targetLabel)){
                            selectedInstrucion=selectedInstrucion.substring(0,length);
                            selectedInstrucion=selectedInstrucion.concat(targetLabel);
                        }

                    }else{
                        instID=0;
                    }
                }else{
                    System.out.println("No Valid Jump Landings");
                    labelJump=false;
                }

            }else if(statCheck){
                switch (instID){
                    case 0:
                        selectedInstrucion= Label.assess+Golem.hStr;
                        break;
                    case 1:
                        selectedInstrucion= Label.assess+Golem.thStr;
                        break;
                    case 2:
                        selectedInstrucion= Label.assess+Golem.initStr;
                        break;
                    default:
                        instID=0;
                        break;
                }
            }else {
                switch (instID) {
                    case 0:
                        selectedInstrucion = Label.moveUp;
                        break;
                    case 1:
                        selectedInstrucion = Label.moveDown;
                        break;
                    case 2:
                        selectedInstrucion = Label.moveLeft;
                        break;
                    case 3:
                        selectedInstrucion = Label.moveRight;
                        break;
                    case 4:
                        selectedInstrucion = Label.assess;
                        break;
                    case 5:
                        selectedInstrucion= Label.pickUp;
                        break;
                    case 6:
                        selectedInstrucion=Label.useItem;
                        break;
                    case 7:
                        selectedInstrucion=Label.dropItem;
                        break;
                    case 8:
                        selectedInstrucion=Label.selfDestruct;
                        break;
                    case 9:
                        selectedInstrucion=Label.attack;
                        break;
                    case 10:
                        selectedInstrucion=Label.jumpTo;
                        break;
                    case 11:
                        selectedInstrucion=Label.startLayPath;
                        break;
                    case 12:
                        selectedInstrucion=Label.endLayPath;
                        break;
                    case 13:
                        selectedInstrucion=Label.follow;
                        break;
                    case 14:
                        selectedInstrucion=Label.backFollow;
                        break;
                    case 15:
                        selectedInstrucion=Label.onPath;
                        break;
                    case 16:
                        selectedInstrucion=Label.faceClk;
                        break;
                    case 17:
                        selectedInstrucion=Label.faceCclk;
                        break;
                    case 18:
                        selectedInstrucion=Label.faceN;
                        break;
                    case 19:
                        selectedInstrucion=Label.faceE;
                        break;
                    case 20:
                        selectedInstrucion=Label.faceS;
                        break;
                    case 21:
                        selectedInstrucion=Label.faceW;
                        break;
                    case 22:
                        selectedInstrucion=Label.orientPath;
                        break;
                    case 23:
                        selectedInstrucion=Label.moveFor;
                        break;
                    case 24:
                        selectedInstrucion=Label.moveBack;
                        break;
                    case 25:
                        selectedInstrucion=Label.senseItm;
                        break;
                    default:
                        instID = 0;
                        break;

                }
            }
            if(game.getKeyManager().attack && actSel==SELCOOL){
                actSel=0;
                if(labelJump){
                    labelJump=false;
                    curWin.intructions.add(selectedInstrucion);
                }else if(statCheck){
                    statCheck=false;
                    labelJump=true;
                    length=selectedInstrucion.length();
                }else if(selectedInstrucion.equals(Label.assess)){
                    statCheck = true;
                }else if(selectedInstrucion.equals(Label.jumpTo)||selectedInstrucion.equals(Label.onPath)){
                    labelJump=true;
                    length=selectedInstrucion.length();
                }
                else {
                    curWin.intructions.add(selectedInstrucion);
                }
                //System.out.println(curWin.intructions);
            }
            if(game.getKeyManager().backspace && actSel==SELCOOL){
                actSel=0;
                if(curWin.intructions.size()>0) {
                    curWin.intructions.remove(curWin.intructions.size() - 1);
                }
            }
        }



    }



    private void scTopInfoTick(Window curWin){
        maxOptions=2;
        if(selectedOpt==0){
            game.getKeyManager().setEnableType(true);
            if (game.getKeyManager().backspace && actBKS >= BKSPCOOL) {
                actBKS = 0;
                if (game.getKeyManager().madeString.length() > 0) {
                    game.getKeyManager().madeString = game.getKeyManager().madeString.substring(0, game.getKeyManager().madeString.length() - 1);
                    curWin.name = game.getKeyManager().madeString;
                }
            } else {
                if (!curWin.name.equals(game.getKeyManager().madeString)) {
                    curWin.name = game.getKeyManager().madeString;
                }
            }
        }
        if(selectedOpt==1){
            game.getKeyManager().setEnableType(false);
            if(game.getKeyManager().attack){
                myWins.add(new Window("fill","Label"));
                Window.currentWin=Window.numbWins-1;
                selectedOpt=0;
            }

        }
        if(selectedOpt==2){
            game.getKeyManager().setEnableType(false);
            if(game.getKeyManager().attack){
                createScript();
            }
        }
        scName=curWin.name;



    }

    public void createScript(){
        Label[] holdLabels=new Label[Window.numbWins-1];
        boolean valid=true;
        for(int i=1;i<myWins.size();i++){
            if(myWins.get(i).createdLb!=null) {
                holdLabels[i - 1] = myWins.get(i).createdLb;
            }else{
                //System.out.println("ABORTED: NULL LABEL");
                i=myWins.size();
                valid=false;
                putInfoUp("UNSAVED/EMPTY LABEL");
            }
        }
        if(scName.contains("*")||scName.contains("/")||scName.contains(":")||scName.contains("?")||scName.contains("\"")||scName.contains("<")
        ||scName.contains("|")){
            valid=false;
            putInfoUp("INVALID SCRIPT NAME");
        }
        if(valid){
            Script savingScript= new Script(scName+".gol",holdLabels);
            savingScript.save();
            putInfoUp("SAVE SUCCESSFUL");
        }
    }

    public void render(Graphics g){
        int winX=myWins.get(Window.getCurrentWin()).x; int winY=myWins.get(Window.getCurrentWin()).y;
        int winWid=myWins.get(Window.getCurrentWin()).width; int winHei=myWins.get(Window.getCurrentWin()).height;
        for(int i=myWins.size()-1;i>Window.currentWin;i--){
            myWins.get(i).render(g);
        }
        myWins.get(Window.getCurrentWin()).render(g);
        if(myWins.get(Window.getCurrentWin()).type=="Create"){
            g.setColor(Color.GRAY);
            g.fillRect(winX+1,winY+1,winWid-1,50);
            g.setColor(Color.black);
            g.fillRect(winX+1, winY+51,winWid-1,25);
            g.setColor(Color.white);
            g.drawString(scName, winX,winY+74);
            g.setColor(Color.black);
            if(selectedOpt==1) {
                g.drawString(">Create New Label", winX+10, winY+100);
            }else{
                g.drawString("Create New Label", winX+10, winY+100);
            }
            if(selectedOpt==2){
                g.drawString(">Save Script", winX+10, winY+120);
            }else{
                g.drawString("Save Script", winX+10, winY+120);
            }
        }
        if(myWins.get(Window.getCurrentWin()).type=="Label" || myWins.get(Window.getCurrentWin()).type=="LabelM"){
            g.setColor(Color.GRAY);
            g.fillRect(winX+1,winY+1,winWid-1,50);
            g.setColor(Color.black);
            g.fillRect(winX+1, winY+51,winWid-1,25);
            g.setColor(Color.white);
            g.drawString(myWins.get(Window.getCurrentWin()).name, winX,winY+74);
            g.setColor(Color.black);
            if(selectedOpt==1){
                g.drawString(">Save Label",winX+10, winY+100);
            }else{
                g.drawString("Save Label",winX+10, winY+100);
            }
            g.drawLine(winX,winY+105,winX+winWid,winY+105);
            for(int i=0;i<myWins.get(Window.getCurrentWin()).intructions.size();i++){
                g.drawString(String.valueOf(i), winX+10, winY+120+(i*10));
                g.drawString(myWins.get(Window.getCurrentWin()).intructions.get(i), winX+30, winY+120+(i*10));
            }
                g.setColor(Color.BLUE);
                g.drawString(String.valueOf(myWins.get(Window.getCurrentWin()).intructions.size()), winX+10, winY+120+(myWins.get(Window.getCurrentWin()).intructions.size()*10));
                g.drawString(selectedInstrucion, winX+30, winY+120+(myWins.get(Window.getCurrentWin()).intructions.size()*10));
            if(selectedOpt==2){
                //Display lines of code


            }



        }
        if(popInfoWin && infoTimer>0){
            infoTimer--;
            g.setColor(Color.RED);
            g.fillRect(300,200, 200,100);
            g.setColor(Color.black);
            g.drawString(infoWinText,330, 260);
            if(infoTimer==0){
                popInfoWin=false;
                infoWinText="";
            }
        }
    }

    public void printScriptNames(){
        File f = new File("res/scripts");

        // Populates the array with names of files and directories
        String[] pathnames = f.list();

        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
            System.out.println(pathname);
        }

    }
    private void putInfoUp(String text){
        infoTimer=INFO_TIME;
        popInfoWin=true;
        infoWinText=text;
    }

}
