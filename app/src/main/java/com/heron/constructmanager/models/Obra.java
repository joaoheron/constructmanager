package com.heron.constructmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class Obra {
    public String uid;
    private String titulo;
    private String endereco;
    private String tipo_obra;
    private String etapa;
    public List<String> responsibles;

    public Obra() {
        // Default constructor required for calls to DataSnapshot.getValue(Responsability.class)
    }

    public Obra(String uid, String titulo, String endereco, String tipo_obra, String etapa, List responsibles) {
        this.uid = uid;
        this.titulo = titulo;
        this.endereco = endereco;
        this.tipo_obra = tipo_obra;
        this.etapa = etapa;
        this.responsibles = responsibles;
    }
}
