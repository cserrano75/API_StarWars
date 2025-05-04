package com.alura.curso.apiswap.Principal;

import com.alura.curso.apiswap.Modelos.FilmApiSwap;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
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
        List<FilmApiSwap> miListadeTitulos = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson =  gsonBuilder.setPrettyPrinting().create();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);

        while(true){
            System.out.println("Escriba el numero de la pelicula: ");

            var busqueda =lectura.nextLine();

            if (busqueda.equalsIgnoreCase("salir")){
                break;
            }
            String direccion = "https://swapi.py4e.com/api/films/"+Integer.valueOf(busqueda)+"/";

            try{

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();

                FilmApiSwap miFilmApiSwap = gson.fromJson(json, FilmApiSwap.class);
                System.out.println(json);

                miListadeTitulos.add(miFilmApiSwap);

            }catch (Exception e){
                System.out.println("Ocurrio un error");
                System.out.println(e.getMessage());
            }

            FileWriter escritura = null;
            try {
                escritura = new FileWriter("titulo.json");

                escritura.write(gson.toJson(miListadeTitulos));
                System.out.println("Titulos ya convertidos: " +miListadeTitulos);
                escritura.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println(miListadeTitulos);
        System.out.println("Proceso Finalizado!");
    }
}
