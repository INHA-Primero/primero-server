package inha.primero_server.domain.user.entity;

import inha.primero_server.domain.inquiry.entity.Inquiry;
import inha.primero_server.domain.tree.entity.Tree;
import inha.primero_server.global.common.entity.Role;
import inha.primero_server.global.common.entity.Status;
import inha.primero_server.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_tb")
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private Integer studentNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String treeName;

    @Column(nullable = false, unique = true)
    private String deviceUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private String profileImgPath = "/default/profile.png";

    @Column(nullable = false)
    private Integer totalPoint = 0;

    @OneToMany(mappedBy = "user")
    private List<Inquiry> inquiryList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Tree> treeList = new ArrayList<>();

    public static User create(String email, String name, Integer studentNumber, String treeName, String password, String deviceUuid) {
        User user = new User();
        user.email = email;
        user.name = name;
        user.studentNumber = studentNumber;
        user.treeName = treeName;
        user.password = password;
        user.deviceUuid = deviceUuid;
        user.role = Role.USER;
        user.status = Status.ACTIVE;
        return user;
    }

    public void updateInfo(String treeName, String password, String imageUrl) {
        this.treeName = treeName;
        this.password = password;
        this.profileImgPath = imageUrl;
    }

    public void delete() {
        this.status = Status.DELETE;
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public void addTotalPoint(Integer points) {
        this.totalPoint += points;
    }
}