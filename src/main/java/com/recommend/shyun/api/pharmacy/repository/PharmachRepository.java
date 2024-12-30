package com.recommend.shyun.api.pharmacy.repository;

import com.recommend.shyun.api.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmachRepository extends JpaRepository<Pharmacy, Long> {
}
