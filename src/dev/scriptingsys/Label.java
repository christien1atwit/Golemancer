package dev.scriptingsys;

import dev.entities.Golem;

public class Label {
    /*
    Labels are groupings of instructions. They make up a Script, and allow it to loop, and have conditionals.
    A Label's name is always in all-caps, with the exception of the 'Main' Label. The 'Main' label is special because it
    automatically loops and is always the starting point of a Script.
     */
    public String name;
    public int pointer=0;//Used to index through the instructions in the Label
    public String[] instructions;//An ordered 1-D array of specific strings that correspond to methods in a Golem
    public String executedFrom;//Tells what Label to go back to when all of the instructions have been executed.
    public boolean returnFlag=false;//A boolean value used to tell the program to return to the Label that activated this one.

    public static final String moveUp="moveUp", moveDown="moveDown", moveLeft="moveLeft", moveRight="moveRight", assess="assess", pickUp="pickUp",useItem="useItem",
            dropItem="dropItem",selfDestruct="selfDestruct", attack="attack", startLayPath="startLayPath", endLayPath="endLayPath",follow="follow",backFollow="backFollow",
            jumpTo="jumpTo", onPath="onPath", faceClk="faceClk", faceCclk="faceCclk", faceN="faceN",faceE="faceE",faceS="faceS",faceW="faceW", orientPath="orientToPath",
            moveFor="moveForward",moveBack="moveBackward", senseItm="senseItem";



    public Label(String name, String[] instructions){
        this.name=name;
        this.instructions=instructions;
        this.pointer=0;
    }
    public String getIns(){
        String out=instructions[pointer];
        if(pointer!=instructions.length-1){
            pointer++;
        }else if(name.equals("Main")){
            pointer=0;
        }else{
            returnFlag=true;
            pointer=0;

        }
        return out;
    }

}
