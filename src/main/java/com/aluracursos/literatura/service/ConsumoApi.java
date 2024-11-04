package com.aluracursos.literatura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    public ConsumoApi(){
    }

    public String obtenerDatos(String url) throws JsonProcessingException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = null;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch(IOException | InterruptedException e){
            throw new RuntimeException();
        }
        String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(body).path("results").get(0);
        String resultsJson = rootNode.toString();
        System.out.println(resultsJson);
        return resultsJson;
    }
}
