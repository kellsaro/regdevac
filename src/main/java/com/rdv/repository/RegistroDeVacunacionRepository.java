package com.rdv.repository;

import com.rdv.domain.RegistroDeVacunacion;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RegistroDeVacunacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistroDeVacunacionRepository extends JpaRepository<RegistroDeVacunacion, Long> {
    @Query(
        "select registroDeVacunacion from RegistroDeVacunacion registroDeVacunacion where registroDeVacunacion.user.login = ?#{principal.username}"
    )
    List<RegistroDeVacunacion> findByUserIsCurrentUser();
}
