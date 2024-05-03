package com.faboda.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.faboda.curl.MockASTService;
import com.faboda.curl.ast.ASTNode;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ASTRequestClientTest {

  @Test
  public void testASTRequestClient() throws IOException {
    ASTHttpClientWrapper client = new ASTHttpClientWrapper();
    ASTNode ast = MockASTService.buildAST();
    String response = client.send(ast);
    assertEquals("{\"id\":101,\"title\":\"BMW Pencil\"}", response);
  }
}
