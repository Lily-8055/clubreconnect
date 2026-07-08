package com.clubreconnect.repository;

import com.clubreconnect.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByCategory(String category);
    List<Club> findByNameContainingIgnoreCase(String name);
    List<Club> findByCategoryAndNameContainingIgnoreCase(String category, String name);
}
