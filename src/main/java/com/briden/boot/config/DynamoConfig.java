package com.briden.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Bean
    public DynamoDbEnhancedClient amazonDynamoDB() {
        DynamoDbClient client = DynamoDbClient.builder()
                .credentialsProvider(amazonAWSCredentials())
                .endpointOverride(URI.create(amazonDynamoDBEndpoint))
                .build();

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();
    }

    @Bean
    public AwsCredentialsProvider amazonAWSCredentials() {
        return DefaultCredentialsProvider.create();
    }
}
