package com.vdobrikov.opensearch.autoconfigure;

import lombok.Data;

@Data
public class HostProperties {
    private String hostname = "localhost";
    private int port = 9200;
    private String protocol = "https";
}
