package com.clubreconnect.repository;

import com.clubreconnect.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByClubIdOrderByTimestampAsc(Long clubId);
}
