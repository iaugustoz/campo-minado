package com.iaugusto.campo_minado.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void must() {
        Field notNeighbot = new Field(1, 1);
        boolean result = field.addNeighbors(notNeighbot);

        assertFalse(result);
    }
}