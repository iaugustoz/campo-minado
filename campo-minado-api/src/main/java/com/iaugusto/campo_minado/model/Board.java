package com.iaugusto.campo_minado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private int lines;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        associateNeighbors();
        drawMines();
    }

    public void open(int line, int column) {
        fields.parallelStream()
                .filter(f -> f.getLine() == line && f.getColumn() == column)
                .findFirst()
                .ifPresent(f ->  f.opened());
    }

    public void mark(int line, int column) {
        fields.parallelStream()
                .filter(f -> f.getLine() == line && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.toogleTag());
    }


    private void generateFields() {
        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                fields.add(new Field(line, column));
            }
        }
    }

    private void associateNeighbors() {
        for (Field f1: fields) {
            for (Field f2: fields) {
                f1.addNeighbors(f2);
            }
        }
    }

    private void drawMines() {
        long armedMines = 0;
        Predicate<Field> mined = f -> f.isMined();

        do {
            armedMines = fields.stream().filter(mined).count();
            int random = (int) (Math.random() * fields.size());
            fields.get(random).mine();

        } while (armedMines < mines);
    }

    public boolean objectiveAchieved() {
        return fields.stream().allMatch(f -> f.objectiveAchieved());
    }

    public void restart() {
        fields.stream().forEach(f -> f.restart());
        drawMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (int l = 0; l < lines; l++) {
            for (int c = 0; c < columns; c++) {
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
