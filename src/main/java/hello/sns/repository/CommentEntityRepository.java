package hello.sns.repository;

import hello.sns.model.entity.CommentEntity;
import hello.sns.model.entity.LikeEntity;
import hello.sns.model.entity.PostEntity;
import hello.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {

    Page<CommentEntity> findAllByPost(PostEntity post, Pageable pageable);
    @Transactional
    @Modifying
    @Query("UPDATE CommentEntity entity SET deletedAt=NOW() where entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity postEntity);



}
