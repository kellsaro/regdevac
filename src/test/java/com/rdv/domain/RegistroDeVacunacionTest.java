package com.rdv.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rdv.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegistroDeVacunacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroDeVacunacion.class);
        RegistroDeVacunacion registroDeVacunacion1 = new RegistroDeVacunacion();
        registroDeVacunacion1.setId(1L);
        RegistroDeVacunacion registroDeVacunacion2 = new RegistroDeVacunacion();
        registroDeVacunacion2.setId(registroDeVacunacion1.getId());
        assertThat(registroDeVacunacion1).isEqualTo(registroDeVacunacion2);
        registroDeVacunacion2.setId(2L);
        assertThat(registroDeVacunacion1).isNotEqualTo(registroDeVacunacion2);
        registroDeVacunacion1.setId(null);
        assertThat(registroDeVacunacion1).isNotEqualTo(registroDeVacunacion2);
    }
}
