// br.com.alura.literalura.model.Autor
package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    // Adicionado CascadeType.PERSIST e CascadeType.MERGE aqui também
    // para garantir que, se um livro for adicionado a um autor já gerenciado,
    // o livro seja tratado corretamente.
    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Livro> livros = new HashSet<>();

    @SuppressWarnings("unused")
    public Autor() {}

    public Autor(AutorData autorData) {
        this.nome = autorData.nome();
        this.anoNascimento = autorData.anoNascimento();
        this.anoFalecimento = autorData.anoFalecimento();
    }

    public void addLivro(Livro livro) {
        this.livros.add(livro);
        if (!livro.getAutores().contains(this)) {
            livro.getAutores().add(this);
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getAnoNascimento() { return anoNascimento; }
    public void setAnoNascimento(Integer anoNascimento) { this.anoNascimento = anoNascimento; }
    public Integer getAnoFalecimento() { return anoFalecimento; }
    public void setAnoFalecimento(Integer anoFalecimento) { this.anoFalecimento = anoFalecimento; }
    public Set<Livro> getLivros() { return livros; }
    public void setLivros(Set<Livro> livros) { this.livros = livros; }

    @Override
    public String toString() {
        return "Autor{" +
                "nome='" + nome + '\'' +
                ", anoNascimento=" + anoNascimento +
                ", anoFalecimento=" + anoFalecimento +
                '}';
    }
}

