package com.itic.userservice.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_stats")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "total_points")
    private Long totalPoints;

    @Column(name = "level")
    private Integer level;

    @Column(name = "next_level_points")
    private Long nextLevelPoints;

    @Column(name = "total_challenges_completed")
    private Integer totalChallengesCompleted;

    @Column(name = "total_distance")
    private Long totalDistance;

    @Column(name = "total_calories")
    private Long totalCalories;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
