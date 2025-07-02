package br.com.alura.literalura.service;

import br.com.alura.literalura.model.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

    @Service
    public class ApiConsumer {

        private final ObjectMapper objectMapper = new ObjectMapper();

        public GutendexResponse getData(String url) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    return objectMapper.readValue(response.body(), GutendexResponse.class);
                } else {
                    System.out.println("Erro na requisição: " + response.statusCode());
                    System.out.println("Corpo da resposta: " + response.body());
                    return null;
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Erro ao buscar dados da API: " + e.getMessage());
                return null;
            }
        }
    }

