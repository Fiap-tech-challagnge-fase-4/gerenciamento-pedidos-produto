package br.com.fiap.produto.service;

import java.util.List;

import br.com.fiap.produto.exception.ProdutoException;
import br.com.fiap.produto.service.impl.ProdutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.fiap.produto.model.Produto;
import br.com.fiap.produto.repository.ProdutoRepository;

@Service
public class ProdutoService implements ProdutoServiceImpl {


    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // Método para listar todos os produtos
    @Override
    public List<Produto> listarProduto() {
        return produtoRepository.findAll();
    }

    // Método para criar um novo produto
    @Override
    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Método para obter um produto pelo ID
    @Override
    public Produto obterProduto(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + id + " não encontrado"));
    }

    // Método para atualizar um produto existente
    @Override
    public Produto atualizarProduto(Integer id, Produto produto) {
        if (produtoRepository.existsById(id)) {
            produto.setId(id);
            return produtoRepository.save(produto);
        }
        throw new IllegalArgumentException("Produto com ID " + id + " não encontrado");

    }

    // Método para excluir um produto pelo ID
    @Override
    public void excluirProduto(Integer id) {
        produtoRepository.deleteById(id);
    }

    // Método para efetuar uma carga de produtos
    @Override
    public void carregarProdutos(List<Produto>produtoList ) {

        try {
            produtoList.stream()
                    .forEach(produtoRepository::save);
        }catch(DataAccessException e) {
            throw   new ProdutoException("Erro ao salvar o produto");
        }

    }

            //Para adicionar enviar valor positivo, para remover, enviar quantidade negativa
        @Override
       public Produto atualizarEstoque(Integer produtoId, int quantidade) {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);

        if(produto != null) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);

            return produtoRepository.save(produto);
        }

        return null;
    }
}
