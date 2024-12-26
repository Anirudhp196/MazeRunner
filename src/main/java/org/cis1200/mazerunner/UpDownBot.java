package org.cis1200.mazerunner;

public class UpDownBot extends MazeElement {

    private boolean moveUp;

    public UpDownBot(Location loc, int size, String imgStr) {
        super(loc, size, imgStr);
        moveUp = true;
    }

    @Override
    public void move(int key, char[][] maze) {
        int r = getLoc().getRow();
        int c = getLoc().getCol();

        if (moveUp) {
            if (r - 1 >= 0 && maze[r - 1][c] != '#') {
                getLoc().incR(-1);
            } else {
                moveUp = false;
            }
        } else {
            if (r + 1 < maze.length && maze[r + 1][c] != '#') {
                getLoc().incR(1);
            } else {
                moveUp = true;
            }
        }
    }
}
