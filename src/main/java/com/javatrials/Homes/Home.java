package com.javatrials.Homes;

import com.javatrials.Errors.StudentBadRequestException;
import com.javatrials.Errors.StudentNotFoundException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class Home {

  private static DynamoDBClient client;
  private String tableName;

  public Home(String tableName){
    this.tableName  = tableName;
  }

  static {
    client = DynamoDBClient.builder().region(Region.US_EAST_2).build();
  }

  protected Map<String, AttributeValue> GetOne(Integer id){

    QueryRequest request = QueryById(id.toString());

    return ExecuteAndGetResultOrThrow(request, (it) -> client.query(it), (QueryResponse it) -> it.items().get(0));
  }

  protected Collection<Map<String, AttributeValue>> GetAll() {
    ScanRequest request = ScanRequest.builder().tableName("Students").build();

    return ExecuteAndGetResultOrThrow(request, (it) -> client.scan(it), (ScanResponse it) -> it.items());
  }

  protected PutItemResponse Create(
    Map<String,AttributeValue> values
  ) {

    PutItemRequest request = PutItemRequest.builder()
      .tableName(tableName)
      .item(values)
      .build();

    return SafeExecute(request, (it) -> client.putItem(it));
  }

  protected UpdateItemResponse Update(
    Map<String,AttributeValue> item_key,
    Map<String, AttributeValueUpdate> updated_values
  ) {

    UpdateItemRequest request = UpdateItemRequest.builder()
      .tableName(tableName)
      .key(item_key)
      .attributeUpdates(updated_values)
      .build();

    return SafeExecute(request, (it) -> client.updateItem(it));
  }

  protected DeleteItemResponse Delete(Map<String,AttributeValue> item_key) {
    DeleteItemRequest request = DeleteItemRequest.builder()
      .tableName(tableName)
      .key(item_key)
      .build();

    return SafeExecute(request, (it) -> client.deleteItem(it));
  }

  protected void addToValues(String key, String value, Map<String, AttributeValueUpdate> values) {
    values.put(
      key,
      AttributeValueUpdate.builder()
        .value(
          AttributeValue.builder()
            .s(value)
            .build()
        )
        .action(AttributeAction.PUT)
        .build()
    );
  }

  private QueryRequest QueryById(String id) {
    return QueryRequest.builder()
      .tableName(tableName)
      .keyConditionExpression("id = :id")
      .expressionAttributeValues(new HashMap<String, AttributeValue>() {{
        put(":id", AttributeValue.builder().n(id).build());
      }})
      .build();
  }

  private <T, R, E> E ExecuteAndGetResultOrThrow(T request, Function<T, R> executable, Function<R, E> getter){
    R response = SafeExecute(request, executable);
    return GetResultOrElseThrow(response, getter);
  }

  private <T, R> R GetResultOrElseThrow(T response, Function<T, R> getter) {
    return Optional.ofNullable(getter.apply(response)).orElseThrow(() -> new StudentNotFoundException());
  }

  private <T, R> R SafeExecute(T request, Function<T, R> executable) {
    try {
      return executable.apply(request);
    }
    catch(Exception e) {
      System.out.println("ooops, something went wrong");
      System.out.println(e.getMessage());

      throw new StudentBadRequestException();
    }
  }
}
