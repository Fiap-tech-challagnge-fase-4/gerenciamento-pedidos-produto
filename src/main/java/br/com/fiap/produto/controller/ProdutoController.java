package br.com.fiap.produto.controller;

import br.com.fiap.produto.mapper.ProdutoMapper;
import br.com.fiap.produto.model.dto.ProdutoRequestDTO;
import br.com.fiap.produto.model.dto.ProdutoResponseDTO;
import br.com.fiap.produto.service.impl.impl.ProdutoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.produto.model.Produto;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoServiceImpl produtoService;
    private final ProdutoMapper produtoMapper;

    public ProdutoController(ProdutoServiceImpl produtoService, ProdutoMapper produtoMapper) {
        this.produtoService = produtoService;
        this.produtoMapper = produtoMapper;
    }

    @GetMapping
    public List<Produto> listarProduto() {
        return produtoService.listarProduto();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO criarProduto(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoMapper.converterRequestDTOParaProduto(produtoRequestDTO);
        Produto produtoCriado = produtoService.criarProduto(produto);

        return produtoMapper.converterProdutoParaResponseDTO(produtoCriado);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO obterProduto(@PathVariable Integer id) {
        Produto produto = produtoService.obterProduto(id);
        return produtoMapper.converterProdutoParaResponseDTO(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO atualizarProduto(@PathVariable Integer id, @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoMapper.converterRequestDTOParaProduto(produtoRequestDTO);
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produto);
        return produtoMapper.converterProdutoParaResponseDTO(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void excluirProduto(@PathVariable Integer id) {
        produtoService.excluirProduto(id);
    }

    @PutMapping("/atualizarEstoque/{id}/{quantidade}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO atualizarEstoque(@PathVariable Integer id, @PathVariable int quantidade)
    {
        Produto produto = produtoService.atualizarEstoque(id, quantidade);
        return produtoMapper.converterProdutoParaResponseDTO(produto);
    }
}