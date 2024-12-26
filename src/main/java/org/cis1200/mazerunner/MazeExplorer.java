package org.cis1200.mazerunner;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public class MazeExplorer extends MazeElement {
    public static final String[] EXPLORER_IMAGES = {
        "files/explorerUp.png",
        "files/explorerRight.png",
        "files/explorerDown.png",
        "files/explorerLeft.png"};
    private int dir;
    private int steps;
    private int lives;

    private BufferedImage[] images;

    public MazeExplorer(Location loc, int size) {
        super(loc, size, "files/ExplorerRight.png");
        this.lives = 3;
        dir = 1;
        steps = 0;
        images = new BufferedImage[4];
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(new File(EXPLORER_IMAGES[i]));
            }
        } catch (IOException e) {
            System.out.println("Image Not Loaded");
        }
    }

    @Override
    public BufferedImage getImg() {
        return images[dir];
    }

    public int getDir() {
        return dir;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int newLives) {
        lives = newLives;
    }

    @Override
    public void move(int key, char[][] maze) {
        int r = getLoc().getRow();
        int c = getLoc().getCol();

        if (key == 38) {
            if (dir == 0) {
                if (r - 1 >= 0 && maze[r - 1][c] != '#') {
                    getLoc().incR(-1);
                    steps++;
                }
            }
            if (dir == 1) { // moving right
                if (maze[r][c + 1] != '#' && c + 1 < maze[0].length) {
                    getLoc().incC(1);
                    steps++;
                }
            }
            if (dir == 2) { //moving down
                if (r + 1 < maze.length && maze[r + 1][c] != '#') {
                    getLoc().incR(1);
                    steps++;
                }
            }
            if (dir == 3) { //moving left
                if (maze[r][c - 1] != '#' && c - 1 >= 0) {
                    getLoc().incC(-1);
                    steps++;
                }
            }
        }

        if (key == 37) {
            dir--;
            if (dir < 0) {
                dir = 3;
            }
        }

        if (key == 39) {
            dir++;
            if (dir > 3) {
                dir = 0;
            }
        }
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int newStep) {
        steps = newStep;
    }
}
