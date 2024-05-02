package com.faboda.http;

import com.faboda.curl.MockASTService;
import com.faboda.curl.ast.ASTNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ASTRequestClientTest {

    @Test
    public void testASTRequestClient() throws IOException {
        ASTHttpClientWrapper client = new ASTHttpClientWrapper();
        ASTNode ast = MockASTService.buildAST();
        String response = client.send(ast);
        assertEquals("{\"id\":101}", response);
    }

}
