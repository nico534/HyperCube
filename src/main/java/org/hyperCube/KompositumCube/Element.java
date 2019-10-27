package org.hyperCube.KompositumCube;

public interface Element {

    Element[] get(int dimension);
    public Line[] getLines();
    void changeElement(int e1, int e2);
    void changeElement();

}
