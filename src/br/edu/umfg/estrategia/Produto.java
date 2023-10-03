package br.edu.umfg.estrategia;

public class Produto {
    String codigoDeBarras;
    String descricao;
    Double valor;

    public Produto(String codigoDeBarras, String descricao,
                   Double valor) {
        this.codigoDeBarras = codigoDeBarras;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getValor() {
        return valor;
    }
}
