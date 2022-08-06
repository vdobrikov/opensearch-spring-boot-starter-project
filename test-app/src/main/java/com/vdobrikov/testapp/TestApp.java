package com.vdobrikov.testapp;

import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestApp {
    @Autowired
    private OpenSearchClient openSearchClient;

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

    @Bean
    public StartupRunner startupRunner(OpenSearchClient openSearchClient) {
        return new StartupRunner(openSearchClient);
    }
}
