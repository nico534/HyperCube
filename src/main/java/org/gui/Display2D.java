package org.gui;


import com.sun.javafx.geom.Matrix3f;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Display2D extends Canvas {

    GraphicsContext gc;
    public Display2D(double width, double height){
        super(width, height);
        gc = this.getGraphicsContext2D();
    }

    public GraphicsContext getGc(){
        return gc;
    }

    public void drawAllPoints(){

    }
}
