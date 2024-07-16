package com.alura.ForoHub.domain;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(

        String titulo,
        String mensaje,
        String curso ) {
}
