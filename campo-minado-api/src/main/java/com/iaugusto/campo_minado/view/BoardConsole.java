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
            showIntroduction();

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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            input.close();
        }
    }

    public static void writeWithEffect(String mensagem) throws InterruptedException {
        for (char caractere : mensagem.toCharArray()) {
            System.out.print(caractere);
            Thread.sleep(25);
        }
    }

    private void showIntroduction() throws InterruptedException {
        writeWithEffect("===============================================\n");
        writeWithEffect("🎉 Bem-vindo ao Campo Minado no Console! 🎉\n");
        writeWithEffect("===============================================\n\n");

        writeWithEffect("💣 OBJETIVO DO JOGO 💣\n");
        writeWithEffect("---------------------\n");
        writeWithEffect("O campo minado é um jogo de estratégia onde seu objetivo é\n");
        writeWithEffect("limpar um campo de minas sem explodir nenhuma bomba! 🧨\n");
        writeWithEffect("Cada célula do tabuleiro pode conter uma bomba ou um número\n");
        writeWithEffect("indicando quantas bombas estão ao seu redor. Cuidado onde pisa!\n\n");

        writeWithEffect("🔍 COMO JOGAR 🔍\n");
        writeWithEffect("----------------\n");
        writeWithEffect("1. Escolha uma posição no tabuleiro informando a linha e a coluna.\n");
        writeWithEffect("2. Se a posição escolhida estiver limpa, você verá um número que indica\n");
        writeWithEffect("   quantas bombas estão ao redor.\n");
        writeWithEffect("3. Continue abrindo posições, mas lembre-se: o perigo está por todos os lados!\n");
        writeWithEffect("4. Se você abrir uma posição com uma bomba... BOOM! 💥 Game Over.\n");
        writeWithEffect("5. Vença o jogo limpando todas as células sem bombas.\n\n");

        writeWithEffect("===============================================\n");
        writeWithEffect("🌟 Boa sorte! Que comece o desafio do Campo Minado! 🌟\n");
        writeWithEffect("===============================================\n");
    }

    private void gameCycle() {
        try {
            while (!board.objectiveAchieved()) {
                System.out.println("\n");
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
