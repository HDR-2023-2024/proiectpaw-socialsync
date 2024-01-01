package com.socialsync.querymicroservice.interfaces;

import com.socialsync.querymicroservice.pojo.CommentQueueMessage;
import com.socialsync.querymicroservice.pojo.PostQueueMessage;
import com.socialsync.querymicroservice.pojo.TopicQueueMessage;
import com.socialsync.querymicroservice.pojo.UserQueueMessage;

public interface QueryServiceMethods {
    void handleTopic(TopicQueueMessage msgQ);
    void handlePost(PostQueueMessage msgQ);
    void handleComment(CommentQueueMessage msgQ);
    void handleUser(UserQueueMessage msgQ);
}
