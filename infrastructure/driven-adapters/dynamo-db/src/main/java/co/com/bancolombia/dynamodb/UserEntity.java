package co.com.bancolombia.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DynamoDBTable(tableName = "test2")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @DynamoDBRangeKey(attributeName = "id")
    private Long id;

    @DynamoDBHashKey(attributeName = "tipo")
    private String tipo;

    @DynamoDBAttribute(attributeName = "name")
    private String nombre;

    @DynamoDBAttribute(attributeName = "email")
    private String email;
}
