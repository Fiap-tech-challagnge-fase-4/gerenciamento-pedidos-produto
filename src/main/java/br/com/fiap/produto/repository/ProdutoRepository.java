package br.com.fiap.produto.repository;

import br.com.fiap.produto.model.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {



}

