package com.briden.boot.repository.dynamo;

import com.briden.boot.entity.dynamo.DynamoEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Service
public class DynamoService  {

    @Autowired
    private DynamoDbEnhancedClient dynamoDb;

    public void saveToDynamo(DynamoEntry dynamoEntry) {
        DynamoDbTable<DynamoEntry> entryTable = dynamoDb.table("dynamo-entrys", TableSchema.fromBean(DynamoEntry.class));
        entryTable.putItem(dynamoEntry);
    }

    public DynamoEntry getItemFromDynamo(DynamoEntry dynamoEntry) {
        DynamoDbTable<DynamoEntry> entryTable = dynamoDb.table("dynamo-entrys", TableSchema.fromBean(DynamoEntry.class));
        return entryTable.getItem(dynamoEntry);
    }

    public void deleteItemFromDynamo(DynamoEntry dynamoEntry) {
        DynamoDbTable<DynamoEntry> entryTable = dynamoDb.table("dynamo-entrys", TableSchema.fromBean(DynamoEntry.class));
        entryTable.deleteItem(dynamoEntry);
    }
}
