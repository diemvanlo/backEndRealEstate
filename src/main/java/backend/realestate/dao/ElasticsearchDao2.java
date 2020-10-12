package backend.realestate.dao;

import backend.realestate.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import fr.pilato.elasticsearch.tools.ElasticsearchBeyonder;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

//@Component
public class ElasticsearchDao2 {
//    private final Client esClient;
//    private final ObjectMapper mapper;
//    private final BulkProcessor bulkProcessor;
//
//    public ElasticsearchDao2(ObjectMapper mapper) {
//        this.mapper = mapper;
//        esClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(
//                new TransportAddress(
//                        new InetSocketAddress("127.0.0.1", 9300)));
//        this.bulkProcessor = BulkProcessor.builder(esClient, new BulkProcessor.Listener() {
//            @Override
//            public void beforeBulk(long l, BulkRequest bulkRequest) {
//
//            }
//
//            @Override
//            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
//
//            }
//
//            @Override
//            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
//
//            }
//        }).setBulkActions(1000).setFlushInterval(TimeValue.timeValueSeconds(5)).build();
//        try {
//            ElasticsearchBeyonder.start(esClient);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void save(Product product) throws JsonProcessingException, ExecutionException, InterruptedException {
//        byte[] bytes = mapper.writeValueAsBytes(product);
//        esClient.index(new IndexRequest("product", "doc", product.idAsString()).source(bytes, XContentType.JSON)).get();
//    }
//    public void save(Agent agent) throws JsonProcessingException, ExecutionException, InterruptedException {
//        byte[] bytes = mapper.writeValueAsBytes(agent);
//        esClient.index(new IndexRequest("agent", "doc", agent.idAsString()).source(bytes, XContentType.JSON)).get();
//    }
//    public void save(Project project) throws JsonProcessingException, ExecutionException, InterruptedException {
//        byte[] bytes = mapper.writeValueAsBytes(project);
//        esClient.index(new IndexRequest("project", "doc", project.idAsString()).source(bytes, XContentType.JSON)).get();
//    }
//    public void save(News news) throws JsonProcessingException, ExecutionException, InterruptedException {
//        byte[] bytes = mapper.writeValueAsBytes(news);
//        esClient.index(new IndexRequest("news", "doc", news.idAsString()).source(bytes, XContentType.JSON)).get();
//    }
//    public void save(Partner partner) throws JsonProcessingException, ExecutionException, InterruptedException {
//        byte[] bytes = mapper.writeValueAsBytes(partner);
//        esClient.index(new IndexRequest("partner", "doc", partner.idAsString()).source(bytes, XContentType.JSON)).get();
//    }
//    public void save(Category category) throws JsonProcessingException, ExecutionException, InterruptedException {
//        byte[] bytes = mapper.writeValueAsBytes(category);
//        esClient.index(new IndexRequest("category", "doc", category.idAsString()).source(bytes, XContentType.JSON)).get();
//    }
//    public void save(Image image) throws JsonProcessingException, ExecutionException, InterruptedException {
//        byte[] bytes = mapper.writeValueAsBytes(image);
//        esClient.index(new IndexRequest("image", "doc", image.idAsString()).source(bytes, XContentType.JSON)).get();
//    }
//
//    public void delete(Long id) throws ExecutionException, InterruptedException {
//        esClient.delete(new DeleteRequest("product", "doc", id.toString())).get();
//    }
//    public void delete1(Long id) throws ExecutionException, InterruptedException {
//        esClient.delete(new DeleteRequest("project", "doc", id.toString())).get();
//    }
//    public void delete2(Long id) throws ExecutionException, InterruptedException {
//        esClient.delete(new DeleteRequest("agent", "doc", id.toString())).get();
//    }
//    public void delete3(Long id) throws ExecutionException, InterruptedException {
//        esClient.delete(new DeleteRequest("news", "doc", id.toString())).get();
//    }
//    public void delete4(Long id) throws ExecutionException, InterruptedException {
//        esClient.delete(new DeleteRequest("partner", "doc", id.toString())).get();
//    }
//    public void delete5(Long id) throws ExecutionException, InterruptedException {
//        esClient.delete(new DeleteRequest("category", "doc", id.toString())).get();
//    }
//    public void delete6(Long id) throws ExecutionException, InterruptedException {
//        esClient.delete(new DeleteRequest("image", "doc", id.toString())).get();
//    }
//
//    public SearchResponse search(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException {
//        SearchResponse response = esClient.search(new SearchRequest("product").source(
//                new SearchSourceBuilder().query(query).from(from).size(size))).get();
//        return response;
//    }
//    public SearchResponse search1(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException {
//        SearchResponse response = esClient.search(new SearchRequest("project").source(
//                new SearchSourceBuilder().query(query).from(from).size(size))).get();
//        return response;
//    }
//    public SearchResponse search2(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException {
//        SearchResponse response = esClient.search(new SearchRequest("agent").source(
//                new SearchSourceBuilder().query(query).from(from).size(size))).get();
//        return response;
//    }
//    public SearchResponse search3(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException {
//        SearchResponse response = esClient.search(new SearchRequest("news").source(
//                new SearchSourceBuilder().query(query).from(from).size(size))).get();
//        return response;
//    }
//    public SearchResponse search4(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException {
//        SearchResponse response = esClient.search(new SearchRequest("partner").source(
//                new SearchSourceBuilder().query(query).from(from).size(size))).get();
//        return response;
//    }
//    public SearchResponse search5(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException {
//        SearchResponse response = esClient.search(new SearchRequest("category").source(
//                new SearchSourceBuilder().query(query).from(from).size(size))).get();
//        return response;
//    }
//    public SearchResponse search6(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException {
//        SearchResponse response = esClient.search(new SearchRequest("image").source(
//                new SearchSourceBuilder().query(query).from(from).size(size))).get();
//        return response;
//    }
//

}
