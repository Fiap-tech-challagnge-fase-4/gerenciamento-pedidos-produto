package br.com.fiap.produto.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Produto {

    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidadeEstoque;
    private String categoria;
    private String imagemUrl;
    private String codigoBarras;
    private String status;

    public Produto(Integer id, String nome, String descricao, BigDecimal preco, int quantidadeEstoque, String categoria, String imagemUrl, String codigoBarras, String status)
    {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
        this.imagemUrl = imagemUrl;
        this.codigoBarras = codigoBarras;
        this.status = status;
    }
}
