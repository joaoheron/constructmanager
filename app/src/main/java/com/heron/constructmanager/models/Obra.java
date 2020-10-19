package com.heron.constructmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Obra {
    private String titulo;
    private String endereco;
    private String tipo_obra;
    private String etapa;
    private String responsibles;

    public Obra() {
        // Default constructor required for calls to DataSnapshot.getValue(Obra.class)
    }

    public Obra(String titulo, String endereco, String tipo_obra, String etapa, String responsibles) {
        this.titulo = titulo;
        this.endereco = endereco;
        this.tipo_obra = tipo_obra;
        this.etapa = etapa;
        this.responsibles = responsibles;
    }

    // [START obra_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("titulo", titulo);
        result.put("endereco", endereco);
        result.put("tipo_obra", tipo_obra);
        result.put("etapa", etapa);
        result.put("responsibles", responsibles);

        return result;
    }
    // [END obra_to_map]
}