package br.com.alura.literalura;

import br.com.alura.literalura.service.ConverteDados;
import br.com.alura.literalura.service.LiterAluraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class Principal implements CommandLineRunner {

    @Autowired
    private LiterAluraService literAluraService;

    private ConverteDados conversor = new ConverteDados();
    private Scanner teclado = new Scanner(System.in);
    private String json;

    private String menu = """
            -------------
            
            Escolha o número da sua opção:  
                     
            1 - Buscar livro pelo título
            2 - Listar livros registrados
            3 - Listar autores registrados
            4 - Listar autores vivos em determinado ano
            5 - Listar livros em um determinado idioma
            0 - Sair
            """;

    public static void main(String[] args) {
        SpringApplication.run(Principal.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int option = -1;

        while (option != 0) {
            System.out.println(menu);
            try {
                option = teclado.nextInt();
                teclado.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Digite o título do livro: ");
                        String titulo = teclado.nextLine(); // Usando 'teclado'
                        literAluraService.buscarLivroPorTitulo(titulo);
                        // Exemplo de uso do 'json' (se for pego de algum lugar)
                        // this.json = apiConsumer.getData(url); // Assumindo que ApiConsumer retorna o JSON raw
                        // GutendexResponse dados = conversor.obterDados(this.json, GutendexResponse.class);
                        break;
                    case 2:
                        literAluraService.listarLivrosRegistrados();
                        break;
                    case 3:
                        literAluraService.listarAutores();
                        break;
                    case 4:
                        System.out.print("Digite o ano: ");
                        int ano = teclado.nextInt(); // Usando 'teclado'
                        teclado.nextLine(); // Consumir a nova linha
                        literAluraService.listarAutoresVivosEmAno(ano);
                        break;
                    case 5:
                        System.out.print("Digite o idioma (ex: en para Inglês, pt para Português): ");
                        String idioma = teclado.nextLine().toLowerCase(); // Usando 'teclado'
                        literAluraService.listarLivrosPorIdioma(idioma);
                        break;
                    case 0:
                        System.out.println("Saindo do LiterAlura. Até mais!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                teclado.nextLine(); // Usando 'teclado' para consumir a entrada inválida
                option = -1; // Resetar opção para continuar o loop
            }
        }
        teclado.close(); // Fechar o scanner ao sair
    }
}


