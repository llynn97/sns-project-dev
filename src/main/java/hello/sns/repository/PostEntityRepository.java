package hello.sns.repository;

import hello.sns.model.Post;
import hello.sns.model.entity.PostEntity;
import hello.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity,Integer> {

    Page<PostEntity> findAllByUser(UserEntity entity, Pageable pageable);




}
