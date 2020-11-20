package com.banco.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.nonNull;

/**
 * Domínio para os campos do tipo S/N.
 */
@Getter
@RequiredArgsConstructor
public enum Indicador {

    S(Boolean.TRUE),
    N(Boolean.FALSE);

    private final Boolean valor;

    public static Indicador getIndicador(Boolean valor) {
        for (Indicador indicador : Indicador.values()) {
            if (indicador.getValor().equals(valor)) {
                return indicador;
            }
        }
        return null;
    }

    public static Indicador getIndicador(final String indicador) {
        return getIndicador("S".equals(indicador));
    }

    public static boolean getValue(final Boolean value) {
        return nonNull(value) && value;
    }

    public static boolean getValue(final Indicador indicador) {
        return nonNull(indicador) && indicador.getValor();
    }

    public Indicador not() {
        return Indicador.getIndicador(!this.valor);
    }

}