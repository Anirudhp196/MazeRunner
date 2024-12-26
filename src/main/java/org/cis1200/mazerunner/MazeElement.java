package org.cis1200.mazerunner;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public abstract class MazeElement {
    private Location loc;
    private int size;
    private BufferedImage img;

    public MazeElement(Location loc, int size, String imgStr) {
        this.loc = loc;
        this.size = size;
        try {
            img = ImageIO.read(new File(imgStr));
        } catch (IOException e) {
            System.out.println("Image [" + imgStr + "] not loaded");
        }
    }

    /* Getters */
    public Location getLoc() {
        return loc;
    }

    public int getSize() {
        return size;
    }

    public BufferedImage getImg() {
        return img;
    }

    public boolean intersects(MazeElement other) {
        return this.loc.equals(other.getLoc());
    }

    abstract void move(int key, char[][] maze);

}
