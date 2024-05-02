package com.faboda.http;

import com.faboda.curl.MockASTService;
import com.faboda.curl.ast.ASTNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ATSRequestBuilderTest {

    @Test
    @DisplayName("Test constructor initialization")
    public void testConstructorInitialization() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ASTRequestBuilder(createNullKeyValueASTNode());
        });
    }

    @Test
    @DisplayName("Test RequestBuilder")
    public void testRequestBuilder() {
        ASTRequestBuilder requestBuilder = new ASTRequestBuilder(MockASTService.buildAST());
        requestBuilder.build();
        assertTrue(requestBuilder.getRequest().isHttps());
        assertEquals(1, requestBuilder.getHeaders().size());
        assertNotNull(requestBuilder.getRequest().body());
    }

    private ASTNode createNullKeyValueASTNode() {
    return new ASTNode(null, null);
    }


}
