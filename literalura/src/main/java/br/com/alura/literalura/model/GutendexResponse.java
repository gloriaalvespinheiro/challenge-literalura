package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record GutendexResponse(
        @JsonAlias("count") Integer count,
        @JsonAlias("next") String next,
        @JsonAlias("previous") String previous,
        @JsonAlias("results") List<LivroData> resultados
) {
}
