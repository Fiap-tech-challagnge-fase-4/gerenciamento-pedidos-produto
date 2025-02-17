package br.com.fiap.controller.produto;

import br.com.fiap.produto.controller.ProdutoController;
import br.com.fiap.produto.mapper.ProdutoMapper;
import br.com.fiap.produto.model.Produto;
import br.com.fiap.produto.model.dto.ProdutoRequestDTO;
import br.com.fiap.produto.service.impl.impl.ProdutoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProdutoControllerTest {

    private MockMvc mockMvc;

    private AutoCloseable openMocks;

    @Mock
    private ProdutoServiceImpl produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        ProdutoMapper produtoMapper = new ProdutoMapper();
        produtoController = new ProdutoController(produtoService, produtoMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @AfterEach
    void teardown() throws Exception {
        openMocks.close();
    }


    @Test
    void deveListarProdutos() throws Exception {
        Produto produto = gerarUmProduto();
        when(produtoService.listarProduto()).thenReturn(Collections.singletonList(produto));

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Spaghetti"));
    }

    @Test
    void deveCriarProduto() throws Exception {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO("Spaghetti", "Macarrão", new BigDecimal("10.00"), 10, null, null, null, "ATIVO");
        Produto produto = gerarUmProduto();
        when(produtoService.criarProduto(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Spaghetti"));
    }

    @Test
    void deveObterProduto() throws Exception {
        Produto produto = gerarUmProduto();
        when(produtoService.obterProduto(anyInt())).thenReturn(produto);

        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Spaghetti"));
    }

    @Test
    void deveAtualizarProduto() throws Exception {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO("Spaghetti", "Macarrão", new BigDecimal("10.00"), 10, null, null, null, "ATIVO");
        Produto produto = gerarUmProduto();
        when(produtoService.atualizarProduto(anyInt(), any(Produto.class))).thenReturn(produto);

        mockMvc.perform(put("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Spaghetti"));
    }

    @Test
    void deveExcluirProduto() throws Exception {
        mockMvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isOk());
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
