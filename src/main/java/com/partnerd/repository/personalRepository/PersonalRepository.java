package com.partnerd.repository.personalRepository;

import com.partnerd.domain.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
}
