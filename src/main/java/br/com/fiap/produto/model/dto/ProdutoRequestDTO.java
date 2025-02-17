package br.com.fiap.produto.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record ProdutoRequestDTO(
        String nome,
        String descricao,
        BigDecimal preco,
        int quantidadeEstoque,
        String categoria,
        String imagemUrl,
        String codigoBarras,
        String status
) { }
