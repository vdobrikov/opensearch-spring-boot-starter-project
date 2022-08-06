package com.vdobrikov.opensearch.autoconfigure;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(OpenSearchProperties.class)
@Configuration
public class OpenSearchAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public CredentialsProvider credentialsProvider(OpenSearchProperties properties) {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword()));
        return credentialsProvider;
    }

    @ConditionalOnMissingBean
    @Bean
    public RestClientBuilder restClientBuilder(OpenSearchProperties properties, CredentialsProvider credentialsProvider) {
        return RestClient.builder(getHttpHosts(properties)).
                setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider))
                        .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                            .setConnectTimeout(properties.getConnectTimeoutMillis())
                            .setSocketTimeout(properties.getReadTimeoutMillis()));
    }

    @ConditionalOnMissingBean
    @Bean
    public OpenSearchClient openSearchClient(RestClientBuilder restClientBuilder) {
        RestClient restClient = restClientBuilder.build();
        OpenSearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new OpenSearchClient(transport);
    }

    @ConditionalOnMissingBean
    @Bean
    public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }

    private HttpHost[] getHttpHosts(OpenSearchProperties properties) {
        return properties.getHosts().stream()
                .map(host -> new HttpHost(host.getHostname(), host.getPort(), host.getProtocol()))
                .toArray(HttpHost[]::new);
    }
}
