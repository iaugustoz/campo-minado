package com.iaugusto.campo_minado.view;

import com.iaugusto.campo_minado.exception.ExplosionException;
import com.iaugusto.campo_minado.exception.GoOutException;
import com.iaugusto.campo_minado.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardConsole {

    private Board board;
    private Scanner input = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;

        runGame();
    }

    private void runGame() {
        try {
            boolean proceed = true;

            while (proceed) {
                gameCycle();

                System.out.println("Outra partida? (S/n)");
                String response = input.nextLine();

                if ("n".equalsIgnoreCase(response)) {
                    proceed = false;
                }
            }
        } catch (GoOutException e) {
            System.out.println("Tchau!!!");
        } finally {
            input.close();
        }
    }

    private void gameCycle() {
        try {
            while (!board.objectiveAchieved()) {
                System.out.println(board);

                String digitated = captureDigitatedValue("Digite (x, y): ");

                Iterator<Integer> xy = Arrays.stream(digitated.split(","))
                        .map(e -> Integer.parseInt(e.trim()))
                        .iterator();

                digitated = captureDigitatedValue("1 - Abrir ou 2 - (Des)Marcar: ");

                if ("1".equals(digitated)) {
                    board.open(xy.next(), xy.next());
                } else if ("2".equals(digitated)) {
                    board.mark(xy.next(), xy.next());
                }
            }
            System.out.println(board);
            System.out.println("Você ganhou!");

        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("Você perdeu");
        }
    }

    private String captureDigitatedValue(String text) {
        System.out.print(text);
        String digitated = input.nextLine();

        if ("sair".equalsIgnoreCase(digitated)) {
            throw new GoOutException();
        }

        return digitated;
    }

}
