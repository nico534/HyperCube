package org.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.hyperCube.KompositumCube.Face;
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
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,getWidth(), getHeight());
    }

    public void drawAllFaces(Face[] faces){

        gc.setStroke(Color.LIGHTGRAY);
        for(Face f: faces) {
            gc.setFill(new Color(f.getLightIntensity(), f.getLightIntensity(), f.getLightIntensity(), 1));
            double[] xAxes = new double[]{Math.ceil(f.getP1().get(0) + getWidth() / 2), Math.ceil(f.getP2().get(0) + getWidth() / 2), Math.ceil(f.getP3().get(0) + getWidth() / 2)};
            double[] yAxes = new double[]{Math.ceil(f.getP1().get(1) + getHeight() / 2), Math.ceil(f.getP2().get(1) + getHeight() / 2), Math.ceil(f.getP3().get(1) + getHeight() / 2)};
            gc.fillPolygon(xAxes, yAxes, 3);

            gc.strokeLine(f.getP1().get(0)+ getWidth() / 2, f.getP1().get(1)+ getHeight()/2,
                         f.getP2().get(0)+ getWidth() / 2, f.getP2().get(1)+ getHeight()/2);
            gc.strokeLine(f.getP2().get(0)+ getWidth() / 2, f.getP2().get(1)+ getHeight()/2,
                         f.getP3().get(0)+ getWidth() / 2, f.getP3().get(1)+ getHeight()/2);
        }
    }
}
