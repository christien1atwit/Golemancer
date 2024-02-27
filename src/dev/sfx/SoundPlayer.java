package dev.sfx;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundPlayer {
    private InputStream in;
    private Clip clip;


    public SoundPlayer(){

    }
    public void play(boolean loop, String audio){
        try{
            /*
            if(clip==null){
                in= new BufferedInputStream(this.getClass().getResourceAsStream(audio));
                clip= AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(in));
                if (loop){
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }else{

                clip.setMicrosecondPosition(0);
            }*/
            in= new BufferedInputStream(this.getClass().getResourceAsStream(audio));
            clip= AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(in));
            if (loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();




        }catch (Exception e){
            System.err.println(e);


        }
    }


}
