package com.chjcfcaloc2020.fafi.service;

import com.chjcfcaloc2020.fafi.dto.EventDTO;
import com.chjcfcaloc2020.fafi.entity.Event;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceAlreadyExistsException;
import com.chjcfcaloc2020.fafi.exception.payload.ResourceNotFoundException;
import com.chjcfcaloc2020.fafi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event createEvent(EventDTO eventDTO) {
        if (eventRepository.existsByEventName(eventDTO.getEventName())) {
            throw new ResourceAlreadyExistsException("Event's name already exists");
        }
        Event event = Event.builder().eventName(eventDTO.getEventName()).build();
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID = " + id + " not found"));
    }

    public Event updateEvent(Long id, EventDTO eventDTO) {
        Event event = getEventById(id);
        event.setEventName(eventDTO.getEventName());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }
}
