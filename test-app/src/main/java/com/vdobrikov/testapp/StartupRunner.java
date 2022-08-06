package com.vdobrikov.testapp;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.indices.GetIndexRequest;
import org.opensearch.client.opensearch.indices.GetIndexResponse;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

public class StartupRunner implements ApplicationRunner {
    private final OpenSearchClient openSearchClient;

    public StartupRunner(OpenSearchClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index(List.of("*")).build();
        GetIndexResponse response = openSearchClient.indices().get(getIndexRequest);
        response.result().keySet().forEach(System.out::println);
    }
}
