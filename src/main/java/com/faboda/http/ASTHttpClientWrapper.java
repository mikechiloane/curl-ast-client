package com.faboda.http;

import com.faboda.curl.ast.ASTNode;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ASTHttpClientWrapper {
    private final OkHttpClient client;

    public ASTHttpClientWrapper() {
        this.client = new OkHttpClient();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public String send(ASTNode astNode) throws IOException {
        ASTRequestBuilder astRequestBuilder = new ASTRequestBuilder(astNode);
        astRequestBuilder.build();
    try (Response response = client.newCall(astRequestBuilder.getRequest()).execute()) {
      return response.body().string();
    }
    }


}
