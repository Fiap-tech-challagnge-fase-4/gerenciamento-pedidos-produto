package br.com.fiap.produto.service.impl;

import br.com.fiap.produto.model.Produto;

import java.util.List;

public interface ProdutoService {
    Produto criarProduto(Produto produto);
    public List<Produto> listarProduto();

    Produto obterProduto(Integer id);
    Produto atualizarProduto(Integer id, Produto produto);
    void excluirProduto(Integer id) ;
    void carregarProdutos(List<Produto> produtoList);
    public Produto atualizarEstoque(Integer produtoId, int quantidade);
}
