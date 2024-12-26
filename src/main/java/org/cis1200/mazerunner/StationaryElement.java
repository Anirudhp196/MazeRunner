package org.cis1200.mazerunner;

public class StationaryElement extends MazeElement {

    public StationaryElement(Location loc, int size, String imgStr) {
        super(loc, size, imgStr);
    }

    @Override
    void move(int key, char[][] maze) {
        System.out.println("Stationary Object Doesn't move");
    }
}
