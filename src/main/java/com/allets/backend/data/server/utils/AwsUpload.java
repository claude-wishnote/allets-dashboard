package com.allets.backend.data.server.utils;

import com.allets.backend.data.server.consts.Const;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class AwsUpload {

    private static Log LOG = LogFactory.getLog(AwsUpload.class);

    String awsAccessKey;
    String awsSecretKey;
    String awsBucketName;
    String fileUploadDir;
    String fileUploadFileName;

    public AwsUpload(String awsAccessKey, String awsSecretKey, String awsBucketName) {
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
        this.awsBucketName = awsBucketName;
    }

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public void setAwsSecretKey(String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
    }

    public String getAwsBucketName() {
        return awsBucketName;
    }

    public void setAwsBucketName(String awsBucketName) {
        this.awsBucketName = awsBucketName;
    }

    public String getFileUploadDir() {
        return fileUploadDir;
    }

    public void setFileUploadDir(String fileUploadDir) {
        this.fileUploadDir = fileUploadDir;
    }

    public String getFileUploadFileName() {
        return fileUploadFileName;
    }

    public void setFileUploadFileName(String fileUploadFileName) {
        this.fileUploadFileName = fileUploadFileName;
    }

    private AmazonS3Client initialize() {
        AWSCredentials cred = new BasicAWSCredentials(this.getAwsAccessKey(), this.getAwsSecretKey());
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSocketTimeout(20 * 1000);
        clientConfiguration.setConnectionTimeout(20 * 1000);
        return new AmazonS3Client(cred, clientConfiguration);
    }

    private int uploadInputStream(AmazonS3Client awsClient, String remoteName, InputStream inputStream,Long contentLength) throws IOException {
        int succeeds = 0;

        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(contentLength);
            System.err.println(String.format("ATTEMPT AWS-UPLOAD [%s] -> [%s]", "InputStream", remoteName));
            PutObjectRequest putObjectRequest = new PutObjectRequest(this.getAwsBucketName(), remoteName, inputStream, meta);
            PutObjectResult putObjectResult = awsClient.putObject(putObjectRequest);
            System.err.println(String.format("ATTEMPT AWS-UPLOAD [%s] -> [%s]", "contentLength", contentLength));

            if (putObjectResult != null) {
                System.err.println(String.format("AWS-UPLOAD STOR OK ([%s] -> [%s])", "InputStream", remoteName));
                succeeds++;
//                doPurgeCdn(remoteName);
            } else {
                LOG.warn(String.format("AWS-UPLOAD STOR FAIL ([%s] -> [%s])", "InputStream", remoteName));
            }
        } catch (AmazonServiceException e) {
            LOG.error(String.format("LOCAL-FILE-READ -or- AWS-UPLOAD FAIL! [%s] -> [%s], SKIP!", "InputStream", remoteName), e);
        } catch (AmazonClientException e) {
            LOG.error(String.format("LOCAL-FILE-READ -or- AWS-UPLOAD FAIL! [%s] -> [%s], SKIP!", "InputStream", remoteName), e);
        }
        return succeeds;
    }

    private int upload(AmazonS3Client awsClient, List<Pair<String, File>> uploads) {
        int succeeds = 0;
        for (Pair<String, File> p : uploads) {
            final String remoteName = p.getLeft();
            final File filePath = p.getRight();
            try {
                System.err.println(String.format("ATTEMPT AWS-UPLOAD [%s] -> [%s]", filePath, remoteName));

                PutObjectRequest putObjectRequest = new PutObjectRequest(this.getAwsBucketName(), remoteName, filePath);
                PutObjectResult putObjectResult = awsClient.putObject(putObjectRequest);

                if (putObjectResult != null) {
                    System.err.println(String.format("AWS-UPLOAD STOR OK ([%s] -> [%s])", filePath, remoteName));
                    succeeds++;
//                     doPurgeCdn(remoteName);
                } else {
                    LOG.warn(String.format("AWS-UPLOAD STOR FAIL ([%s] -> [%s])", filePath, remoteName));
                }
            } catch (AmazonServiceException e) {
                LOG.error(String.format("LOCAL-FILE-READ -or- AWS-UPLOAD FAIL! [%s] -> [%s], SKIP!", filePath, remoteName), e);
            } catch (AmazonClientException e) {
                LOG.error(String.format("LOCAL-FILE-READ -or- AWS-UPLOAD FAIL! [%s] -> [%s], SKIP!", filePath, remoteName), e);
            }
        }
        return succeeds;
    }

    private void delete(AmazonS3Client awsClient, List<String> keys) {
        for (String remoteName : keys) {
            try {
                System.err.println(String.format("ATTEMPT AWS-DELETE [%s]", remoteName));
                awsClient.deleteObject(this.getAwsBucketName(), remoteName);
            } catch (Exception e) {
                LOG.error(String.format("AWS-DELETE FAIL! [%s], SKIP!", remoteName), e);
            }
        }
    }
    public int upload(String remoteName, InputStream inputStream,Long contentLength) throws IOException {
        AmazonS3Client awsClient = null;
        try {
            awsClient = this.initialize();
        } catch (AmazonClientException e) {
            LOG.error("AWS-CLIENT CONNECT FAIL!", e);
            return 0;
        }
        if (awsClient != null) {
            return this.uploadInputStream(awsClient, remoteName,inputStream,contentLength);
        } else {
            return 0;
        }
    }
    public int upload(List<Pair<String, File>> uploads) {
        AmazonS3Client awsClient = null;
        try {
            awsClient = this.initialize();
        } catch (AmazonClientException e) {
            LOG.error("AWS-CLIENT CONNECT FAIL!", e);
            return 0;
        }
        if (awsClient != null) {
            return this.upload(awsClient, uploads);
        } else {
            return 0;
        }
    }

    public void delete(List<String> keys) {
        AmazonS3Client awsClient = null;
        try {
            awsClient = this.initialize();
        } catch (AmazonClientException e) {
            LOG.error("AWS-CLIENT CONNECT FAIL!", e);
            return;
        }
        if (awsClient != null) {
            this.delete(awsClient, keys);
        }
    }

    public void doPurgeCdn(String fileKey) {

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(Const.CDN_PURGE.CDN_PURGE_USER, Const.CDN_PURGE.CDN_PURGE_PASS);
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        System.err.println("fileKey:"+fileKey);
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("type", Const.CDN_PURGE.CDN_PURGE_TYPE);
            jsonObj.put("action", Const.CDN_PURGE.CDN_PURGE_ACTION);
            jsonObj.put("domain", Const.CDN_PURGE.CDN_PURGE_DOMAIN);  //
            JSONArray objects = new JSONArray();
            objects.add(Const.CDN_PURGE.CDN_PURGE_PAD + "/" + fileKey);
            jsonObj.put("objects", objects);

            URI uri = new URI(Const.CDN_PURGE.CDN_PURGE_URL);

            HttpPost httppost = new HttpPost(uri);
            httppost.setHeader("Content-Type", "application/json");
            httppost.setEntity(new StringEntity(jsonObj.toString(), Consts.UTF_8));
            HttpResponse response = httpclient.execute(httppost);
            int status = response.getStatusLine().getStatusCode();

            if (status != 201) {
                String result = EntityUtils.toString(response.getEntity());
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
                if (jsonObject != null) {
                    JsonElement je = jsonObject.get("resultCode");

                    LOG.warn("Can not purge profile image " + fileKey + ", get response code : "
                            + jsonObject.get("httpStatus").getAsString() + ", " + jsonObject.get("title").getAsString() + ", " + jsonObject.get("detail").getAsString());

                }
            }
            System.err.println(EntityUtils.toString(response.getEntity()).toString());
        } catch (SocketTimeoutException e) {
            LOG.warn(Const.CDN_PURGE.CDN_PURGE_URL + " socket timeout, can not purge profile image", e);
        } catch (ConnectTimeoutException e) {
            LOG.warn(Const.CDN_PURGE.CDN_PURGE_URL + " connect timeout, can not purge profile image", e);
        } catch (ClientProtocolException e) {
            LOG.warn(Const.CDN_PURGE.CDN_PURGE_URL + " ClientProtocolException is occurred, can not purge profile image", e);
        } catch (IOException e) {
            LOG.warn(Const.CDN_PURGE.CDN_PURGE_URL + " IOException is occurred, can not purge profile image", e);
        } catch (URISyntaxException e) {
            LOG.warn(Const.CDN_PURGE.CDN_PURGE_URL + " connect timeout, can not purge profile image", e);
        }
    }
}
