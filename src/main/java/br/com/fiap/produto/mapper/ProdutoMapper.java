package br.com.fiap.produto.mapper;

import br.com.fiap.produto.model.Produto;
import br.com.fiap.produto.model.dto.ProdutoRequestDTO;
import br.com.fiap.produto.model.dto.ProdutoResponseDTO;
import br.com.fiap.produto.model.entity.ProdutoEntity;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoMapper {
    public Produto converterRequestDTOParaProduto(ProdutoRequestDTO produtoRequestDTO) {
        return new Produto(
            null,
            produtoRequestDTO.nome(),
            produtoRequestDTO.descricao(),
            produtoRequestDTO.preco(),
            produtoRequestDTO.quantidadeEstoque(),
            produtoRequestDTO.categoria(),
            produtoRequestDTO.imagemUrl(),
            produtoRequestDTO.codigoBarras(),
            produtoRequestDTO.status()
        );
    }

    public ProdutoResponseDTO converterProdutoParaResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
        	produto.getId(),
        	produto.getNome(),
        	produto.getDescricao(),
        	produto.getPreco(),
        	produto.getQuantidadeEstoque(),
            produto.getCategoria(),
            produto.getImagemUrl(),
            produto.getCodigoBarras(),
            produto.getStatus()

        );
    }

    public Produto converterProdutoEntityParaProduto(ProdutoEntity produtoEntity) {
        return new Produto(
        	produtoEntity.getId(),
        	produtoEntity.getNome(),
        	produtoEntity.getDescricao(),
            produtoEntity.getPreco(),
            produtoEntity.getQuantidadeEstoque(),
            produtoEntity.getCategoria(),
            produtoEntity.getImagemUrl(),
            produtoEntity.getCodigoBarras(),
            produtoEntity.getStatus()

        );
    }

    public ProdutoEntity converterProdutoParaProdutoEntity(Produto produto) {

        return new ProdutoEntity(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria(),
                produto.getImagemUrl(),
                produto.getCodigoBarras(),
                produto.getStatus());
    }
}
