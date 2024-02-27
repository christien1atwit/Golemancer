package dev.gfx;

public class Camera {
    /*
    The Camera class is used to assist in rendering graphics and scroll the field of view smoothly.
    Any object that is affected by a camera movement would have the camX and camY as part of their render method.
    It is declared in the Game class so that every active object can easily access it.
     */
    public static int camX, camY;

    public Camera(int x, int y){
        this.camX=x;
        this.camY=y;
    }

    public  void changeCam(int x, int y){
        camX+=x;
        camY+=y;
    }

    public void setCam(int x, int y){
        camX=x;
        camY=y;
    }

}
