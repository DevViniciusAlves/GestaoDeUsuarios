package com.usuarios.GestaoDeUsuarios.service;

import java.util.List;

import com.usuarios.GestaoDeUsuarios.model.Usuario;
import com.usuarios.GestaoDeUsuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Criando o usuario
    public Usuario criarUsuario(Usuario usuario) {
        // Validando email se for duplicado
        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email ja está cadastrado");
                });

        // Garantindo que nasce ativo
        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }
    // Listar usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    // Buscando por ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }
    // Desativando um usuario
    public Usuario desativarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        return usuarioRepository.save(usuario);
    }
    // Ativando um usuario
    public Usuario ativarUsuario(Long id){
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }
    // Atualizar um usuario
    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioAtualizado = buscarPorId(id);
        usuarioAtualizado.setEmail(usuario.getEmail());
        usuarioAtualizado.setNome(usuario.getNome());
        usuarioAtualizado.setSenha(usuario.getSenha());
        return usuarioRepository.save(usuarioAtualizado);
    }
}
