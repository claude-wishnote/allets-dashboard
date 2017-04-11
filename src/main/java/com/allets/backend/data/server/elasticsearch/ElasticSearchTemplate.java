package com.allets.backend.data.server.elasticsearch;

import com.allets.backend.data.server.elasticsearch.model.MG_COMMENT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.allets.backend.data.server.elasticsearch.model.COMMENT_REPORT;
import com.allets.backend.data.server.elasticsearch.model.USER;
import com.allets.backend.data.server.exception.ELSWriteException;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by claude on 2015/12/17.
 */
public class ElasticSearchTemplate {
    private final Logger log = LoggerFactory
            .getLogger(ElasticSearchTemplate.class);
    private String host;
    /*
    not http request port
     */
    private Integer transportClientPort;

    private String clustername;

    private Boolean elasticsearchSwitch;

    public ElasticSearchTemplate(String host, Integer transportClientPort, String clustername, Boolean elasticsearchSwitch) throws Exception {
        this.host = host;
        this.transportClientPort = transportClientPort;
        this.clustername = clustername;
        this.elasticsearchSwitch = elasticsearchSwitch;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getTransportClientPort() {
        return transportClientPort;
    }

    public void setTransportClientPort(Integer transportClientPort) {
        this.transportClientPort = transportClientPort;
    }

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public Boolean getElasticsearchSwitch() {
        return elasticsearchSwitch;
    }

    public void setElasticsearchSwitch(Boolean elasticsearchSwitch) {
        this.elasticsearchSwitch = elasticsearchSwitch;
    }

    public SearchHits searchMgComment(String indexName, String type, String field, String keyWord, Date startDate, Date endDate, Integer offset, Integer limit) throws Exception {
        StringBuilder QParseSB = new StringBuilder();
        QParseSB.append("{\"query\": {\"bool\": {\"must\": [");
        if (StringUtils.isNotBlank(keyWord)) {
            if (field.equals("nickName")) {
                QParseSB.append("{\"match\": {\"user_name\": \"" + keyWord + "\"}},");
            } else if (field.equals("email")) {
                QParseSB.append("{\"match\": {\"user_email\": \"" + keyWord + "\"}},");
            } else if (field.equals("keyword")) {
                QParseSB.append("{\"match\": {\"text\": \"" + keyWord + "\"}},");
            }
        }
        QParseSB.append("{\"match\": {\"status\": \"ACTV\"}}");
        QParseSB.append(",{\"range\": {\"cdate\": {");
        if (startDate != null || endDate != null) {
            if (startDate != null) {
                QParseSB.append("\"gte\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(startDate) + "\"");
            }
            if (startDate != null && endDate != null) {
                QParseSB.append(",");
            }
            if (endDate != null) {
                QParseSB.append("\"lt\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(endDate) + "\"");
            }
        }
        QParseSB.append("}}}]}},  \"sort\":[");
        if (StringUtils.isNotBlank(keyWord)) {
            QParseSB.append("{ \"_score\": { \"order\": \"desc\" }},");
        }
        QParseSB.append("{\"cdate\":{\"order\": \"desc\"}}],");
        QParseSB.append("\"from\": ");
        QParseSB.append(offset);
        QParseSB.append(",\"size\": ");
        QParseSB.append(limit);
        QParseSB.append("}");
        log.info(QParseSB.toString());
        Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), transportClientPort));
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName).setTypes(type).setSource(QParseSB.toString());
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        client.close();
        return hits;
    }

    public SearchHits searchReportComment(String indexName, String type, String field, String keyWord, Date startDate, Date endDate, Integer offset, Integer limit) throws Exception {
        String QParse = "{\"query\": {\"filtered\": {\"query\": {\"";
        log.info(field);
        log.info(keyWord);
        if (StringUtils.isNotBlank(keyWord)) {
            if (field.equals("nickName")) {
                QParse = QParse + "match\": {\"user_name\": \"" + keyWord + "\"}";
            } else if (field.equals("email")) {
                QParse = QParse + "match\": {\"user_email\": \"" + keyWord + "\"}";
            } else if (field.equals("keyword")) {
                QParse = QParse + "match\": {\"text\": \"" + keyWord + "\"}";
            }
        } else {
            QParse = QParse + "match_all\": {}";
        }
        QParse = QParse + "},\"filter\": [";
        QParse = QParse + "{\"range\": {\"report_cdate\": {";
        if (startDate != null || endDate != null) {
            if (startDate != null) {
                QParse = QParse + "\"gte\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(startDate) + "\"";
            }
            if (startDate != null && endDate != null) {
                QParse = QParse + ",";
            }
            if (endDate != null) {
                QParse = QParse + "\"lt\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(endDate) + "\"";
            }
        }
        QParse = QParse + "}}}";
        QParse = QParse + "]" +
                "}},  \"sort\":[{ \"_score\": { \"order\": \"desc\" }},{\"report_cdate\":{\"order\": \"desc\"}}]," +
                "\"from\": " + offset + "," +
                "\"size\": " + limit + "}";
        log.info(QParse);
        Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), transportClientPort));
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName).setTypes(type).setSource(QParse);
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        client.close();
        return hits;
    }

    public void DeleteCommentReport(String indexName, String type, List<Long> list) throws Exception {
        List<DeleteRequest> requests = new ArrayList<DeleteRequest>();
        Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), transportClientPort));
        log.info(list.toString());
        try {
            for (Long id : list) {
                DeleteRequest request = client
                        .prepareDelete(indexName, type, String.valueOf(id))
                        .request();
                requests.add(request);
            }
            if (requests.size() > 0) {
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                for (DeleteRequest request : requests) {
                    bulkRequest.add(request);
                }
                BulkResponse bulkResponse = bulkRequest.setRefresh(true).execute().actionGet();
                if (bulkResponse.hasFailures()) {
                    throw new ELSWriteException();
                } else {
                    log.info("ELS Write data successfully.");
                }
                bulkRequest.request().requests().clear();
            } else {
                log.info("No data for ELS");
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
            client.close();
        }
    }

    public void ModifyCommentReport(String indexName, String type, List<COMMENT_REPORT> list) throws Exception {
        List<IndexRequest> requests = new ArrayList<IndexRequest>();
        Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), transportClientPort));
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
            for (COMMENT_REPORT comment_report : list) {
                String json = mapper.writeValueAsString(comment_report);
                //   String json = mg_comment.toJsonString();
                log.info(comment_report.toString());
                log.info(json);
                IndexRequest request = client
                        .prepareIndex(indexName, type, comment_report.getComment_id().toString()).setSource(json)
                        .request();
                requests.add(request);
            }
            if (requests.size() > 0) {
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                log.info(String.valueOf(requests.size()));
                for (IndexRequest request : requests) {
                    bulkRequest.add(request);
                }
                BulkResponse bulkResponse = bulkRequest.setRefresh(true).execute().actionGet();
                if (bulkResponse.hasFailures()) {
                    throw new ELSWriteException();
                }
                bulkRequest.request().requests().clear();
                log.info("ELS Write data successfully.");
            } else {
                log.info("No data for ELS");
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
            client.close();
        }
    }

    public void ModifyMgComment(String indexName, String type, List<MG_COMMENT> list) throws Exception {
        List<UpdateRequest> requests = new ArrayList<UpdateRequest>();
        Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), transportClientPort));
        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
            for (MG_COMMENT mg_comment : list) {
//                String json = mapper.writeValueAsString(mg_comment);
//                //   String json = mg_comment.toJsonString();
//                log.info(mg_comment.toString());
//                log.info(json);

                UpdateRequest request = client.prepareUpdate(indexName, type, mg_comment.getComment_id().toString())
                        .setDoc("{\"status\": \"" + mg_comment.getHandle_result() + "\"}").request();
                requests.add(request);
            }

            if (requests.size() > 0) {
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                log.info(String.valueOf(requests.size()));
                for (UpdateRequest request : requests) {
                    bulkRequest.add(request);
                }
                BulkResponse bulkResponse = bulkRequest.setRefresh(true).execute().actionGet();
                if (bulkResponse.hasFailures()) {
                    throw new ELSWriteException();
                }
                bulkRequest.request().requests().clear();
                log.info("ELS Write data successfully.");
            } else {
                log.info("No data for ELS");
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
            client.close();
        }
    }

    public SearchHits searcherUser(String elsIndex, String type, String searchType, String keyWord, Date uDate, Integer offset, Integer limit) throws Exception {
        log.info("searcher User");
        String QParse = "{\"query\": {\"filtered\": {\"query\": {\"";
        if (org.apache.commons.lang.StringUtils.isNotBlank(keyWord)) {
            if (searchType.equals("email")) {
                QParse = QParse + "match\": {\"email\": \"" + keyWord + "\"}";
            } else {
                QParse = QParse + "match\": {\"name\": \"" + keyWord + "\"}";
            }
        } else {
            QParse = QParse + "match_all\": {}";
        }
        QParse = QParse + "}";
        if (uDate != null) {
            QParse = QParse + ",\"filter\": {\"range\": {\"udate\": {";
            QParse = QParse + "\"gte\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(uDate) + "\"";
            QParse = QParse + "}}}";
        }
        QParse = QParse + "}},  \"sort\":[{\"_score\":{\"order\": \"desc\"}}]," +
                "\"from\": " + offset + "," +
                "\"size\": " + limit + "}";
        log.info(QParse);
        Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), transportClientPort));
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(elsIndex).setTypes(type).setSource(QParse);
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        log.info("select items =" + hits.getTotalHits());
        client.close();
        return hits;
    }

    public void ModifyUser(String indexName, String type, List<USER> list) throws Exception {
        List<UpdateRequest> requests = new ArrayList<UpdateRequest>();
        Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), transportClientPort));
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
            for (USER user : list) {
                String json = mapper.writeValueAsString(user);
//                //   String json = mg_comment.toJsonString();
//                log.info(mg_comment.toString());
//                log.info(json);

                UpdateRequest request = client.prepareUpdate(indexName, type, user.getUid().toString())
                        .setDoc(json).request();
                requests.add(request);
            }

            if (requests.size() > 0) {
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                log.info(String.valueOf(requests.size()));
                for (UpdateRequest request : requests) {
                    bulkRequest.add(request);
                }
                BulkResponse bulkResponse = bulkRequest.setRefresh(true).execute().actionGet();
                if (bulkResponse.hasFailures()) {
                    throw new ELSWriteException();
                }
                bulkRequest.request().requests().clear();
                log.info("ELS Write data successfully.");
            } else {
                log.info("No data for ELS");
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
            client.close();
        }
    }
}
