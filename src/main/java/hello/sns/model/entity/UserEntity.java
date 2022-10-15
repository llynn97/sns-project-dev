package hello.sns.model.entity;

import hello.sns.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@SQLDelete(sql="UPDATE \"user\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role=UserRole.USER;

    @Column(name = "register_at")
    private Timestamp registeredAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(name="deleted_at")
    private Timestamp deletedAt;

    @PrePersist //영속되는 시점 이전에 실행
    void registeredAt(){
        this.registeredAt=Timestamp.from(Instant.now());
    }

    @PreUpdate // 영속된 상태에서 데이터가 업데이트 되기 전에 실행
    void updatedAt(){
        this.updatedAt=Timestamp.from(Instant.now());
    }

    public static UserEntity of(String userName,String password){
        UserEntity userEntity=new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }
}
