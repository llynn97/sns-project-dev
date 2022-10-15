package hello.sns.controller.response;

import hello.sns.model.Comment;
import hello.sns.model.Post;
import hello.sns.model.entity.CommentEntity;
import hello.sns.model.entity.PostEntity;
import hello.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponse {

    private Integer id;
    private String comment;
    private String userName;
    private Integer postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static CommentResponse fromComment(Comment comment){
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUserName(),
                comment.getPostId(),
                comment.getRegisteredAt(),
                comment.getUpdatedAt(),
                comment.getDeletedAt()
        );
    }
}
