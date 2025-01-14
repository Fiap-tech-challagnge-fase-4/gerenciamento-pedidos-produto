package br.com.fiap.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.produto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}

