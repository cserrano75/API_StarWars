package com.alura.curso.apiswap.Principal;

import com.alura.curso.apiswap.Modelos.FilmApiSwap;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        System.out.println("Iniciando...");

        Scanner lectura = new Scanner(System.in);
        //List<Films> films = new ArrayList<>();
        List<FilmApiSwap> films = new ArrayList<>();

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
        gsonbuilder.setPrettyPrinting();
        Gson gson = gsonbuilder.create();

        while (true) {
            System.out.println("Escriba el numero de la pelicula: ");

            var busqueda = lectura.nextLine();

            if (busqueda.equalsIgnoreCase("salir")) {
                break;
            }

            String direccion = "https://swapi.py4e.com/api/films/"+busqueda + "/";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = null;
            try {
                response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String json = response.body();
            FilmApiSwap mifilmApiSwap = gson.fromJson(json, FilmApiSwap.class);
            System.out.println(json);
            System.out.println("Film ya convertido: " +mifilmApiSwap);
        }

        System.out.println("Finalizo la ejecucion del programa!!");
    }
}
