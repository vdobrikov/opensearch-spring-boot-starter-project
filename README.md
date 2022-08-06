# opensearch-spring-boot-starter

## Description
Spring Boot starter for [opensearch](https://opensearch.apache.org/).

## Usage
### `pom.xml`

```xml
        <dependency>
            <groupId>com.vdobrikov</groupId>
            <artifactId>opensearch-spring-boot-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

### `application.properties`

```properties
opensearch.hosts[0].hostname = localhost
opensearch.hosts[0].port = 9200
opensearch.hosts[0].protocol = http
opensearch.username=test
opensearch.password=test-password
opensearch.connect-timeout-millis=1500
opensearch.read-timeout-millis=3500
```

### Java code

```java
    // New OpenSearch client
    @Autowired
    private OpenSearchClient openSearchClient;
    
    // OpenSearch client from ElasticSearch
    @Autowired
    private RestHighLevelClient restHighLevelClient;
```