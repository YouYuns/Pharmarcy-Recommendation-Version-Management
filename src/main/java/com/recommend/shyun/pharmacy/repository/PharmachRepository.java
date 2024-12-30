package com.recommend.shyun.pharmacy.repository;

import com.recommend.shyun.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmachRepository extends JpaRepository<Pharmacy, Long> {
}
