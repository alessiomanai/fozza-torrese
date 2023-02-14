package tk.alessiomanai.fozzatorrese.model;

import java.util.ArrayList;

public class Squadra extends ArrayList<Giocatore> {

    private String nome;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
