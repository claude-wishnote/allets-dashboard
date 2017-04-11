package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.data.result.CommentResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by claude on 2015/8/22.
 */
@Service
public class MockDataProvider {

    private List<CommentResult> commentFrontList;

    public List<CommentResult> initCommentFrontList() throws Exception
    {
        if(commentFrontList==null) {
            Random random = new Random();
            commentFrontList = new ArrayList<CommentResult>();
            for (int i = 0; i < 286; i++) {
                CommentResult comment = new CommentResult();
                comment.setCommentId(Long.valueOf(0));
                comment.setcDate(new Date());
                comment.setReportCount(random.nextInt(1000));
                comment.setReportType("B");
                comment.setCommentText(random.nextInt(1000) + "test");
                comment.setLikeCount(random.nextInt(1000));
                comment.setNickName(random.nextInt(1000) + "jack");

                comment.setSlangType("A");
                commentFrontList.add(comment);
            }
        }
        return  commentFrontList;
    }

    public Long getCommentFrontTotalSize() throws Exception
    {
         return Long.valueOf(initCommentFrontList().size());
    }

    public List<CommentResult> getCommentFrontList(int offset,int limit) throws Exception  {
         if(offset>initCommentFrontList().size())
        {
            return null;
        }
        if(offset<=initCommentFrontList().size()&&(offset+limit)>initCommentFrontList().size())
        {
            limit = initCommentFrontList().size()-offset;
        }
        return initCommentFrontList().subList(offset,offset+limit);
    }
}
