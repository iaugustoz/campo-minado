package com.iaugusto.campo_minado.model;

import com.iaugusto.campo_minado.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int line;
    private final int column;

    private boolean open = false;
    private boolean mined = false;
    private boolean marked = false;
    private List<Field> neighbors = new ArrayList<>();

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    boolean addNeighbors(Field neighbor) {
        boolean differentLine = line != neighbor.line;
        boolean differentColumn = column != neighbor.column;

        boolean diagonal = differentLine && differentColumn;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColumn = Math.abs(column - neighbor.column);
        int deltaTotal = deltaLine + deltaColumn;

        if (deltaTotal == 1 && !diagonal) {
            neighbors.add(neighbor);
            return true;

        } else if (deltaTotal == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;

        }
    }

    void toogleTag() {
        if (!open) {
            marked = !marked;
        }
    }

    boolean opened() {
        if (!open && !marked) {
            open = true;

            if (mined) {
                throw new ExplosionException();
            }

            if (safeNeighborhood()) {
                neighbors.forEach(Field::opened);
            }
            return true;

        } else {
            return false;

        }
    }

    boolean safeNeighborhood() {
        return neighbors.stream().noneMatch(v -> v.mined);
    }

    void mine() {
        mined = true;
    }

    public boolean isMined() {
        return mined;
    }

    public boolean isMarked() {
        return marked;
    }

    void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    boolean objectiveAchieved() {
        boolean fieldUnveiled = !mined && open;
        boolean fieldProtected = mined && marked;

        return fieldUnveiled || fieldProtected;
    }

    long minesInTheNeighborhood() {
        return neighbors.stream().filter(v -> v.mined).count();
    }

    void restart() {
        open = false;
        mined = false;
        marked = false;
    }

    public String toString() {
        if (marked) {
            return "x";
        } else if (open && mined) {
            return "*";
        } else if (open && minesInTheNeighborhood() > 0) {
            return Long.toString(minesInTheNeighborhood());
        } else if (open) {
            return " ";
        } else {
            return "?";
        }
    }
}
