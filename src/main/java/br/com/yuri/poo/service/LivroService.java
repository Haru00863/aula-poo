package br.com.yuri.poo.service;

import br.com.yuri.poo.entity.Autor;
import br.com.yuri.poo.entity.Livro;
import br.com.yuri.poo.repository.AutorRepository;
import br.com.yuri.poo.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> listarPorAutor(Long autorId) {
        return livroRepository.findByAutorId(autorId);
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Livro salvar(Long autorId, Livro livro) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new IllegalArgumentException("Autor nao encontrado com id: " + autorId));
        livro.setAutor(autor);
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro dadosAtualizados) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setTitulo(dadosAtualizados.getTitulo());
                    livro.setAnoPublicacao(dadosAtualizados.getAnoPublicacao());
                    return livroRepository.save(livro);
                })
                .orElseThrow(() -> new IllegalArgumentException("Livro nao encontrado com id: " + id));
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }
}
