package com.briden.boot.entity.dynamo;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Setter
public class DynamoEntry {

    private String id;
    private String companyId;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public String getCompanyId() {
        return companyId;
    }
}
