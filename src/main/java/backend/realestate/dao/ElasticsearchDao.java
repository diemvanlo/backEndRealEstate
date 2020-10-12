package backend.realestate.dao;

import backend.realestate.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import fr.pilato.elasticsearch.tools.ElasticsearchBeyonder;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ExecutionException;

@Component
public class ElasticsearchDao {
    private final RestHighLevelClient esClient;
    private final ObjectMapper mapper;
//    private final BulkProcessor bulkProcessor;

//    public ElasticsearchDao(ObjectMapper mapper) {
//        this.mapper = mapper;
//        esClient = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(
//                new TransportAddress(
//                        new InetSocketAddress("https://yvfpxa5r67:jb6jp5qsb1@product-9315306240.us-east-1.bonsaisearch.net", 443)));
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

    public ElasticsearchDao(ObjectMapper mapper) {
        this.mapper = mapper;
        CredentialsProvider cp = new BasicCredentialsProvider();
        cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("h2ub1kad9w", "26325i1do8"));

        this.esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("us-east-1.bonsaisearch.net", 443, "product-9315306240"))
                        .setHttpClientConfigCallback(
                                httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(cp)
                                        .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));

        // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high-search.html
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        try {
//            SearchResponse resp  = this.esClient.search(searchRequest);
            // Show that the query worked
//            System.out.println(resp.toString());
        } catch (Exception ex) {
            // Log the exception
            System.out.println(ex.toString());
        }

    }

    public void save(Product product) throws IOException, ExecutionException, InterruptedException, IOException {
        byte[] bytes = mapper.writeValueAsBytes(product);
        esClient.index(new IndexRequest("product", "doc", product.idAsString()).source(bytes, XContentType.JSON), RequestOptions.DEFAULT).getIndex();
    }
    public void save(Agent agent) throws IOException, ExecutionException, InterruptedException, IOException {
        byte[] bytes = mapper.writeValueAsBytes(agent);
        esClient.index(new IndexRequest("agent", "doc", agent.idAsString()).source(bytes, XContentType.JSON), RequestOptions.DEFAULT).getIndex();
    }
    public void save(Project project) throws IOException, ExecutionException, InterruptedException, IOException {
        byte[] bytes = mapper.writeValueAsBytes(project);
        esClient.index(new IndexRequest("project", "doc", project.idAsString()).source(bytes, XContentType.JSON), RequestOptions.DEFAULT).getIndex();
    }
    public void save(News news) throws IOException, ExecutionException, InterruptedException, IOException {
        byte[] bytes = mapper.writeValueAsBytes(news);
        esClient.index(new IndexRequest("news", "doc", news.idAsString()).source(bytes, XContentType.JSON), RequestOptions.DEFAULT).getIndex();
    }
    public void save(Partner partner) throws IOException, ExecutionException, InterruptedException, IOException {
        byte[] bytes = mapper.writeValueAsBytes(partner);
        esClient.index(new IndexRequest("partner", "doc", partner.idAsString()).source(bytes, XContentType.JSON), RequestOptions.DEFAULT).getIndex();
    }
    public void save(Category category) throws IOException, ExecutionException, InterruptedException, IOException {
        byte[] bytes = mapper.writeValueAsBytes(category);
        esClient.index(new IndexRequest("category", "doc", category.idAsString()).source(bytes, XContentType.JSON), RequestOptions.DEFAULT).getIndex();
    }
    public void save(Image image) throws IOException, ExecutionException, InterruptedException, IOException {
        byte[] bytes = mapper.writeValueAsBytes(image);
        esClient.index(new IndexRequest("image", "doc", image.idAsString()).source(bytes, XContentType.JSON), RequestOptions.DEFAULT).getIndex();
    }

    public void delete(Long id) throws ExecutionException, InterruptedException, IOException {
        esClient.delete(new DeleteRequest("product", "doc", id.toString()), RequestOptions.DEFAULT).getIndex();
    }
    public void delete1(Long id) throws ExecutionException, InterruptedException, IOException {
        esClient.delete(new DeleteRequest("project", "doc", id.toString()), RequestOptions.DEFAULT).getIndex();
    }
    public void delete2(Long id) throws ExecutionException, InterruptedException, IOException {
        esClient.delete(new DeleteRequest("agent", "doc", id.toString()), RequestOptions.DEFAULT).getIndex();
    }
    public void delete3(Long id) throws ExecutionException, InterruptedException, IOException {
        esClient.delete(new DeleteRequest("news", "doc", id.toString()), RequestOptions.DEFAULT).getIndex();
    }
    public void delete4(Long id) throws ExecutionException, InterruptedException, IOException {
        esClient.delete(new DeleteRequest("partner", "doc", id.toString()), RequestOptions.DEFAULT).getIndex();
    }
    public void delete5(Long id) throws ExecutionException, InterruptedException, IOException {
        esClient.delete(new DeleteRequest("category", "doc", id.toString()), RequestOptions.DEFAULT).getIndex();
    }
    public void delete6(Long id) throws ExecutionException, InterruptedException, IOException {
        esClient.delete(new DeleteRequest("image", "doc", id.toString()), RequestOptions.DEFAULT).getIndex();
    }

    public SearchResponse search(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException, IOException {
        SearchResponse response = esClient.search(new SearchRequest("product").source(
                new SearchSourceBuilder().query(query).from(from).size(size)),RequestOptions.DEFAULT);
        return response;
    }
//    public SearchResponse search1(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException, IOException {
//        SearchResponse response = esClient.search(new SearchRequest("project").source(
//                new SearchSourceBuilder().query(query).from(from).size(size)));
//        return response;
//    }
//    public SearchResponse search2(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException, IOException {
//        SearchResponse response = esClient.search(new SearchRequest("agent").source(
//                new SearchSourceBuilder().query(query).from(from).size(size)));
//        return response;
//    }
//    public SearchResponse search3(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException, IOException {
//        SearchResponse response = esClient.search(new SearchRequest("news").source(
//                new SearchSourceBuilder().query(query).from(from).size(size)));
//        return response;
//    }
//    public SearchResponse search4(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException, IOException {
//        SearchResponse response = esClient.search(new SearchRequest("partner").source(
//                new SearchSourceBuilder().query(query).from(from).size(size)));
//        return response;
//    }
//    public SearchResponse search5(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException, IOException {
//        SearchResponse response = esClient.search(new SearchRequest("category").source(
//                new SearchSourceBuilder().query(query).from(from).size(size)));
//        return response;
//    }
//    public SearchResponse search6(QueryBuilder query, Integer from, Integer size) throws ExecutionException, InterruptedException, IOException {
//        SearchResponse response = esClient.search(new SearchRequest("image").source(
//                new SearchSourceBuilder().query(query).from(from).size(size)));
//        return response;
//    }


}
