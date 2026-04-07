package br.com.bibliotecadofrancisco.bibliotecaapi.model;

/**
 * Representa um conjunto fixo de constantes.
 * Por que usar Enum?
 * Para garantir que o banco só aceite valores específicos e evitar erros de digitação.
 */
public enum Categoria {
    FICCAO, NAO_FICCAO, ROMANCE, TERROR, CIENTIFICO
}