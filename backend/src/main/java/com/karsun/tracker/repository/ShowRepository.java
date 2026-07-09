package com.karsun.tracker.repository;

import com.karsun.tracker.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}
