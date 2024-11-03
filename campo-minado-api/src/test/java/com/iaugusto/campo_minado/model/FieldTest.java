package com.iaugusto.campo_minado.model;

import com.iaugusto.campo_minado.exception.ExplosionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field(3, 3);
    }

    @Test
    @DisplayName("Deve verificar se a distância entre os vizinhos em um campo minado é de 1 casa com sucesso")
    void mustCheckIfTheDistanceBetweenNeighborsIsOneSuccessfully() {
        Field neighbor = new Field(3, 2);
        boolean result = field.addNeighbors(neighbor);

        assertTrue(result);
    }

    @Test
    @DisplayName("Deve verificar se a distância entre os vizinhos em um campo minado é de 2 casa com sucesso")
    void mustCheckIfTheDistanceBetweenNeighborsIsTwoSuccessfully() {
        Field neighbor = new Field(2, 2);
        boolean result = field.addNeighbors(neighbor);

        assertTrue(result);
    }

    @Test
    @DisplayName("Deve verificar se os campos não são vizinhos com sucesso")
    void mustCheckIfFieldsAreNotNeighborsSuccessfully() {
        Field notNeighbot = new Field(1, 1);
        boolean result = field.addNeighbors(notNeighbot);

        assertFalse(result);
    }

    @Test
    @DisplayName("Deve alterar a marcação com sucesso")
    void mustChangeTheMarkupSuccessfully() {
        field.toogleTag();
        assertTrue(field.isMarked());
    }

    @Test
    @DisplayName("Deve verificar o valor padrão do atributo marcado com sucesso")
    void mustCheckDefaultValueSuccessfullyMarkedAttribute() {
        assertFalse(field.isMarked());
    }

    @Test
    @DisplayName("Deve desmarcar um campo já marcado com sucesso")
    void mustUncheckFieldThatHasAlreadyBeenSuccessfullyChecked() {
        field.toogleTag();
        field.toogleTag();
        assertFalse(field.isMarked());
    }

    @Test
    @DisplayName("Deve abrir um campo não marcado e não minado com sucesso")
    void mustOpenUnmarkedFieldAndUnminedFieldSuccessfully() {
        assertTrue(field.opened());
    }

    @Test
    @DisplayName("Não deve abrir um campo marcado com sucesso")
    void mustNotOpenFieldMarkedSuccessfully() {
        field.toogleTag();
        assertFalse(field.opened());
    }

    @Test
    @DisplayName("Não deve abrir um campo marcado e minado com sucesso")
    void mustNotOpenFieldMarkedAndMinedFieldSuccessfully() {
        field.toogleTag();
        field.mine();

        assertFalse(field.opened());
    }

    @Test
    @DisplayName("Deve lançar uma ExplosionException ao abrir um campo minado com sucesso")
    void mustThrowExplosionExceptionWhenOpeningMinefieldSuccessfully() {
        ExplosionException e = assertThrows(ExplosionException.class,
                () -> {
                    field.mine();
                    field.opened();
                }
        );
    }

    @Test
    @DisplayName("Deve abrir os campos adjacentes caso a vizinhança esteja segura")
    void mustOpenAdjacentFieldsIfTheNeighborhoodIsSafe() {
        Field neighbor11 = new Field(1, 1);
        Field neighbor22 = new Field(2, 2);

        neighbor22.addNeighbors(neighbor11);

        field.addNeighbors(neighbor22);
        field.opened();

        assertTrue(neighbor22.isOpen() && neighbor11.isOpen());
    }

    @Test
    @DisplayName("Não deve abrir os campos adjacentes que estão minados")
    void mustNotOpenAdjacentFieldsThatAreMined() {
        Field neighbor11 = new Field(1, 1);
        Field neighbor12 = new Field(1, 1);

        neighbor12.mine();

        Field neighbor22 = new Field(2, 2);
        neighbor22.addNeighbors(neighbor11);
        neighbor22.addNeighbors(neighbor12);

        field.addNeighbors(neighbor22);
        field.opened();

        assertTrue(neighbor22.isOpen() && neighbor11.isClosed());
    }

    @Test
    @DisplayName("Deve revelar e proteger um campo com sucesso")
    void mustSuccessfullyRevealAndProtectField() {
        field.opened();
        boolean result = field.objectiveAchieved();

        assertTrue(result);
    }

    @Test
    @DisplayName("Deve esperar uma mina nos vizinhos e retornar a contagem de minas na vizinhança com sucesso")
    void mustWaitForMineInTheNeighborsAndReturnCountOfMinesInVicinitySuccessfully() {
        Field fieldMined1 = new Field(3, 4);
        Field fieldMined2 = new Field(4, 3);

        fieldMined1.mine();

        field.addNeighbors(fieldMined1);
        field.addNeighbors(fieldMined2);

        long amountMines = field.minesInTheNeighborhood();

        assertEquals(1L, amountMines);
        assertNotNull(amountMines);
    }

    @Test
    @DisplayName("Deve esperar zero minas nos vizinhos com sucesso")
    void mustExpectZeroMinesOnNeighborsWithSuccess() {
        Field neighbor1 = new Field(3, 4);
        Field neighbor2 = new Field(4, 3);

        field.addNeighbors(neighbor1);
        field.addNeighbors(neighbor2);

        assertEquals(0, field.minesInTheNeighborhood(), "Esperado 0 minas nos vizinhos.");
    }


    @Test
    @DisplayName("Deve reiniciar o jogo com sucesso")
    void mustRestartTheGameSuccessfully() {
        field.opened();
        field.mine();
        field.toogleTag();

        field.restart();

        assertFalse(field.isOpen());
        assertFalse(field.isMarked());
    }

    @Test
    @DisplayName("Deve retornar o símbolo correspondente ao campo marcado com sucesso")
    void mustReturntheSymbolCorrespondingFieldMarkedSuccessfully() {
        field.toogleTag();
        String result = field.toString();

        assertEquals("x", result);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Deve retornar o símbolo correspondente ao campo aberto e minado com sucesso")
    void mustReturntheSymbolCorrespondingFieldOpenedAndMinedSuccessfully() {
        field.opened();
        field.mine();

        String result = field.toString();

        assertEquals("*", result);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Deve retornar o símbolo correspondente a quantidade com sucesso")
    void mustReturntheSymbolCorrespondingFieldAmountSfuccessfully() {
        Field fieldMined1 = new Field(3, 4);
        Field fieldMined2 = new Field(4, 3);

        fieldMined1.mine();

        field.addNeighbors(fieldMined1);
        field.addNeighbors(fieldMined2);

        field.opened();

        String result = field.toString();

        assertEquals("1", result);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Deve retornar um espaço vazio quando o campo estiver aberto")
    void mustReturnEmptySpaceWhenFieldIsOpen() {
        field.opened();

        String result = field.toString();

        assertEquals(" ", result);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Deve retornar o símbolo de interrogação quando for a exceção")
    void mustReturnQuestionMarkWhenItIsException() {
        String result = field.toString();

        assertEquals("?", result);
        assertNotNull(result);
    }
}