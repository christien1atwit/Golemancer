package dev.course.maps;

import dev.course.Rooms;
import dev.gfx.Camera;

public class LoadableMap extends Rooms {
    public LoadableMap(Camera cam, String fileName, boolean saveLoad) {
        super(cam);
        loadMap(fileName,saveLoad);
        soundPlayer.play(true, BGM);
    }
}
