package com.atours.atours_backend.usuario.service;

import com.atours.atours_backend.ruta.domain.Ruta;
import com.atours.atours_backend.ruta.domain.repository.RutaRepository;
import com.atours.atours_backend.usuario.domain.Usuario;
import com.atours.atours_backend.usuario.domain.mapper.UsuarioMapper;
import com.atours.atours_backend.usuario.domain.repository.UsuarioRepository;
import com.atours.atours_backend.usuario.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Implementación de la interfaz IUsuarioService para gestionar operaciones relacionadas con usuarios.
 */
@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RutaRepository rutaRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor que inicializa los repositorios, el mapper y el codificador de contraseñas.
     *
     * @param usuarioRepository Repositorio de usuarios.
     * @param rutaRepository    Repositorio de rutas.
     * @param usuarioMapper     Mapper para convertir entre entidades y DTOs de usuario.
     * @param passwordEncoder   Codificador de contraseñas.
     */
    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RutaRepository rutaRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rutaRepository = rutaRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Crea un nuevo usuario y lo guarda en la base de datos.
     *
     * @param usuarioRequestDTO DTO con la información del usuario a crear.
     * @return El DTO del usuario creado.
     */
    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioRequestDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(savedUsuario);
    }

    /**
     * Obtiene un usuario por su identificador.
     *
     * @param id Identificador del usuario.
     * @return Un Optional que contiene el DTO del usuario si se encuentra, o vacío si no.
     */
    @Override
    public Optional<UsuarioDTO> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDto);
    }

    /**
     * Obtiene un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario.
     * @return Un Optional que contiene el DTO del usuario si se encuentra, o vacío si no.
     */
    @Override
    public Optional<UsuarioDTO> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDto);
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param id                Identificador del usuario a actualizar.
     * @param usuarioRequestDTO DTO con la información actualizada del usuario.
     * @return El DTO del usuario actualizado.
     * @throws ResponseStatusException si el usuario no se encuentra.
     */
    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioRequestDTO) {
        Usuario usuarioActualizado = usuarioMapper.toEntity(usuarioRequestDTO);
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
            Usuario updatedUsuario = usuarioRepository.save(usuario);
            return usuarioMapper.toDto(updatedUsuario);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id Identificador del usuario a eliminar.
     * @throws ResponseStatusException si el usuario no se encuentra.
     */
    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Añade una ruta favorita a la lista de rutas favoritas de un usuario.
     *
     * @param usuarioId Identificador del usuario.
     * @param rutaId    Identificador de la ruta a añadir.
     * @return El DTO del usuario actualizado con la ruta añadida.
     * @throws ResponseStatusException si el usuario o la ruta no se encuentran.
     */
    @Override
    public UsuarioDTO addRutaFavoritaToUsuario(Long usuarioId, Long rutaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada"));
        usuario.getRutasFavoritas().add(ruta);
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(updatedUsuario);
    }

    /**
     * Elimina una ruta favorita de la lista de rutas favoritas de un usuario.
     *
     * @param usuarioId Identificador del usuario.
     * @param rutaId    Identificador de la ruta a eliminar.
     * @throws ResponseStatusException si el usuario o la ruta no se encuentran.
     */
    @Override
    public void removeRutaFavoritaFromUsuario(Long usuarioId, Long rutaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada"));
        usuario.getRutasFavoritas().remove(ruta);
        usuarioRepository.save(usuario);
    }
}
