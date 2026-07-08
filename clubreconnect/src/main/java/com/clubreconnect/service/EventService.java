package com.clubreconnect.service;

import com.clubreconnect.entity.Event;
import com.clubreconnect.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getEventsForClub(Long clubId) {
        return eventRepository.findByClubId(clubId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
