//package com.pc.dubboprovider.config;
//
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import org.springframework.util.StringUtils;
//
///**
// * es配置
// *
// * @author pengchao
// * @date 14:40 2020-09-25
// */
//@Configuration
//public class ElasticSearchConfiguration {
//
//    @Value("${elasticsearch.hosts}")
//    private String host;
//    @Value("${elasticsearch.username}")
//    private String username;
//    @Value("${elasticsearch.password}")
//    private String password;
//
//    @Bean(destroyMethod = "close",name = "highLevelClient")//这个close是调用RestHighLevelClient中的close
//    @Scope("singleton")
//    public RestHighLevelClient highLevelClient() {
//        RestHighLevelClient restHighLevelClient;
//        try {
//            String[] hosts = host.split(",");
//            HttpHost[] httpHosts = new HttpHost[hosts.length];
//            for (int i = 0; i < httpHosts.length; i++) {
//                String h = hosts[i];
//                httpHosts[i] = new HttpHost(h.split(":")[0], Integer.parseInt(h.split(":")[1]), "http");
//            }
//
//            if(!StringUtils.isEmpty(username)) {
//                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//                credentialsProvider.setCredentials(AuthScope.ANY,
//                        new UsernamePasswordCredentials(username, password));  //es账号密码（默认用户名为elastic）
//                restHighLevelClient = new RestHighLevelClient(
//                        RestClient.builder(httpHosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                            @Override
//                            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                                httpClientBuilder.disableAuthCaching();
//                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                            }
//                        })
//                );
//            }else{
//                restHighLevelClient = new RestHighLevelClient(
//                        RestClient.builder(httpHosts));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return restHighLevelClient;
//    }
//}
