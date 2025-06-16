package inha.primero_server.domain.bin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bin_tb")
public class Bin {
    @Id
    @Column(name = "bin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private double residualCapacity;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private LocalDateTime installedDate;
} 