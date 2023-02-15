package com.airbyte.charity.comment;

import com.airbyte.charity.dto.CommentDTO;
import com.airbyte.charity.model.Comment;
import org.springframework.stereotype.Component;

import static com.airbyte.charity.CommonTestData.*;

@Component
public class CommentDataProvider {
    public CommentDTO createEntity() {
        CommentDTO comment = new CommentDTO();
        comment.setSenderName(DEFAULT_STRING);
        comment.setText(DEFAULT_STRING);
        comment.setLikeCount(DEFAULT_LONG);
        comment.setDisLikeCount(DEFAULT_LONG);
        comment.setProjectId(DEFAULT_STRING);
        return comment;
    }

    public CommentDTO updateEntity() {
        CommentDTO comment = new CommentDTO();
        comment.setSenderName(UPDATED_STRING);
        comment.setText(UPDATED_STRING);
        comment.setLikeCount(UPDATED_LONG);
        comment.setDisLikeCount(UPDATED_LONG);
        comment.setProjectId(UPDATED_STRING);
        return comment;
    }
}
