package org.gui;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import matrixLibrary.matrix.Matrix;
import org.hyperCube.CubeCalculator;
import org.hyperCube.HyperCube;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    public AnchorPane renderPane;
    public VBox rotateBox;

    private Slider[] sliders;
    private Label[] labels;

    private Display2D display;
    private Camera3D cam;
    private int dimensions;

    private HyperCube cube;

    private AnimationTimer reDraw;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Matrix camMatrix = new Matrix(3);
        camMatrix.set(2, 2);
        cam = new Camera3D(camMatrix);

        dimensions = 3;
        cube = new HyperCube(dimensions);
        display = new Display2D(renderPane.getPrefWidth(), renderPane.getPrefHeight());
        renderPane.getChildren().add(display);
        createSliders();
        display.reset();
        display.drawAllLines(cube.getToDrawLines(cam));
        startDrawRoutine();
    }

    private void createSliders(){
        sliders = new Slider[CubeCalculator.calcRotateAngels(dimensions)];
        labels = new Label[sliders.length];
        int counter = 0;
        for(int i = 0; i < dimensions - 1; i++){
            for(int j = i+1; j < dimensions; j++){
                //System.out.println(i + "|" + j);
                sliders[counter] = new Slider();
                labels[counter] = new Label();
                labels[counter].setText(i + "|" + j);
                rotateBox.getChildren().add(labels[counter]);
                rotateBox.getChildren().add(sliders[counter]);
                int finalCounter = counter;
                int finalI = i;
                int finalJ = j;

                sliders[counter].valueProperty().addListener((obs, oldval, newval) -> {
                    cube.getRotateJob(finalCounter).setRotateValue(newval.doubleValue() * 0.001);
                    sliders[finalCounter].setValue(newval.doubleValue());
                    labels[finalCounter].setText("Axes " +finalI + "|" + finalJ+ " = " + newval.doubleValue() * 0.001);
                });
                //System.out.println("create: " + counter);
                counter++;
            }
        }
    }

    private void startDrawRoutine(){
        reDraw = new AnimationTimer(){

            @Override
            public void handle(long l) {
                cube.rotate();
                display.reset();
                display.drawAllLines(cube.getToDrawLines(cam));
            }
        };
        reDraw.start();
    }
}
