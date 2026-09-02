package br.com.yuri.poo.service;

import br.com.yuri.poo.entity.Perfil;
import br.com.yuri.poo.entity.Usuario;
import br.com.yuri.poo.repository.PerfilRepository;
import br.com.yuri.poo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    public Optional<Perfil> buscarPorId(Long id) {
        return perfilRepository.findById(id);
    }

    public Perfil salvar(Long usuarioId, Perfil perfil) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado com id: " + usuarioId));

        if (usuario.getPerfil() != null) {
            throw new IllegalStateException("Usuario " + usuarioId + " ja possui um perfil cadastrado");
        }

        perfil.setUsuario(usuario);
        return perfilRepository.save(perfil);
    }

    public Perfil atualizar(Long id, Perfil dadosAtualizados) {
        return perfilRepository.findById(id)
                .map(perfil -> {
                    perfil.setBio(dadosAtualizados.getBio());
                    perfil.setAvatarUrl(dadosAtualizados.getAvatarUrl());
                    return perfilRepository.save(perfil);
                })
                .orElseThrow(() -> new IllegalArgumentException("Perfil nao encontrado com id: " + id));
    }

    public void deletar(Long id) {
        perfilRepository.deleteById(id);
    }
}
