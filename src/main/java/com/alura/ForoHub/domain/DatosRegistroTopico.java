package com.alura.ForoHub.domain;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DatosRegistroTopico(

        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotBlank
        String autor,

        @NotBlank
        String curso ) {
}
