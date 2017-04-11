package com.allets.backend.data.server.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by jhyun on 2014-05-22.
 */
public class AwsUploadJob implements Runnable {
    private static Log LOG = LogFactory.getLog(AwsUploadJob.class);

    private AwsUpload awsUpload;

    private List<Pair<String, File>> uploads;

    private String remoteName;

    private InputStream inputStream;

    private Long contentLength;

    public AwsUploadJob(AwsUpload awsUpload, List<Pair<String, File>> uploads) {
        this.awsUpload = awsUpload;
        this.uploads = uploads;
    }

    public AwsUploadJob(AwsUpload awsUpload, String remoteName, InputStream inputStream,Long contentLength) {
        this.awsUpload = awsUpload;
        this.remoteName = remoteName;
        this.inputStream = inputStream;
        this.contentLength = contentLength;
    }

    @Override
    public void run() {
        try {
            if(remoteName!=null)
            {
                System.err.println("fileKey:"+remoteName);
                final int awsUploadSucceeds = awsUpload.upload(remoteName,inputStream,contentLength);
                if (awsUploadSucceeds != 1) {
                    LOG.warn(String.format("SOME AWS-UPLOADS FAILED! succeeds=[%s/%s]", awsUploadSucceeds, remoteName));
                }
            }else {
                System.err.println("fileKey:" + awsUpload.getFileUploadFileName());
                final int awsUploadSucceeds = awsUpload.upload(uploads);
                if (awsUploadSucceeds != uploads.size()) {
                    LOG.warn(String.format("SOME AWS-UPLOADS FAILED! succeeds=[%s/%s]", awsUploadSucceeds, uploads.size()));
                }
            }
        } catch (Exception exc) {
            LOG.error("AWS-UPLOADS FAILED!", exc);
        }
    }
}
