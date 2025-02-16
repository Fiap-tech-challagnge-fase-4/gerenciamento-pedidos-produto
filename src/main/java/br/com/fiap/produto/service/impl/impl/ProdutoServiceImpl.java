package br.com.fiap.produto.service.impl.impl;

import br.com.fiap.produto.exception.ProdutoException;
import br.com.fiap.produto.mapper.ProdutoMapper;
import br.com.fiap.produto.model.Produto;
import br.com.fiap.produto.model.entity.ProdutoEntity;
import br.com.fiap.produto.repository.ProdutoRepository;
import br.com.fiap.produto.service.impl.ProdutoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper)
    {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    // Método para listar todos os produtos
    @Override
    public List<Produto> listarProduto() {
        List<ProdutoEntity> produtoEntity = produtoRepository.findAll();
        return produtoEntity.stream().map(produtoMapper::converterProdutoEntityParaProduto).toList();
    }

    // Método para criar um novo produto
    @Override
    public Produto criarProduto(Produto produto) {
        ProdutoEntity produtoEntity = produtoMapper.converterProdutoParaProdutoEntity(produto);
        ProdutoEntity produtoEntitySave = produtoRepository.save(produtoEntity);
        return produtoMapper.converterProdutoEntityParaProduto(produtoEntitySave);
    }

    // Método para obter um produto pelo ID
    @Override
    public Produto obterProduto(Integer id) {
        ProdutoEntity produtoEntity = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoException("Produto com ID " + id + " não encontrado"));
        return produtoMapper.converterProdutoEntityParaProduto(produtoEntity);
    }

    // Método para atualizar um produto existente
    @Override
    public Produto atualizarProduto(Integer id, Produto produto) {

        if (produtoRepository.existsById(id)) {
            ProdutoEntity produtoEntity = produtoMapper.converterProdutoParaProdutoEntity(produto);
            produtoEntity.setId(id);
            produtoRepository.save(produtoEntity);


            return produtoMapper.converterProdutoEntityParaProduto(produtoEntity);
        }
        throw new ProdutoException("Produto com ID " + id + " não encontrado");

    }

    // Método para excluir um produto pelo ID
    @Override
    public void excluirProduto(Integer id) {
        produtoRepository.deleteById(id);
    }

    //Para adicionar enviar valor positivo, para remover, enviar quantidade negativa
    @Override
    public Produto atualizarEstoque(Integer produtoId, int quantidade) {
        ProdutoEntity produtoEntity = produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoException("Produto com ID " + produtoId + " não encontrado"));

            if (produtoEntity.getQuantidadeEstoque() - quantidade <= 0) {
                throw new ProdutoException("Quantidade em estoque insuficiente");
            }
            produtoEntity.setQuantidadeEstoque(produtoEntity.getQuantidadeEstoque() - quantidade);
            produtoRepository.save(produtoEntity);

            return produtoMapper.converterProdutoEntityParaProduto(produtoEntity);

    }
}
