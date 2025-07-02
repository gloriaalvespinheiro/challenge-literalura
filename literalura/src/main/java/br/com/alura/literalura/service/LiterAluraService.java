package br.com.alura.literalura.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets; // Importe também para especificar o charset
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.model.LivroData;
import br.com.alura.literalura.model.AutorData;
import br.com.alura.literalura.model.GutendexResponse;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importe esta anotação

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LiterAluraService {

    private static final String API_BASE_URL = "https://gutendex.com/books/";

    @Autowired
    private ApiConsumer apiConsumer;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Transactional // Garante que toda a operação ocorra em uma única transação
    public void buscarLivroPorTitulo(String titulo) {
        try {
            String tituloCodificado = URLEncoder.encode(titulo, StandardCharsets.UTF_8.toString());
            String url = API_BASE_URL + "?search=" + tituloCodificado;

            GutendexResponse response = apiConsumer.getData(url);

            // TODO O CÓDIGO ABAIXO ESTAVA FORA DESTE BLOCO 'IF' E DO 'TRY'
            // AGORA ESTÁ DENTRO DA LÓGICA ESPERADA

            if (response != null && !response.resultados().isEmpty()) {
                Optional<LivroData> livroDataOptional = response.resultados().stream()
                        // Tenta encontrar uma correspondência exata primeiro (ignorando maiúsculas/minúsculas)
                        .filter(l -> l.titulo().equalsIgnoreCase(titulo))
                        .findFirst();

                if (livroDataOptional.isEmpty()) {
                    // Se não encontrou correspondência exata, tenta encontrar uma que contenha o título
                    livroDataOptional = response.resultados().stream()
                            .filter(l -> l.titulo().toLowerCase().contains(titulo.toLowerCase()))
                            .findFirst();
                }
                if (livroDataOptional.isEmpty()) {
                    System.out.println("Nenhum livro encontrado com o título exato ou similar: '" + titulo + "' na API Gutendex.");
                    return; // Sai do método se não encontrar uma correspondência
                }

                LivroData livroData = livroDataOptional.get(); // Pega o livroData filtrado
                // --- FIM DA MUDANÇA ---

                Optional<Livro> livroExistente = livroRepository.findByTituloContainsIgnoreCase(livroData.titulo());

                if (livroExistente.isPresent()) {
                    System.out.println("Livro já registrado no banco de dados: " + livroExistente.get().getTitulo());
                } else {
                    Livro livro = new Livro();
                    livro.setTitulo(livroData.titulo());
                    livro.setGutendexId(livroData.idGutendex());
                    livro.setIdioma(livroData.idiomas() != null && !livroData.idiomas().isEmpty() ? livroData.idiomas().get(0) : "N/A");
                    livro.setNumeroDownloads(livroData.contagemDownloads());

                    if (livroData.autores() != null) {
                        for (AutorData autorDataDaApi : livroData.autores()) {
                            Optional<Autor> autorExistenteNoDB = autorRepository.findByNomeContainsIgnoreCase(autorDataDaApi.nome());

                            Autor autorParaAssociar;
                            if (autorExistenteNoDB.isPresent()) {
                                autorParaAssociar = autorExistenteNoDB.get();
                            } else {
                                autorParaAssociar = new Autor(autorDataDaApi);
                            }
                            livro.addAutor(autorParaAssociar);
                        }
                    }

                    livroRepository.save(livro);
                    System.out.println("Livro salvo com sucesso no banco de dados!");
                    System.out.println(livro);
                }
            } else {
                System.out.println("Nenhum livro encontrado com o título: '" + titulo + "' na API Gutendex.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar livro na API: " + e.getMessage());
            e.printStackTrace(); // É bom para debug, mas pode remover em produção
        }
    }

    // --- MÉTODOS DE LISTAGEM RESTAURADOS ---

    public void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            System.out.println("\n----- LIVROS REGISTRADOS -----");
            livros.forEach(System.out::println);
            System.out.println("------------------------------\n");
        }
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("\n----- AUTORES REGISTRADOS -----");
            autores.forEach(System.out::println);
            System.out.println("-------------------------------\n");
        }
    }

    public void listarAutoresVivosEmAno(Integer ano) {
        List<Autor> autores = autorRepository.findAutoresVivosEmAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo em " + ano + " encontrado.");
        } else {
            System.out.println("\n----- AUTORES VIVOS EM " + ano + " -----");
            autores.forEach(System.out::println);
            System.out.println("-------------------------------\n");
        }
    }

    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idioma);
        } else {
            System.out.println("\n----- LIVROS POR IDIOMA: " + idioma.toUpperCase() + " -----");
            livros.forEach(System.out::println);
            System.out.println("----------------------------------------\n");
        }
    }
}