package com.kevin.crawler.service.aws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.identity.spi.IdentityProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

public class AwsDynamicDbHelper {

  private static final Logger log = LoggerFactory.getLogger(AwsDynamicDbHelper.class);

  private static final List<String> TABLE_LIST = List.of("PttCrawler", "AwsSns");

  public static void main(String[] args) {
    AwsDynamicDbHelper awsDynamicDbHelper = new AwsDynamicDbHelper();

    AwsCredentialHelper credentialHelper = new AwsCredentialHelper();
//    IdentityProvider provider = credentialHelper.getProvider();
    DynamoDbClient ddb = DynamoDbClient.builder()
//      .credentialsProvider(provider)
      .region(Region.US_WEST_2)
      .build();

    List<String> list = awsDynamicDbHelper.showTableList(ddb);
    System.out.println(list);

    awsDynamicDbHelper.initAwsPttCrawlerItem(ddb);

//    for (String table : TABLE_LIST) {
//      if (!list.contains(table)) {
//        awsDynamicDbHelper.createTable(ddb, table);
//      }
//    }

  }


  // 若表不存在則創建
  public String createTable(DynamoDbClient ddb, String tableName) {
    DynamoDbWaiter dbWaiter = ddb.waiter();
    CreateTableRequest request = CreateTableRequest.builder().attributeDefinitions(
        AttributeDefinition.builder()
          .attributeName("id")
          .attributeType(ScalarAttributeType.S)
          .build())
      .keySchema(KeySchemaElement.builder()
        .attributeName("id")
        .keyType(KeyType.HASH)
        .build())
      .provisionedThroughput(ProvisionedThroughput.builder()
        .readCapacityUnits(10L)
        .writeCapacityUnits(10L)
        .build())
      .tableName(tableName)
      .build();

    try {
      CreateTableResponse response = ddb.createTable(request);
      DescribeTableRequest tableRequest = DescribeTableRequest.builder()
        .tableName(tableName)
        .build();
      WaiterResponse<DescribeTableResponse> waiterResponse = dbWaiter.waitUntilTableExists(
        tableRequest);
      waiterResponse.matched().response().ifPresent(System.out::println);
      return response.tableDescription().tableName();
    } catch (DynamoDbException e) {
      log.error(e.getMessage());
      System.exit(1);
    }
    return "";
  }

  public List<String> showTableList(DynamoDbClient ddb) {
    return ddb.listTables().tableNames();
  }

  private void initAwsSnsTableItem() {

  }


  private void initAwsPttCrawlerItem(DynamoDbClient ddb) {
    HashMap<String, AttributeValue> keyToGet = new HashMap<>();
    keyToGet.put("name", AttributeValue.builder().s("demo")
      .build());

    GetItemRequest getItemRequest = GetItemRequest.builder()
      .key(keyToGet)
      .tableName("subscriber")
      .build();

    try {
      Map<String, AttributeValue> returnedItem = ddb.getItem(getItemRequest).item();

      if (returnedItem != null) {
        Set<String> keys = returnedItem.keySet();
        System.out.println("Amazon DynamoDB table attributes: \n");

        for (String key1 : keys) {
          System.out.format("%s: %s\n", key1, returnedItem.get(key1).toString());
        }
      } else {
        System.out.format("No item found with the key %s!\n", keyToGet);
      }
    } catch (DynamoDbException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

  }


}
