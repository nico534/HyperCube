package org.hyperCube;

public class HyperCube {
    public Construct cube;

    private RotateJob[] rotateJobs;

    public HyperCube(int dimension){
        cube = CubeCalculator.createCube(dimension);
    }

    public RotateJob getRotateJob(int jobIndex){
        return rotateJobs[jobIndex];
    }
}
