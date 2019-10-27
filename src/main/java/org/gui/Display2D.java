package org.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import org.hyperCube.KompositumCube.Construct;
import org.hyperCube.KompositumCube.Line;

public class Display2D extends Canvas {
    private GraphicsContext gc;

    public Display2D(double width, double height){
        super(width, height);
        gc = this.getGraphicsContext2D();
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.BLACK);
    }

    public GraphicsContext getGc(){
        return gc;
    }

    public void drawAllLines(Line[] toDraw){
        for(Line line: toDraw){
            gc.strokeLine(line.getP1().get(0) + getWidth()/2, line.getP1().get(1) + getHeight()/2,
                         line.getP2().get(0) + getWidth()/2, line.getP2().get(1) + getHeight()/2);
        }
    }

    public void reset(){
        gc.fillRect(0,0,getWidth(), getHeight());
    }

    public void drawAllPoints(){

    }
}
