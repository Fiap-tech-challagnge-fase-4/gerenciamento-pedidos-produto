package br.com.fiap.controller.produto;


import br.com.fiap.produto.ProdutoApplication;
import br.com.fiap.produto.model.dto.ProdutoRequestDTO;
import br.com.fiap.produto.model.entity.ProdutoEntity;
import br.com.fiap.produto.repository.ProdutoRepository;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;

@AutoConfigureTestDatabase
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = ProdutoApplication.class)
public class ProutoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setId(1);
        produtoEntity.setNome("Spaghetti");
        produtoEntity.setDescricao("Macarrão");
        produtoEntity.setPreco(new BigDecimal("10.00"));
        produtoEntity.setQuantidadeEstoque(10);
        produtoEntity.setStatus("ATIVO");
        produtoRepository.save(produtoEntity);

    }

    @Test
    void devePermitirRegistrarUmProduto() {
        // Arrange
        ProdutoRequestDTO requestProduto = gerarUmProdutoRequestDTO();

        // Act & Assert
        given().filter(new AllureRestAssured()).contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(requestProduto)
            .when().post("/api/produtos")
            .then().statusCode(HttpStatus.CREATED.value())
            .body(matchesJsonSchemaInClasspath("schemas/ProdutoSchema.json"))
           //body("$", hasKey("id"))
            .body("$", hasKey("nome"))
            .body("$", hasKey("descricao"))
            .body("$", hasKey("preco"))
            .body("$", hasKey("quantidadeEstoque"))
            .body("$", hasKey("status"));
    }

    @Test
    void deveAtualizarUmProduto() {
        // Arrange
        ProdutoRequestDTO requestProduto = gerarUmProdutoRequestDTO();
        Integer produtoId = 1; // Assume this product ID exists in the database

        // Act & Assert
        given().filter(new AllureRestAssured()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestProduto)
                .when().put("/api/produtos/{id}", produtoId)
                .then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/ProdutoSchema.json"))
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("descricao"))
                .body("$", hasKey("preco"))
                .body("$", hasKey("quantidadeEstoque"))
                .body("$", hasKey("status"));
    }

    @Test
    void deveDeletarUmProduto() {
        // Arrange
        Integer produtoId = 1; // Assume this product ID exists in the database

        // Act & Assert
        given().filter(new AllureRestAssured()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/produtos/{id}", produtoId)
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveBuscarUmProdutoPorId() {
        // Arrange
        Integer produtoId = 1; // Assume this product ID exists in the database

        // Act & Assert
        given().filter(new AllureRestAssured()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/produtos/{id}", produtoId)
                .then().statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/ProdutoSchema.json"))
                .body("$", hasKey("id"))
                .body("$", hasKey("nome"))
                .body("$", hasKey("descricao"))
                .body("$", hasKey("preco"))
                .body("$", hasKey("quantidadeEstoque"))
                .body("$", hasKey("status"));
    }
    ProdutoRequestDTO gerarUmProdutoRequestDTO() {
        return ProdutoRequestDTO.builder()
            .nome("Spaghetti")
            .descricao("Macarrão")
            .preco(new BigDecimal("10.00"))
            .quantidadeEstoque(10)
            .status("ATIVO")
            .build();
    }
}
