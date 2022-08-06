package com.vdobrikov.opensearch.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collection;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "opensearch")
public class OpenSearchProperties {
    private Collection<HostProperties> hosts = List.of(new HostProperties());
    private String username = "admin";
    private String password = "admin";
    private int connectTimeoutMillis = 1_000;
    private int readTimeoutMillis = 10_000;
}
