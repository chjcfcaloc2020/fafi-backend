package com.chjcfcaloc2020.fafi.repository;

import com.chjcfcaloc2020.fafi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByEventName(String name);
}
