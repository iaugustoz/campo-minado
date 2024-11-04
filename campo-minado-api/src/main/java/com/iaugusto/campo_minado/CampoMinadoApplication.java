package com.iaugusto.campo_minado;

import com.iaugusto.campo_minado.model.Board;

public class CampoMinadoApplication {

    public static void main(String[] args) {
        Board board = new Board(6, 6, 6);

        System.out.println(board);
    }

}
