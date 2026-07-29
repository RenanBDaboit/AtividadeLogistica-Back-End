package org.example.model;

public class Motorista {

    private int id;
    private String nome;
    private String cnh;
    private String veiculo;
    private String cidade_base;

    public Motorista(int id, String nome, String cnh, String veiculo, String cidade_base) {
        this.id = id;
        this.nome = nome;
        this.cnh = cnh;
        this.veiculo = veiculo;
        this.cidade_base = cidade_base;
    }

    public Motorista(String nome, String cnh, String veiculo, String cidade_base) {
        this.nome = nome;
        this.cnh = cnh;
        this.veiculo = veiculo;
        this.cidade_base = cidade_base;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getCidade_base() {
        return cidade_base;
    }

    public void setCidade_base(String cidade_base) {
        this.cidade_base = cidade_base;
    }

    @Override
    public String toString() {
        return "Motorista{" +
                ", nome='" + nome + '\'' +
                ", cnh='" + cnh + '\'' +
                ", veiculo='" + veiculo + '\'' +
                ", cidade_base='" + cidade_base + '\'' +
                '}';
    }
}