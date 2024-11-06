package com.iaugusto.campo_minado;

import com.iaugusto.campo_minado.model.Board;
import com.iaugusto.campo_minado.view.BoardConsole;

public class CampoMinadoApplication {

    public static void main(String[] args) {
        Board board = new Board(6, 6, 6);
        new BoardConsole(board);
    }

}
