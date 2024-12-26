package org.cis1200.mazerunner;

public class Location {
    private int row;
    private int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /* Getters */

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /* Incrementers */

    public int incR(int x) {
        return row += x;
    }

    public int incC(int x) {
        return col += x;
    }

    /* Setters */

    public void set(int newRow, int newCol) {
        row = newRow;
        col = newCol;
    }

    public boolean equals(Location other) {
        if (this.row == other.getRow() && this.col == other.getCol()) {
            return true;
        }
        return false;
    }


}
