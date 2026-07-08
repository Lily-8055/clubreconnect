package com.clubreconnect.repository;

import com.clubreconnect.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByUserId(Long userId);
    List<Membership> findByClubId(Long clubId);
}
