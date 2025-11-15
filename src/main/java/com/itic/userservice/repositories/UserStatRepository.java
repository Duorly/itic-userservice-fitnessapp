package com.itic.userservice.repositories;

import com.itic.userservice.entities.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatRepository extends JpaRepository<UserStat, Long> {
}
