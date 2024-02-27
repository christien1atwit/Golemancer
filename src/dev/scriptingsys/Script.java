package dev.scriptingsys;


import dev.gfx.Assets;
import dev.sfx.SoundPlayer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Script {
    public String name;
    public Label[] labels;// Holds all of the Script's Labels
    public Label currentLabel;//changes when its Golem activates a method that changes labels
    private SoundPlayer soundPlayer=new SoundPlayer();
    public Script(String name, Label[] labels){
        this.name=name;
        this.labels=labels;
        for(int i=0;i<labels.length;i++){//This sets the current Label to the 'Main' Label.
            if(labels[i].name.equals("Main")){
                currentLabel=labels[i];
            }
        }

    }
    public Script(String scName){
        /*
        This constructor is used when using a file to create the script instead of giving a name and array of labels.
         */
        readFile(scName);
        for(int i=0;i<labels.length;i++){//This sets the current Label to the 'Main' Label.
            if(labels[i].name.equals("Main")){
                currentLabel=labels[i];
            }
        }

    }
    public String read(){
        if(currentLabel.returnFlag){//Once a Label that is not 'Main' has completed execution, this conditional sets the current Label to the Label that triggered it, continuing execution from where it left off
            currentLabel.returnFlag=false;
            goToLabel(currentLabel.executedFrom);
            String output=currentLabel.getIns();
            return output;

        }else {
            String output = currentLabel.getIns();//Gets the instruction from current Label
            return output;
        }

    }
    public void goToLabel(String destLabel){//Changes the Label that is being executed
        String executor=currentLabel.name;//Saves the name of what Label caused the change in the current Label
        currentLabel.pointer=0;
        if(destLabel.contains(Label.jumpTo)){
            String newDest=destLabel.substring(6);
            ///
            for(int i=0;i<labels.length;i++){
                if(labels[i].name.equals(newDest)){
                    currentLabel=labels[i];
                    currentLabel.pointer=0;
                    currentLabel.executedFrom=executor;
                }
            }

        }else{
            for(int i=0;i<labels.length;i++){
                if(labels[i].name.equals(destLabel)){
                    currentLabel=labels[i];
                }
            }
            currentLabel.executedFrom=executor;//Tells the current Label what Label triggered the change to it.
            if(currentLabel.name==executor){//If recusion is used this makes sure the label resets back to the top.
                currentLabel.pointer=0;
            }

        }

    }

    public void save(){
        /*
        The save method creates a '.gol' script file that can recreate the current script when read. The file is written
        to the res/scripts directory.
         */
        ArrayList<String> lines = new ArrayList<>();
        try {
            Files.deleteIfExists(Paths.get("res/scripts/"+name));
        } catch (IOException e) {
            e.printStackTrace();
        }
       //add actual content here
        lines.add("name-"+name);
        lines.add("SCRIPTLEN="+labels.length);
        for(int i=0;i<labels.length;i++){
            lines.add("LABEL-"+labels[i].name);
            lines.add("LABLEN="+labels[i].instructions.length);
            for(int z=0;z<labels[i].instructions.length;z++){
                lines.add(labels[i].instructions[z]);
            }
        }



        try {
            Files.write(Paths.get("res/scripts/"+name),lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            soundPlayer.play(false, Assets.save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(String scName){
        /*
        This method reads a ".gol" script file and sets the script running this to act like the one described in the file.
        scName is a string that is just the name of the '.gol' file without the extension. The program only looks in the
        res/scripts directory.
         */
        String path="res/scripts/"+scName;
        try {
        RandomAccessFile file = new RandomAccessFile(path, "r");
        String str;
        int index=-1;
        int instIndex=-1;
        while ((str = file.readLine()) != null) {
            //System.out.println(str);
            if(str.contains("name-")){
                name=str.substring(str.indexOf("-")+1);
                //System.out.println(name);
            }
            else if(str.contains("SCRIPTLEN=")){
                labels=new Label[Integer.parseInt(str.substring(str.indexOf("=")+1))];
                //System.out.println(labels.length);
            }
            else if(str.contains("LABEL-")){
                index++;
                instIndex=-1;
                labels[index]=new Label(str.substring(str.indexOf("-")+1),new String[1]);
            }
            else if(str.contains("LABLEN=")){
                labels[index].instructions=new String[Integer.parseInt(str.substring(str.indexOf("=")+1))];
            }else{
                instIndex++;
                //System.out.println(instIndex);
                //System.out.println(labels[index].instructions.length);
                labels[index].instructions[instIndex]=str;
            }


        }

        file.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}
