package com.pc.dubboprovider;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singletonMap;

/**
 *
 * @author pengchao
 * @date 13:56 2020-09-25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsClientTest {


    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Test
    public void test() {
//        esClient.aggs(AggsType.count)


        SearchRequest searchRequest = new SearchRequest("library");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //如果用name直接查询，其实是匹配name分词过后的索引查到的记录(倒排索引)；如果用name.keyword查询则是不分词的查询，正常查询到的记录
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("birthday").from("1991-01-01").to("2010-10-10").format("yyyy-MM-dd");//范围查询
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name.keyword", name);//精准查询
        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("name.keyword", "Fourth");//前缀查询
//        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("name.keyword", "*三");//通配符查询
//        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "三");//模糊查询
        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("num");//按照年龄排序
        fieldSortBuilder.sortMode(SortMode.MIN);//从小到大排序

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(rangeQueryBuilder).should(prefixQueryBuilder);//and or  查询

        sourceBuilder.query(boolQueryBuilder).sort(fieldSortBuilder);//多条件查询
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            List<Book> list = new ArrayList<>();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                Book t = JSONObject.parseObject(sourceAsString,Book.class);
                list.add(t);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println();


    }


    @Test
    public void indexApi() throws Exception {

    //1.四种索引方式
//        IndexRequest request = new IndexRequest("posts");
//        request.id("1");
//        String jsonString = "{" +
//                "\"user\":\"kimchy\"," +
//                "\"postDate\":\"2013-01-30\"," +
//                "\"message\":\"trying out Elasticsearch\"" +
//                "}";
//        request.source(jsonString, XContentType.JSON);
//
//        IndexResponse indexResponse = restHighLevelClient.index(request,RequestOptions.DEFAULT);



//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("user", "pengchao");
//        jsonMap.put("postDate", new Date());
//        jsonMap.put("message", "give up Elasticsearch");
//        IndexRequest indexRequest = new IndexRequest("posts")
//                .id("2").source(jsonMap);
//        IndexResponse indexResponse = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);


//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//        {
//            builder.field("user", "鲁迅");
//            builder.timeField("postDate", new Date());
//            builder.field("message", "trying out Elasticsearch");
//        }
//        builder.endObject();
//        IndexRequest indexRequest = new IndexRequest("posts")
//                .id("3").source(builder);
//        IndexResponse indexResponse = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);


//
        IndexRequest request = new IndexRequest("posts")
                .id("1")
                .source("user", "超小超",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
//        IndexResponse indexResponse = restHighLevelClient.index(request,RequestOptions.DEFAULT);


        //2.可选参数

        //等待主分片超时
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");


        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        request.setRefreshPolicy("wait_for");



        //3.异步执行
        IndexRequest myrequest = new IndexRequest("posts")
                .id("8")
                .source("user", "静静",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");

        Cancellable cancellable = restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(indexResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.err.println(e);
            }
        });


        //4处理response

        IndexRequest request5 = new IndexRequest("posts")
                .id("6")
                .source("user", "静静",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
        IndexResponse indexResponse = restHighLevelClient.index(request5,RequestOptions.DEFAULT);
        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            //处理首次创建文档的情况
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            //处理已存在的文档被重写的情况
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            //处理成功分片数量少于总分片数量的情况
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                //处理潜在的故障
                String reason = failure.reason();
            }
        }

        //5.乐观锁
        IndexRequest request6 = new IndexRequest("posts")
                .id("6")
                .source("user", "静静",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch")
                .setIfSeqNo(10L)
                .setIfPrimaryTerm(20L);
//                .opType(DocWriteRequest.OpType.CREATE);已存在会报错
        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            //seqNo和primaryTerm对不上会抛出409异常
            if (e.status() == RestStatus.CONFLICT) {

            }
        }

    }



    @Test
    public void getApi() throws Exception {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");


        GetResponse response  = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        Map<String, Object> source = response.getSource();
        String string = response.getSourceAsString();
        byte[] bytes = response.getSourceAsBytes();


        Cancellable cancellable  = restHighLevelClient.getAsync(getRequest, RequestOptions.DEFAULT, new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse documentFields) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        //{"user":"kimchy","postDate":"2013-01-30","message":"trying out Elasticsearch"}
        System.out.println(response.getSourceAsString());

        //1
//        getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);//指定需要返回字段的上下文，是storedFields的补充与完善，支持通配符

        //2 {"postDate":"2013-01-30","message":"trying out Elasticsearch"}
//        String[] includes = new String[]{"message", "*Date"};
//        String[] excludes = Strings.EMPTY_ARRAY;
//        FetchSourceContext fetchSourceContext =
//                new FetchSourceContext(true, includes, excludes);
//
//        //3 {"postDate":"2013-01-30","user":"kimchy"}
        String[] includes = Strings.EMPTY_ARRAY;
        String[] excludes = new String[]{"message"};
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);


        getRequest.fetchSourceContext(fetchSourceContext);
        GetResponse response1 = restHighLevelClient.get(getRequest,RequestOptions.DEFAULT);
        System.out.println(response1.getSourceAsString());





        GetRequest request = new GetRequest(
                "posts",
                "1");
        request.storedFields("message","user");
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        String message = getResponse.getField("message").getValue();
        System.out.println(message);


    }

    @Test
    public void existApi() throws Exception {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");


        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);

        System.out.println();

    }


    @Test
    public void deleteApi() throws Exception {

        DeleteRequest deleteRequest = new DeleteRequest("posts","1");

        deleteRequest.timeout(TimeValue.timeValueSeconds(20));

        DeleteResponse response = restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);


        System.out.println();


        //防止版本冲突
//        DeleteResponse deleteResponse = restHighLevelClient.delete(
//                new DeleteRequest("posts", "1").setIfSeqNo(100).setIfPrimaryTerm(2),
//                RequestOptions.DEFAULT);




        IndexRequest myrequest = new IndexRequest("posts")
                .id("1")
                .source("user", "超小超",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
        restHighLevelClient.index(myrequest,RequestOptions.DEFAULT);

    }

    @Test
    public void updateApi() {

        UpdateRequest request = new UpdateRequest(
                "posts",
                "1");

        Map<String, Object> parameters = singletonMap("count", 4);

        Script inline = new Script(ScriptType.INLINE, "painless",
                "ctx._source.field += params.count", parameters);
        request.script(inline);




    }



}

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Book {

    private String name;
    private String author;
    private Integer num;//页
    private String date;//出版日期


}
