package com.rdv.domain.enumeration;

/**
 * The TipoDeVacuna enumeration.
 */
public enum TipoDeVacuna {
    SPUTNIK("Sputnik"),
    ASTRAZENECA("AstraZeneca"),
    PFIZER("Pfizer"),
    JHONSONANDJHONSON("Jhonson &amp; Jhonson");

    private final String value;

    TipoDeVacuna(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
