package com.itic.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_stats")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId // This makes UserStat use the same PK as User
    @JoinColumn(name = "id") // FK column that's also the PK
    private User user;
}
