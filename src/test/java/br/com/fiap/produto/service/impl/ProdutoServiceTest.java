
package br.com.fiap.produto.service.impl;


import br.com.fiap.produto.mapper.ProdutoMapper;
import br.com.fiap.produto.model.Produto;
import br.com.fiap.produto.model.entity.ProdutoEntity;
import br.com.fiap.produto.repository.ProdutoRepository;
import br.com.fiap.produto.service.impl.impl.ProdutoServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoServiceImpl produtoService;
    AutoCloseable openMocks;
    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void teardown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarUmProduto() {
        // Arrange
        Produto produto = getProduto();
        ProdutoEntity produtoEntity = getProdutoEntity();

        when(produtoMapper.converterProdutoParaProdutoEntity(any(Produto.class))).thenReturn(produtoEntity);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(produtoMapper.converterProdutoEntityParaProduto(any(ProdutoEntity.class))).thenReturn(produto);

        // Act
        Produto produtoObtido = produtoService.criarProduto(produto);

        // Assert
        assertThat(produtoObtido).isNotNull();
        assertThat(produtoObtido.getNome()).isEqualTo("Spaghetti");
        assertThat(produtoObtido.getDescricao()).isEqualTo("Macarrão com toque italiano");
    }
    @Test
    void devePermitirAtualizarUmProduto() {
        // Arrange
        Produto produto = getProduto();
        ProdutoEntity produtoEntity = getProdutoEntity();

        when(produtoMapper.converterProdutoParaProdutoEntity(any(Produto.class))).thenReturn(produtoEntity);
        when(produtoRepository.existsById(1)).thenReturn(true);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(produtoMapper.converterProdutoEntityParaProduto(any(ProdutoEntity.class))).thenReturn(produto);


        // Act
        Produto produtoObtido = produtoService.atualizarProduto(1, produto);

        // Assert
        assertThat(produtoObtido).isNotNull();
        assertThat(produtoObtido.getNome()).isEqualTo(produtoEntity.getNome());
        assertThat(produtoObtido.getDescricao()).isEqualTo(produtoEntity.getDescricao());
    }
    @Test
    void devePermitirListarTodosProdutos(){
        //Arrange
        Produto produto = getProduto();
        ProdutoEntity produtoEntity = getProdutoEntity();

        when(produtoMapper.converterProdutoParaProdutoEntity(any(Produto.class))).thenReturn(produtoEntity);
        when(produtoRepository.findAll()).thenReturn(List.of(produtoEntity));
        when(produtoMapper.converterProdutoEntityParaProduto(any(ProdutoEntity.class))).thenReturn(produto);


        //Act
        List<Produto> produtosObtidos = produtoService.listarProduto();

        //Assert
        assertThat(produtosObtidos).hasSize(1);
        assertThat(produtosObtidos.get(0).getNome()).isEqualTo(produtoEntity.getNome());
    }

    @Test
    void devePermitirObterUmProdutoPorId(){
        //Arrange
        Produto produto = getProduto();
        ProdutoEntity produtoEntity = getProdutoEntity();

        when(produtoMapper.converterProdutoParaProdutoEntity(any(Produto.class))).thenReturn(produtoEntity);
        when(produtoRepository.findById(1)).thenReturn(java.util.Optional.of(produtoEntity));
        when(produtoMapper.converterProdutoEntityParaProduto(any(ProdutoEntity.class))).thenReturn(produto);

        //Act
        Produto produtoObtido = produtoService.obterProduto(1);

        //Assert
        verify(produtoRepository, times(1)).findById(anyInt());
        assertThat(produtoObtido).isNotNull();
        assertThat(produtoObtido.getNome()).isEqualTo(produtoEntity.getNome());
        assertThat(produtoObtido.getDescricao()).isEqualTo(produtoEntity.getDescricao());
    }

    @Test
    void devePermitirExcluirUmProduto(){
        //Arrange
        int idProduto = 1;
        var produto =  getProduto();
        ProdutoEntity produtoEntity = getProdutoEntity();
        when(produtoRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(produtoEntity));
        doNothing().when(produtoRepository).deleteById(any(Integer.class));

        //Act
        produtoService.excluirProduto(idProduto);

        //Assert
        verify(produtoRepository, times(1)).deleteById(idProduto);
        verify(produtoRepository).deleteById(any(Integer.class));
    }

    @Test
    void devePermitirAtualizarEstoque(){
        //Arrange
        Produto produto = getProduto();
        int quantidade = 1;

        ProdutoEntity produtoEntity = getProdutoEntity();
        when(produtoMapper.converterProdutoParaProdutoEntity(any(Produto.class))).thenReturn(produtoEntity);

        when(produtoRepository.findById(1)).thenReturn(java.util.Optional.of(produtoEntity));
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(produtoMapper.converterProdutoEntityParaProduto(any(ProdutoEntity.class))).thenReturn(produto);
        //Act
        Produto produtoObtido = produtoService.atualizarEstoque(1, quantidade);

        //Assert
        assertThat(produtoObtido).isNotNull();
        assertThat(produtoObtido.getQuantidadeEstoque()).isEqualTo(produto.getQuantidadeEstoque() );

    }

    @Test
    void verificaMockDoRepositorio() {
        assertThat(produtoRepository).isNotNull();
        assertThat(produtoService).isNotNull();
    }

    private static ProdutoEntity getProdutoEntity() {
        return ProdutoEntity.builder()
                .id(1)
                .nome("Spaghetti")
                .descricao("Macarrão com toque italiano")
                .categoria("Massa")
                .codigoBarras("123456789")
                .preco(BigDecimal.valueOf(20.0))
                .quantidadeEstoque(10)
                .imagemUrl("https://www.google.com")
                .status("Ativo")
                .build();
    }

    private static Produto getProduto() {
        return Produto.builder()
                .id(1)
                .nome("Spaghetti")
                .descricao("Macarrão com toque italiano")
                .categoria("Massa")
                .codigoBarras("123456789")
                .preco(BigDecimal.valueOf(20.0))
                .quantidadeEstoque(10)
                .imagemUrl("https://www.google.com")
                .status("Ativo")
                .build();
    }



}

