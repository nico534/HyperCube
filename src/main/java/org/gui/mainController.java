package org.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import matrixLibrary.matrix.Matrix;
import org.hyperCube.CubeCalculator;
import org.hyperCube.HyperCube;

public class mainController extends BorderPane {
    private VBox rotateBox;

    private Slider[] sliders;
    private Label[] labels;

    private Display2D display;
    private Camera3D cam;
    private Matrix light;
    private int dimensions;

    private HyperCube cube;

    private AnimationTimer reDraw;

    public mainController(){
        setPrefHeight(600);
        setPrefWidth(800);

        Matrix camMatrix = new Matrix(3);
        camMatrix.set(2, 2);
        cam = new Camera3D(camMatrix);
        light = new Matrix(3);
        light.set(2, 1);

        dimensions = 5;
        cube = new HyperCube(dimensions);
        display = new Display2D(600, 600);
        setCenter(display);
        this.widthProperty().addListener(e -> {
            display.setWidth(getWidth() - 200);
        });
        this.heightProperty().addListener(e -> {
            display.setHeight(getHeight());
        });
        rotateBox = new VBox();
        setLeft(rotateBox);
        rotateBox.setPrefWidth(200);
        createSliders();
        display.reset();
        //display.drawAllLines(cube.getToDrawLines(cam));
        //display.drawAllFaces(cube.getToDrawFaces(cam, light));

        startFaceDrawRoutine();
        //startLineDrawRoutine();
    }

    private void createSliders(){
        sliders = new Slider[CubeCalculator.calcRotateAngels(dimensions)];
        labels = new Label[sliders.length];
        Slider scale = new Slider();
        Label scaleL = new Label();

        rotateBox.getChildren().add(scaleL);
        rotateBox.getChildren().add(scale);

        scale.setMin(1);
        scale.setMax(3000);
        scale.setValue(500);
        scaleL.setText("Scale: 500");
        scale.valueProperty().addListener((obs, oldval, newVal) ->{
            scale.setValue(newVal.intValue());
            scaleL.setText("Scale: " + newVal.intValue());
            HyperCube.scale = newVal.intValue();
        });

        Slider distance = new Slider();
        Label distanceL = new Label();

        rotateBox.getChildren().add(distanceL);
        rotateBox.getChildren().add(distance);

        distance.setMin(1);
        distance.setMax(5);
        distance.setValue(2);
        distanceL.setText("Distance: 2.0");
        distance.valueProperty().addListener((obs, oldval, newVal) ->{
            distance.setValue(newVal.doubleValue());
            distanceL.setText("Distance: " + newVal.doubleValue());
            cam.getVisionAxes().set(2, newVal.doubleValue());
        });
        int counter = 0;
        for(int i = 0; i < dimensions - 1; i++){
            for(int j = i+1; j < dimensions; j++){
                //System.out.println(i + "|" + j);
                sliders[counter] = new Slider();
                labels[counter] = new Label();
                labels[counter].setText("Axes " +i + "|" + j+ " = 0.0");
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

    private void startLineDrawRoutine(){
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

    private void startFaceDrawRoutine(){
        reDraw = new AnimationTimer(){

            @Override
            public void handle(long l) {
                cube.rotate();
                display.reset();
                display.drawAllFaces(cube.getToDrawFaces(cam, light));
            }
        };
        reDraw.start();
    }
}
