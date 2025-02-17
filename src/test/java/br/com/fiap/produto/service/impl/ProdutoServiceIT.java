package br.com.fiap.produto.service.impl;

import br.com.fiap.produto.model.Produto;
import br.com.fiap.produto.model.entity.ProdutoEntity;
import br.com.fiap.produto.repository.ProdutoRepository;
import br.com.fiap.produto.service.impl.impl.ProdutoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProdutoServiceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoServiceImpl produtoServiceImpl;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setup() {
        produtoRepository.deleteAll();
    }

    @Test
    void deveCriarProduto() {
        Produto produto = gerarUmProduto();
        Produto produtoCriado = produtoServiceImpl.criarProduto(produto);

        assertNotNull(produtoCriado.getId());
        assertEquals("Spaghetti", produtoCriado.getNome());
    }

    @Test
    void deveListarProdutos() {
        Produto produto = gerarUmProduto();
        produtoServiceImpl.criarProduto(produto);

        List<Produto> produtos = produtoServiceImpl.listarProduto();
        assertFalse(produtos.isEmpty());
        assertEquals(1, produtos.size());
    }

    @Test
    void deveObterProduto() {
        Produto produto = gerarUmProduto();
        Produto produtoCriado = produtoServiceImpl.criarProduto(produto);

        Produto produtoObtido = produtoServiceImpl.obterProduto(produtoCriado.getId());
        assertNotNull(produtoObtido);
        assertEquals("Spaghetti", produtoObtido.getNome());
    }

    @Test
    void deveAtualizarProduto() {
        Produto produto = Produto.builder()
                .nome("Spaghetti")
                .descricao("Macarrão")
                .preco(new BigDecimal("10.00"))
                .quantidadeEstoque(10)
                .status("ATIVO")
                .build();
        Produto produtoCriado = produtoServiceImpl.criarProduto(produto);

        Produto produtoAtualizado = Produto.builder()
                .id(produtoCriado.getId())
                .nome("Spaghetti Atualizado")
                .descricao(produtoCriado.getDescricao())
                .preco(produtoCriado.getPreco())
                .quantidadeEstoque(produtoCriado.getQuantidadeEstoque())
                .categoria(produtoCriado.getCategoria())
                .imagemUrl(produtoCriado.getImagemUrl())
                .codigoBarras(produtoCriado.getCodigoBarras())
                .status(produtoCriado.getStatus())
                .build();

        Produto produtoAtualizadoResult = produtoServiceImpl.atualizarProduto(produtoCriado.getId(), produtoAtualizado);

        assertEquals("Spaghetti Atualizado", produtoAtualizadoResult.getNome());
    }

   @Test
    void deveExcluirProduto() {
        Produto produto = gerarUmProduto();
        Produto produtoCriado = produtoServiceImpl.criarProduto(produto);

        produtoServiceImpl.excluirProduto(produtoCriado.getId());
        Optional<ProdutoEntity> produtoExcluido = produtoRepository.findById(produtoCriado.getId());

        assertTrue(produtoExcluido.isEmpty());
    }

    @Test
    void deveAtualizarEstoque() {
        Produto produto = gerarUmProduto();
        Produto produtoCriado = produtoServiceImpl.criarProduto(produto);

        Produto produtoAtualizado = produtoServiceImpl.atualizarEstoque(produtoCriado.getId(), 5);

        assertEquals(5, produtoAtualizado.getQuantidadeEstoque());
    }

    Produto gerarUmProduto()
    {
        return Produto.builder()
                .id(1)
                .nome("Spaghetti")
                .descricao("Macarrão")
                .preco(new BigDecimal("10.00"))
                .quantidadeEstoque(10)
                .categoria(null)
                .imagemUrl(null)
                .codigoBarras(null)
                .status("ATIVO")
                .build();
    }

}
