package br.com.yuri.poo.service;

import br.com.yuri.poo.entity.Usuario;
import br.com.yuri.poo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario dadosAtualizados) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(dadosAtualizados.getNome());
                    usuario.setEmail(dadosAtualizados.getEmail());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado com id: " + id));
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
