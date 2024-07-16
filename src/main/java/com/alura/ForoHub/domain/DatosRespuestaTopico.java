package com.alura.ForoHub.domain;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean estado,
        String autor,
        String curso ) {
}
