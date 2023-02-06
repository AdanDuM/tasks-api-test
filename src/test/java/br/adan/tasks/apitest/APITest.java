package br.adan.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

  @BeforeClass
  public static void setup() {
    RestAssured.baseURI = "http://localhost:8001/tasks-backend";
  }

  @Test
  public void deveRetornarTarefas() throws Exception {
    RestAssured
        .given()
        .when().get("/todo")
        .then().statusCode(200);
  }

  @Test
  public void deveAdicionarTarefaComSucesso() throws Exception {
    RestAssured
        .given()
        .body("{  \"task\": \"Teste via API\", \"dueDate\": \"2030-12-30\" }")
        .contentType(ContentType.JSON)
        .when().post("/todo")
        .then().statusCode(201);
  }

  @Test
  public void naoDeveAdicionarTarefaInvalida() throws Exception {
    RestAssured
        .given()
        .body("{  \"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\" }")
        .contentType(ContentType.JSON)
        .when().post("/todo")
        .then()
        .statusCode(400)
        .body("error", CoreMatchers.is("Bad Request"))
        .body("message", CoreMatchers.is("Due date must not be in past"));
  }

}
