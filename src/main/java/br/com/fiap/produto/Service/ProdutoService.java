package br.com.fiap.produto.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.produto.model.Produto;
import br.com.fiap.produto.repository.ProdutoRepository;

@Service
public class ProdutoService {


    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    
    // Método para listar todos os produtos
    public List<Produto> listarProduto() {
        return produtoRepository.findAll();
    }

    // Método para criar um novo produto
    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Método para obter um produto pelo ID
    public Produto obterProduto(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + id + " não encontrado"));
    }

    // Método para atualizar um produto existente
    public Produto atualizarProduto(Integer id, Produto produto) {
        if (produtoRepository.existsById(id)) {
            produto.setId(id);
            return produtoRepository.save(produto);
        }
        return null;
    }

    // Método para excluir um produto pelo ID
    public void excluirProduto(Integer id) {
        produtoRepository.deleteById(id);
    }

    // Método para efetuar uma carga de produtos
    public void carregarProdutos(Integer id) {
        //TODO: 
        //Descrição Funcional: esta funcionalidade específica permitirá a 
        //importação em massa de dados de produtos, incluindo descrições, preços e 
        //quantidades em estoque. A importação poderá ser agendada ou iniciada 
        //manualmente, garantindo que o catálogo de produtos esteja sempre atualizado.

        //produtoRepository.deleteById(id);
    }

            //Para adicionar enviar valor positivo, para remover, enviar quantidade negativa
            public Produto atualizarEstoque(Integer produtoId, int quantidade)
    {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);

        if(produto != null)
        {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);

            return produtoRepository.save(produto);
        }
        
        return null;
    }
}
