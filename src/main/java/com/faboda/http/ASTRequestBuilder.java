package com.faboda.http;

import com.faboda.curl.ast.ASTNode;
import com.faboda.curl.ast.NodeType;
import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ASTRequestBuilder {

    private ASTNode astNode;
    private Request.Builder requestBuilder;
    private Request request;
    private Map<String, String> headers;

    public ASTRequestBuilder(ASTNode astNode) {
        if (astNode.getValue() == null || astNode.getType() == null || astNode.getChildren() == null) {
            throw new IllegalArgumentException("ASTNode cannot be null");
        }
        this.requestBuilder = new Request.Builder();
        this.astNode = astNode;
    }

    public void setAstNode(ASTNode astNode) {
        this.astNode = astNode;
    }

    public Request.Builder getRequestBuilder() {
        return requestBuilder;
    }

    public void setRequestBuilder(Request.Builder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public Request getRequest() {
        return request;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void build() {
        setUrl();
        setHeaders();
        setData();
        this.request = requestBuilder.build();
    }

    private void setHeaders() {
        List<ASTNode> headerNodes = astNode.getChildren().stream().filter(node -> node.getType().equals(NodeType.HEADER)).map(node -> (ASTNode) node).toList();
        headers = headerNodes.stream().collect(Collectors.toMap(ASTNode::getKey, ASTNode::getValue));
        headers.forEach(requestBuilder::addHeader);
    }

    private void setUrl() {
        String url = astNode.getChildren().stream().filter(node -> node.getType().equals(NodeType.URL)).findFirst().get().getValue();
        requestBuilder.url(url);
    }

    private void setData() {
        List<ASTNode> dataNodes = astNode.getChildren().stream().filter(node -> node.getType().equals(NodeType.DATA)).map(node -> (ASTNode) node).toList();
        String encoding = isApplicationJsonEncoded() ? MediaTypes.APPLICATION_JSON.getMediaTypeValue() : MediaTypes.APPLICATION_X_WWW_FORM_URLENCODED.getMediaTypeValue();
        Map<String, String> data = dataNodes.stream().collect(Collectors.toMap(ASTNode::getKey, ASTNode::getValue));

        if (encoding.equals(MediaTypes.APPLICATION_JSON.getMediaTypeValue())) {
            MediaType mediaType = MediaType.parse(encoding);
            Gson gson = new Gson();
            String json = gson.toJson(data);
            RequestBody requestBody = RequestBody.create(json, mediaType);
            requestBuilder.post(requestBody);
        } else {
            RequestBody requestBody = RequestBody.create(data.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&")), MediaType.parse(encoding));
            requestBuilder.post(requestBody);
        }

    }
    private boolean isApplicationJsonEncoded() {
        List<String> headerKeys = astNode.getChildren().stream().filter(nd -> nd.getType().equals(NodeType.HEADER)).map(ASTNode::getValue).toList();
        return headerKeys.stream().anyMatch(key -> key.contains("application/json"));
    }
}
