package br.com.fiap.produto.mapper;

import br.com.fiap.produto.model.Produto;
import br.com.fiap.produto.model.dto.ProdutoRequestDTO;
import br.com.fiap.produto.model.dto.ProdutoResponseDTO;
import br.com.fiap.produto.model.entity.ProdutoEntity;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoMapper {
    public Produto converterRequestDTOParaProduto(ProdutoRequestDTO produtoRequestDTO) {
        return Produto.builder()
                .nome(produtoRequestDTO.nome())
                .descricao(produtoRequestDTO.descricao())
                .preco(produtoRequestDTO.preco())
                .quantidadeEstoque(produtoRequestDTO.quantidadeEstoque())
                .categoria(produtoRequestDTO.categoria())
                .imagemUrl(produtoRequestDTO.imagemUrl())
                .codigoBarras(produtoRequestDTO.codigoBarras())
                .status(produtoRequestDTO.status())
                .build();
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
        return Produto.builder()
                .id(produtoEntity.getId())
                .nome(produtoEntity.getNome())
                .descricao(produtoEntity.getDescricao())
                .preco(produtoEntity.getPreco())
                .quantidadeEstoque(produtoEntity.getQuantidadeEstoque())
                .categoria(produtoEntity.getCategoria())
                .imagemUrl(produtoEntity.getImagemUrl())
                .codigoBarras(produtoEntity.getCodigoBarras())
                .status(produtoEntity.getStatus())
                .build();
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
