package hello.sns.model;

import hello.sns.model.entity.CommentEntity;
import hello.sns.model.entity.PostEntity;
import hello.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {

    private Integer id;
    private String userName;
    private String comment;
    private Integer postId;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getUser().getUserName(),
                entity.getComment(),
                entity.getPost().getId(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }
}
