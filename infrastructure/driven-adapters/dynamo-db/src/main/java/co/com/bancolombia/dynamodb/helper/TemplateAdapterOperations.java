package co.com.bancolombia.dynamodb.helper;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import org.reactivecommons.utils.ObjectMapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;


public abstract class TemplateAdapterOperations<E, K, V> {
    private final Class<V> dataClass;
    protected DynamoDBMapper dynamoDBMapper;
    private final Function<V, E> toEntityFn;
    protected ObjectMapper mapper;

    protected TemplateAdapterOperations(AmazonDynamoDB amazonDynamoDB, ObjectMapper mapper, Function<V, E> toEntityFn) {
        this.toEntityFn = toEntityFn;
        this.mapper = mapper;
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, DynamoDBMapperConfig.DEFAULT);
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<V>) genericSuperclass.getActualTypeArguments()[2];
        createTableIfNotExists(amazonDynamoDB);
    }

    private void createTableIfNotExists(AmazonDynamoDB amazonDynamoDB) {
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(dataClass);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
    }

    public E save(E model) {
        V entity = toEntity(model);
        dynamoDBMapper.save(entity);
        return toModel(entity);
    }

    public E findById(K id) {
        return toModel(dynamoDBMapper.load(dataClass, id));
    }

    public void delete(E model) {
        dynamoDBMapper.delete(toEntity(model));
    }

    public List<E> query(DynamoDBQueryExpression<V> queryExpression) {
        PaginatedQueryList<V> result = dynamoDBMapper.query(dataClass,queryExpression);
        return result.stream().map(this::toModel).toList();
    }


    protected V toEntity(E model) {
        return mapper.map(model, dataClass);
    }

    protected E toModel(V data) {
        return data != null ? toEntityFn.apply(data) : null;
    }
}

