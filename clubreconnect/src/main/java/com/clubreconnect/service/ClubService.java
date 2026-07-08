package com.clubreconnect.service;

import com.clubreconnect.entity.Club;
import com.clubreconnect.entity.Membership;
import com.clubreconnect.entity.User;
import com.clubreconnect.repository.ClubRepository;
import com.clubreconnect.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClubService {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    public Club createClub(Club club, User creator) {
        club.setCreator(creator);
        Club savedClub = clubRepository.save(club);
        
        // Auto-join creator as ACTIVE
        joinClub(creator, savedClub, "ACTIVE");
        
        return savedClub;
    }

    public Membership joinClub(User user, Club club, String status) {
        Membership membership = Membership.builder()
                .user(user)
                .club(club)
                .status(status)
                .build();
        return membershipRepository.save(membership);
    }

    public List<Membership> getPendingMemberships(Long clubId) {
        return membershipRepository.findByClubId(clubId).stream()
                .filter(m -> "PENDING".equals(m.getStatus()))
                .toList();
    }

    public void updateMembershipStatus(Long membershipId, String status) {
        membershipRepository.findById(membershipId).ifPresent(m -> {
            m.setStatus(status);
            membershipRepository.save(m);
        });
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public List<Club> searchClubs(String category, String query) {
        if ((category == null || category.isEmpty() || category.equals("All")) && (query == null || query.isEmpty())) {
            return clubRepository.findAll();
        } else if (category == null || category.isEmpty() || category.equals("All")) {
            return clubRepository.findByNameContainingIgnoreCase(query);
        } else if (query == null || query.isEmpty()) {
            return clubRepository.findByCategory(category);
        } else {
            return clubRepository.findByCategoryAndNameContainingIgnoreCase(category, query);
        }
    }

    public Club getClubById(Long id) {
        return clubRepository.findById(id).orElse(null);
    }
}
