package com.alura.ForoHub.controller;

import com.alura.ForoHub.domain.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder){
        // Verificar si ya existe un tópico con el mismo título y mensaje
        Optional<Topico> existente = topicoRepository.findByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje());

        if (existente.isPresent()) {
            // Si ya existe, devolver un error de conflicto
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            // Si no existe, guardar el nuevo tópico
            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));
            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                    topico.getFechaCreacion(), topico.getEstado(), topico.getAutor(), topico.getCurso());
            URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(url).body(datosRespuestaTopico);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Topico>> listarTopicos(@PageableDefault(size = 5) Pageable paginacion){
//        return topicoRepository.findAll(paginacion);
        return ResponseEntity.ok(topicoRepository.findByEstadoTrue(paginacion));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico){

        Optional<Topico> topicoExistente = topicoRepository.findById(id);

        if (topicoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado.");
        }

        // Actualizar el tópico existente
        Topico topico = topicoExistente.get();
        topico.actualizarDatos(datosActualizarTopico);
        topicoRepository.save(topico);

        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion(), topico.getEstado(), topico.getAutor(), topico.getCurso()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoExistente = topicoRepository.findById(id);

        if (topicoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado.");
        }
        Topico topico = topicoExistente.get();
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }

    //Mostrar tópico individual
    @GetMapping("/{id}")
    public ResponseEntity retornaDatosTopico(@PathVariable Long id) {
        Optional<Topico> topicoExistente = topicoRepository.findById(id);

        if (topicoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado.");
        }
        Topico topico = topicoExistente.get();
        var datosTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion(), topico.getEstado(), topico.getAutor(), topico.getCurso());
        return ResponseEntity.ok(datosTopico);
    }
}
