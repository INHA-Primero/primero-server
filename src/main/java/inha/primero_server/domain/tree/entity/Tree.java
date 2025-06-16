package inha.primero_server.domain.tree.entity;

import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tree_tb")
public class Tree extends BaseEntity {

    @Id
    @Column(name = "tree_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Setter
    private String photoPath;

    @Column
    @Setter
    private String pinColor = "#4CAF50";

    @Column
    private double latitude; //위도

    @Column
    private double longitude; //경도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private User user;

    @Builder
    public Tree(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateTree(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }
}
