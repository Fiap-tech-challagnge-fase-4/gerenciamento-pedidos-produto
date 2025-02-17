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

}
