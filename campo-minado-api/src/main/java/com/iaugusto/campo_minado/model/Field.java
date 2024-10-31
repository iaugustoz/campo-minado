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

    public boolean isMarked() {
        return marked;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !isOpen();
    }
}
