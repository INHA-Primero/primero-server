package inha.primero_server.domain.inquiry.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
    @OneToMany(mappedBy = "user")
    private List<Inquiry> inquirys = new ArrayList<>();
    */
}
