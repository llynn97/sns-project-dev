package hello.sns.repository;

import hello.sns.model.entity.AlarmEntity;
import hello.sns.model.entity.LikeEntity;
import hello.sns.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.jar.JarEntry;

@Repository
public interface AlarmEntityRepository extends JpaRepository<AlarmEntity,Integer> {

    Page<AlarmEntity> findAllByUserId(Integer userId, Pageable pageable);
}
