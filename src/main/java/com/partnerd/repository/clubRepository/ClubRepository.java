package com.partnerd.repository.clubRepository;

import com.partnerd.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findTop4ClubByOrderByViewsDesc();
}