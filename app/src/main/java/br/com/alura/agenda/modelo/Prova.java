package br.com.alura.agenda.modelo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Prova implements Serializable {

    private String materia;

    private String data;

    private List<String> topicos;

    public Prova(String materia, String data, String... topicos) {
        this.materia = materia;
        this.data = data;
        this.topicos = Arrays.asList(topicos);
    }

    public String getMateria() {
        return materia;
    }

    public String getData() {
        return data;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    @Override
    public String toString() {
        return materia;
    }
}
