package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Geralmente usa-se 'id' minúsculo para convenção
    private Long gutendexId;
    private String titulo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();
    private String idioma;
    private Integer numeroDownloads; // Mantido como numeroDownloads, mas verifique seu record LivroData

    public Livro() {}

    public Livro(LivroData livroData) {
        this.titulo = livroData.titulo();
        this.gutendexId = livroData.idGutendex();
        this.idioma = livroData.idiomas() != null && !livroData.idiomas().isEmpty() ? livroData.idiomas().get(0) : "N/A";
        // CORRIGIDO: Use 'numeroDownloads' aqui, e verifique se o LivroData tem 'numeroDownloads()' ou 'contagemDownloads()'
        // Se no seu LivroData o campo for 'contagemDownloads', então você precisará:
        // this.numeroDownloads = livroData.contagemDownloads();
        // Caso contrário, use:
        this.numeroDownloads = livroData.contagemDownloads(); // Supondo que LivroData.numeroDownloads() existe
        this.autores = new HashSet<>(); // Inicializa vazia, autores serão adicionados no serviço
    }

    @SuppressWarnings("unused")
    public void addAutor(Autor autor) {
        this.autores.add(autor);
        // Garante a bidirecionalidade: adiciona este livro à lista de livros do autor
        if (!autor.getLivros().contains(this)) {
            autor.getLivros().add(this);
        }
    } // CORRIGIDO: Removida a chave } extra aqui.

    public Long getId() {
        return id; // CORRIGIDO: Usa 'id' minúsculo
    }

    public void setId(Long id) {
        this.id = id; // CORRIGIDO: Usa 'this.id' para se referir ao campo
    }

    public Long getGutendexId() {
        return gutendexId;
    }

    public void setGutendexId(Long gutendexId) {
        this.gutendexId = gutendexId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        String nomesAutores = autores.stream()
                .map(Autor::getNome)
                .collect(Collectors.joining(", "));
        return String.format("----- LIVRO -----%nTitulo: %s%nAutores: %s%nIdioma: %s%nDownloads: %d%n--------------%n",
                titulo, nomesAutores, idioma, numeroDownloads);
    }
}