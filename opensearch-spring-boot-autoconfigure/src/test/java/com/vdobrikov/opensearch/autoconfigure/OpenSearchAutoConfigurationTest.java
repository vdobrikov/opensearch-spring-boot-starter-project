package com.vdobrikov.opensearch.autoconfigure;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.junit.jupiter.api.Test;
import org.opensearch.client.Node;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = OpenSearchAutoConfiguration.class)
class OpenSearchAutoConfigurationTest {

    @Autowired
    OpenSearchProperties properties;

    @Autowired
    private CredentialsProvider credentialsProvider;

    @Autowired
    private RestClientBuilder restClientBuilder;

    @Autowired
    private OpenSearchClient openSearchClient;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void credentialsProvider() {
        assertThat(credentialsProvider)
                .isNotNull();
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY))
                .isNotNull();
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getUserPrincipal())
                .isNotNull();
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getUserPrincipal().getName())
                .isEqualTo(properties.getUsername());
        assertThat(credentialsProvider.getCredentials(AuthScope.ANY).getPassword())
                .isEqualTo(properties.getPassword());
    }

    @Test
    void restClientBuilder() {
        assertThat(restClientBuilder)
                .isNotNull();
        assertThat(restClientBuilder)
                .hasFieldOrPropertyWithValue("nodes", properties.getHosts()
                        .stream()
                        .map(host -> new HttpHost(host.getHostname(), host.getPort(), host.getProtocol()))
                        .map(Node::new)
                        .collect(Collectors.toList()));
    }

    @Test
    void openSearchClient() {
        assertThat(openSearchClient)
                .isNotNull();
    }

    @Test
    void restHighLevelClient() {
        assertThat(restHighLevelClient)
                .isNotNull();
    }
}