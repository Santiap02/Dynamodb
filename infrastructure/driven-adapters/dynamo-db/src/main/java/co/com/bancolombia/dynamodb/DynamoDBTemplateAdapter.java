package co.com.bancolombia.dynamodb;

import co.com.bancolombia.dynamodb.helper.TemplateAdapterOperations;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<User, Long, UserEntity> implements UserRepository {

    public DynamoDBTemplateAdapter(AmazonDynamoDB connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, d -> mapper.map(d, User.class));
    }

        public List<User> findByType(String type) {
            DynamoDBQueryExpression<UserEntity> queryExpression = generateQueryType(type);
            return query(queryExpression);
        }

    public List<User> findByName(String name, String type) {
        DynamoDBQueryExpression<UserEntity> queryExpression = generateQueryName(name, type);
        return query(queryExpression);
    }

        private DynamoDBQueryExpression<UserEntity> generateQueryName(String name, String type) {
            Map<String, AttributeValue> eav = new HashMap<>();
            eav.put(":val1", new AttributeValue().withS(type));
            eav.put(":val2", new AttributeValue().withS(name));
            return new DynamoDBQueryExpression<UserEntity>()
                    .withKeyConditionExpression("tipo = :val1")
                    .withFilterExpression("nombre = :val2")
                    .withExpressionAttributeValues(eav);
        }

    private DynamoDBQueryExpression<UserEntity> generateQueryType(String type) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(type));
        return new DynamoDBQueryExpression<UserEntity>()
                .withKeyConditionExpression("tipo = :val1")
                .withExpressionAttributeValues(eav);
    }

}
