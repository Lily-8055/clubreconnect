package com.clubreconnect.controller;

import com.clubreconnect.entity.Club;
import com.clubreconnect.entity.Event;
import com.clubreconnect.entity.Message;
import com.clubreconnect.entity.User;
import com.clubreconnect.service.ClubService;
import com.clubreconnect.service.EventService;
import com.clubreconnect.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/clubs")
public class ClubController {
    @Autowired
    private ClubService clubService;

    @Autowired
    private EventService eventService;

    @Autowired
    private MessageService messageService;

    @GetMapping
    public String listClubs(@RequestParam(required = false) String category, @RequestParam(required = false) String query, Model model) {
        model.addAttribute("clubs", clubService.searchClubs(category, query));
        model.addAttribute("category", category);
        model.addAttribute("query", query);
        return "clubs";
    }

    @GetMapping("/create")
    public String createClubPage(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        model.addAttribute("club", new Club());
        return "create-club";
    }

    @PostMapping("/create")
    public String createClub(@ModelAttribute Club club, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        clubService.createClub(club, user);
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}")
    public String clubDetail(@PathVariable Long id, Model model) {
        Club club = clubService.getClubById(id);
        model.addAttribute("club", club);
        model.addAttribute("events", eventService.getEventsForClub(id));
        model.addAttribute("messages", messageService.getMessagesForClub(id));
        return "club-detail";
    }

    @PostMapping("/{id}/join")
    public String joinClub(@PathVariable Long id, @RequestParam(defaultValue = "ACTIVE") String status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Club club = clubService.getClubById(id);
        clubService.joinClub(user, club, status);
        return "redirect:/clubs/" + id;
    }
    
    @PostMapping("/{id}/events")
    public String addEvent(@PathVariable Long id, @RequestParam String title, @RequestParam String description, @RequestParam String date, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        Club club = clubService.getClubById(id);
        Event event = Event.builder()
                .title(title)
                .description(description)
                .eventDate(LocalDateTime.parse(date))
                .club(club)
                .build();
        eventService.createEvent(event);
        return "redirect:/clubs/" + id;
    }

    @PostMapping("/{id}/messages")
    public String postMessage(@PathVariable Long id, @RequestParam String content, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        Club club = clubService.getClubById(id);
        Message message = Message.builder()
                .content(content)
                .sender(user)
                .club(club)
                .build();
        messageService.sendMessage(message);
        return "redirect:/clubs/" + id;
    }

    @GetMapping("/{id}/manage")
    public String manageClub(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Club club = clubService.getClubById(id);
        if (user == null || !club.getCreator().getId().equals(user.getId())) {
            return "redirect:/clubs/" + id;
        }
        model.addAttribute("club", club);
        model.addAttribute("pendingMembers", clubService.getPendingMemberships(id));
        return "admin-dashboard";
    }

    @PostMapping("/membership/{membershipId}/update")
    public String updateMembership(@PathVariable Long membershipId, @RequestParam String status, @RequestParam Long clubId) {
        clubService.updateMembershipStatus(membershipId, status);
        return "redirect:/clubs/" + clubId + "/manage";
    }
}
