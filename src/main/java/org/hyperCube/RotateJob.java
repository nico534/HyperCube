package org.hyperCube;

public class RotateJob {
    private int axes1;
    private int axes2;
    private double rotateValue;
    private boolean rotate;
    public RotateJob(int axes1, int axes2, double rotateValue){
        this.axes1 = axes1;
        this.axes2 = axes2;
        this.rotateValue = rotateValue;
        this.rotate = (rotateValue != 0);
    }

    public void setRotateValue(double value){
        this.rotateValue = value;
        this.rotate = value != 0;
    }

    public boolean getRotate(){
        return this.rotate;
    }

    public int getAxes1(){
        return axes1;
    }

    public int getAxes2(){
        return axes2;
    }

    public double getRotateValue(){
        return rotateValue;
    }
}
