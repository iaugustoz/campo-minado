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
        Field neighbor11 = new Field(1,1);
        Field neighbor12 = new Field(1,1);

        neighbor12.mine();

        Field neighbor22 = new Field(2,2);
        neighbor22.addNeighbors(neighbor11);
        neighbor22.addNeighbors(neighbor12);

        field.addNeighbors(neighbor22);
        field.opened();

        assertTrue(neighbor22.isOpen() && neighbor11.isClosed());
    }
}