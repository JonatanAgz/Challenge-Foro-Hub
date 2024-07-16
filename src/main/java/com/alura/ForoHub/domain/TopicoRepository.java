package com.alura.ForoHub.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    Page<Topico> findByEstadoTrue(Pageable paginacion);
}
