package com.recommend.shyun.direction.repository;

import com.recommend.shyun.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
