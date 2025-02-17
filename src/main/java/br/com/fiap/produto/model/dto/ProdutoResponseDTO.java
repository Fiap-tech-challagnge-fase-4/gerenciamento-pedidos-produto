package br.com.fiap.produto.model.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO (
         Integer id,
         String nome,
         String descricao,
         BigDecimal preco,
         int quantidadeEstoque,
         String categoria,
         String imagemUrl,
         String codigoBarras,
         String status
){


}
