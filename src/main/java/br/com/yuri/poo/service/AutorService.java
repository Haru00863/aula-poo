package br.com.yuri.poo.service;

import br.com.yuri.poo.entity.Autor;
import br.com.yuri.poo.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor atualizar(Long id, Autor dadosAtualizados) {
        return autorRepository.findById(id)
                .map(autor -> {
                    autor.setNome(dadosAtualizados.getNome());
                    autor.setNacionalidade(dadosAtualizados.getNacionalidade());
                    return autorRepository.save(autor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Autor nao encontrado com id: " + id));
    }

    public void deletar(Long id) {
        autorRepository.deleteById(id);
    }
}
