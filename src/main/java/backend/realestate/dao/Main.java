package backend.realestate.dao;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.net.URI;
public class Main {

    public static void main(String[] args) {
//        String connString = System.getenv("https://h2ub1kad9w:26325i1do8@product-9315306240.us-east-1.bonsaisearch.net:443");
//        URI connUri = URI.create(connString);
//        String[] auth = connUri.getUserInfo().split(":");

        CredentialsProvider cp = new BasicCredentialsProvider();
        cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("h2ub1kad9w", "26325i1do8"));

        RestHighLevelClient rhlc = new RestHighLevelClient(
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
//            SearchResponse resp  = rhlc.search(searchRequest);
            // Show that the query worked
//            System.out.println(resp.toString());
        } catch (Exception ex) {
            // Log the exception
            System.out.println(ex.toString());
        }

        // Need to close the client so the thread will exit
        try {
            rhlc.close();
        } catch (Exception ex) {

        }
    }
}
