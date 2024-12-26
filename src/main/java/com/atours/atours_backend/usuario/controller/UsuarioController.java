package com.atours.atours_backend.usuario.controller;

import com.atours.atours_backend.usuario.dto.UsuarioDTO;
import com.atours.atours_backend.usuario.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para gestionar operaciones relacionadas con usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    /**
     * Constructor que inicializa el servicio de usuario.
     *
     * @param usuarioService Servicio de usuario.
     */
    @Autowired
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param usuarioDTO DTO con la información del usuario a crear.
     * @return El DTO del usuario creado.
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO createdUsuario = usuarioService.crearUsuario(usuarioDTO);
        return ResponseEntity.ok(createdUsuario);
    }

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id Identificador del usuario.
     * @return El DTO del usuario si se encuentra, o una respuesta 404 si no.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<UsuarioDTO> usuario = usuarioService.obtenerUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param id         Identificador del usuario a actualizar.
     * @param usuarioDTO DTO con la información actualizada del usuario.
     * @return El DTO del usuario actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO updatedUsuario = usuarioService.actualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(updatedUsuario);
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id Identificador del usuario a eliminar.
     * @return Una respuesta sin contenido si la eliminación es exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Añade una ruta favorita a la lista de rutas favoritas de un usuario.
     *
     * @param id     Identificador del usuario.
     * @param rutaId Identificador de la ruta a añadir.
     * @return El DTO del usuario actualizado con la ruta añadida.
     */
    @PostMapping("/{id}/rutas-favoritas/{rutaId}")
    public ResponseEntity<UsuarioDTO> addRutaFavoritaToUsuario(
            @PathVariable Long id,
            @PathVariable Long rutaId) {
        UsuarioDTO updatedUsuario = usuarioService.addRutaFavoritaToUsuario(id, rutaId);
        return ResponseEntity.ok(updatedUsuario);
    }

    /**
     * Elimina una ruta favorita de la lista de rutas favoritas de un usuario.
     *
     * @param id     Identificador del usuario.
     * @param rutaId Identificador de la ruta a eliminar.
     * @return Una respuesta sin contenido si la eliminación es exitosa.
     */
    @DeleteMapping("/{id}/rutas-favoritas/{rutaId}")
    public ResponseEntity<Void> removeRutaFavoritaFromUsuario(
            @PathVariable Long id,
            @PathVariable Long rutaId) {
        usuarioService.removeRutaFavoritaFromUsuario(id, rutaId);
        return ResponseEntity.noContent().build();
    }
}
