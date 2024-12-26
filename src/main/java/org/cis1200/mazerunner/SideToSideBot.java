package org.cis1200.mazerunner;

public class SideToSideBot extends MazeElement {

    private boolean moveRight;

    public SideToSideBot(Location loc, int size, String imgStr) {
        super(loc, size, imgStr);
        moveRight = true;
    }

    @Override
    public void move(int key, char[][] maze) {
        int r = getLoc().getRow();
        int c = getLoc().getCol();

        if (moveRight) {
            if (c + 1 < maze[0].length && maze[r][c + 1] != '#') {
                getLoc().incC(1);
            } else {
                moveRight = false;
            }
        } else {
            if (c - 1 >= 0 && maze[r][c - 1] != '#') { //moves left until it hits a wall
                getLoc().incC(-1);
            } else {
                moveRight = true;
            }
        }
    }
}
