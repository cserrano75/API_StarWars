package com.alura.curso.apiswap.Modelos;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BuscaFilm {

    FilmApiSwap buscaPelicula (int numeroPelicula){
        String direccion = "https://swapi.py4e.com/api/films/"+numeroPelicula + "/";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

            HttpResponse<String> response;

            try {
                response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                throw new RuntimeException("No encontré esa película");

            }
            return new Gson().fromJson(response.body(),FilmApiSwap.class);
    }

}
